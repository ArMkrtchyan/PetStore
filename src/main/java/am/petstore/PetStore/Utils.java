package am.petstore.PetStore;

import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.ResponseEntity.badRequest;

public class Utils {
    public static void showErrorLog(String name, String message) {
        LoggerFactory.getLogger(name).error(message);
    }

    public static void showInfoLog(String name, String message) {
        LoggerFactory.getLogger(name).info(message);
    }

    public static ResponseEntity<Map<Object, Object>> userNotFound() {
        Map<Object, Object> model = new HashMap<>();
        model.put("code", 400);
        model.put("message", "User not found");
        return badRequest().body(model);
    }


}
