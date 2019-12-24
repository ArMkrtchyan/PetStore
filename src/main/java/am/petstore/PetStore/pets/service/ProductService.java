package am.petstore.PetStore.pets.service;

import am.petstore.PetStore.pets.dao.ProductDao;
import am.petstore.PetStore.pets.model.Store;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class ProductService {

    private Map<Object, Object> data = new HashMap<>();
    private Map<Object, Object> model = new HashMap<>();

    private ProductDao productDao;
    private ObjectMapper mapper;

    @PersistenceContext
    private EntityManager manager;

    public ProductService(ProductDao productDao, ObjectMapper mapper) {
        this.productDao = productDao;
        this.mapper = mapper;
    }

    public ResponseEntity<Map<Object, Object>> create(@RequestBody JsonNode jsonNode) {
        data.clear();
        model.clear();
        model.put("code", 200);
        model.put("message", "Success");
        model.put("store", new Store());
        data.put("data", model);
        return ResponseEntity.ok(data);
    }


    public ResponseEntity<Map<Object, Object>> findAll() {
        data.clear();
        model.clear();
        model.put("code", 200);
        model.put("message", "Success");
        model.put("store", new Store());
        data.put("data", model);
        return ResponseEntity.ok(data);
    }


    public ResponseEntity<Map<Object, Object>> delete(@RequestBody JsonNode jsonNode) {
        data.clear();
        model.clear();
        model.put("code", 200);
        model.put("message", "Success");
        model.put("store", new Store());
        data.put("data", model);
        return ResponseEntity.ok(data);
    }


    public ResponseEntity<Map<Object, Object>> update(@RequestBody JsonNode jsonNode) {
        data.clear();
        model.clear();
        model.put("code", 200);
        model.put("message", "Success");
        model.put("store", new Store());
        data.put("data", model);
        return ResponseEntity.ok(data);
    }
}
