package am.petstore.petstore.pets.service

import am.petstore.petstore.Utils
import am.petstore.petstore.pets.dao.*
import am.petstore.petstore.pets.entity.ProductEntity
import am.petstore.petstore.pets.model.Store
import am.petstore.petstore.user.dao.UserDao
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.withContext
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.RequestBody
import javax.persistence.EntityManager
import javax.persistence.EntityManagerFactory
import javax.servlet.http.HttpServletRequest

@Service
@Transactional
class ProductService @Autowired constructor(private val productDao: ProductDao,
                                            private val userDao: UserDao,
                                            private val colorDao: ColorDao,
                                            private val descriptionDao: DescriptionDao,
                                            private val otherOptionsDao: OtherOptionsDao,
                                            private val photoDao: PhotoDao,
                                            private val priceDao: PriceDao,
                                            private val sizeDao: SizeDao,
                                            private val tastyDao: TastyDao,
                                            private val titleDao: TitleDao,
                                            private val volumeDao: VolumeDao,
                                            private val weightDao: WeightDao,
                                            private val mapper: ObjectMapper,
                                            private val entityManagerFactory: EntityManagerFactory) {
    private val data: MutableMap<Any, Any> = HashMap()
    private val model: MutableMap<Any, Any> = HashMap()
    private lateinit var entityManager: EntityManager

    suspend fun create(@RequestBody jsonNode: JsonNode?, request: HttpServletRequest): ResponseEntity<MutableMap<Any, Any>> {
        data.clear()
        model.clear()
        data["code"] = 200
        data["message"] = "Success"
        model["product"] = withContext(Dispatchers.Default + Job()) { productDao.findAll(Sort.by("id")) }
        data["data"] = model
        return ResponseEntity.ok(data)

    }

    suspend fun findAll(categoryId: Int?, page: Int?, limit: Int?, sort: String?): ResponseEntity<MutableMap<Any, Any>> {
        data.clear()
        model.clear()
        val paging = PageRequest.of(page ?: 0, limit ?: 0, Sort.by(sort ?: "id"))
        val pagedResult = withContext(Dispatchers.Default + Job()) { productDao.findByCategoryId(categoryId, paging) }
        if (pagedResult.hasContent()) {
            model["products"] = pagedResult.content
        } else {
            model["products"] = ArrayList<ProductEntity>()
        }
        data["code"] = 200
        data["message"] = "Success"
        model["count"] = withContext(Dispatchers.Default + Job()) { productDao.findAllByCategoryId(categoryId).size }
        data["data"] = model
        return ResponseEntity.ok(data)

    }

    suspend fun findAllWithPetId(petId: Int?, page: Int?, limit: Int?, sort: String?): ResponseEntity<MutableMap<Any, Any>> {
        data.clear()
        model.clear()
        val paging = PageRequest.of(page ?: 0, limit ?: 0, Sort.by(sort ?: "id"))
        val pagedResult = withContext(Dispatchers.Default + Job()) { productDao.findByPetId(petId, paging) }
        if (pagedResult.hasContent()) {
            model["products"] = pagedResult.content
        } else {
            model["products"] = ArrayList<ProductEntity>()
        }
        data["code"] = 200
        data["message"] = "Success"
        model["count"] = withContext(Dispatchers.Default + Job()) { productDao.findAllByPetId(petId).size }
        data["data"] = model
        return ResponseEntity.ok(data)

    }
    suspend fun findAllWithPetId(petId: Int): ResponseEntity<MutableMap<Any, Any>> {
        data.clear()
        model.clear()
        val products = ArrayList<ProductEntity>()
        withContext(Dispatchers.Default + Job()) {
            productDao.findAllByPetId(petId, Sort.by("id")).map { it ->
                products.add(it)
            }
        }
        model["products"] = products
        data["code"] = 200
        data["message"] = "Success"
        model["count"] = withContext(Dispatchers.Default + Job()) { products.size }
        data["data"] = model
        return ResponseEntity.ok(data)

    }

    suspend fun findAll(page: Int?, limit: Int?, sort: String?): ResponseEntity<MutableMap<Any, Any>> {
        data.clear()
        model.clear()
        val paging = PageRequest.of(page ?: 0, limit ?: 0, Sort.by(sort ?: "id"))
        val pagedResult = withContext(Dispatchers.Default + Job()) { productDao.findAll(paging) }
        if (pagedResult.hasContent()) {
            model["products"] = pagedResult.content
        } else {
            model["products"] = ArrayList<ProductEntity>()
        }
        data["code"] = 200
        data["message"] = "Success"
        model["count"] = withContext(Dispatchers.Default + Job()) { productDao.findAll().size }
        data["data"] = model
        return ResponseEntity.ok(data)
    }

    suspend fun findAll(categoryId: Int?): ResponseEntity<MutableMap<Any, Any>> {
        data.clear()
        model.clear()
        val products = ArrayList<ProductEntity>()
        withContext(Dispatchers.Default + Job()) {
            productDao.findAllByCategoryId(categoryId, Sort.by("id")).map { it ->
                products.add(it)
            }
        }
        model["products"] = products
        data["code"] = 200
        data["message"] = "Success"
        model["count"] = withContext(Dispatchers.Default + Job()) { products.size }
        data["data"] = model
        return ResponseEntity.ok(data)

    }

    suspend fun findAll(): ResponseEntity<MutableMap<Any, Any>> {
        data.clear()
        model.clear()
        model["products"] = productDao.findAll(Sort.by("id"))
        data["code"] = 200
        data["message"] = "Success"
        model["count"] = withContext(Dispatchers.Default + Job()) {
            productDao.findAll(Sort.by("id")).size
        }
        data["data"] = model
        return ResponseEntity.ok(data)
    }

    suspend fun delete(@RequestBody jsonNode: JsonNode?): ResponseEntity<MutableMap<Any, Any>> {
        data.clear()
        model.clear()
        data["code"] = 200
        data["message"] = "Success"
        model["store"] = Store()
        data["data"] = model
        return ResponseEntity.ok(data)
    }

    suspend fun update(@RequestBody jsonNode: JsonNode?, request: HttpServletRequest): ResponseEntity<MutableMap<Any, Any>> {
        data.clear()
        model.clear()
        data["code"] = 200
        data["message"] = "Success"
        model["store"] = Store()
        data["data"] = model
        return ResponseEntity.ok(data)
    }

    suspend fun addToFavorites(productId: Int, request: HttpServletRequest): ResponseEntity<MutableMap<Any, Any>> {
        val userId = Utils.getUserId(request)
        LoggerFactory.getLogger("UserId").info(userId.toString())
        if (!userDao.existsById(userId!!)) {
            return badRequestResponse("User Not found")
        }
        if (!productDao.existsById(productId.toLong())) {
            return badRequestResponse("Product with $productId Not found")
        }
        val user = withContext(Dispatchers.Default) { userDao.getOne(userId) }
        val product = withContext(Dispatchers.Default) { productDao.getOne(productId.toLong()) }
        LoggerFactory.getLogger("UserFavorites").info(user.favorites?.contains(product)!!.toString())
        user.favorites?.map {
            if (it.id == product.id) {
                return badRequestResponse("You already have product with $productId in your favorites")
            }
        }
        val fav = user.favorites?.filter {
            it.id != product.id
        }
        user.favorites?.clear()
        user.favorites?.addAll(fav!!)
        user.favorites?.add(product)
        withContext(Dispatchers.Default) {
            entityManager = entityManagerFactory.createEntityManager()
            entityManager.transaction.begin()
            entityManager.merge(user)
            entityManager.transaction.commit()
        }
        data.clear()
        model.clear()
        data["code"] = 200
        data["message"] = "Success"
        model["favorites"] = withContext(Dispatchers.Default) { userDao.getOne(userId).favorites!! }
        data["data"] = model
        return ResponseEntity.ok(data)
    }

    suspend fun removeFromFavorites(productId: Int, request: HttpServletRequest): ResponseEntity<MutableMap<Any, Any>> {
        val userId = Utils.getUserId(request)
        LoggerFactory.getLogger("UserId").info(userId.toString())
        if (!userDao.existsById(userId!!)) {
            return badRequestResponse("User Not found")
        }
        if (!productDao.existsById(productId.toLong())) {
            return badRequestResponse("Product with $productId Not found")
        }
        val user = withContext(Dispatchers.Default) { userDao.getOne(userId) }
        val product = withContext(Dispatchers.Default) { productDao.getOne(productId.toLong()) }
        LoggerFactory.getLogger("UserFavorites").info(user.favorites?.contains(product).toString())
        if (!user.favorites?.contains(product)!!) {
            return badRequestResponse("You didn't have product with $productId in your favorites")
        }
        val fav = user.favorites?.filter {
            it.id != product.id
        }
        user.favorites?.clear()
        user.favorites?.addAll(fav!!)
        withContext(Dispatchers.Default) {
            entityManager = entityManagerFactory.createEntityManager()
            entityManager.transaction.begin()
            entityManager.merge(user)
            entityManager.transaction.commit()
        }
        data.clear()
        model.clear()
        data["code"] = 200
        data["message"] = "Success"
        model["favorites"] = withContext(Dispatchers.Default) { userDao.getOne(userId).favorites!! }
        data["data"] = model
        return ResponseEntity.ok(data)
    }

    suspend fun getFavorites(request: HttpServletRequest): ResponseEntity<MutableMap<Any, Any>> {
        val userId = Utils.getUserId(request)
        LoggerFactory.getLogger("UserId").info(userId.toString())
        userId ?: return badRequestResponse("User Not found")
        data.clear()
        model.clear()
        data["code"] = 200
        data["message"] = "Success"
        model["count"] = withContext(Dispatchers.Default) { userDao.getOne(userId!!).favorites!!.size }
        model["favorites"] = withContext(Dispatchers.Default) { userDao.getOne(userId!!).favorites!! }
        data["data"] = model
        return ResponseEntity.ok(data)
    }

    private fun badRequestResponse(message: String?): ResponseEntity<MutableMap<Any, Any>> {
        data.clear()
        data["code"] = 400
        data["message"] = message!!
        return ResponseEntity.badRequest().body(data)
    }

}