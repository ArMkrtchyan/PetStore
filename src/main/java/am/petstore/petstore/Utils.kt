package am.petstore.petstore

import am.petstore.petstore.user.service.FileStorageService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.withContext
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.util.*
import javax.servlet.http.HttpServletRequest

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

    suspend fun saveFile(fileStorageService: FileStorageService, photo: MultipartFile?, request: HttpServletRequest): String =
        withContext(Dispatchers.Default + Job()) {
            val fileName = fileStorageService.storeFile(photo!!)
             ServletUriComponentsBuilder.fromContextPath(request)
                    .path("user/downloadFile/")
                    .path(fileName)
                    .toUriString()
    }
}