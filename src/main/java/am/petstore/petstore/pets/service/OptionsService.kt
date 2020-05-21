package am.petstore.petstore.pets.service

import am.petstore.petstore.pets.dao.*
import am.petstore.petstore.pets.model.Store
import am.petstore.petstore.user.dao.UserDao
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.RequestBody
import javax.persistence.EntityManager
import javax.persistence.EntityManagerFactory

@Service
@Transactional
class OptionsService @Autowired constructor(private val productDao: ProductDao,
                                            private val userDao: UserDao,
                                            private val colorDao: ColorDao,
                                            private val otherOptionsDao: OptionsDao,
                                            private val sizeDao: SizeDao,
                                            private val tastyDao: TastyDao,
                                            private val volumeDao: VolumeDao,
                                            private val weightDao: WeightDao,
                                            private val mapper: ObjectMapper,
                                            private val entityManagerFactory: EntityManagerFactory) {

    private val data: MutableMap<Any, Any> = HashMap()
    private val model: MutableMap<Any, Any> = HashMap()
    private lateinit var entityManager: EntityManager

    fun createColor(@RequestBody jsonNode: JsonNode?): ResponseEntity<Map<Any, Any>> {
        data.clear()
        model.clear()
        data["code"] = 200
        data["message"] = "Success"
        model["store"] = Store()
        data["data"] = model
        return ResponseEntity.ok(data)
    }

    fun findAllColors(): ResponseEntity<Map<Any, Any>> {
        data.clear()
        model.clear()
        data["code"] = 200
        data["message"] = "Success"
        model["store"] = Store()
        data["data"] = model
        return ResponseEntity.ok(data)
    }

    fun deleteColor(@RequestBody jsonNode: JsonNode?): ResponseEntity<Map<Any, Any>> {
        data.clear()
        model.clear()
        data["code"] = 200
        data["message"] = "Success"
        model["store"] = Store()
        data["data"] = model
        return ResponseEntity.ok(data)
    }

    fun updateColor(@RequestBody jsonNode: JsonNode?): ResponseEntity<Map<Any, Any>> {
        data.clear()
        model.clear()
        data["code"] = 200
        data["message"] = "Success"
        model["store"] = Store()
        data["data"] = model
        return ResponseEntity.ok(data)
    }

    fun findAllCapacities(): ResponseEntity<*>? {
        data.clear()
        model.clear()
        data["code"] = 200
        data["message"] = "Success"
        model["store"] = Store()
        data["data"] = model
        return ResponseEntity.ok(data)
    }

    fun createCapacity(jsonNode: JsonNode?): ResponseEntity<*>? {
        data.clear()
        model.clear()
        data["code"] = 200
        data["message"] = "Success"
        model["store"] = Store()
        data["data"] = model
        return ResponseEntity.ok(data)
    }

    fun updateCapacity(jsonNode: JsonNode?): ResponseEntity<*>? {
        data.clear()
        model.clear()
        data["code"] = 200
        data["message"] = "Success"
        model["store"] = Store()
        data["data"] = model
        return ResponseEntity.ok(data)
    }

    fun deleteCapacity(jsonNode: JsonNode?): ResponseEntity<*>? {
        data.clear()
        model.clear()
        data["code"] = 200
        data["message"] = "Success"
        model["store"] = Store()
        data["data"] = model
        return ResponseEntity.ok(data)
    }

    fun findAllOtherOptions(): ResponseEntity<*>? {
        data.clear()
        model.clear()
        data["code"] = 200
        data["message"] = "Success"
        model["store"] = Store()
        data["data"] = model
        return ResponseEntity.ok(data)
    }

    fun createOtherOptions(jsonNode: JsonNode?): ResponseEntity<*>? {
        data.clear()
        model.clear()
        data["code"] = 200
        data["message"] = "Success"
        model["store"] = Store()
        data["data"] = model
        return ResponseEntity.ok(data)
    }

    fun updateOtherOptions(jsonNode: JsonNode?): ResponseEntity<*>? {
        data.clear()
        model.clear()
        data["code"] = 200
        data["message"] = "Success"
        model["store"] = Store()
        data["data"] = model
        return ResponseEntity.ok(data)
    }

    fun deleteOtherOptions(jsonNode: JsonNode?): ResponseEntity<*>? {
        data.clear()
        model.clear()
        data["code"] = 200
        data["message"] = "Success"
        model["store"] = Store()
        data["data"] = model
        return ResponseEntity.ok(data)
    }

    fun findAllOption(): ResponseEntity<*>? {
        data.clear()
        model.clear()
        data["code"] = 200
        data["message"] = "Success"
        model["store"] = Store()
        data["data"] = model
        return ResponseEntity.ok(data)
    }

    fun createOption(jsonNode: JsonNode?): ResponseEntity<*>? {
        data.clear()
        model.clear()
        data["code"] = 200
        data["message"] = "Success"
        model["store"] = Store()
        data["data"] = model
        return ResponseEntity.ok(data)
    }

    fun updateOption(jsonNode: JsonNode?): ResponseEntity<*>? {
        data.clear()
        model.clear()
        data["code"] = 200
        data["message"] = "Success"
        model["store"] = Store()
        data["data"] = model
        return ResponseEntity.ok(data)
    }

    fun deleteOption(jsonNode: JsonNode?): ResponseEntity<*>? {
        data.clear()
        model.clear()
        data["code"] = 200
        data["message"] = "Success"
        model["store"] = Store()
        data["data"] = model
        return ResponseEntity.ok(data)
    }

    fun findAllPrice(): ResponseEntity<*>? {
        data.clear()
        model.clear()
        data["code"] = 200
        data["message"] = "Success"
        model["store"] = Store()
        data["data"] = model
        return ResponseEntity.ok(data)
    }

    fun createPrice(jsonNode: JsonNode?): ResponseEntity<*>? {
        data.clear()
        model.clear()
        data["code"] = 200
        data["message"] = "Success"
        model["store"] = Store()
        data["data"] = model
        return ResponseEntity.ok(data)
    }

    fun updatePrice(jsonNode: JsonNode?): ResponseEntity<*>? {
        data.clear()
        model.clear()
        data["code"] = 200
        data["message"] = "Success"
        model["store"] = Store()
        data["data"] = model
        return ResponseEntity.ok(data)
    }

    fun deletePrice(jsonNode: JsonNode?): ResponseEntity<*>? {
        data.clear()
        model.clear()
        data["code"] = 200
        data["message"] = "Success"
        model["store"] = Store()
        data["data"] = model
        return ResponseEntity.ok(data)
    }

    fun findAllSizes(): ResponseEntity<*>? {
        data.clear()
        model.clear()
        data["code"] = 200
        data["message"] = "Success"
        model["store"] = Store()
        data["data"] = model
        return ResponseEntity.ok(data)
    }

    fun createSize(jsonNode: JsonNode?): ResponseEntity<*>? {
        data.clear()
        model.clear()
        data["code"] = 200
        data["message"] = "Success"
        model["store"] = Store()
        data["data"] = model
        return ResponseEntity.ok(data)
    }

    fun updateSize(jsonNode: JsonNode?): ResponseEntity<*>? {
        data.clear()
        model.clear()
        data["code"] = 200
        data["message"] = "Success"
        model["store"] = Store()
        data["data"] = model
        return ResponseEntity.ok(data)
    }

    fun deleteSize(jsonNode: JsonNode?): ResponseEntity<*>? {
        data.clear()
        model.clear()
        data["code"] = 200
        data["message"] = "Success"
        model["store"] = Store()
        data["data"] = model
        return ResponseEntity.ok(data)
    }

    fun findAllTasty(): ResponseEntity<*>? {
        data.clear()
        model.clear()
        data["code"] = 200
        data["message"] = "Success"
        model["store"] = Store()
        data["data"] = model
        return ResponseEntity.ok(data)
    }

    fun createTasty(jsonNode: JsonNode?): ResponseEntity<*>? {
        data.clear()
        model.clear()
        data["code"] = 200
        data["message"] = "Success"
        model["store"] = Store()
        data["data"] = model
        return ResponseEntity.ok(data)
    }

    fun updateTasty(jsonNode: JsonNode?): ResponseEntity<*>? {
        data.clear()
        model.clear()
        data["code"] = 200
        data["message"] = "Success"
        model["store"] = Store()
        data["data"] = model
        return ResponseEntity.ok(data)
    }

    fun deleteTasty(jsonNode: JsonNode?): ResponseEntity<*>? {
        data.clear()
        model.clear()
        data["code"] = 200
        data["message"] = "Success"
        model["store"] = Store()
        data["data"] = model
        return ResponseEntity.ok(data)
    }

    fun findAllTitle(): ResponseEntity<*>? {
        data.clear()
        model.clear()
        data["code"] = 200
        data["message"] = "Success"
        model["store"] = Store()
        data["data"] = model
        return ResponseEntity.ok(data)
    }

    fun createTitle(jsonNode: JsonNode?): ResponseEntity<*>? {
        data.clear()
        model.clear()
        data["code"] = 200
        data["message"] = "Success"
        model["store"] = Store()
        data["data"] = model
        return ResponseEntity.ok(data)
    }

    fun updateTitle(jsonNode: JsonNode?): ResponseEntity<*>? {
        data.clear()
        model.clear()
        data["code"] = 200
        data["message"] = "Success"
        model["store"] = Store()
        data["data"] = model
        return ResponseEntity.ok(data)
    }

    fun deleteTitle(jsonNode: JsonNode?): ResponseEntity<*>? {
        data.clear()
        model.clear()
        data["code"] = 200
        data["message"] = "Success"
        model["store"] = Store()
        data["data"] = model
        return ResponseEntity.ok(data)
    }

    fun findAllVolume(): ResponseEntity<*>? {
        data.clear()
        model.clear()
        data["code"] = 200
        data["message"] = "Success"
        model["store"] = Store()
        data["data"] = model
        return ResponseEntity.ok(data)
    }

    fun createVolume(jsonNode: JsonNode?): ResponseEntity<*>? {
        data.clear()
        model.clear()
        data["code"] = 200
        data["message"] = "Success"
        model["store"] = Store()
        data["data"] = model
        return ResponseEntity.ok(data)
    }

    fun updateVolume(jsonNode: JsonNode?): ResponseEntity<*>? {
        data.clear()
        model.clear()
        data["code"] = 200
        data["message"] = "Success"
        model["store"] = Store()
        data["data"] = model
        return ResponseEntity.ok(data)
    }

    fun deleteVolume(jsonNode: JsonNode?): ResponseEntity<*>? {
        data.clear()
        model.clear()
        data["code"] = 200
        data["message"] = "Success"
        model["store"] = Store()
        data["data"] = model
        return ResponseEntity.ok(data)
    }

    fun findAllWeight(): ResponseEntity<*>? {
        data.clear()
        model.clear()
        data["code"] = 200
        data["message"] = "Success"
        model["store"] = Store()
        data["data"] = model
        return ResponseEntity.ok(data)
    }

    fun createWeight(jsonNode: JsonNode?): ResponseEntity<*>? {
        data.clear()
        model.clear()
        data["code"] = 200
        data["message"] = "Success"
        model["store"] = Store()
        data["data"] = model
        return ResponseEntity.ok(data)
    }

    fun updateWeight(jsonNode: JsonNode?): ResponseEntity<*>? {
        data.clear()
        model.clear()
        data["code"] = 200
        data["message"] = "Success"
        model["store"] = Store()
        data["data"] = model
        return ResponseEntity.ok(data)
    }

    fun deleteWeight(jsonNode: JsonNode?): ResponseEntity<*>? {
        data.clear()
        model.clear()
        data["code"] = 200
        data["message"] = "Success"
        model["store"] = Store()
        data["data"] = model
        return ResponseEntity.ok(data)
    }
}