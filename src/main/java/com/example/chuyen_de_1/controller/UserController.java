package com.example.chuyen_de_1.controller;

import com.example.chuyen_de_1.model.ResponseObject;
import com.example.chuyen_de_1.model.User;
import com.example.chuyen_de_1.repository.UserRepository;
import com.example.chuyen_de_1.service.OtpService;
import com.example.chuyen_de_1.service.TempUserService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping(path = "api/v1/user")
public class
UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    OtpService otpService;

    @Autowired
    TempUserService tempUserService;

    @PostMapping("/register")
    public ResponseEntity<ResponseObject> registerUser(@RequestBody User user) throws MessagingException {
        User checkUser = userRepository.findByUsername(user.getUsername());
        if(checkUser == null) {

            int userId = tempUserService.putUser(user.getUsername(), user.getPassword(), user.getEmail());
            String otp = otpService.generatedOtp(String.valueOf(userId));

            otpService.sendOtp(user.getEmail(), otp);

            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseObject("created" , "success create user", userId));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("error" , "error to create user", ""));
    }

    @PostMapping("/verify-json")
    public ResponseEntity<ResponseObject> verifyUserByJson(@RequestBody Map<String, Object> json) throws ExecutionException {

        String otpCode = (String) json.get("otp");
        String userId = (String) json.get("userId");
        boolean isVerify = otpService.checkOtp(otpCode, userId);
        System.out.println(isVerify);
        if (isVerify) {
            User user = tempUserService.getUser(Integer.valueOf(userId));
            User newUser = userRepository.save(user);
            tempUserService.deleteUser(Integer.parseInt(userId));

            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseObject("created" , "success to verify user", String.valueOf(newUser.getId())));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseObject("error" , "error to verify user", ""));

    }

    @PostMapping("/verify")
    public ResponseEntity<ResponseObject> verifyUser(@RequestParam("userId") String userId,
                                                     @RequestParam("otp") String otpCode) throws ExecutionException {

        boolean isVerify = otpService.checkOtp(otpCode, userId);
        System.out.println(isVerify);
        if (isVerify) {
            User user = tempUserService.getUser(Integer.valueOf(userId));
            userRepository.save(user);
            tempUserService.deleteUser(Integer.parseInt(userId));

            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseObject("created" , "success to verify user", ""));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseObject("error" , "error to verify user", ""));
    }

    @PostMapping("/resend-otp-json")
    public ResponseEntity<ResponseObject> reSendOtpJson(@RequestBody Map<String, Object> json) throws ExecutionException, MessagingException {
        String userId = (String) json.get("userId");
        otpService.deleteOtp(userId);
        String otpCode = otpService.generatedOtp(userId);
        otpService.sendOtp(tempUserService.getUser(
                Integer.parseInt(userId)).getEmail(),
                otpCode);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseObject("created" , "Your otp has been sent again", userId));
    }

    @PostMapping("/resend-otp")
    public ResponseEntity<ResponseObject> reSendOtp(@RequestParam("userId") String userId) throws ExecutionException, MessagingException {

        otpService.deleteOtp(userId);
        String otpCode = otpService.generatedOtp(userId);
        otpService.sendOtp(tempUserService.getUser(
                        Integer.parseInt(userId)).getEmail(),
                otpCode);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseObject("created" , "Your otp has been sent again", userId));
    }



    @PostMapping("/login")
    public ResponseEntity<ResponseObject> loginUser(@RequestBody User user) {
        User checkUser = userRepository.findByUsername(user.getUsername());
        if (checkUser.getPassword().equals(user.getPassword())) {
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseObject("Success" , "Login success", ""));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("error" , "Login error", ""));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseObject> updateUser(@PathVariable Long id, @RequestBody User user) {
        User checkUser = userRepository.findById(id).orElse(null);
        if (checkUser != null) {
            checkUser.setUsername(user.getUsername());
            checkUser.setPassword(user.getPassword());
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Success", "Update user success", userRepository.save(checkUser)));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("Error", "Can't find id of user", ""));
    }

    @GetMapping("/name/{username}")
    public ResponseEntity<ResponseObject> findByUsername(@PathVariable String username) {
        User checkUser = userRepository.findByUsername((username));
        if (checkUser != null) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Success", "Find username success", checkUser));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("Error", "Can't find username", checkUser));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<ResponseObject> findById(@PathVariable Long id) {
        User checkUser = userRepository.findById(id).orElse(null);
        if (checkUser != null) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Success", "Find id success", checkUser));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("Error", "Can't find id of user", checkUser));
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<ResponseObject> deleteUser(@PathVariable Long id) {
        User checkUser = userRepository.findById(id).orElse(null);
        if (checkUser != null) {
            userRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Success", "Delete user success", ""));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("Error", "Can't find id of user", ""));
    }

    @GetMapping("/all")
    public ResponseEntity<ResponseObject> getAllUser() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseObject("Ok", "success get all user", userRepository.findAll()));
    }
}
