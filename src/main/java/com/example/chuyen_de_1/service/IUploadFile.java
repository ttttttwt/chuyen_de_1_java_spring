package com.example.chuyen_de_1.service;

import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

interface IUploadFile {
    String uploadFile(MultipartFile file);

    // load all files
    public Stream<Path> loadAll();

    // read file content
    public byte[] readFileContent(String fileName);

    // delete all files
    public void deleteAllFiles();





}
