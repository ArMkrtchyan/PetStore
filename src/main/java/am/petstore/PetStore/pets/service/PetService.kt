package am.petstore.PetStore.pets.service

import am.petstore.PetStore.Utils
import am.petstore.PetStore.pets.dao.PetDao
import am.petstore.PetStore.pets.entity.PetEntity
import am.petstore.PetStore.pets.model.Pet
import am.petstore.PetStore.user.service.FileStorageService
import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
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
class PetService @Autowired constructor(private val petDao: PetDao, private val mapper: ObjectMapper, private val fileStorageService: FileStorageService) {
    private val data: MutableMap<Any, Any> = HashMap()
    private val model: MutableMap<Any, Any> = HashMap()

    @PersistenceContext
    private val manager: EntityManager? = null

    fun create(photo: MultipartFile?, title: String?): ResponseEntity<Map<Any, Any>>? {
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
        return if (petDao.existsByTitle(title)) {
            if (petDao.findByTitle(title)?.deletedAt == null) {
                data.clear()
                model.clear()
                model["code"] = 400
                model["message"] = "Pet with $title already exist."
                data["data"] = model
                ResponseEntity.badRequest().body<Map<Any, Any>>(data)
            } else {
                update(petDao.findByTitle(title)?.id!!, photo, title)
            }
        } else {
            val petEntity = PetEntity(Date(), Date(), title, Utils.saveFile(fileStorageService,photo))
            petDao.saveAndFlush(petEntity)
            data.clear()
            model.clear()
            model["code"] = 200
            model["message"] = "Created"
            model["pet"] = Pet(petDao.findAll()[petDao.findAll().size - 1]!!)
            data["data"] = model
            ResponseEntity.ok(data)
        }
    }


    fun findAll(): ResponseEntity<Map<Any, Any>>? {
        val pets: MutableList<Pet?> = ArrayList()
        val petEntities = petDao.findAll()
        for (petEntity in petEntities) {
            if (petEntity?.deletedAt == null) {
                pets.add(Pet(petEntity!!))
            }
        }
        data.clear()
        model.clear()
        model["code"] = 200
        model["message"] = "Success"
        model["pets"] = pets
        data["data"] = model
        return ResponseEntity.ok(data)
    }


    fun delete(id: Long): ResponseEntity<Map<Any, Any>>? {
        petDao.delete(id, Date())
        data.clear()
        model.clear()
        model["code"] = 200
        model["message"] = "Pet with $id deleted"
        model["pet"] = Pet(petDao.getOne(id))
        data["data"] = model
        return ResponseEntity.ok(data)
    }

    fun update(id: Long, photo: MultipartFile?, title: String?): ResponseEntity<Map<Any, Any>>? {
        val petEntity = petDao.getOne(id)
        if (petEntity == null) {
            data.clear()
            model.clear()
            model["code"] = 400
            model["message"] = "Pet with $id not found."
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
        if (photo != null && title != null) {
            petDao.update(id, Date(), Utils.saveFile(fileStorageService,photo), title, null)
        } else if (title != null) {
            petDao.update(id, Date(), petEntity.photo, title, null)
        } else {
            petDao.update(id, Date(), Utils.saveFile(fileStorageService,photo), petEntity.title, null)
        }
        data.clear()
        model.clear()
        model["code"] = 200
        model["message"] = "Pet with $title updated."
        model["pet"] = Pet(petDao.getOne(petEntity.id!!))
        data["data"] = model
        return ResponseEntity.ok(data)
    }
}