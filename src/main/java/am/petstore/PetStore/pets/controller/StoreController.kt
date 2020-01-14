package am.petstore.PetStore.pets.controller

import am.petstore.PetStore.pets.service.StoreService
import com.fasterxml.jackson.databind.JsonNode
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/store")
class StoreController {
    @Autowired
    private val service: StoreService? = null

    @GetMapping("/findAll")
    fun findAll(@RequestBody jsonNode: JsonNode?): ResponseEntity<*>? {
        return service!!.findAll()
    }

    @PostMapping("/create")
    fun create(@RequestBody jsonNode: JsonNode?): ResponseEntity<*>? {
        return service!!.create(jsonNode)
    }

    @PutMapping("/update")
    fun update(@RequestBody jsonNode: JsonNode?): ResponseEntity<*>? {
        return service!!.update(jsonNode)
    }

    @PostMapping("/delete")
    fun delete(@RequestBody jsonNode: JsonNode?): ResponseEntity<*>? {
        return service!!.delete(jsonNode)
    }
}