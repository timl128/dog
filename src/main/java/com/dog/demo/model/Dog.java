package com.dog.demo.model;

import com.dog.demo.model.request.CreateDogRequest;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Getter
@Setter
public class Dog {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long dogId;

    private String breed;

    private Date uploadDate;

    private String s3Link;

    public Dog() {
    }

    public Dog(String breed, String s3Link) {
        this.breed = breed;
        this.s3Link = s3Link;
    }

    public Dog(CreateDogRequest request) {
        this.breed = request.getBreed();
    }
}
