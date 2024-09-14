package com.faraz.ecomm_project.service;

import com.faraz.ecomm_project.model.Product;
import com.faraz.ecomm_project.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepo repo;
    public List<Product> getAllProducts()
    {
        return repo.findAll();
    }

    public Product getProductById(int id)
    {
        return repo.findById(id).orElse(null);
    }

    //A MultipartFile in Spring is an interface that represents a file uploaded as part of a multipart request, typically when you are submitting a form with file input fields or sending files along with data. This interface allows the handling of file uploads in web applications and services.
    public Product addProduct(Product product, MultipartFile imageFile) throws IOException {
        // Set the image name, type, and data
        product.setImageName(imageFile.getOriginalFilename());//with the help of getters and setters
        product.setImageType(imageFile.getContentType());
        product.setImageData(imageFile.getBytes());  //Its image file  Use setImageData here, not setImageDate
        return repo.save(product);
    }

    public Product updateProduct(int id, Product product, MultipartFile imageFile)throws IOException
    {
        product.setImageData(imageFile.getBytes());
        product.setImageName(imageFile.getOriginalFilename());
        product.setImageType(imageFile.getContentType());

       return  repo.save(product);
    }


    public void deleteProduct(int id)
    {
        repo.deleteById(id);
    }

    public List<Product> searchProducts(String keyword)
    {
        return repo.searchProducts(keyword);
    }
}
