package am.petstore.PetStore.user.controller

import am.petstore.PetStore.user.jwt.JwtTokenProvider
import am.petstore.PetStore.user.service.AuthProvider
import am.petstore.PetStore.user.service.DeviceService
import am.petstore.PetStore.user.service.UserService
import com.fasterxml.jackson.databind.JsonNode
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("/user")
class Controller @Autowired constructor(private val userService: UserService, private val deviceService: DeviceService, private val authenticationManager: AuthProvider, private val jwtTokenProvider: JwtTokenProvider) {
    @PostMapping("/device")
    fun registerDevice(@RequestBody jsonNode: JsonNode, request: HttpServletRequest?): ResponseEntity<*>? {
        return deviceService.addOrUpdateDevice(jsonNode)
    }

    @PostMapping("/device/free")
    fun setDeviceFree(@RequestBody jsonNode: JsonNode): ResponseEntity<*>? {
        return deviceService.setDeviceFree(jsonNode)
    }

    @PostMapping("/signup/phone")
    fun registerPhone(@RequestBody data: JsonNode): ResponseEntity<*>? {
        return userService.registerPhoneNumber(data)
    }

    @PostMapping(value = ["/signup"], consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun signUpUser(@RequestParam(value = "image", required = false) photo: MultipartFile?,
                   @RequestParam(value = "id", required = false) id: String,
                   @RequestParam(value = "phone", required = false) phone: String?,
                   @RequestParam(value = "firebase_id", required = false) firebase_id: String?,
                   @RequestParam(value = "fullname", required = false) fullname: String?,
                   @RequestParam(value = "email", required = false) email: String?,
                   @RequestParam(value = "gender", required = false) gender: String?,
                   @RequestParam(value = "birthday", required = false) birthday: String?,
                   @RequestParam(value = "device_id", required = false) device_id: String?,
                   @RequestParam(value = "password", required = false) password: String?): ResponseEntity<*>? {
        return userService.signUpUser(photo, id, phone, firebase_id, fullname, email, gender, birthday, device_id, password)
    }

    @PostMapping("/signin")
    fun signIn(@RequestBody data: JsonNode): ResponseEntity<*>? {
        return userService.signIn(data, jwtTokenProvider, authenticationManager)
    }

    @PutMapping("/update")
    fun updateUser(@RequestBody data: JsonNode): ResponseEntity<*>? {
        return userService.updateUser(data)
    }

    @PutMapping("/signout/{id}")
    fun signOut(@PathVariable("id") id: Long, @RequestBody data: JsonNode): ResponseEntity<*>? {
        return userService.signOut(id, data)
    }

    @DeleteMapping("/update/{id}")
    fun deleteUser(@PathVariable id: Long?): ResponseEntity<*>? {
        return userService.deleteUser(id)
    }

    @GetMapping("/downloadFile/{fileName:.+}")
    fun downloadFileFromLocal(@PathVariable fileName: String): ResponseEntity<*>? {
        return userService.downloadImage(fileName)
    }

    @PostMapping(value = ["/upload"], consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun uploadFile(@RequestParam(value = "image", required = false) photo: MultipartFile?): ResponseEntity<*>? {
        return userService.uploadImage(photo)
    }

    companion object {
        private val log = LoggerFactory.getLogger("application")
    }

}