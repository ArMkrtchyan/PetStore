package am.petstore.petstore.pets.controller

import am.petstore.petstore.pets.service.PetService
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.runBlocking
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("/pets")
class PetController @Autowired constructor(private val petService: PetService) {

    @GetMapping("/findAll")
    fun findAll(): ResponseEntity<MutableMap<Any, Any>> = runBlocking {
        petService.findAll()
    }


    @PostMapping(value = ["/create"], consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun create(@RequestParam(value = "image", required = false) photo: MultipartFile?,
               @RequestParam(value = "title", required = false) title: String?, request: HttpServletRequest): ResponseEntity<MutableMap<Any, Any>> = runBlocking {
        petService.create(photo, title, request)

    }

    @PostMapping(value = ["/update/{id}"], consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun update(@PathVariable("id") id: Long?, @RequestParam(value = "image", required = false) photo: MultipartFile?,
               @RequestParam(value = "title", required = false) title: String?, request: HttpServletRequest): ResponseEntity<MutableMap<Any, Any>> = runBlocking {
        petService.update(id!!, photo, title, request)

    }

    @PutMapping("/delete/{id}")
    fun delete(@PathVariable("id") id: Long?): ResponseEntity<*> = runBlocking {
        petService.delete(id!!)

    }

    @GetMapping("/downloadFile/{fileName:.+}")
    fun downloadCategoryPhoto(@PathVariable fileName: String): ResponseEntity<*>? = runBlocking {
        petService.downloadPetPhoto(fileName)
    }
}