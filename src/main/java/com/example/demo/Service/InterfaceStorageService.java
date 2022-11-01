package com.example.demo.Service;

import org.springframework.web.multipart.MultipartFile;

import java.nio.channels.MulticastChannel;
import java.nio.file.Path;
import java.util.stream.Stream;

public interface InterfaceStorageService {
    String storeFile(MultipartFile multipartFile);
    Stream<Path> loadAll(); // Load all file inside a folder
    byte[] readFileContent(String fileName);
    void deleteAllFile();
}
