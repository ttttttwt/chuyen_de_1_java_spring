package com.example.chuyen_de_1.controller;

import com.example.chuyen_de_1.model.ResponseObject;
import com.example.chuyen_de_1.service.UploadFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/upload")
public class UploadController {

    @Autowired
    UploadFile uploadFile;

    @PostMapping("/uploadFile")
    public ResponseEntity<ResponseObject> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            String generatedFileName = uploadFile.uploadFile(file);
            return ResponseEntity.ok(new ResponseObject("ok", "success", generatedFileName));
        } catch (Exception e) {
            return ResponseEntity.ok(new ResponseObject("error", "error", ""));
        }

    }
}
