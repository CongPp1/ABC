package com.example.demo.Service;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import static org.apache.commons.io.FilenameUtils.normalize;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Stream;




@Service // Để JavaSpring hiểu đây là 1 service

public class StorageService implements InterfaceStorageService{
    private final Path storageFolder = Paths.get("uploads"); // Dùng để tham chiếu đến thư mục upload ảnh
    //Contrucstor:
    public StorageService(){
        try {
            Files.createDirectories(storageFolder);
        }catch(IOException ioException){
            throw new RuntimeException("Cannot initialize storage", ioException);
        }
    }
    private boolean isImageFile(MultipartFile file){
        //Let install FileNameUtils:
        String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
        return Arrays.asList(new String[] {"png", "jpg", "jpeg", "bmp"}).contains(fileExtension.trim().toLowerCase());
    }
    @Override
    public String storeFile(MultipartFile multipartFile) {
       try {
           System.out.println("haha");
            if(multipartFile.isEmpty()){
                throw new RuntimeException("Failed to store empty file");
            }
            //Check file image?
            if(!isImageFile(multipartFile)){
                throw new RuntimeException("You can only upload image file");
            }
            //File's memory must be <= 5mb:
           float memorySize = multipartFile.getSize() / 1_000_000.0f;
           if(memorySize > 5.0f){
                throw new RuntimeException("File's memory size must be <= 5mb");
           }
           //File must be rename:
           String fileExtension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
           String generatedFilename = UUID.randomUUID().toString().replace("-", "");
           generatedFilename = generatedFilename + "." + fileExtension;
           Path destinationFilePath = storageFolder.resolve(Paths.get(generatedFilename));
           if(!destinationFilePath.getParent().equals(storageFolder.toAbsolutePath())){
                throw  new RuntimeException("Cannot store file outside current directory");
                //Copy file vua roi vao destinationFIle
           }try(InputStream inputStream = multipartFile.getInputStream()){
               Files.copy(inputStream, destinationFilePath, StandardCopyOption.REPLACE_EXISTING);
           }
           return generatedFilename;
       }catch(IOException ioException){
           throw new RuntimeException("Failed to store file", ioException);
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
    public void deleteAllFile() {

    }
}
