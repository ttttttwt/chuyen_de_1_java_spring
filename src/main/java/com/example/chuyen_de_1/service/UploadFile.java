package com.example.chuyen_de_1.service;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.stream.Stream;

@Service
public class UploadFile implements IUploadFile {

    private final Path rootLocation = Paths.get("upload");

    public UploadFile() {
        try {
            Files.createDirectories(rootLocation);
            Files.createDirectories(rootLocation.resolve("image"));
            Files.createDirectories(rootLocation.resolve("video"));
            Files.createDirectories(rootLocation.resolve("audio"));
            Files.createDirectories(rootLocation.resolve("document"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isImageFile(MultipartFile file) {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        return Arrays.asList(new String[] {"png", "jpg", "jpeg", "bmp"}).contains(extension.trim().toLowerCase());
    }

    public boolean isVideoFile(MultipartFile file) {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        return Arrays.asList(new String[] {"mp4", "avi", "mkv", "flv"}).contains(extension.trim().toLowerCase());
    }

    public boolean isAudioFile(MultipartFile file) {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        return Arrays.asList(new String[] {"mp3", "wav", "ogg", "flac"}).contains(extension.trim().toLowerCase());
    }

    public boolean isDocumentFile(MultipartFile file) {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        return Arrays.asList(new String[] {"doc", "docx", "pdf", "txt"}).contains(extension.trim().toLowerCase());
    }

    @Override
    public String uploadFile(MultipartFile file) {
        try {
            String type = "";

            if (file.isEmpty()) {
                throw new RuntimeException("file is empty");
            }

            if (isImageFile(file)) {
                type = "image";
            } else if (isVideoFile(file)) {
                type = "video";
            } else if (isAudioFile(file)) {
                type = "audio";
            } else if (isDocumentFile(file)) {
                type = "document";
            } else {
                throw new RuntimeException("only image, video, audio, document file to store");
            }

            Float size = (float) (file.getSize() / 1000000);
            if (size > 10.0f) {
                throw new RuntimeException("file must be least than 10mb");
            }
            String extension = FilenameUtils.getExtension(file.getOriginalFilename());
            String generaName = file.getOriginalFilename();
            generaName = generaName + "." + extension;

            generaName = type + "/" + generaName;
            Path destinationFilePath= this.rootLocation.resolve(Paths.get(generaName)).normalize().toAbsolutePath();

            try {
                Files.copy(file.getInputStream(), destinationFilePath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                throw new RuntimeException("fail to store file");
            }
            return generaName;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Stream<Path> loadAll() {
        return null;
    }

    @Override
    public byte[] readFileContent(String fileName) {
        return new byte[0];
    }

    @Override
    public void deleteAllFiles() {

    }
}
