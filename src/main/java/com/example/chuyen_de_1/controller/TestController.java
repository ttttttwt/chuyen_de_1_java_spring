package com.example.chuyen_de_1.controller;

import com.example.chuyen_de_1.model.ResponseObject;
import netscape.javascript.JSObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/test")
public class TestController {
    @GetMapping("/hello")
    public ResponseEntity<ResponseObject> test(@RequestBody Map<String, Object> json){
        String name = (String) json.get("name");
        int num = (int) json.get("num");
        return ResponseEntity.ok().body(new ResponseObject("", "hello", name + num));
    }

}
