package am.petstore.PetStore.user.service

import am.petstore.PetStore.user.dao.DeviceDao
import am.petstore.PetStore.user.entity.Device
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Service
@Transactional
class DeviceService @Autowired constructor(private val deviceDao: DeviceDao, private val mapper: ObjectMapper) {
    private val log = LoggerFactory.getLogger(this.javaClass.name)
    private val data: MutableMap<Any, Any> = HashMap()
    private val model: MutableMap<Any, Any?> = HashMap()
    @PersistenceContext
    private val manager: EntityManager? = null

    fun addOrUpdateDevice(jsonNode: JsonNode): ResponseEntity<Map<Any, Any>> {
        return try {
            var device = mapper.readValue(jsonNode["data"].toString(), Device::class.java)
            val uid = device.uid
            val installDate = device.first_install_date
            log.info(device.toString())
            if (deviceDao.existsByUid(uid)) {
                deviceDao.updateDeviceByUid(uid, device.app_version, device.first_install_date, device.language,
                        device.model, device.platform, device.sdk_version, device.firebase_token, Date())
                device = deviceDao.findByUid(uid)
                LoggerFactory.getLogger("UpdateDevice").info(device.toString())
                val installDates = device.install_dates
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
                device.install_dates = installDates
                manager!!.merge(device)
                addOrUpdateDeviceOkResponse(device, "Updated")
            } else {
                val installDates: MutableSet<String?> = HashSet()
                installDates.add(installDate)
                device.install_dates = installDates
                deviceDao.saveAndFlush(device!!)
                addOrUpdateDeviceOkResponse(device, "Created")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            badRequestResponse("Invalid data")
        }
    }

    fun setDeviceFree(jsonNode: JsonNode): ResponseEntity<Map<Any, Any>> {
        return try {
            val uid = jsonNode["data"]["uid"].asText()
            val device = deviceDao.findByUid(uid)
            device?.user = null
            manager!!.merge(device)
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

    private fun addOrUpdateDeviceOkResponse(device: Device?, message: String): ResponseEntity<Map<Any, Any>> {
        data.clear()
        model.clear()
        model["code"] = 200
        model["message"] = message
        model["device"] = deviceDao.findByUid(device?.uid)
        data["data"] = model
        return ResponseEntity.ok(data)
    }

}