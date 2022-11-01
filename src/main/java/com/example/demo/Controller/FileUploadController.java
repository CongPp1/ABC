package com.example.demo.Controller;

import com.example.demo.Entity.ResponseObject;
import com.example.demo.Service.InterfaceStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping(path = "/api/v1/FileUpload")
public class FileUploadController {
    @Autowired
    InterfaceStorageService interfaceStorageService;
    //This controller receive file/image from client
    @PostMapping("")
    public ResponseEntity<ResponseObject> uploadFile(@RequestParam("file") MultipartFile multipartFile) {
        try {
            //save file to a folder => use a service
            String generatedFileName = interfaceStorageService.storeFile(multipartFile);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok",
                    "Upload file succesfully", generatedFileName));
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok",
                    exception.getMessage(), ""));
        }
    }
}

