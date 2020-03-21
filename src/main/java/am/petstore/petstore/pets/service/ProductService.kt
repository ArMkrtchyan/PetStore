package am.petstore.petstore.pets.service

import am.petstore.petstore.pets.dao.ProductDao
import am.petstore.petstore.pets.entity.ProductEntity
import am.petstore.petstore.pets.model.Store
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.RequestBody
import java.util.*
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.servlet.http.HttpServletRequest
import kotlin.collections.ArrayList

@Service
@Transactional
class ProductService @Autowired constructor(private val productDao: ProductDao, private val mapper: ObjectMapper) {
    private val data: MutableMap<Any, Any> = HashMap()
    private val model: MutableMap<Any, Any> = HashMap()

    suspend fun create(@RequestBody jsonNode: JsonNode?, request: HttpServletRequest): ResponseEntity<MutableMap<Any, Any>> = coroutineScope {
        withContext(Dispatchers.Default + Job()) {
            for (i in 20 until 100) {
                productDao.saveAndFlush(ProductEntity(
                        Date(), Date(), null, "Product $i", "http://192.168.0.102:8080/user/downloadFile/Dog.png",
                        "Producer name",
                        "http://192.168.0.102:8080/user/downloadFile/Dog.png",
                        "Blue",
                        "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.\n" +
                                "\n" +
                                "Why do we use it?\n" +
                                "It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', making it look like readable English. Many desktop publishing packages and web page editors now use Lorem Ipsum as their default model text, and a search for 'lorem ipsum' will uncover many web sites still in their infancy. Various versions have evolved over the years, sometimes by accident, sometimes on purpose (injected humour and the like).\n" +
                                "\n" +
                                "\n" +
                                "Where does it come from?\n" +
                                "Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, consectetur, from a Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered the undoubtable source. Lorem Ipsum comes from sections 1.10.32 and 1.10.33 of \"de Finibus Bonorum et Malorum\" (The Extremes of Good and Evil) by Cicero, written in 45 BC. This book is a treatise on the theory of ethics, very popular during the Renaissance. The first line of Lorem Ipsum, \"Lorem ipsum dolor sit amet..\", comes from a line in section 1.10.32.",
                        3.54, 1.5, 6, 1, 2000, 50, 4000
                ))
            }
            data.clear()
            model.clear()
            data["code"] = 200
            data["message"] = "Success"
            model["product"] = productDao.findAll(Sort.by("id"))
            data["data"] = model
            ResponseEntity.ok(data)
        }
    }

    suspend fun findAll(categoryId: Int?, page: Int?, limit: Int?, sort: String?): ResponseEntity<MutableMap<Any, Any>> = coroutineScope {
        withContext(Dispatchers.Default + Job()) {
            data.clear()
            model.clear()
            val paging = PageRequest.of(page ?: 0, limit ?: 0, Sort.by(sort ?: "id"))
            val pagedResult = productDao.findByCategoryId(categoryId, paging)
            if (pagedResult.hasContent()) {
                model["products"] = pagedResult.content
            } else {
                model["products"] = ArrayList<ProductEntity>()
            }
            data["code"] = 200
            data["message"] = "Success"
            model["count"] = productDao.findAllByCategoryId(categoryId).size
            data["data"] = model
            ResponseEntity.ok(data)
        }
    }

    suspend fun findAll(page: Int?, limit: Int?, sort: String?): ResponseEntity<MutableMap<Any, Any>> = coroutineScope {
        withContext(Dispatchers.Default + Job()) {
            data.clear()
            model.clear()
            val paging = PageRequest.of(page ?: 0, limit ?: 0, Sort.by(sort ?: "id"))
            val pagedResult = productDao.findAll(paging)
            if (pagedResult.hasContent()) {
                model["products"] = pagedResult.content
            } else {
                model["products"] = ArrayList<ProductEntity>()
            }
            data["code"] = 200
            data["message"] = "Success"
            model["count"] = productDao.findAll().size
            data["data"] = model
            ResponseEntity.ok(data)
        }
    }

    suspend fun findAll(categoryId: Int?): ResponseEntity<MutableMap<Any, Any>> = coroutineScope {
        withContext(Dispatchers.Default + Job()) {
            data.clear()
            model.clear()
            val products = ArrayList<ProductEntity>()
            productDao.findAllByCategoryId(categoryId, Sort.by("id")).map { it ->
                products.add(it)
            }
            model["products"] = products
            data["code"] = 200
            data["message"] = "Success"
            model["count"] = products.size
            data["data"] = model
            ResponseEntity.ok(data)
        }
    }

    suspend fun findAll(): ResponseEntity<MutableMap<Any, Any>> = coroutineScope {
        withContext(Dispatchers.Default + Job()) {
            data.clear()
            model.clear()
            model["products"] = productDao.findAll(Sort.by("id"))
            data["code"] = 200
            data["message"] = "Success"
            model["count"] = productDao.findAll(Sort.by("id")).size
            data["data"] = model
            ResponseEntity.ok(data)
        }
    }

    suspend fun delete(@RequestBody jsonNode: JsonNode?): ResponseEntity<MutableMap<Any, Any>> = coroutineScope {
        withContext(Dispatchers.Default + Job()) {
            data.clear()
            model.clear()
            data["code"] = 200
            data["message"] = "Success"
            model["store"] = Store()
            data["data"] = model
            ResponseEntity.ok(data)
        }
    }

    suspend fun update(@RequestBody jsonNode: JsonNode?, request: HttpServletRequest): ResponseEntity<MutableMap<Any, Any>> = coroutineScope {
        withContext(Dispatchers.Default + Job()) {
            data.clear()
            model.clear()
            data["code"] = 200
            data["message"] = "Success"
            model["store"] = Store()
            data["data"] = model
            ResponseEntity.ok(data)
        }
    }

}