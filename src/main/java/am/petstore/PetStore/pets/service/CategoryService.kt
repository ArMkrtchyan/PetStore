package am.petstore.PetStore.pets.service

import am.petstore.PetStore.pets.dao.CategoryDao
import am.petstore.PetStore.pets.entity.CategoryEntity
import am.petstore.PetStore.pets.model.Category
import am.petstore.PetStore.user.service.FileStorageService
import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
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

    fun create(photo: MultipartFile?, title: String?, petId: Long?): ResponseEntity<Map<Any, Any>>? {
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
                update(categoryDao.findByTitle(title)?.id!!, photo, title)
            }
        } else {
            var fileDownloadUri = ""
            LoggerFactory.getLogger("updateUserPhoto  ").info(null)
            val fileName = fileStorageService.storeFile(photo)
            LoggerFactory.getLogger("updateUser").info(fileName)
            fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("user/downloadFile/")
                    .path(fileName)
                    .toUriString()
            val categoryEntity = CategoryEntity(Date(), Date(), title, fileDownloadUri, null, petId)
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


    fun findAll(petId: Long?): ResponseEntity<Map<Any, Any>>? {
        val categories: MutableList<Category?> = ArrayList()
        val categoryEntities = if (petId == null) {
            categoryDao.findAll(Sort.by("id"))
        } else {
            categoryDao.findByPetId(petId,Sort.by("id"))
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

    fun update(id: Long, photo: MultipartFile?, title: String?): ResponseEntity<Map<Any, Any>>? {
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
        var fileDownloadUri = ""
        if (photo != null && title != null) {
            LoggerFactory.getLogger("updateUserPhoto  ").info(null)
            val fileName = fileStorageService.storeFile(photo)
            LoggerFactory.getLogger("updateUser").info(fileName)
            fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("user/downloadFile/")
                    .path(fileName)
                    .toUriString()
            categoryDao.update(id, Date(), fileDownloadUri, title, null)
        } else if (title != null) {
            categoryDao.update(id, Date(), categoryEntity.photo, title, null)
        } else {
            LoggerFactory.getLogger("updateUserPhoto  ").info(null)
            val fileName = fileStorageService.storeFile(photo!!)
            LoggerFactory.getLogger("updateUser").info(fileName)
            fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("user/downloadFile/")
                    .path(fileName)
                    .toUriString()
            categoryDao.update(id, Date(), fileDownloadUri, categoryEntity.title, null)
        }
        data.clear()
        model.clear()
        model["code"] = 200
        model["message"] = "Category with $title updated."
        model["category"] = Category(categoryDao.getOne(categoryEntity.id!!))
        data["data"] = model
        return ResponseEntity.ok(data)
    }

    fun findAllSubcategory(petId: Long?, categoryId: Long?): ResponseEntity<*>? {
        val categories: MutableList<Category?> = ArrayList()
        val categoryEntities: MutableList<CategoryEntity?>
        if (categoryId != null) {
            categoryEntities = categoryDao.findByCategoryId(categoryId)
        } else {
            categoryEntities = categoryDao.findAll()
        }
        for (categoryEntity in categoryEntities) {
            if (categoryEntity?.deletedAt == null && categoryEntity?.categoryId != null) {
                categories.add(Category(categoryEntity))
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

    fun createSubcategory(photo: MultipartFile?, title: String?, categoryId: Long?,petId: Long?): ResponseEntity<Map<Any, Any>>? {
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
        if (categoryId == null) {
            data.clear()
            model.clear()
            model["code"] = 400
            model["message"] = "Field categoryId can't be null or empty."
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
                updateSubcategory(categoryDao.findByTitle(title)?.id!!, photo, title, categoryId)
            }
        } else {
            var fileDownloadUri = ""
            LoggerFactory.getLogger("updateUserPhoto  ").info(null)
            val fileName = fileStorageService.storeFile(photo)
            LoggerFactory.getLogger("updateUser").info(fileName)
            fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("user/downloadFile/")
                    .path(fileName)
                    .toUriString()
            val categoryEntity = CategoryEntity(Date(), Date(), title, fileDownloadUri, categoryId,petId)
            categoryDao.saveAndFlush(categoryEntity)
            data.clear()
            model.clear()
            model["code"] = 200
            model["message"] = "Created"
            model["subcategory"] = Category(categoryDao.findAll()[categoryDao.findAll().size - 1]!!)
            data["data"] = model
            ResponseEntity.ok(data)
        }
    }

    fun updateSubcategory(id: Long, photo: MultipartFile?, title: String?, categoryId: Long?): ResponseEntity<Map<Any, Any>>? {
        val categoryEntity = categoryDao.getOne(id)
        if (categoryEntity == null) {
            data.clear()
            model.clear()
            model["code"] = 400
            model["message"] = "Subcategory with $id not found."
            data["data"] = model
            return ResponseEntity.badRequest().body(data)
        }
        if (categoryId == null) {
            data.clear()
            model.clear()
            model["code"] = 400
            model["message"] = "Field categoryId can't be null or empty."
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
        var fileDownloadUri = ""
        if (photo != null && title != null) {
            LoggerFactory.getLogger("updateUserPhoto  ").info(null)
            val fileName = fileStorageService.storeFile(photo)
            LoggerFactory.getLogger("updateUser").info(fileName)
            fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("user/downloadFile/")
                    .path(fileName)
                    .toUriString()
            categoryDao.update(id, Date(), fileDownloadUri, title, null)
        } else if (title != null) {
            categoryDao.update(id, Date(), categoryEntity.photo, title, null)
        } else {
            LoggerFactory.getLogger("updateUserPhoto  ").info(null)
            val fileName = fileStorageService.storeFile(photo!!)
            LoggerFactory.getLogger("updateUser").info(fileName)
            fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("user/downloadFile/")
                    .path(fileName)
                    .toUriString()
            categoryDao.update(id, Date(), fileDownloadUri, categoryEntity.title, null)
        }
        data.clear()
        model.clear()
        model["code"] = 200
        model["message"] = "Category with $title updated."
        model["subcategory"] = Category(categoryDao.getOne(categoryEntity.id!!))
        data["data"] = model
        return ResponseEntity.ok(data)
    }

    fun deleteSubcategory(id: Long): ResponseEntity<*>? {
        categoryDao.delete(id, Date())
        data.clear()
        model.clear()
        model["code"] = 200
        model["message"] = "Subategory with $id deleted"
        model["subcategory"] = Category(categoryDao.getOne(id))
        data["data"] = model
        return ResponseEntity.ok(data)
    }

}