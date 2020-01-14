package am.petstore.PetStore.pets.service

import am.petstore.PetStore.pets.dao.ProductDao
import am.petstore.PetStore.pets.model.Store
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.RequestBody
import java.util.*
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Service
@Transactional
class ProductService(private val productDao: ProductDao, private val mapper: ObjectMapper) {
    private val data: MutableMap<Any, Any> = HashMap()
    private val model: MutableMap<Any, Any> = HashMap()
    @PersistenceContext
    private val manager: EntityManager? = null

    fun create(@RequestBody jsonNode: JsonNode?): ResponseEntity<Map<Any, Any>> {
        data.clear()
        model.clear()
        model["code"] = 200
        model["message"] = "Success"
        model["store"] = Store()
        data["data"] = model
        return ResponseEntity.ok(data)
    }

    fun findAll(): ResponseEntity<Map<Any, Any>> {
        data.clear()
        model.clear()
        model["code"] = 200
        model["message"] = "Success"
        model["store"] = Store()
        data["data"] = model
        return ResponseEntity.ok(data)
    }

    fun delete(@RequestBody jsonNode: JsonNode?): ResponseEntity<Map<Any, Any>> {
        data.clear()
        model.clear()
        model["code"] = 200
        model["message"] = "Success"
        model["store"] = Store()
        data["data"] = model
        return ResponseEntity.ok(data)
    }

    fun update(@RequestBody jsonNode: JsonNode?): ResponseEntity<Map<Any, Any>> {
        data.clear()
        model.clear()
        model["code"] = 200
        model["message"] = "Success"
        model["store"] = Store()
        data["data"] = model
        return ResponseEntity.ok(data)
    }

}