package am.petstore.PetStore.pets.controller;

import am.petstore.PetStore.pets.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/pets")
public class PetController {

    private final PetService service;

    @Autowired
    public PetController(PetService service) {
        this.service = service;
    }

    @GetMapping("/findAll")
    public ResponseEntity findAll() {
        return service.findAll();
    }


    @PostMapping(value = "/create", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Map<Object, Object>> create(@RequestParam(value = "image", required = false) MultipartFile photo,
                                                      @RequestParam(value = "title", required = false) String title) {
        return service.create(photo, title);
    }

    @PostMapping(value = "/update/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Map<Object, Object>> update(@PathVariable("id") Long id, @RequestParam(value = "image", required = false) MultipartFile photo,
                                                      @RequestParam(value = "title", required = false) String title) {
        return service.update(id, photo, title);
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        return service.delete(id);
    }
}
