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
    fun findAll(
            @RequestParam(value = "categoryId", required = false) categoryId: String?,
            @RequestParam(value = "page", required = false) page: String?,
            @RequestParam(value = "limit", required = false) limit: String?,
            @RequestParam(value = "sort", required = false) sort: String?
    ): ResponseEntity<*>? = when {
        page != null && limit != null -> {
            if (categoryId != null) {
                service?.findAll(categoryId.toInt(), page.toInt() - 1, limit.toInt(), sort)
            } else {
                service?.findAll(page.toInt() - 1, limit.toInt(), sort)
            }
        }
        page == null || limit == null -> {
            if (categoryId != null) {
                service?.findAll(categoryId.toInt())
            } else {
                service?.findAll()
            }
        }
        else -> {
            service?.findAll()
        }
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