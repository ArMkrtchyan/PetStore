package am.petstore.PetStore

import am.petstore.PetStore.user.service.FileStorageService
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.util.*

object Utils {
    fun showErrorLog(name: String?, message: String?) {
        LoggerFactory.getLogger(name).error(message)
    }

    fun showInfoLog(name: String?, message: String?) {
        LoggerFactory.getLogger(name).info(message)
    }

    fun userNotFound(): ResponseEntity<Map<Any, Any>> {
        val model: MutableMap<Any, Any> = HashMap()
        model["code"] = 400
        model["message"] = "User not found"
        return ResponseEntity.badRequest().body(model)
    }

    fun saveFile(fileStorageService: FileStorageService, photo: MultipartFile?): String {
        val fileName = fileStorageService.storeFile(photo!!)
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("user/downloadFile/")
                .path(fileName)
                .toUriString()
    }
}