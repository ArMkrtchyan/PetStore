package am.petstore.PetStore.order.controller

import com.fasterxml.jackson.databind.JsonNode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/order")
class OrderController {
    @GetMapping("/findAll")
    fun findAll(@RequestBody jsonNode: JsonNode?): ResponseEntity<*>? {
        return null
    }
}