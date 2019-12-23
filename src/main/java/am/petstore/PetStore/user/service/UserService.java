package am.petstore.PetStore.user.service;

import am.petstore.PetStore.user.entity.Device;
import am.petstore.PetStore.user.entity.Role;
import am.petstore.PetStore.user.entity.UserModel;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import am.petstore.PetStore.Utils;
import am.petstore.PetStore.user.dao.DeviceDao;
import am.petstore.PetStore.user.dao.UserDao;
import am.petstore.PetStore.user.exceptions.FileStorageException;
import am.petstore.PetStore.user.jwt.JwtTokenProvider;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Validator;
import java.util.*;

import static org.springframework.http.ResponseEntity.ok;

@Service
@Transactional
public class UserService implements UserDetailsService {
    private Map<Object, Object> responseData = new HashMap<>();
    private Map<Object, Object> model = new HashMap<>();

    private UserDao userDao;
    private ObjectMapper mapper;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private Validator validator;
    private DeviceDao deviceDao;
    private FileStorageService fileStorageService;

    @PersistenceContext
    private EntityManager manager;

    @Autowired
    public UserService(UserDao userDao, ObjectMapper mapper, BCryptPasswordEncoder bCryptPasswordEncoder,
                       Validator validator, DeviceDao deviceDao, FileStorageService fileStorageService, EntityManager manager) {
        this.userDao = userDao;
        this.mapper = mapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.validator = validator;
        this.deviceDao = deviceDao;
        this.fileStorageService = fileStorageService;
        this.manager = manager;
    }


    public ResponseEntity registerPhoneNumber(JsonNode node) {
        try {
            String phone = node.get("data").get("phone").asText("-1");
            LoggerFactory.getLogger("registerPhoneNumber").info(phone);
            UserModel user = userDao.findByPhone(phone);
            if (user != null && user.getEmail() != null && !user.getEmail().equals("")) {
                return badRequestResponse(phone + " is registered. Please sign in or register with other phone number.");
            } else if (user != null && user.getPhone() != null) {
                UserModel newUser = userDao.findByPhone(phone);
                responseData.clear();
                model.clear();
                Map<Object, Object> userResponse = new HashMap<>();
                userResponse.put("id", newUser.getId());
                userResponse.put("phone", newUser.getPhone());
                model.put("code", 200);
                model.put("message", "Phone number is registered");
                model.put("user", userResponse);
                responseData.put("data", model);
                return ResponseEntity.ok(responseData);
            } else {
                Set<Role> roles = new HashSet<>();
                roles.add(Role.USER);
                if (phone.equals("+37493876378")) {
                    roles.add(Role.ADMIN);
                }
                UserModel newUser = new UserModel();
                newUser.setPhone(phone);
                newUser.setRoles(roles);
                userDao.saveAndFlush(newUser);
                newUser = userDao.findByPhone(phone);
                responseData.clear();
                model.clear();
                Map<Object, Object> userResponse = new HashMap<>();
                userResponse.put("id", newUser.getId());
                userResponse.put("phone", newUser.getPhone());
                model.put("code", 200);
                model.put("message", "Phone number is registered");
                model.put("user", userResponse);
                responseData.put("data", model);
                return ResponseEntity.ok(responseData);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return badRequestResponse("Invalid data");
        }
    }

    public ResponseEntity signUpUser(MultipartFile photo,
                                     String id,
                                     String phone,
                                     String firebase_id,
                                     String fullname,
                                     String email,
                                     String gender,
                                     String birthday,
                                     String device_id,
                                     String password) {
        try {
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

            if (!userDao.existsById(Long.parseLong(id))) {
                return badRequestResponse("Invalid user id");
            } else {


                UserModel user = userDao.getOne(Long.parseLong(id));
                LoggerFactory.getLogger("updateUser").info(user.toString());

                LoggerFactory.getLogger("updateUser").info(device_id);
                Device device = deviceDao.findByUid(device_id);
                Set<Device> devices = new HashSet<>();
                if (user.getDevices() != null) {
                    devices = user.getDevices();
                }
                devices.removeIf(device1 -> device1.getUid().equals(device.getUid()));
                devices.add(device);
                userDao.updateUserInfo(Long.parseLong(id), fullname, bCryptPasswordEncoder.encode(password),
                        email, birthday, gender, firebase_id, new Date(), fileDownloadUri);
                UserModel newUser = userDao.getOne(Long.parseLong(id));
                newUser.setDevices(devices);
                manager.merge(newUser);
                responseData.clear();
                model.clear();
                model.put("code", 200);
                model.put("message", "You are successfully registered");
                model.put("user", newUser);
                responseData.put("data", model);
                return ResponseEntity.ok(responseData);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return badRequestResponse("Invalid data");
        }

    }


    public ResponseEntity updateUser(JsonNode jsonNode) {
        JsonNode data = jsonNode.get("data");
        String phone = data.get("phone").asText();
        String newPhone = data.get("new_phone").asText();
        UserModel user = userDao.findByPhone(phone);
        UserModel checkUser = userDao.findByPhone(newPhone);
        if (checkUser != null) {
            return badRequestResponse(phone + " is registered. Please sign in or register with other phone number.");
        }
        userDao.updateUserPhone(user.getId(), newPhone, new Date());
        user = userDao.findByPhone(newPhone);
        responseData.clear();
        model.clear();
        model.put("code", 200);
        model.put("message", "Success");
        model.put("user", user);
        responseData.put("data", model);
        return ResponseEntity.ok(responseData);
    }

    public ResponseEntity signIn(JsonNode jsonNode, JwtTokenProvider jwtTokenProvider,
                                 AuthProvider authenticationManager) {
        JsonNode data = jsonNode.get("data");
        String device_id = data.get("device_id").asText();
        String phone = data.get("phone").asText();
        String password = data.get("password").asText();
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(phone, password));
        } catch (BadCredentialsException e) {
            Utils.showErrorLog(this.getClass().getName(), e.getMessage());
            return badRequestResponse(e.getMessage());
        }
        String token = jwtTokenProvider.createToken(userDao.findByPhone(phone).getPhone()
                , userDao.findByPhone(phone).getAuthorities());
        UserModel user = userDao.findByPhone(phone);
        Device device = deviceDao.findByUid(device_id);
        LoggerFactory.getLogger("devices").info(device.toString());
        Set<Device> devices = user.getDevices();
        for (Device device1 : user.getDevices()) {
            if (device1.getUid().equals(device.getUid())) {
                devices.remove(device1);
            }
        }
        devices.add(device);
        LoggerFactory.getLogger("devices").info(devices.toString());
        user.setDevices(devices);
        manager.merge(user);
        responseData.clear();
        model.clear();
        model.put("code", 200);
        model.put("message", "Success");
        model.put("user", user);
        model.put("token", token);
        responseData.put("data", model);
        return ResponseEntity.ok(responseData);
    }

    public ResponseEntity deleteUser(Long id) {
        return null;
    }

    public ResponseEntity signOut(Long id, JsonNode jsonNode) {
        try {
            JsonNode data = jsonNode.get("data");
            String device_id = data.get("device_id").asText();
            Device device = deviceDao.findByUid(device_id);
            UserModel user = userDao.getOne(id);
            Set<Device> devices = user.getDevices();
            for (Device device1 : user.getDevices()) {
                if (device1.getUid().equals(device.getUid())) {
                    devices.remove(device1);
                }
            }
            manager.merge(user);
            responseData.clear();
            model.clear();
            model.put("code", 200);
            model.put("message", "User with id " + id + " is sign out.");
            responseData.put("data", model);
            return ResponseEntity.ok(responseData);
        } catch (Exception e) {
            return badRequestResponse("Invalid data");
        }
    }


    private ResponseEntity<Map<Object, Object>> badRequestResponse(String message) {
        responseData.clear();
        model.clear();
        model.put("code", 400);
        model.put("message", message);
        responseData.put("data", model);
        return ResponseEntity.badRequest().body(responseData);
    }


    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        UserDetails user = userDao.findByPhone(phone);
        return user;
    }

    public ResponseEntity downloadImage(String fileName) {
        Resource resource = fileStorageService.loadFileAsResource(fileName);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    public ResponseEntity uploadImage(MultipartFile photo) {
        try {
            String fileDownloadUri = "";
            if (photo != null) {
                String fileName = fileStorageService.storeFile(photo);
                LoggerFactory.getLogger("updateUser").info(fileName);
                fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("user/downloadFile/")
                        .path(fileName)
                        .toUriString();
            }
            responseData.clear();
            model.clear();
            model.put("code", 200);
            model.put("message", "Success");
            model.put("photo", fileDownloadUri);
            responseData.put("data", model);
            return ResponseEntity.ok(responseData);
        }catch (FileStorageException f){
            return badRequestResponse(f.getMessage());
        }catch (Exception e) {
            return badRequestResponse("Something went wrong");
        }

    }
}
