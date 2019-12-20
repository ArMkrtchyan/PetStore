package am.store.PeetStore.user.service

import am.store.PeetStore.user.AuthProvider
import am.store.PeetStore.user.dao.DeviceDao
import am.store.PeetStore.user.dao.UserDao
import am.store.PeetStore.user.entity.DeviceEntity
import am.store.PeetStore.user.entity.Role
import am.store.PeetStore.user.entity.UserEntity
import am.store.PeetStore.user.exception.FileStorageException
import am.store.PeetStore.user.jwt.JwtTokenProvider
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
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.util.*
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.transaction.Transactional
import javax.validation.Validator
import kotlin.collections.HashMap
import kotlin.collections.HashSet

@Service
@Transactional
class UserService @Autowired constructor(userDao: UserDao, mapper: ObjectMapper,  validator: Validator, deviceDao: DeviceDao, fileStorageService: FileStorageService, manager: EntityManager) : UserDetailsService {
    private val responseData: HashMap<Any, Any> = HashMap()
    private val model: HashMap<Any, Any> = HashMap()

    private var userDao: UserDao? = userDao
    private var mapper: ObjectMapper? = mapper
    private var validator: Validator? = validator
    private var deviceDao: DeviceDao? = deviceDao
    private var fileStorageService: FileStorageService? = fileStorageService
    @Autowired
    private var bCryptPasswordEncoder: BCryptPasswordEncoder? = null
    @PersistenceContext
    private var manager: EntityManager? = manager

    fun registerPhoneNumber(node: JsonNode): ResponseEntity<*>? {
        return try {
            val phone = node["data"]["phone"].asText("-1")
            LoggerFactory.getLogger("registerPhoneNumber").info(phone)
            val user: UserEntity = userDao?.findByPhone(phone)!!
            if (user.email != null && user.email!!.isNotEmpty()) {
                badRequestResponse("$phone is registered. Please sign in or register with other phone number.")
            } else if (user.phone != null) {
                val newUser: UserEntity = userDao?.findByPhone(phone)!!
                responseData.clear()
                model.clear()
                val userResponse: MutableMap<Any, Any> = HashMap()
                userResponse["id"] = newUser.id
                userResponse["phone"] = newUser.phone!!
                model["code"] = 200
                model["message"] = "Phone number is registered"
                model["user"] = userResponse
                responseData["data"] = model
                ResponseEntity.ok(responseData)
            } else {
                val roles: MutableSet<Role> = HashSet<Role>()
                roles.add(Role.USER)
                if (phone == "+37493876378") {
                    roles.add(Role.ADMIN)
                }
                var newUser = UserEntity()
                newUser.phone = phone
                newUser.roles = roles
                userDao!!.saveAndFlush(newUser)
                newUser = userDao?.findByPhone(phone)!!
                responseData.clear()
                model.clear()
                val userResponse: MutableMap<Any, Any> = HashMap()
                userResponse["id"] = newUser.id
                userResponse["phone"] = newUser.phone!!
                model["code"] = 200
                model["message"] = "Phone number is registered"
                model["user"] = userResponse
                model["data"] = model
                ResponseEntity.ok(responseData)
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
                   password: String?): ResponseEntity<*>? {
        return try {
            var fileDownloadUri = ""
            if (photo != null) {
                LoggerFactory.getLogger("updateUserPhoto  ").info(null)
                val fileName = fileStorageService!!.storeFile(photo)
                LoggerFactory.getLogger("updateUser").info(fileName)
                fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("user/downloadFile/")
                        .path(fileName!!)
                        .toUriString()
            }
            if (!userDao!!.existsById(id.toLong())) {
                badRequestResponse("Invalid user id")
            } else {
                val user: UserEntity = userDao!!.getOne(id.toLong())
                LoggerFactory.getLogger("updateUser").info(user.toString())
                LoggerFactory.getLogger("updateUser").info(device_id)
                val device: DeviceEntity? = deviceDao!!.findByUid(device_id)
                var devices: HashSet<DeviceEntity> = HashSet()
                user.devices?.let { devices = it as HashSet<DeviceEntity> }
                for (deviceIt in devices) {
                    when (deviceIt.uid) {
                        device?.uid -> {
                            devices.remove(device)
                        }
                    }
                }
                devices.add(device!!)
                userDao?.updateUserInfo(id.toLong(), fullname, bCryptPasswordEncoder!!.encode(password),
                        email, firebase_id, Date())
                val newUser: UserEntity = userDao!!.getOne(id.toLong())
                newUser.devices = devices
                manager!!.merge<Any>(newUser)
                responseData.clear()
                model.clear()
                model["code"] = 200
                model["message"] = "You are successfully registered"
                model["user"] = newUser
                model["data"] = model
                ResponseEntity.ok(responseData)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            badRequestResponse("Invalid data")
        }
    }


    fun updateUser(jsonNode: JsonNode): ResponseEntity<*>? {
        val data = jsonNode["data"]
        val phone = data["phone"].asText()
        val newPhone = data["new_phone"].asText()
        var user: UserEntity = userDao?.findByPhone(phone)!!
        val checkUser: UserEntity? = userDao?.findByPhone(newPhone)!!
        checkUser?.let {
            return badRequestResponse("$phone is registered. Please sign in or register with other phone number.")
        }
        userDao?.updateUserPhone(user.id, newPhone, Date())
        user = userDao?.findByPhone(newPhone)!!
        responseData.clear()
        model.clear()
        model["code"] = 200
        model["message"] = "Success"
        model["user"] = user
        model["data"] = model
        return ResponseEntity.ok(responseData)
    }

    fun signIn(jsonNode: JsonNode, jwtTokenProvider: JwtTokenProvider,
               authenticationManager: AuthProvider): ResponseEntity<*>? {
        val data = jsonNode["data"]
        val device_id = data["device_id"].asText()
        val phone = data["phone"].asText()
        val password = data["password"].asText()
        try {
            authenticationManager.authenticate(UsernamePasswordAuthenticationToken(phone, password))
        } catch (e: BadCredentialsException) {
            return badRequestResponse(e.message)
        }
        val token: String = jwtTokenProvider.createToken(userDao?.findByPhone(phone)?.phone
                , userDao?.findByPhone(phone)?.authorities)
        val user: UserEntity = userDao?.findByPhone(phone)!!
        val device: DeviceEntity? = deviceDao?.findByUid(device_id)!!
        LoggerFactory.getLogger("devices").info(device.toString())
        val devices: Set<DeviceEntity>? = user.devices!!
        for (device1 in user.devices!!) {
            if (device1.uid == device?.uid) {
                (devices as HashSet).remove(device1)
            }
        }
        (devices as HashSet).add(device!!)
        LoggerFactory.getLogger("devices").info(devices.toString())
        user.devices = devices
        manager!!.merge<Any>(user)
        responseData.clear()
        model.clear()
        model["code"] = 200
        model["message"] = "Success"
        model["user"] = user
        model["token"] = token
        responseData["data"] = model
        return ResponseEntity.ok(responseData)
    }

    fun deleteUser(id: Long?): ResponseEntity<*>? {
        return null
    }

    fun signOut(id: Long, jsonNode: JsonNode): ResponseEntity<*>? {
        return try {
            val data = jsonNode["data"]
            val device_id = data["device_id"].asText()
            val device: DeviceEntity? = deviceDao?.findByUid(device_id)!!
            val user: UserEntity? = userDao?.getOne(id)!!
            val devices: Set<DeviceEntity> = user?.devices!!
            for (device1 in user.devices!!) {
                if (device1.uid == device?.uid) {
                    (devices as HashSet).remove(device1)
                }
            }
            manager!!.merge<Any>(user)
            responseData.clear()
            model.clear()
            model["code"] = 200
            model["message"] = "User with id $id is sign out."
            model["data"] = model
            ResponseEntity.ok(responseData)
        } catch (e: Exception) {
            badRequestResponse("Invalid data")
        }
    }


    private fun badRequestResponse(message: String?): ResponseEntity<Map<Any?, Any?>>? {
        responseData.clear()
        model.clear()
        model["code"] = 400
        model["message"] = message!!
        model["data"] = model
        return ResponseEntity.badRequest().body<Map<Any?, Any?>>(responseData as Map<Any?, Any?>)
    }


    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(phone: String?): UserDetails? {
        return userDao?.findByPhone(phone)
    }

    fun downloadImage(fileName: String?): ResponseEntity<*>? {
        val resource = fileStorageService!!.loadFileAsResource(fileName!!)
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource!!.filename + "\"")
                .body(resource)
    }

    fun uploadImage(photo: MultipartFile?): ResponseEntity<*>? {
        return try {
            var fileDownloadUri = ""
            if (photo != null) {
                val fileName = fileStorageService!!.storeFile(photo)
                LoggerFactory.getLogger("updateUser").info(fileName)
                fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("user/downloadFile/")
                        .path(fileName!!)
                        .toUriString()
            }
            responseData.clear()
            model.clear()
            model.put("code", 200)
            model.put("message", "Success")
            model.put("photo", fileDownloadUri)
            responseData.put("data", model)
            ResponseEntity.ok(responseData)
        } catch (f: FileStorageException) {
            badRequestResponse(f.message)
        } catch (e: Exception) {
            badRequestResponse("Something went wrong")
        }
    }

}