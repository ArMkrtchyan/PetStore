package am.petstore.PetStore.pets.controller

import am.petstore.PetStore.pets.service.PetService
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.runBlocking
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.reactive.function.client.WebClient

@RestController
@RequestMapping("/pets")
class PetController @Autowired constructor(private val petService: PetService) {

    @FlowPreview
    @GetMapping("/findAll")
    fun findAll(): ResponseEntity<MutableMap<Any, Any>> {
        return runBlocking {
            petService.findAll()
        }
    }

    @PostMapping(value = ["/create"], consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun create(@RequestParam(value = "image", required = false) photo: MultipartFile?,
               @RequestParam(value = "title", required = false) title: String?): ResponseEntity<Map<Any, Any>>? {
        return petService.create(photo, title)
    }

    @PostMapping(value = ["/update/{id}"], consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun update(@PathVariable("id") id: Long?, @RequestParam(value = "image", required = false) photo: MultipartFile?,
               @RequestParam(value = "title", required = false) title: String?): ResponseEntity<Map<Any, Any>>? {
        return petService.update(id!!, photo, title)
    }

    @PutMapping("/delete/{id}")
    fun delete(@PathVariable("id") id: Long?): ResponseEntity<*>? {
        return petService.delete(id!!)
    }

}