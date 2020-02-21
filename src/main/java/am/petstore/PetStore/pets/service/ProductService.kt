package am.petstore.PetStore.pets.service

import am.petstore.PetStore.pets.dao.ProductDao
import am.petstore.PetStore.pets.entity.ProductEntity
import am.petstore.PetStore.pets.model.Store
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Sort
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.RequestBody
import java.util.*
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Service
@Transactional
class ProductService @Autowired constructor(private val productDao: ProductDao, private val mapper: ObjectMapper) {
    private val data: MutableMap<Any, Any> = HashMap()
    private val model: MutableMap<Any, Any> = HashMap()
    @PersistenceContext
    private val manager: EntityManager? = null

    fun create(@RequestBody jsonNode: JsonNode?): ResponseEntity<Map<Any, Any>> {
        for (i in 0 until 55) {
            productDao.saveAndFlush(ProductEntity(
                    Date(), Date(), null, "Product ${i}", "http://192.168.0.120:8080/user/downloadFile/Dog.png",
                    "Producer name",
                    "http://192.168.0.120:8080/user/downloadFile/Dog.png",
                    "Blue",
                    "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. ",
                    3.54, 1.5, 5, 1, 2000, 50, 4000
            ))
        }
        data.clear()
        model.clear()
        data["code"] = 200
        data["message"] = "Success"
        model["product"] = productDao.findAll(Sort.by("id"))
        data["data"] = model
        return ResponseEntity.ok(data)
    }

    fun findAll(categoryId: Int?): ResponseEntity<Map<Any, Any>> {
        data.clear()
        model.clear()
        data["code"] = 200
        data["message"] = "Success"
        model["count"] = productDao.findAllByCategoryId(categoryId,Sort.by("id")).size
        model["products"] = productDao.findAllByCategoryId(categoryId,Sort.by("id"))
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