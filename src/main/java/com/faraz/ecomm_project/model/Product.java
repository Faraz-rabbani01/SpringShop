package com.faraz.ecomm_project.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//The @GeneratedValue(strategy = GenerationType.IDENTITY) annotation in JPA (Java Persistence API) specifies how the primary key values should be automatically generated for an entity. It is used with the @Id annotation to indicate that the field should be the primary key for the entity.
    private int id;
    private String name;
    private String description;
    private String brand;
    private BigDecimal price;
    private String category;

   //  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yy")// this helps in changing date format with the help of jackson Library
    private Date releaseDate;
    private boolean productAvailable;
    private int stockQuantity;

    private String imageName;
    private String imageType;

    @Lob
    private byte[] imageData;

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }


}
