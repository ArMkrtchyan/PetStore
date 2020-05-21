package am.petstore.petstore.pets.controller

import am.petstore.petstore.pets.service.OptionsService
import com.fasterxml.jackson.databind.JsonNode
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/options")
class OptionsController {
    @Autowired
    private val service: OptionsService? = null

    @GetMapping("/color/findAll")
    fun findAllColors(@RequestBody jsonNode: JsonNode?): ResponseEntity<*>? {
        return service!!.findAllColors()
    }

    @PostMapping("/color/create")
    fun createColor(@RequestBody jsonNode: JsonNode?): ResponseEntity<*>? {
        return service!!.createColor(jsonNode)
    }

    @PutMapping("/color/update")
    fun updateColor(@RequestBody jsonNode: JsonNode?): ResponseEntity<*>? {
        return service!!.updateColor(jsonNode)
    }

    @PostMapping("/color/delete")
    fun deleteColor(@RequestBody jsonNode: JsonNode?): ResponseEntity<*>? {
        return service!!.deleteColor(jsonNode)
    }

    @GetMapping("/capacity/findAll")
    fun findAllCapacities(@RequestBody jsonNode: JsonNode?): ResponseEntity<*>? {
        return service!!.findAllCapacities()
    }

    @PostMapping("/capacity/create")
    fun createCapacity(@RequestBody jsonNode: JsonNode?): ResponseEntity<*>? {
        return service!!.createCapacity(jsonNode)
    }

    @PutMapping("/capacity/update")
    fun updateCapacity(@RequestBody jsonNode: JsonNode?): ResponseEntity<*>? {
        return service!!.updateCapacity(jsonNode)
    }

    @PostMapping("/capacity/delete")
    fun deleteCapacity(@RequestBody jsonNode: JsonNode?): ResponseEntity<*>? {
        return service!!.deleteCapacity(jsonNode)
    }

    @GetMapping("/other/findAll")
    fun findAllOther(@RequestBody jsonNode: JsonNode?): ResponseEntity<*>? {
        return service!!.findAllOtherOptions()
    }

    @PostMapping("/other/create")
    fun createOther(@RequestBody jsonNode: JsonNode?): ResponseEntity<*>? {
        return service!!.createOtherOptions(jsonNode)
    }

    @PutMapping("/other/update")
    fun updateOther(@RequestBody jsonNode: JsonNode?): ResponseEntity<*>? {
        return service!!.updateOtherOptions(jsonNode)
    }

    @PostMapping("/other/delete")
    fun deleteOther(@RequestBody jsonNode: JsonNode?): ResponseEntity<*>? {
        return service!!.deleteOtherOptions(jsonNode)
    }


    @GetMapping("/findAll")
    fun findAllOption(@RequestBody jsonNode: JsonNode?): ResponseEntity<*>? {
        return service!!.findAllOption()
    }

    @PostMapping("/create")
    fun createOption(@RequestBody jsonNode: JsonNode?): ResponseEntity<*>? {
        return service!!.createOption(jsonNode)
    }

    @PutMapping("/update")
    fun updateOption(@RequestBody jsonNode: JsonNode?): ResponseEntity<*>? {
        return service!!.updateOption(jsonNode)
    }

    @PostMapping("/delete")
    fun deleteOption(@RequestBody jsonNode: JsonNode?): ResponseEntity<*>? {
        return service!!.deleteOption(jsonNode)
    }

    @GetMapping("/size/findAll")
    fun findAllSize(@RequestBody jsonNode: JsonNode?): ResponseEntity<*>? {
        return service!!.findAllSizes()
    }

    @PostMapping("/size/create")
    fun createSize(@RequestBody jsonNode: JsonNode?): ResponseEntity<*>? {
        return service!!.createSize(jsonNode)
    }

    @PutMapping("/size/update")
    fun updateSize(@RequestBody jsonNode: JsonNode?): ResponseEntity<*>? {
        return service!!.updateSize(jsonNode)
    }

    @PostMapping("/size/delete")
    fun deleteSize(@RequestBody jsonNode: JsonNode?): ResponseEntity<*>? {
        return service!!.deleteSize(jsonNode)
    }

    @GetMapping("/tasty/findAll")
    fun findAllTasty(@RequestBody jsonNode: JsonNode?): ResponseEntity<*>? {
        return service!!.findAllTasty()
    }

    @PostMapping("/tasty/create")
    fun createTasty(@RequestBody jsonNode: JsonNode?): ResponseEntity<*>? {
        return service!!.createTasty(jsonNode)
    }

    @PutMapping("/tasty/update")
    fun updateTasty(@RequestBody jsonNode: JsonNode?): ResponseEntity<*>? {
        return service!!.updateTasty(jsonNode)
    }

    @PostMapping("/tasty/delete")
    fun deleteTasty(@RequestBody jsonNode: JsonNode?): ResponseEntity<*>? {
        return service!!.deleteTasty(jsonNode)
    }

    @GetMapping("/volume/findAll")
    fun findAllVolume(@RequestBody jsonNode: JsonNode?): ResponseEntity<*>? {
        return service!!.findAllVolume()
    }

    @PostMapping("/volume/create")
    fun createVolume(@RequestBody jsonNode: JsonNode?): ResponseEntity<*>? {
        return service!!.createVolume(jsonNode)
    }

    @PutMapping("/volume/update")
    fun updateVolume(@RequestBody jsonNode: JsonNode?): ResponseEntity<*>? {
        return service!!.updateVolume(jsonNode)
    }

    @PostMapping("/volume/delete")
    fun deleteVolume(@RequestBody jsonNode: JsonNode?): ResponseEntity<*>? {
        return service!!.deleteVolume(jsonNode)
    }

    @GetMapping("/weight/findAll")
    fun findAllWeight(@RequestBody jsonNode: JsonNode?): ResponseEntity<*>? {
        return service!!.findAllWeight()
    }

    @PostMapping("/weight/create")
    fun createWeight(@RequestBody jsonNode: JsonNode?): ResponseEntity<*>? {
        return service!!.createWeight(jsonNode)
    }

    @PutMapping("/weight/update")
    fun updateWeight(@RequestBody jsonNode: JsonNode?): ResponseEntity<*>? {
        return service!!.updateWeight(jsonNode)
    }

    @PostMapping("/weight/delete")
    fun deleteWeight(@RequestBody jsonNode: JsonNode?): ResponseEntity<*>? {
        return service!!.deleteWeight(jsonNode)
    }
}