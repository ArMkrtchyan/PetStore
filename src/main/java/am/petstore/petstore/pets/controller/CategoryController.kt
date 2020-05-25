package am.petstore.petstore.pets.controller


import am.petstore.petstore.pets.service.CategoryService
import kotlinx.coroutines.runBlocking
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("/category")
class CategoryController @Autowired constructor(private val service: CategoryService) {

    @GetMapping("/findAll")
    fun findAll(@RequestParam(value = "petId", required = false) petId: String?,
                @RequestParam(value = "categoryId", required = false) categoryId: String?): ResponseEntity<*>? {
        return runBlocking { service.findAll(petId?.toLong(), categoryId?.toLong()) }
    }

    @PostMapping(value = ["/create"], consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun create(@RequestParam(value = "image", required = false) photo: MultipartFile?,
               @RequestParam(value = "petId", required = false) petId: String?,
               @RequestParam(value = "title", required = false) title: String?,
               @RequestParam(value = "categoryId", required = false) categoryId: String?, request: HttpServletRequest): ResponseEntity<MutableMap<Any, Any>>? {
        return runBlocking { service.create(photo, title, petId?.toLong(), categoryId?.toLong(), request) }

    }

    @PostMapping(value = ["/update/{id}"], consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun update(@PathVariable("id") id: Long?, @RequestParam(value = "image", required = false) photo: MultipartFile?,
               @RequestParam(value = "petId", required = false) petId: String?,
               @RequestParam(value = "title", required = false) title: String?,
               @RequestParam(value = "categoryId", required = false) categoryId: String?, request: HttpServletRequest): ResponseEntity<MutableMap<Any, Any>>? {
        return runBlocking { service.update(id!!, photo, title, categoryId?.toLong(), petId?.toLong(), request) }

    }

    @PutMapping("/delete/{id}")
    fun delete(@PathVariable("id") id: Long?): ResponseEntity<*>? {
        return runBlocking { service.delete(id!!) }
    }

    @GetMapping("/downloadFile/{fileName:.+}")
    fun downloadCategoryPhoto(@PathVariable fileName: String): ResponseEntity<*>? = runBlocking {
        service.downloadCategoryPhoto(fileName)
    }
}