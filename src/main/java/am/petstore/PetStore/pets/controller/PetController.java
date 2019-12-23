package am.petstore.PetStore.pets.controller;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pets")
public class PetController {

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody JsonNode jsonNode) {
        return null;
    }

    @GetMapping("/findAll")
    public ResponseEntity findAll(@RequestBody JsonNode jsonNode) {
        return null;
    }

    @PostMapping("/delete")
    public ResponseEntity delete(@RequestBody JsonNode jsonNode) {
        return null;
    }

    @PutMapping("/update")
    public ResponseEntity update(@RequestBody JsonNode jsonNode) {
        return null;
    }
}
