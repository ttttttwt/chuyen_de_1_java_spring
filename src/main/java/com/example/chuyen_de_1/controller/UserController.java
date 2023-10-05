package com.example.chuyen_de_1.controller;

import com.example.chuyen_de_1.model.ResponseObject;
import com.example.chuyen_de_1.model.User;
import com.example.chuyen_de_1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/user")
public class
UserController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<ResponseObject> registerUser(@RequestBody User user) {
        User checkUser = userRepository.findByUsername(user.getUsername());
        if(checkUser == null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseObject("created" , "success create user", userRepository.save(user)));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("error" , "error to create user", ""));
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
