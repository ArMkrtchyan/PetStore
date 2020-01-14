package am.petstore.PetStore

import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
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
}