package am.petstore.petstore.user.service

import am.petstore.petstore.user.dao.DeviceDao
import am.petstore.petstore.user.entity.Device
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*
import javax.persistence.EntityManager
import javax.persistence.EntityManagerFactory

@Service
@Transactional
class DeviceService @Autowired constructor(private val deviceDao: DeviceDao, private val mapper: ObjectMapper, private val entityManagerFactory: EntityManagerFactory) {
    private val log = LoggerFactory.getLogger(this.javaClass.name)
    private val data: MutableMap<Any, Any> = HashMap()
    private val model: MutableMap<Any, Any?> = HashMap()

    private lateinit var entityManager: EntityManager

    suspend fun addOrUpdateDevice(jsonNode: JsonNode): ResponseEntity<Map<Any, Any>> {
        return try {
            log.info(jsonNode.toString())
            var device = mapper.readValue(jsonNode["data"].toString(), Device::class.java)
            val uid = device.uid
            val installDate = device.firstInstallDate
            log.info(device.toString())
            if (deviceDao.existsByUid(uid)) {
                withContext(Dispatchers.Default) {
                    deviceDao.updateDeviceByUid(uid, device.appVersion, device.firstInstallDate, device.language,
                            device.model, device.platform, device.sdkVersion, device.firebaseToken, Date())
                }
                device = withContext(Dispatchers.Default) { deviceDao.findByUid(uid) }
                LoggerFactory.getLogger("UpdateDevice").info(device.toString())
                val installDates = device.installDates
                var a = false
                for (install in installDates!!) {
                    if (install == installDate) {
                        a = true
                        break
                    }
                }
                if (!a) {
                    installDates.add(installDate)
                }
                device.installDates = installDates
                withContext(Dispatchers.Default) {
                    entityManager = entityManagerFactory.createEntityManager()
                    entityManager.transaction.begin()
                    entityManager.merge(device)
                    entityManager.transaction.commit()
                }
                addOrUpdateDeviceOkResponse(device, "Updated")
            } else {
                val installDates: MutableSet<String?> = HashSet()
                installDates.add(installDate)
                device.installDates = installDates
                withContext(Dispatchers.Default) { deviceDao.saveAndFlush(device!!) }
                addOrUpdateDeviceOkResponse(device, "Created")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            badRequestResponse("Invalid data")
        }
    }

    suspend fun setDeviceFree(jsonNode: JsonNode): ResponseEntity<Map<Any, Any>> {
        return try {
            val uid = jsonNode["data"]["uid"].asText()
            val device = withContext(Dispatchers.Default) { deviceDao.findByUid(uid) }
            device?.user = null
            withContext(Dispatchers.Default) {
                entityManager = entityManagerFactory.createEntityManager()
                entityManager.transaction.begin()
                entityManager.merge(device)
            }
            addOrUpdateDeviceOkResponse(device, "Success")
        } catch (e: Exception) {
            e.printStackTrace()
            badRequestResponse("Invalid data")
        }
    }

    private fun badRequestResponse(message: String): ResponseEntity<Map<Any, Any>> {
        data.clear()
        model.clear()
        model["code"] = 400
        model["message"] = message
        data["data"] = model
        return ResponseEntity.badRequest().body(data)
    }

    private suspend fun addOrUpdateDeviceOkResponse(device: Device?, message: String): ResponseEntity<Map<Any, Any>> {
        data.clear()
        model.clear()
        model["code"] = 200
        model["message"] = message
        model["device"] = withContext(Dispatchers.Default) { deviceDao.findByUid(device?.uid) }
        data["data"] = model
        return ResponseEntity.ok(data)
    }

}