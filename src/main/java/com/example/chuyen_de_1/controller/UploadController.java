package com.example.chuyen_de_1.controller;

import com.example.chuyen_de_1.model.ResponseObject;
import com.example.chuyen_de_1.service.UploadFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("/file/{type}/{filename:.+}")
    public ResponseEntity<byte[]> readDetailFile(@PathVariable String type ,@PathVariable String filename) {
        try {
            String path = type + "/" + filename;
            byte[] bytes = uploadFile.readFileContent(path);
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(bytes);
        } catch (Exception e) {
            return ResponseEntity.noContent().build();
        }
    }
}
