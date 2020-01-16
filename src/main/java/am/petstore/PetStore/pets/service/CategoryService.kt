package am.petstore.PetStore.pets.service

import am.petstore.PetStore.Utils
import am.petstore.PetStore.pets.dao.CategoryDao
import am.petstore.PetStore.pets.entity.CategoryEntity
import am.petstore.PetStore.pets.model.Category
import am.petstore.PetStore.user.service.FileStorageService
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.data.domain.Sort
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.util.*
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Service
@Transactional
class CategoryService(private val categoryDao: CategoryDao, private val mapper: ObjectMapper, private val fileStorageService: FileStorageService) {
    private val data: MutableMap<Any, Any> = HashMap()
    private val model: MutableMap<Any, Any> = HashMap()

    @PersistenceContext
    private val manager: EntityManager? = null

    fun create(photo: MultipartFile?, title: String?, petId: Long?, categoryId: Long?): ResponseEntity<Map<Any, Any>>? {
        if (photo == null) {
            data.clear()
            model.clear()
            model["code"] = 400
            model["message"] = "Field photo can't be null or empty."
            data["data"] = model
            return ResponseEntity.badRequest().body(data)
        }
        if (title == null) {
            data.clear()
            model.clear()
            model["code"] = 400
            model["message"] = "Field title can't be null or empty."
            data["data"] = model
            return ResponseEntity.badRequest().body(data)
        }
        return if (categoryDao.existsByTitle(title)) {
            if (categoryDao.findByTitle(title)?.deletedAt == null) {
                data.clear()
                model.clear()
                model["code"] = 400
                model["message"] = "Category with $title already exist."
                data["data"] = model
                ResponseEntity.badRequest().body<Map<Any, Any>>(data)
            } else {
                update(categoryDao.findByTitle(title)?.id!!, photo, title, categoryId, petId)
            }
        } else {
            val categoryEntity = CategoryEntity(Date(), Date(), title, Utils.saveFile(fileStorageService,photo), categoryId, petId)
            categoryDao.saveAndFlush(categoryEntity)
            data.clear()
            model.clear()
            model["code"] = 200
            model["message"] = "Created"
            model["category"] = Category(categoryDao.findAll()[categoryDao.findAll().size - 1]!!)
            data["data"] = model
            ResponseEntity.ok(data)
        }
    }


    fun findAll(petId: Long?, categoryId: Long?): ResponseEntity<Map<Any, Any>>? {
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
        model["code"] = 200
        model["message"] = "Success"
        model["categories"] = categories
        data["data"] = model
        return ResponseEntity.ok(data)
    }


    fun delete(id: Long): ResponseEntity<Map<Any, Any>>? {
        categoryDao.delete(id, Date())
        data.clear()
        model.clear()
        model["code"] = 200
        model["message"] = "Category with $id deleted"
        model["category"] = Category(categoryDao.getOne(id))
        data["data"] = model
        return ResponseEntity.ok(data)
    }

    fun update(id: Long, photo: MultipartFile?, title: String?, categoryId: Long?, petId: Long?): ResponseEntity<Map<Any, Any>>? {
        val categoryEntity = categoryDao.getOne(id)
        if (categoryEntity == null) {
            data.clear()
            model.clear()
            model["code"] = 400
            model["message"] = "Category with $id not found."
            data["data"] = model
            return ResponseEntity.badRequest().body(data)
        }
        if (photo == null && title == null) {
            data.clear()
            model.clear()
            model["code"] = 400
            model["message"] = "Wrong sent data. Fields \"photo\" and \"title\" could not be null at same time"
            data["data"] = model
            return ResponseEntity.badRequest().body(data)
        }
        if (petId == null) {
            data.clear()
            model.clear()
            model["code"] = 400
            model["message"] = "Wrong sent data. Fields \"petId\" could not be null"
            data["data"] = model
            return ResponseEntity.badRequest().body(data)
        }
        if (photo != null && title != null) {
            categoryDao.update(id, Date(),  Utils.saveFile(fileStorageService,photo), title, null, petId, categoryId)
        } else if (title != null) {
            categoryDao.update(id, Date(), categoryEntity.photo, title, null, petId, categoryId)
        } else {
            categoryDao.update(id, Date(),  Utils.saveFile(fileStorageService,photo), categoryEntity.title, null, petId, categoryId)
        }
        data.clear()
        model.clear()
        model["code"] = 200
        model["message"] = "Category with $title updated."
        model["category"] = Category(categoryDao.getOne(categoryEntity.id!!))
        data["data"] = model
        return ResponseEntity.ok(data)
    }


}