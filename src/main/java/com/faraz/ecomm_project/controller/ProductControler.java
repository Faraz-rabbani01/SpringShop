package com.faraz.ecomm_project.controller;

import com.faraz.ecomm_project.model.Product;
import com.faraz.ecomm_project.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ProductControler
{
    @Autowired
    private ProductService service;

    @RequestMapping("/")
    public String greet()
    {
        return "Hello World";
    }
    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts()//in order to return the data as well as response or status like bad gateway or the data is not found or found we use responseentity
    {
        return new ResponseEntity<>(service.getAllProducts(), HttpStatus.OK );
    }
    @GetMapping ("/product/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable int id)
    {
        Product product = service.getProductById(id);
        if(product != null) //prod is an object of Project so what it will do take the id and match that the id is available or not and if it is available then it will return the product of that id or else it will return that not found with the help of responseentity
            return new ResponseEntity<>(product, HttpStatus.OK);//in order to return the data as well as response or status like bad gateway or the data is not found or found we use responseentity
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    //@RequestBody is meant for single part requests, typically used to bind JSON or XML data, while @RequestPart is for handling multipart requests (such as files and form data).
    //@RequestBody deals with the entire body as one, whereas @RequestPart is used to handle multiple parts within the body of a multipart request.
    @PostMapping("/product")
    public ResponseEntity<?> addProduct(@RequestPart Product product,
                                        @RequestPart MultipartFile imageFile)//because data and images are of different type so first part will accept the data usually json and other will accept image
    {try {
        Product product1 =  service.addProduct(product, imageFile);
        return new ResponseEntity<>(product1, HttpStatus.CREATED);
    } catch (Exception e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    }
    @GetMapping("/product/{productId}/image")
    public ResponseEntity<byte []> getImageByProductId(@PathVariable int productId)
    {
        Product product = service.getProductById(productId);
        byte[] imageFile = product.getImageData();

        return ResponseEntity.ok().contentType(MediaType.valueOf(product.getImageType())).body(imageFile);
    }
    @PutMapping("/product/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable int id, @RequestPart Product product, @RequestPart MultipartFile imageFile)
    {   Product product1 = null;
        try {
            product1 = service.updateProduct(id, product, imageFile);
        } catch (IOException e) {
//            throw new RuntimeException(e);
        }
        if (product1 != null)
            return new ResponseEntity<>("Updated", HttpStatus.OK);
        else
            return new ResponseEntity<>("Failed to update", HttpStatus.BAD_REQUEST);
    }
    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable int id)
    {
        Product product = service.getProductById(id);
        if(product != null) {
            service.deleteProduct(id);
            return new ResponseEntity<>("updated", HttpStatus.OK);
        }
        else
            return new ResponseEntity<>("Product not found", HttpStatus.OK);
    }
    @GetMapping("/product/search")
    public ResponseEntity<List<Product>> searchProducts(String keyword)
    {
        List<Product> products = service.searchProducts(keyword);
        return new ResponseEntity<>(products,HttpStatus.OK);
    }

}

