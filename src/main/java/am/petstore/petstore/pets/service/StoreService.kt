package am.petstore.petstore.pets.service

import am.petstore.petstore.pets.dao.StoreDao
import am.petstore.petstore.pets.model.Store
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.RequestBody
import java.util.*

@Service
@Transactional
class StoreService(private val storeDao: StoreDao, private val mapper: ObjectMapper) {
    private val data: MutableMap<Any, Any> = HashMap()
    private val model: MutableMap<Any, Any> = HashMap()

    fun create(@RequestBody jsonNode: JsonNode?): ResponseEntity<Map<Any, Any>> {
        data.clear()
        model.clear()
        data["code"] = 200
        data["message"] = "Success"
        model["store"] = Store()
        data["data"] = model
        return ResponseEntity.ok(data)
    }

    fun findAll(): ResponseEntity<Map<Any, Any>> {
        data.clear()
        model.clear()
        data["code"] = 200
        data["message"] = "Success"
        model["store"] = Store()
        data["data"] = model
        return ResponseEntity.ok(data)
    }

    fun delete(@RequestBody jsonNode: JsonNode?): ResponseEntity<Map<Any, Any>> {
        data.clear()
        model.clear()
        data["code"] = 200
        data["message"] = "Success"
        model["store"] = Store()
        data["data"] = model
        return ResponseEntity.ok(data)
    }

    fun update(@RequestBody jsonNode: JsonNode?): ResponseEntity<Map<Any, Any>> {
        data.clear()
        model.clear()
        data["code"] = 200
        data["message"] = "Success"
        model["store"] = Store()
        data["data"] = model
        return ResponseEntity.ok(data)
    }

}