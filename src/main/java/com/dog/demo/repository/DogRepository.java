package com.dog.demo.repository;

import com.dog.demo.model.Dog;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DogRepository extends CrudRepository<Dog,Long> {

    List<Dog> findAllByBreed(String breed);

    @Query( value = "select distinct breed from Dog")
    List<String> findDistinctByBreed();
}
