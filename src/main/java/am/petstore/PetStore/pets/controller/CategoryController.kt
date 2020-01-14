package am.petstore.PetStore.pets.controller

import am.petstore.PetStore.pets.service.CategoryService
import com.fasterxml.jackson.databind.JsonNode
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/category")
class CategoryController @Autowired constructor(private val service: CategoryService) {

    @GetMapping("/findAll")
    fun findAll(): ResponseEntity<*> {
        return service.findAll()
    }

    @PostMapping("/create")
    fun create(@RequestBody jsonNode: JsonNode?): ResponseEntity<*> {
        return service.create(jsonNode)
    }

    @PutMapping("/update")
    fun update(@RequestBody jsonNode: JsonNode?): ResponseEntity<*> {
        return service.update(jsonNode)
    }

    @PostMapping("/delete")
    fun delete(@RequestBody jsonNode: JsonNode?): ResponseEntity<*> {
        return service.delete(jsonNode)
    }
}