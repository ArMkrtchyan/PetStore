package am.petstore.PetStore.pets.controller

import am.petstore.PetStore.pets.service.ProductService
import com.fasterxml.jackson.databind.JsonNode
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/product")
class ProductController {
    @Autowired
    private val service: ProductService? = null

    @PostMapping("/create")
    fun create(@RequestBody jsonNode: JsonNode?): ResponseEntity<*> {
        return service!!.create(jsonNode)
    }

    @GetMapping("/findAll")
    fun findAll(): ResponseEntity<*> {
        return service!!.findAll()
    }

    @PostMapping("/delete")
    fun delete(@RequestBody jsonNode: JsonNode?): ResponseEntity<*> {
        return service!!.delete(jsonNode)
    }

    @PutMapping("/update")
    fun update(@RequestBody jsonNode: JsonNode?): ResponseEntity<*> {
        return service!!.update(jsonNode)
    }
}