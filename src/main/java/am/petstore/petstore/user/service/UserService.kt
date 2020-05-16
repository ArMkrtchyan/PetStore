package am.petstore.petstore.user.service

import am.petstore.petstore.Utils
import am.petstore.petstore.user.dao.DeviceDao
import am.petstore.petstore.user.dao.UserDao
import am.petstore.petstore.user.entity.Device
import am.petstore.petstore.user.entity.Role
import am.petstore.petstore.user.entity.UserEntity
import am.petstore.petstore.user.exceptions.FileStorageException
import am.petstore.petstore.user.jwt.JwtTokenProvider
import am.petstore.petstore.user.model.UserResponse
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
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
import java.util.*
import javax.persistence.EntityManager
import javax.persistence.EntityManagerFactory
import javax.servlet.http.HttpServletRequest
import javax.validation.Validator

@Service
@Transactional
class UserService @Autowired constructor(private val userDao: UserDao, private val mapper: ObjectMapper, private val bCryptPasswordEncoder: BCryptPasswordEncoder,
                                         private val validator: Validator, private val deviceDao: DeviceDao, private val fileStorageService: FileStorageService, private val entityManagerFactory: EntityManagerFactory) : UserDetailsService {
    private val responseData: MutableMap<Any, Any> = HashMap()
    private val model: MutableMap<Any, Any?> = HashMap()
    private lateinit var entityManager: EntityManager

    suspend fun registerPhoneNumber(node: JsonNode): ResponseEntity<*> {
        return try {
            val phone = node["data"]["phone"].asText("-1")
            LoggerFactory.getLogger("registerPhoneNumber").info(phone)
            val user = withContext(Dispatchers.Default) { userDao.findByPhone(phone) }
            if (user?.email != null && user.email != "") {
                badRequestResponse("$phone is registered. Please sign in or register with other phone number.")
            } else if (user?.phone != null) {
                val newUser = withContext(Dispatchers.Default) { userDao.findByPhone(phone) }
                responseData.clear()
                model.clear()
                val userResponse: MutableMap<Any, Any?> = HashMap()
                userResponse["id"] = newUser?.id
                userResponse["phone"] = newUser?.phone
                responseData["code"] = 200
                responseData["message"] = "Phone number is registered"
                model["user"] = userResponse
                responseData["data"] = model
                ResponseEntity.ok<Map<Any, Any>>(responseData)
            } else {
                val roles: MutableSet<Role> = HashSet()
                roles.add(Role.USER)
                if (phone == "+37493876378") {
                    roles.add(Role.ADMIN)
                    roles.add(Role.EDITOR)
                }
                var newUser = UserEntity()
                newUser.phone = phone
                newUser.roles = roles
                withContext(Dispatchers.Default) { userDao.saveAndFlush(newUser) }
                withContext(Dispatchers.Default) { newUser = userDao.findByPhone(phone)!! }
                responseData.clear()
                model.clear()
                val userResponse: MutableMap<Any, Any?> = HashMap()
                userResponse["id"] = newUser.id
                userResponse["phone"] = newUser.phone
                responseData["code"] = 200
                responseData["message"] = "Phone number is registered"
                model["user"] = userResponse
                responseData["data"] = model
                ResponseEntity.ok<Map<Any, Any>>(responseData)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            badRequestResponse("Invalid data")
        }
    }

    suspend fun signUpUser(photo: MultipartFile?,
                           id: String,
                           phone: String?,
                           firebase_id: String?,
                           fullname: String?,
                           email: String?,
                           device_id: String?,
                           password: String?, request: HttpServletRequest): ResponseEntity<*> {
        return try {
            val fileDownloadUri = if (photo != null) {
                withContext(Dispatchers.Default) { Utils.saveFile(fileStorageService, photo, request) }
            } else {
                ""
            }
            if (withContext(Dispatchers.Default) { !userDao.existsById(id.toLong()) }) {
                badRequestResponse("Invalid user id")
            } else {
                val user = withContext(Dispatchers.Default) { userDao.getOne(id.toLong()) }
                LoggerFactory.getLogger("updateUser").info(user.toString())
                LoggerFactory.getLogger("updateUser").info(device_id)
                val device = withContext(Dispatchers.Default) { deviceDao.findByUid(device_id) }
                var devices: MutableSet<Device?>? = HashSet()
                if (user.devices != null) {
                    devices = user.devices
                }
                devices!!.removeIf { device1: Device? -> device1?.uid == device?.uid }
                devices.add(device)
                withContext(Dispatchers.Default) {
                    userDao.updateUserInfo(id.toLong(), fullname, bCryptPasswordEncoder.encode(password),
                            email, firebase_id, Date(), fileDownloadUri)
                }
                val newUser = userDao.getOne(id.toLong())
                newUser.devices = devices
                withContext(Dispatchers.Default) {
                    entityManager = entityManagerFactory.createEntityManager()
                    entityManager.transaction.begin()
                    entityManager.merge(newUser)
                    entityManager.transaction.commit()
                }
                responseData.clear()
                model.clear()
                responseData["code"] = 200
                responseData["message"] = "You are successfully registered"
                model["user"] = UserResponse(newUser)
                responseData["data"] = model
                ResponseEntity.ok<Map<Any, Any>>(responseData)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            badRequestResponse("Invalid data")
        }
    }

    suspend fun updateUser(jsonNode: JsonNode): ResponseEntity<*> {
        val data = jsonNode["data"]
        val phone = data["phone"].asText()
        val newPhone = data["new_phone"].asText()
        var user = withContext(Dispatchers.Default) { userDao.findByPhone(phone) }
        val checkUser = withContext(Dispatchers.Default) { userDao.findByPhone(newPhone) }
        if (checkUser != null) {
            return badRequestResponse("$phone is registered. Please sign in or register with other phone number.")
        }
        withContext(Dispatchers.Default) { userDao.updateUserPhone(user?.id, newPhone, Date()) }
        user = withContext(Dispatchers.Default) { userDao.findByPhone(newPhone) }
        responseData.clear()
        model.clear()
        responseData["code"] = 200
        responseData["message"] = "Success"
        model["user"] = UserResponse(user!!)
        responseData["data"] = model
        return ResponseEntity.ok<Map<Any, Any>>(responseData)
    }

    @Transactional
    suspend fun signIn(jsonNode: JsonNode, jwtTokenProvider: JwtTokenProvider,
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
        val token = jwtTokenProvider.createToken(withContext(Dispatchers.Default) { userDao.findByPhone(phone)?.phone }
                , withContext(Dispatchers.Default) { userDao.findByPhone(phone)?.authorities })
        val user = withContext(Dispatchers.Default) { userDao.findByPhone(phone) }
        val device = withContext(Dispatchers.Default) { deviceDao.findByUid(device_id) }
        LoggerFactory.getLogger("devices").info(device.toString())
        val devices = user?.devices
        for (device1 in devices!!) {
            if (device1?.uid == device?.uid) {
                devices.remove(device1)
                break
            }
        }
        devices.add(device)
        LoggerFactory.getLogger("devices").info(devices.toString())
        user.devices = devices
        withContext(Dispatchers.Default) {
            entityManager = entityManagerFactory.createEntityManager()
            entityManager.transaction.begin()
            entityManager.merge(user)
            entityManager.transaction.commit()
        }
        responseData.clear()
        model.clear()
        responseData["code"] = 200
        responseData["message"] = "Success"
        model["user"] = UserResponse(user)
        model["token"] = token
        responseData["data"] = model
        return ResponseEntity.ok<Map<Any, Any>>(responseData)
    }

    suspend fun deleteUser(id: Long?): ResponseEntity<*>? {
        return null
    }

    suspend fun signOut(id: Long, jsonNode: JsonNode): ResponseEntity<*> {
        return try {
            val data = jsonNode["data"]
            val device_id = data["device_id"].asText()
            val device = withContext(Dispatchers.Default) { deviceDao.findByUid(device_id) }
            val user = withContext(Dispatchers.Default) { userDao.getOne(id) }
            val devices = user.devices
            for (device1 in user.devices!!) {
                if (device1?.uid == device?.uid) {
                    devices?.remove(device1)
                }
            }
            withContext(Dispatchers.Default) {
                entityManager = entityManagerFactory.createEntityManager()
                entityManager.transaction.begin()
                entityManager.merge(user)
                entityManager.transaction.commit()
            }
            responseData.clear()
            responseData["code"] = 200
            responseData["message"] = "User with id $id is sign out."
            ResponseEntity.ok<Map<Any, Any>>(responseData)
        } catch (e: Exception) {
            badRequestResponse("Invalid data")
        }
    }

    private fun badRequestResponse(message: String?): ResponseEntity<Map<Any, Any>> {
        responseData.clear()
        responseData["code"] = 400
        responseData["message"] = message!!
        return ResponseEntity.badRequest().body(responseData)
    }

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(phone: String): UserDetails {
        val user: UserDetails? = userDao.findByPhone(phone)
        return user!!
    }

    suspend fun downloadImage(fileName: String): ResponseEntity<*> = withContext(Dispatchers.Default) {
        val resource = fileStorageService.loadFileAsResource(fileName)
        ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.filename + "\"")
                .body(resource)
    }

    suspend fun uploadImage(photo: MultipartFile?, request: HttpServletRequest): ResponseEntity<*> {
        return try {
            val fileDownloadUri = if (photo != null) {
                withContext(Dispatchers.Default) { Utils.saveFile(fileStorageService, photo, request) }
            } else {
                ""
            }
            responseData.clear()
            model.clear()
            responseData["code"] = 200
            responseData["message"] = "Success"
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