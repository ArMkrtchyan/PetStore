package am.petstore.PetStore.pets.service;

import am.petstore.PetStore.pets.dao.PetDao;
import am.petstore.PetStore.pets.entity.PetEntity;
import am.petstore.PetStore.pets.model.Pet;
import am.petstore.PetStore.user.service.FileStorageService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

@Service
@Transactional
public class PetService {
    private Map<Object, Object> data = new HashMap<>();
    private Map<Object, Object> model = new HashMap<>();

    private PetDao petDao;
    private ObjectMapper mapper;
    private FileStorageService fileStorageService;

    @PersistenceContext
    private EntityManager manager;

    @Autowired
    public PetService(PetDao petDao, ObjectMapper mapper, FileStorageService fileStorageService) {
        this.petDao = petDao;
        this.mapper = mapper;
        this.fileStorageService = fileStorageService;
    }

    public ResponseEntity<Map<Object, Object>> create(MultipartFile photo, String title) {
        if (petDao.existsByTitle(title)) {
            if (petDao.findByTitle(title).getDeletedAt() == null) {
                data.clear();
                model.clear();
                model.put("code", 400);
                model.put("message", "Pet with " + title + " already exist.");
                data.put("data", model);
                return ResponseEntity.badRequest().body(data);
            } else {
                PetEntity petEntity = petDao.findByTitle(title);
                petDao.update(petEntity.getId(), new Date(), petEntity.getPhoto(), petEntity.getTitle(), null);
                data.clear();
                model.clear();
                model.put("code", 200);
                model.put("message", "Pet with " + title + " updated.");
                model.put("pet", new Pet(petDao.getOne(petEntity.getId())));
                data.put("data", model);
                return ResponseEntity.ok(data);
            }
        } else {
            String fileDownloadUri = "";
            if (photo != null) {
                LoggerFactory.getLogger("updateUserPhoto  ").info(null);
                String fileName = fileStorageService.storeFile(photo);
                LoggerFactory.getLogger("updateUser").info(fileName);
                fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("user/downloadFile/")
                        .path(fileName)
                        .toUriString();
            }
            PetEntity petEntity = new PetEntity(new Date(), new Date(), title, fileDownloadUri);
            petDao.saveAndFlush(petEntity);
            data.clear();
            model.clear();
            model.put("code", 200);
            model.put("message", "Created");
            model.put("pet", new Pet(petDao.findAll().get(petDao.findAll().size() - 1)));
            data.put("data", model);
            return ResponseEntity.ok(data);
        }
    }


    public ResponseEntity<Map<Object, Object>> findAll() {
        List<Pet> pets = new ArrayList();
        List<PetEntity> petEntities = petDao.findAll();
        for (PetEntity petEntity : petEntities) {
            if (petEntity.getDeletedAt() == null) {
                pets.add(new Pet(petEntity));
            }
        }
        data.clear();
        model.clear();
        model.put("code", 200);
        model.put("message", "Success");
        model.put("pets", pets);
        data.put("data", model);
        return ResponseEntity.ok(data);
    }


    public ResponseEntity<Map<Object, Object>> delete(Long id) {
        petDao.delete(id, new Date());
        data.clear();
        model.clear();
        model.put("code", 200);
        model.put("message", "Pet with " + id + " deleted");
        model.put("pet", new Pet(petDao.getOne(id)));
        data.put("data", model);
        return ResponseEntity.ok(data);
    }

    public ResponseEntity<Map<Object, Object>> update(Long id, JsonNode jsonNode) {
        String photo = jsonNode.get("data").get("photo").asText(petDao.getOne(id).getPhoto());
        String title = jsonNode.get("data").get("title").asText(petDao.getOne(id).getTitle());
        petDao.update(id, new Date(), photo, title, null);
        data.clear();
        model.clear();
        model.put("code", 200);
        model.put("message", "Pet with " + id + " updated");
        model.put("pet", new Pet(petDao.getOne(id)));
        data.put("data", model);
        return ResponseEntity.ok(data);
    }
}
