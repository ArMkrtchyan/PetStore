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

    @GetMapping("/description/findAll")
    fun findAllDescriptions(@RequestBody jsonNode: JsonNode?): ResponseEntity<*>? {
        return service!!.findAllDescription()
    }

    @PostMapping("/description/create")
    fun createDescriptions(@RequestBody jsonNode: JsonNode?): ResponseEntity<*>? {
        return service!!.createDescription(jsonNode)
    }

    @PutMapping("/description/update")
    fun updateDescriptions(@RequestBody jsonNode: JsonNode?): ResponseEntity<*>? {
        return service!!.updateDescription(jsonNode)
    }

    @PostMapping("/description/delete")
    fun deleteDescriptions(@RequestBody jsonNode: JsonNode?): ResponseEntity<*>? {
        return service!!.deleteDescription(jsonNode)
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


    @GetMapping("/photo/findAll")
    fun findAllPhoto(@RequestBody jsonNode: JsonNode?): ResponseEntity<*>? {
        return service!!.findAllPhoto()
    }

    @PostMapping("/photo/create")
    fun createPhoto(@RequestBody jsonNode: JsonNode?): ResponseEntity<*>? {
        return service!!.createPhoto(jsonNode)
    }

    @PutMapping("/photo/update")
    fun updatePhoto(@RequestBody jsonNode: JsonNode?): ResponseEntity<*>? {
        return service!!.updatePhoto(jsonNode)
    }

    @PostMapping("/photo/delete")
    fun deletePhoto(@RequestBody jsonNode: JsonNode?): ResponseEntity<*>? {
        return service!!.deletePhoto(jsonNode)
    }

    @GetMapping("/price/findAll")
    fun findAllPrice(@RequestBody jsonNode: JsonNode?): ResponseEntity<*>? {
        return service!!.findAllPrice()
    }

    @PostMapping("/price/create")
    fun createPrice(@RequestBody jsonNode: JsonNode?): ResponseEntity<*>? {
        return service!!.createPrice(jsonNode)
    }

    @PutMapping("/price/update")
    fun updatePrice(@RequestBody jsonNode: JsonNode?): ResponseEntity<*>? {
        return service!!.updatePrice(jsonNode)
    }

    @PostMapping("/price/delete")
    fun deletePrice(@RequestBody jsonNode: JsonNode?): ResponseEntity<*>? {
        return service!!.deletePrice(jsonNode)
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

    @GetMapping("/title/findAll")
    fun findAllTitle(@RequestBody jsonNode: JsonNode?): ResponseEntity<*>? {
        return service!!.findAllTitle()
    }

    @PostMapping("/title/create")
    fun createTitle(@RequestBody jsonNode: JsonNode?): ResponseEntity<*>? {
        return service!!.createTitle(jsonNode)
    }

    @PutMapping("/title/update")
    fun updateTitle(@RequestBody jsonNode: JsonNode?): ResponseEntity<*>? {
        return service!!.updateTitle(jsonNode)
    }

    @PostMapping("/title/delete")
    fun deleteTitle(@RequestBody jsonNode: JsonNode?): ResponseEntity<*>? {
        return service!!.deleteTitle(jsonNode)
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