package am.petstore.PetStore.user.controller;

import am.petstore.PetStore.user.service.DeviceService;
import com.fasterxml.jackson.databind.JsonNode;
import am.petstore.PetStore.user.jwt.JwtTokenProvider;
import am.petstore.PetStore.user.service.AuthProvider;
import am.petstore.PetStore.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/user")
public class Controller {

    private static final Logger log = LoggerFactory.getLogger("application");

    private UserService userService;
    private DeviceService deviceService;
    private AuthProvider authenticationManager;
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    public Controller(UserService userService, DeviceService deviceService, AuthProvider authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.deviceService = deviceService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/device")
    public ResponseEntity registerDevice(@RequestBody JsonNode jsonNode) {
        return deviceService.addOrUpdateDevice(jsonNode);
    }

    @PostMapping("/device/free")
    public ResponseEntity setDeviceFree(@RequestBody JsonNode jsonNode) {
        return deviceService.setDeviceFree(jsonNode);
    }

    @PostMapping("/signup/phone")
    public ResponseEntity registerPhone(@RequestBody JsonNode data) {
        return userService.registerPhoneNumber(data);
    }

    @PostMapping(value = "/signup", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity signUpUser(@RequestParam(value = "image", required = false) MultipartFile photo,
                                     @RequestParam(value = "id", required = false) String id,
                                     @RequestParam(value = "phone", required = false) String phone,
                                     @RequestParam(value = "firebase_id", required = false) String firebase_id,
                                     @RequestParam(value = "fullname", required = false) String fullname,
                                     @RequestParam(value = "email", required = false) String email,
                                     @RequestParam(value = "gender", required = false) String gender,
                                     @RequestParam(value = "birthday", required = false) String birthday,
                                     @RequestParam(value = "device_id", required = false) String device_id,
                                     @RequestParam(value = "password", required = false) String password) {
        LoggerFactory.getLogger("updateUserId1  ").info(photo.toString());
        return userService.signUpUser(photo, id, phone, firebase_id, fullname, email, gender, birthday, device_id, password);
    }

    @PostMapping("/signin")
    public ResponseEntity signIn(@RequestBody JsonNode data) {
        return userService.signIn(data, jwtTokenProvider, authenticationManager);
    }

    @PutMapping("/update")
    public ResponseEntity updateUser(@RequestBody JsonNode data) {
        return userService.updateUser(data);
    }

    @PutMapping("/signout/{id}")
    public ResponseEntity signOut(@PathVariable("id") Long id, @RequestBody JsonNode data) {
        return userService.signOut(id, data);
    }

    @DeleteMapping("/update/{id}")
    public ResponseEntity deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }

    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity downloadFileFromLocal(@PathVariable String fileName) {
        return userService.downloadImage(fileName);
    }

    @PostMapping(value = "/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity uploadFile(@RequestParam(value = "image", required = false) MultipartFile photo) {
        return userService.uploadImage(photo);
    }
}
