package am.petstore.PetStore.user.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import am.petstore.PetStore.user.dao.DeviceDao;
import am.petstore.PetStore.user.entity.Device;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

@Service
@Transactional
public class DeviceService {
    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());
    private Map<Object, Object> data = new HashMap<>();
    private Map<Object, Object> model = new HashMap<>();

    private DeviceDao deviceDao;
    private ObjectMapper mapper;

    @PersistenceContext
    private EntityManager manager;

    @Autowired
    public DeviceService(DeviceDao deviceDao, ObjectMapper mapper) {
        this.deviceDao = deviceDao;
        this.mapper = mapper;
    }


    public ResponseEntity<Map<Object, Object>> addOrUpdateDevice(JsonNode jsonNode) {
        try {
            Device device = mapper.readValue(jsonNode.get("data").toString(), Device.class);
            String uid = device.getUid();
            String installDate = device.getFirst_install_date();
            log.info(device.toString());
            if (deviceDao.existsByUid(uid)) {
                deviceDao.updateDeviceByUid(uid, device.getApp_version(), device.getFirst_install_date(), device.getLanguage(),
                        device.getModel(), device.getPlatform(), device.getSdk_version(), device.getFirebase_token(), new Date());
                device = deviceDao.findByUid(uid);
                LoggerFactory.getLogger("UpdateDevice").info(device.toString());
                Set<String> installDates = device.getInstall_dates();
                boolean a = false;
                for (String install : installDates) {
                    if (install.equals(installDate)) {
                        a = true;
                        break;
                    }
                }
                if (!a) {
                    installDates.add(installDate);
                }

                device.setInstall_dates(installDates);
                manager.merge(device);
                return addOrUpdateDeviceOkResponse(device, "Updated");
            } else {
                Set<String> installDates = new HashSet<>();
                installDates.add(installDate);
                device.setInstall_dates(installDates);
                deviceDao.saveAndFlush(device);
                return addOrUpdateDeviceOkResponse(device, "Created");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return badRequestResponse("Invalid data");
        }
    }

    public ResponseEntity<Map<Object, Object>> setDeviceFree(JsonNode jsonNode) {
        try {
            String uid = jsonNode.get("data").get("uid").asText();
            Device device = deviceDao.findByUid(uid);
            device.setUser(null);
            manager.merge(device);
            return addOrUpdateDeviceOkResponse(device,"Success");
        } catch (Exception e) {
            e.printStackTrace();
            return badRequestResponse("Invalid data");
        }
    }

    private ResponseEntity<Map<Object, Object>> badRequestResponse(String message) {
        data.clear();
        model.clear();
        model.put("code", 400);
        model.put("message", message);
        data.put("data", model);
        return ResponseEntity.badRequest().body(data);
    }

    private ResponseEntity<Map<Object, Object>> addOrUpdateDeviceOkResponse(Device device, String message) {
        data.clear();
        model.clear();
        model.put("code", 200);
        model.put("message", message);
        model.put("device", deviceDao.findByUid(device.getUid()));
        data.put("data", model);
        return ResponseEntity.ok(data);
    }
}
