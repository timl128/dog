package com.dog.demo.controller;

import com.dog.demo.model.Dog;
import com.dog.demo.model.request.CreateDogRequest;
import com.dog.demo.service.DogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class DogController {

    @Autowired
    private DogService dogService;

    /**
     * get dog
     * @param id
     * @return
     */
    @GetMapping(value = "/dog/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Dog getDog(@PathVariable("id") long id){
        return dogService.getDogById(id);
    }

    /**
     * delete dog
     * @param id
     * @return
     */
    @DeleteMapping(value = "/dog/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map deleteDog(@PathVariable("id") long id){

        Map<String,Boolean> result = new HashMap<>();
        result.put("operation",dogService.removeDogById(id));
        return result;
    }

    /**
     * get dogs by breed
     * @param breed
     * @return
     */
    @GetMapping(value = "/dog/breed/{breed}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Dog> getBreed(@PathVariable("breed") String breed){
        return dogService.getDogsByBreed(breed);
    }

    /**
     * get all breed
     * @return
     */
    @GetMapping(value = "/dog/list/breed", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<String> listBreed(){
        return dogService.getAllBreedNames();
    }


    /**
     * create a new dog record
     * @return
     */
    @PostMapping(value = "/dog", produces = MediaType.APPLICATION_JSON_VALUE)
    public Dog createDog(@Valid @RequestBody CreateDogRequest request){
        return dogService.createDogs(request);
    }
}
