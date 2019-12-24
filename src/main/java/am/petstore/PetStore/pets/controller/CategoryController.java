package am.petstore.PetStore.pets.controller;

import am.petstore.PetStore.pets.service.CategoryService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService service;

    @GetMapping("/findAll")
    public ResponseEntity findAll() { return service.findAll(); }

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody JsonNode jsonNode) {
        return service.create(jsonNode);
    }

    @PutMapping("/update")
    public ResponseEntity update(@RequestBody JsonNode jsonNode) {
        return service.update(jsonNode);
    }

    @PostMapping("/delete")
    public ResponseEntity delete(@RequestBody JsonNode jsonNode) {
        return service.delete(jsonNode);
    }
}
