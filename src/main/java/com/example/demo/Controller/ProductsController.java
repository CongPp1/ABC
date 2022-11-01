package com.example.demo.Controller;


import com.example.demo.Entity.Product;
import com.example.demo.Entity.ResponseObject;
import com.example.demo.Repository.ProductRepository;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController // Bao cho Spring biet day la COntroller
@RequestMapping("/api/v1/Products") // anh xa cac request
public class ProductsController {
    //DI: dependency Injection
    @Autowired // Doi tuong co annotation nay se duoc khoi tao ngay khi app khoi dong; chi can khoi tao 1 lan
    ProductRepository productRepository;
    @GetMapping("")
    // this request is: http://localhost:8080/api/v1/Products
    List<Product> GetAllProducts(){
        return productRepository.findAll(); // in ra tat ca data da duoc luu
        //You must save this in Database
    }
    //Get detail product:
    @GetMapping("{id}")
    ResponseEntity<ResponseObject> findById(@PathVariable Long id) {
        Optional<Product> foundProduct = productRepository.findById(id);
        if (foundProduct.isPresent()) { //Kiem tra xem data ben trong Optional co foundProduct hay khong
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok",
                    "Query product successfully", foundProduct));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("false",
                    "Cannot find product with ID = "  + id,
                    ""));
        }
    }
    //Insert new product with PostMapping:
    @PostMapping("/insert")
    ResponseEntity<ResponseObject> InsertNewProduct(@RequestBody Product newProduct){
        List<Product> foundProduct = productRepository.findByProductName(newProduct.getProductName().trim());
        // Kiem tra xem san pham vua nhap vao da ton tai hay chua:
        if(foundProduct.size() > 0){
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body((new ResponseObject("Failed",
                    "Product name already taken", "")));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK",
                "Insert product successfully", productRepository.save(newProduct)));
        }
    //Update, upsert = update if found, otherwise insert:
    @PutMapping("/{id}")
    ResponseEntity<ResponseObject> updateProduct(@RequestBody Product newProduct, @PathVariable Long id){
        Product updateProduct = productRepository.findById(id).map(product -> {
            product.setProductName(newProduct.getProductName());
            product.setYear(newProduct.getYear());
            product.setPrice(newProduct.getPrice());
            product.setUrl(newProduct.getUrl());
            return productRepository.save(product);
        }).orElseGet(()-> { // Nếu như không tìm thấy sp thì sẽ tạo 1 sp mới
            newProduct.setId(id);
            return productRepository.save(newProduct);
        });
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK",
                "Update product successfully", updateProduct));
    }
    // Delete product = delete method:
    @DeleteMapping("/{id}")
    ResponseEntity<ResponseObject> deleteProduct( @PathVariable Long id){
        boolean existsProduct = productRepository.existsById(id);
        if(existsProduct){
            productRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK",
                    "Delete product successfully", ""));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("Failed",
                "Cannot find product to delete", ""));
    }
}

