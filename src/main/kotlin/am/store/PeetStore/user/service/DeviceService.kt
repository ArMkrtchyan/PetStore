package am.store.PeetStore.user.service

import am.store.PeetStore.user.dao.DeviceDao
import am.store.PeetStore.user.entity.DeviceEntity
import am.store.PeetStore.user.model.Device
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.util.*
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.transaction.Transactional
import kotlin.collections.HashSet

@Service
@Transactional
class DeviceService {
    private val log = LoggerFactory.getLogger(this.javaClass.name)
    private val data: MutableMap<Any, Any> = HashMap()
    private val model: MutableMap<Any, Any> = HashMap()

    private var deviceDao: DeviceDao? = null
    private var mapper: ObjectMapper? = null

    @PersistenceContext
    private val manager: EntityManager? = null

    @Autowired
    fun DeviceService(deviceDao: DeviceDao?, mapper: ObjectMapper?) {
        this.deviceDao = deviceDao
        this.mapper = mapper
    }


    fun addOrUpdateDevice(jsonNode: JsonNode): ResponseEntity<Map<Any, Any>?>? {
        return try {
            var device: Device = mapper?.readValue(jsonNode["data"].toString(), Device::class.java)!!
            val uid: String = device.uid!!
            val installDate: String = device.firstInstallDate!!
            var deviceEntity: DeviceEntity
            log.info(device.toString())
            if (deviceDao?.existsByUid(uid)!!) {
                deviceDao?.updateDeviceByUid(uid, device.appVersion, device.language,
                        device.model, device.platform, device.sdkVersion, device.firebaseToken, Date())
                deviceEntity = deviceDao?.findByUid(uid)!!
                LoggerFactory.getLogger("UpdateDevice").info(device.toString())
                val installDates = deviceEntity.installDates!! as HashSet
                var a = false
                for (install in installDates) {
                    if (install == installDate) {
                        a = true
                        break
                    }
                }
                if (!a) {
                    installDates.add(installDate)
                }
                deviceEntity.installDates = installDates
                manager!!.merge<Any>(device)
                addOrUpdateDeviceOkResponse(deviceEntity, "Updated")
            } else {
                val installDates: MutableSet<String> = HashSet()
                installDates.add(installDate)
                deviceEntity = DeviceEntity(Date(), Date(), null, installDates, device.firebaseToken, device.deviceId, device.language, device.model, device.platform, device.sdkVersion, device.appVersion)
                deviceDao?.saveAndFlush(deviceEntity)
                addOrUpdateDeviceOkResponse(deviceEntity, "Created")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            badRequestResponse("Invalid data")
        }
    }

    fun setDeviceFree(jsonNode: JsonNode): ResponseEntity<Map<Any, Any>?>? {
        return try {
            val uid = jsonNode["data"]["uid"].asText()
            val device = deviceDao?.findByUid(uid)
            device?.user = null
            manager!!.merge<Any>(device)
            addOrUpdateDeviceOkResponse(device!!, "Success")
        } catch (e: Exception) {
            e.printStackTrace()
            badRequestResponse("Invalid data")
        }
    }

    private fun badRequestResponse(message: String): ResponseEntity<Map<Any, Any>?>? {
        data.clear()
        model.clear()
        model["code"] = 400
        model["message"] = message
        data["data"] = model
        return ResponseEntity.badRequest().body(data)
    }

    private fun addOrUpdateDeviceOkResponse(device: DeviceEntity, message: String): ResponseEntity<Map<Any, Any>?>? {
        data.clear()
        model.clear()
        model["code"] = 200
        model["message"] = message
        model["device"] = deviceDao?.findByUid(device.uid)!!
        data["data"] = model
        return ResponseEntity.ok(data)
    }
}