package am.petstore.petstore.pets.service

import am.petstore.petstore.Utils
import am.petstore.petstore.pets.dao.CategoryDao
import am.petstore.petstore.pets.entity.CategoryEntity
import am.petstore.petstore.pets.model.Category
import am.petstore.petstore.user.service.FileStorageService
import com.fasterxml.jackson.databind.ObjectMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import org.springframework.data.domain.Sort
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import java.util.*
import javax.servlet.http.HttpServletRequest

@Service
@Transactional
class CategoryService(private val categoryDao: CategoryDao, private val mapper: ObjectMapper, private val fileStorageService: FileStorageService) {
    private val data: MutableMap<Any, Any> = HashMap()
    private val model: MutableMap<Any, Any> = HashMap()

    suspend fun create(photo: MultipartFile?, title: String?, petId: Long?, categoryId: Long?, request: HttpServletRequest): ResponseEntity<MutableMap<Any, Any>> = coroutineScope {
        withContext(Dispatchers.Default + Job()) {
            if (photo == null) {
                data.clear()
                model.clear()
                data["code"] = 400
                data["message"] = "Field photo can't be null or empty."
                ResponseEntity.badRequest().body(data)
            }
            if (title == null) {
                data.clear()
                model.clear()
                data["code"] = 400
                data["message"] = "Field title can't be null or empty."
                ResponseEntity.badRequest().body(data)
            }
            if (categoryDao.existsByTitle(title)) {
                if (categoryDao.findByTitle(title)?.deletedAt == null) {
                    data.clear()
                    model.clear()
                    data["code"] = 400
                    data["message"] = "Category with $title already exist."
                    ResponseEntity.badRequest().body(data)
                } else {
                    update(categoryDao.findByTitle(title)?.id!!, photo, title, categoryId, petId, request)
                }
            } else {
                val categoryEntity = CategoryEntity(Date(), Date(), title, Utils.saveFile(fileStorageService, photo, request), categoryId, petId)
                categoryDao.saveAndFlush(categoryEntity)
                data.clear()
                model.clear()
                data["code"] = 200
                data["message"] = "Created"
                model["category"] = Category(categoryDao.findAll()[categoryDao.findAll().size - 1]!!)
                data["data"] = model
                ResponseEntity.ok(data)
            }
        }
    }


    suspend fun findAll(petId: Long?, categoryId: Long?): ResponseEntity<MutableMap<Any, Any>> = coroutineScope {
        withContext(Dispatchers.Default + Job()) {
            val categories: MutableList<Category?> = ArrayList()
            val categoryEntities = when {
                petId == null -> {
                    categoryDao.findAll(Sort.by("id"))
                }
                categoryId != null -> {
                    categoryDao.findByPetIdAndAndCategoryId(petId, categoryId, Sort.by("id"))
                }
                else -> {
                    categoryDao.findByPetId(petId, Sort.by("id"))
                }
            }
            for (categoryEntity in categoryEntities) {
                if (categoryEntity?.deletedAt == null) {
                    categories.add(Category(categoryEntity!!))
                }
            }
            data.clear()
            model.clear()
            data["code"] = 200
            data["message"] = "Success"
            model["categories"] = categories
            data["data"] = model
            ResponseEntity.ok(data)
        }
    }


    suspend fun delete(id: Long): ResponseEntity<MutableMap<Any, Any>> = coroutineScope {
        withContext(Dispatchers.Default + Job()) {
            categoryDao.delete(id, Date())
            data.clear()
            model.clear()
            data["code"] = 200
            data["message"] = "Category with $id deleted"
            model["category"] = Category(categoryDao.getOne(id))
            data["data"] = model
            ResponseEntity.ok(data)
        }
    }

    suspend fun update(id: Long, photo: MultipartFile?, title: String?, categoryId: Long?, petId: Long?, request: HttpServletRequest): ResponseEntity<MutableMap<Any, Any>> = coroutineScope {
        withContext(Dispatchers.Default + Job()) {
            val categoryEntity = categoryDao.getOne(id)
            if (categoryEntity == null) {
                data.clear()
                data["code"] = 400
                model["message"] = "Category with $id not found."
                ResponseEntity.badRequest().body(data)
            }
            if (photo == null && title == null) {
                data.clear()
                data["code"] = 400
                data["message"] = "Wrong sent data. Fields \"photo\" and \"title\" could not be null at same time"
                ResponseEntity.badRequest().body(data)
            }
            if (petId == null) {
                data.clear()
                data["code"] = 400
                data["message"] = "Wrong sent data. Fields \"petId\" could not be null"
                ResponseEntity.badRequest().body(data)
            }
            if (photo != null && title != null) {
                categoryDao.update(id, Date(), Utils.saveFile(fileStorageService, photo, request), title, null, petId!!, categoryId)
            } else if (title != null) {
                categoryDao.update(id, Date(), categoryEntity.photo, title, null, petId!!, categoryId)
            } else {
                categoryDao.update(id, Date(), Utils.saveFile(fileStorageService, photo, request), categoryEntity.title, null, petId!!, categoryId)
            }
            data.clear()
            model.clear()
            data["code"] = 200
            data["message"] = "Category with $title updated."
            model["category"] = Category(categoryDao.getOne(categoryEntity.id!!))
            data["data"] = model
            ResponseEntity.ok(data)
        }
    }


}