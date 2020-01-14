package am.petstore.PetStore.user.service

import am.petstore.PetStore.Utils
import am.petstore.PetStore.user.dao.DeviceDao
import am.petstore.PetStore.user.dao.UserDao
import am.petstore.PetStore.user.entity.Device
import am.petstore.PetStore.user.entity.Role
import am.petstore.PetStore.user.entity.UserModel
import am.petstore.PetStore.user.exceptions.FileStorageException
import am.petstore.PetStore.user.jwt.JwtTokenProvider
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.util.*
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.validation.Validator

@Service
@Transactional
class UserService @Autowired constructor(private val userDao: UserDao, private val mapper: ObjectMapper, private val bCryptPasswordEncoder: BCryptPasswordEncoder,
                                         private val validator: Validator, private val deviceDao: DeviceDao, private val fileStorageService: FileStorageService, @field:PersistenceContext private val manager: EntityManager) : UserDetailsService {
    private val responseData: MutableMap<Any, Any> = HashMap()
    private val model: MutableMap<Any, Any?> = HashMap()

    fun registerPhoneNumber(node: JsonNode): ResponseEntity<*> {
        return try {
            val phone = node["data"]["phone"].asText("-1")
            LoggerFactory.getLogger("registerPhoneNumber").info(phone)
            val user = userDao.findByPhone(phone)
            if (user != null && user.email != null && user.email != "") {
                badRequestResponse("$phone is registered. Please sign in or register with other phone number.")
            } else if (user != null && user.phone != null) {
                val newUser = userDao.findByPhone(phone)
                responseData.clear()
                model.clear()
                val userResponse: MutableMap<Any, Any?> = HashMap()
                userResponse["id"] = newUser.id
                userResponse["phone"] = newUser.phone
                model["code"] = 200
                model["message"] = "Phone number is registered"
                model["user"] = userResponse
                responseData["data"] = model
                ResponseEntity.ok<Map<Any, Any>>(responseData)
            } else {
                val roles: MutableSet<Role> = HashSet()
                roles.add(Role.USER)
                if (phone == "+37493876378") {
                    roles.add(Role.ADMIN)
                }
                var newUser = UserModel()
                newUser.phone = phone
                newUser.roles = roles
                userDao.saveAndFlush(newUser)
                newUser = userDao.findByPhone(phone)
                responseData.clear()
                model.clear()
                val userResponse: MutableMap<Any, Any?> = HashMap()
                userResponse["id"] = newUser.id
                userResponse["phone"] = newUser.phone
                model["code"] = 200
                model["message"] = "Phone number is registered"
                model["user"] = userResponse
                responseData["data"] = model
                ResponseEntity.ok<Map<Any, Any>>(responseData)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            badRequestResponse("Invalid data")
        }
    }

    fun signUpUser(photo: MultipartFile?,
                   id: String,
                   phone: String?,
                   firebase_id: String?,
                   fullname: String?,
                   email: String?,
                   gender: String?,
                   birthday: String?,
                   device_id: String?,
                   password: String?): ResponseEntity<*> {
        return try {
            var fileDownloadUri = ""
            if (photo != null) {
                LoggerFactory.getLogger("updateUserPhoto  ").info(null)
                val fileName = fileStorageService.storeFile(photo)
                LoggerFactory.getLogger("updateUser").info(fileName)
                fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("user/downloadFile/")
                        .path(fileName!!)
                        .toUriString()
            }
            if (!userDao.existsById(id.toLong())) {
                badRequestResponse("Invalid user id")
            } else {
                val user = userDao.getOne(id.toLong())
                LoggerFactory.getLogger("updateUser").info(user.toString())
                LoggerFactory.getLogger("updateUser").info(device_id)
                val device = deviceDao.findByUid(device_id)
                var devices: MutableSet<Device?>? = HashSet()
                if (user.devices != null) {
                    devices = user.devices
                }
                devices!!.removeIf { device1: Device? -> device1?.uid == device?.uid }
                devices.add(device)
                userDao.updateUserInfo(id.toLong(), fullname, bCryptPasswordEncoder.encode(password),
                        email, birthday, gender, firebase_id, Date(), fileDownloadUri)
                val newUser = userDao.getOne(id.toLong())
                newUser.devices = devices
                manager.merge(newUser)
                responseData.clear()
                model.clear()
                model["code"] = 200
                model["message"] = "You are successfully registered"
                model["user"] = newUser
                responseData["data"] = model
                ResponseEntity.ok<Map<Any, Any>>(responseData)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            badRequestResponse("Invalid data")
        }
    }

    fun updateUser(jsonNode: JsonNode): ResponseEntity<*> {
        val data = jsonNode["data"]
        val phone = data["phone"].asText()
        val newPhone = data["new_phone"].asText()
        var user = userDao.findByPhone(phone)
        val checkUser = userDao.findByPhone(newPhone)
        if (checkUser != null) {
            return badRequestResponse("$phone is registered. Please sign in or register with other phone number.")
        }
        userDao.updateUserPhone(user.id, newPhone, Date())
        user = userDao.findByPhone(newPhone)
        responseData.clear()
        model.clear()
        model["code"] = 200
        model["message"] = "Success"
        model["user"] = user
        responseData["data"] = model
        return ResponseEntity.ok<Map<Any, Any>>(responseData)
    }

    fun signIn(jsonNode: JsonNode, jwtTokenProvider: JwtTokenProvider,
               authenticationManager: AuthProvider): ResponseEntity<*> {
        val data = jsonNode["data"]
        val device_id = data["device_id"].asText()
        val phone = data["phone"].asText()
        val password = data["password"].asText()
        try {
            authenticationManager.authenticate(UsernamePasswordAuthenticationToken(phone, password))
        } catch (e: BadCredentialsException) {
            Utils.showErrorLog(this.javaClass.name, e.message)
            return badRequestResponse(e.message)
        }
        val token = jwtTokenProvider.createToken(userDao.findByPhone(phone).phone
                , userDao.findByPhone(phone).authorities)
        val user = userDao.findByPhone(phone)
        val device = deviceDao.findByUid(device_id)
        LoggerFactory.getLogger("devices").info(device.toString())
        val devices = user.devices
        for (device1 in user.devices!!) {
            if (device1?.uid == device?.uid) {
                devices!!.remove(device1)
            }
        }
        devices!!.add(device)
        LoggerFactory.getLogger("devices").info(devices.toString())
        user.devices = devices
        manager.merge(user)
        responseData.clear()
        model.clear()
        model["code"] = 200
        model["message"] = "Success"
        model["user"] = user
        model["token"] = token
        responseData["data"] = model
        return ResponseEntity.ok<Map<Any, Any>>(responseData)
    }

    fun deleteUser(id: Long?): ResponseEntity<*>? {
        return null
    }

    fun signOut(id: Long, jsonNode: JsonNode): ResponseEntity<*> {
        return try {
            val data = jsonNode["data"]
            val device_id = data["device_id"].asText()
            val device = deviceDao.findByUid(device_id)
            val user = userDao.getOne(id)
            val devices = user.devices
            for (device1 in user.devices!!) {
                if (device1?.uid == device?.uid) {
                    devices?.remove(device1)
                }
            }
            manager.merge(user)
            responseData.clear()
            model.clear()
            model["code"] = 200
            model["message"] = "User with id $id is sign out."
            responseData["data"] = model
            ResponseEntity.ok<Map<Any, Any>>(responseData)
        } catch (e: Exception) {
            badRequestResponse("Invalid data")
        }
    }

    private fun badRequestResponse(message: String?): ResponseEntity<Map<Any, Any>> {
        responseData.clear()
        model.clear()
        model["code"] = 400
        model["message"] = message
        responseData["data"] = model
        return ResponseEntity.badRequest().body(responseData)
    }

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(phone: String): UserDetails {
        val user: UserDetails? = userDao.findByPhone(phone)
        return user!!
    }

    fun downloadImage(fileName: String): ResponseEntity<*> {
        val resource = fileStorageService.loadFileAsResource(fileName)
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource!!.filename + "\"")
                .body(resource)
    }

    fun uploadImage(photo: MultipartFile?): ResponseEntity<*> {
        return try {
            var fileDownloadUri = ""
            if (photo != null) {
                val fileName = fileStorageService.storeFile(photo)
                LoggerFactory.getLogger("updateUser").info(fileName)
                fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("user/downloadFile/")
                        .path(fileName!!)
                        .toUriString()
            }
            responseData.clear()
            model.clear()
            model["code"] = 200
            model["message"] = "Success"
            model["photo"] = fileDownloadUri
            responseData["data"] = model
            ResponseEntity.ok<Map<Any, Any>>(responseData)
        } catch (f: FileStorageException) {
            badRequestResponse(f.message)
        } catch (e: Exception) {
            badRequestResponse("Something went wrong")
        }
    }

}