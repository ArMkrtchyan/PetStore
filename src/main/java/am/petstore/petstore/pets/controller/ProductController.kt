package am.petstore.petstore.pets.controller

import am.petstore.petstore.pets.service.ProductService
import com.fasterxml.jackson.databind.JsonNode
import kotlinx.coroutines.runBlocking
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("/product")
class ProductController {
    @Autowired
    private val service: ProductService? = null

    @PostMapping("/create")
    fun create(@RequestBody jsonNode: JsonNode?, request: HttpServletRequest): ResponseEntity<*> {
        return runBlocking { service!!.create(jsonNode, request) }

    }

    @GetMapping("/findAll")
    fun findAll(
            @RequestParam(value = "categoryId", required = false) categoryId: String?,
            @RequestParam(value = "petId", required = false) petId: String?,
            @RequestParam(value = "page", required = false) page: String?,
            @RequestParam(value = "limit", required = false) limit: String?,
            @RequestParam(value = "sort", required = false) sort: String?
    ): ResponseEntity<*>? = runBlocking {
        when {
            page != null && limit != null -> {
                when {
                    petId != null -> {
                        service?.findAllWithPetId(petId.toInt(), page.toInt() - 1, limit.toInt(), sort)
                    }
                    categoryId != null -> {
                        service?.findAll(categoryId.toInt(), page.toInt() - 1, limit.toInt(), sort)
                    }
                    else -> {
                        service?.findAll(page.toInt() - 1, limit.toInt(), sort)
                    }
                }
            }
            page == null || limit == null -> {
                when {
                    petId != null -> {
                        service?.findAllWithPetId(petId.toInt())
                    }
                    categoryId != null -> {
                        service?.findAll(categoryId.toInt())
                    }
                    else -> {
                        service?.findAll()
                    }
                }
            }
            else -> {
                service?.findAll()
            }
        }
    }


    @PostMapping("/delete")
    fun delete(@RequestBody jsonNode: JsonNode?): ResponseEntity<*> = runBlocking {
        service!!.delete(jsonNode)
    }

    @PutMapping("/update")
    fun update(@RequestBody jsonNode: JsonNode?, request: HttpServletRequest): ResponseEntity<*> = runBlocking {
        service!!.update(jsonNode, request)
    }

    @PostMapping("/addToFavorites/{id}")
    fun addToFavorites(@PathVariable(name = "id") id: Int, request: HttpServletRequest): ResponseEntity<*> = runBlocking {
        service!!.addToFavorites(id, request)
    }

    @PostMapping("/removeFromFavorites/{id}")
    fun removeFromFavorites(@PathVariable(name = "id") id: Int, request: HttpServletRequest): ResponseEntity<*> = runBlocking {
        service!!.removeFromFavorites(id, request)
    }

    @GetMapping("/favorites")
    fun getFavorites(request: HttpServletRequest): ResponseEntity<*>? = runBlocking {
        service?.getFavorites(request)
    }

}