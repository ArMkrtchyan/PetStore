package am.petstore.petstore

import am.petstore.petstore.user.service.FileStorageService
import io.jsonwebtoken.Jwts
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.withContext
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.multipart.MultipartFile
import java.util.*
import javax.servlet.http.HttpServletRequest

object Utils {

    var secretKey = "aslkjedomjlwsdk"

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
                "user/downloadFile/$fileName"
            }

    fun getUserId(req: HttpServletRequest): Long? {
        val bearerToken = req.getHeader("Authorization")
        val token = if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            bearerToken.substring(7, bearerToken.length)
        } else {
            return null
        }
        LoggerFactory.getLogger("JwtToken").info(token)
        val user = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
        LoggerFactory.getLogger("user").info(user.toString())
        val body = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)?.body
        LoggerFactory.getLogger("body").info(body.toString())
        val id = body?.get("id").toString()
        LoggerFactory.getLogger("Id").info(id)
        return id.toLong()
    }
}