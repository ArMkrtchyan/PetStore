package am.petstore.petstore.pets.service

import am.petstore.petstore.Utils
import am.petstore.petstore.pets.dao.PetDao
import am.petstore.petstore.pets.entity.PetEntity
import am.petstore.petstore.pets.model.Pet
import am.petstore.petstore.user.service.FileStorageService
import com.fasterxml.jackson.databind.ObjectMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.withContext
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Sort
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import java.util.*
import javax.servlet.http.HttpServletRequest

@Service
@Transactional
class PetService @Autowired constructor(private val petDao: PetDao, private val mapper: ObjectMapper, private val fileStorageService: FileStorageService) {
    private val data: MutableMap<Any, Any> = HashMap()
    private val model: MutableMap<Any, Any> = HashMap()


    suspend fun create(photo: MultipartFile?, title: String?, request: HttpServletRequest): ResponseEntity<MutableMap<Any, Any>> {
        if (photo == null) {
            data.clear()
            model.clear()
            data["code"] = 400
            data["message"] = "Field photo can't be null or empty."
            data["data"] = model
            return ResponseEntity.badRequest().body(data)
        }
        if (title == null) {
            data.clear()
            model.clear()
            data["code"] = 400
            data["message"] = "Field title can't be null or empty."
            return ResponseEntity.badRequest().body(data)
        }
        if (withContext(Dispatchers.Default + Job()) { petDao.existsByTitle(title) }) {
            if (withContext(Dispatchers.Default + Job()) { petDao.findByTitle(title) }?.deletedAt == null) {
                data.clear()
                model.clear()
                data["code"] = 400
                data["message"] = "Pet with $title already exist."
                return ResponseEntity.badRequest().body(data)
            } else {
                return update(withContext(Dispatchers.Default + Job()) { petDao.findByTitle(title) }?.id!!, photo, title, request)
            }
        } else {
            val petEntity = PetEntity(Date(), Date(), title, withContext(Dispatchers.Default + Job()) { Utils.savePetPhoto(fileStorageService, photo, request) })
            withContext(Dispatchers.Default + Job()) { petDao.saveAndFlush(petEntity) }
            data.clear()
            model.clear()
            data["code"] = 200
            data["message"] = "Created"
            model["pet"] = Pet(withContext(Dispatchers.Default + Job()) { petDao.findAll()[petDao.findAll().size - 1]!! })
            data["data"] = model
            return ResponseEntity.ok(data)
        }
    }


    suspend fun findAll(): ResponseEntity<MutableMap<Any, Any>> {
        data.clear()
        model.clear()
        data["code"] = 200
        data["message"] = "Success"
        model["pets"] = withContext(Dispatchers.Default + Job()) { petDao.findAll(Sort.by("id")).filter { it?.deletedAt == null } }
        data["data"] = model
        LoggerFactory.getLogger("Pets").info(model.toString())
        return ResponseEntity.ok(data)
    }


    suspend fun delete(id: Long): ResponseEntity<MutableMap<Any, Any>> {
        petDao.delete(id, Date())
        data.clear()
        model.clear()
        data["code"] = 200
        data["message"] = "Pet with $id deleted"
        model["pet"] = Pet(withContext(Dispatchers.Default + Job()) { petDao.getOne(id) })
        data["data"] = model
        return ResponseEntity.ok(data)
    }


    suspend fun update(id: Long, photo: MultipartFile?, title: String?, request: HttpServletRequest): ResponseEntity<MutableMap<Any, Any>> {
        val petEntity = withContext(Dispatchers.Default + Job()) { petDao.getOne(id) }
        if (withContext(Dispatchers.Default + Job()) { !petDao.existsById(id) }) {
            data.clear()
            model.clear()
            data["code"] = 400
            data["message"] = "Pet with $id not found."
            return ResponseEntity.badRequest().body(data)
        }
        if (photo == null && title == null) {
            data.clear()
            model.clear()
            data["code"] = 400
            data["message"] = "Wrong sent data. Fields \"photo\" and \"title\" could not be null at same time"
            ResponseEntity.badRequest().body(data)
        }
        if (photo != null && title != null) {
            withContext(Dispatchers.Default + Job()) { petDao.update(id, Date(), Utils.savePetPhoto(fileStorageService, photo, request), title, null) }
        } else if (title != null) {
            withContext(Dispatchers.Default + Job()) { petDao.update(id, Date(), petEntity.photo, title, null) }
        } else {
            withContext(Dispatchers.Default + kotlinx.coroutines.Job()) {
                petDao.update(id, Date(), Utils.savePetPhoto(fileStorageService, photo, request), petEntity.title, null)
            }
        }
        data.clear()
        model.clear()
        data["code"] = 200
        data["message"] = "Pet with $title updated."
        model["pet"] = Pet(withContext(Dispatchers.Default + Job()) { petDao.getOne(petEntity.id!!) })
        data["data"] = model
        return ResponseEntity.ok(data)
    }

    suspend fun downloadPetPhoto(fileName: String): ResponseEntity<*> = withContext(Dispatchers.Default) {
        val resource = fileStorageService.loadPetPhoto(fileName)
        ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.filename + "\"")
                .body(resource)
    }
}
