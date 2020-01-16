package am.petstore.PetStore.pets.controller


import am.petstore.PetStore.pets.service.CategoryService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/category")
class CategoryController @Autowired constructor(private val service: CategoryService) {

    @GetMapping("/findAll")
    fun findAll(@RequestParam(value = "petId", required = false) petId: String?,
                @RequestParam(value = "categoryId", required = false) categoryId: String?): ResponseEntity<*>? {
        return service.findAll(petId?.toLong(), categoryId?.toLong())
    }

    @PostMapping(value = ["/create"], consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun create(@RequestParam(value = "image", required = false) photo: MultipartFile?,
               @RequestParam(value = "petId", required = false) petId: String?,
               @RequestParam(value = "title", required = false) title: String?,
               @RequestParam(value = "categoryId", required = false) categoryId: String?): ResponseEntity<Map<Any, Any>>? {
        return service.create(photo, title, petId?.toLong(), categoryId?.toLong())
    }

    @PostMapping(value = ["/update/{id}"], consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun update(@PathVariable("id") id: Long?, @RequestParam(value = "image", required = false) photo: MultipartFile?,
               @RequestParam(value = "petId", required = false) petId: String?,
               @RequestParam(value = "title", required = false) title: String?,
               @RequestParam(value = "categoryId", required = false) categoryId: String?): ResponseEntity<Map<Any, Any>>? {
        return service.update(id!!, photo, title, categoryId?.toLong(), petId?.toLong())
    }

    @PutMapping("/delete/{id}")
    fun delete(@PathVariable("id") id: Long?): ResponseEntity<*>? {
        return service.delete(id!!)
    }


}