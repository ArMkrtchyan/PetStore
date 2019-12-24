package am.petstore.PetStore.pets.controller;

import am.petstore.PetStore.pets.service.ProductService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService service;

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody JsonNode jsonNode) {
        return service.create(jsonNode);
    }

    @GetMapping("/findAll")
    public ResponseEntity findAll() {
        return service.findAll();
    }

    @PostMapping("/delete")
    public ResponseEntity delete(@RequestBody JsonNode jsonNode) {
        return service.delete(jsonNode);
    }

    @PutMapping("/update")
    public ResponseEntity update(@RequestBody JsonNode jsonNode) {
        return service.update(jsonNode);
    }
}
