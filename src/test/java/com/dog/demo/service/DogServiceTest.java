package com.dog.demo.service;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.dog.demo.exception.InvalidDogIdException;
import com.dog.demo.model.Dog;
import com.dog.demo.model.api.ApiResponse;
import com.dog.demo.model.request.CreateDogRequest;
import com.dog.demo.repository.DogRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class DogServiceTest {

    @InjectMocks
    private DogService dogService;

    @Mock
    private DogRepository dogRepository;

    @Mock
    private S3Service s3Service;

    @Mock
    private ApiService apiService;

    @Test
    public void testGetDogById() {

        when(dogRepository.findById(1L)).thenReturn(Optional.of(new Dog("a","a")));

        Dog dog  = dogService.getDogById(1L);
        assertThat(dog.getBreed(),equalTo("a"));
    }

    @Test(expected = InvalidDogIdException.class)
    public void testGetDogByIdWithInvalid() {

        when(dogRepository.findById(1L)).thenReturn(Optional.empty());
        dogService.getDogById(1L);
    }

    @Test
    public void testRemoveDogById() {

        when(dogRepository.findById(1L)).thenReturn(Optional.of(new Dog("a","a")));
        assertThat(dogService.removeDogById(1L),equalTo(true));
    }

    @Test(expected = InvalidDogIdException.class)
    public void testInvalidRemoveDogById() {

        when(dogRepository.findById(1L)).thenReturn(Optional.empty());
        dogService.removeDogById(1L);
    }

    @Test
    public void testGetDogsByBreed() {

        List<Dog> dogList = Arrays.asList(new Dog("a","a"));
        when(dogRepository.findAllByBreed("a")).thenReturn(dogList);
        List<Dog> result = dogService.getDogsByBreed("a");
        assertThat(result.get(0).getBreed(),equalTo("a"));
    }

    @Test
    public void testGetDogsByBreedWithEmpty() {

        List<Dog> dogList = new ArrayList<>();
        when(dogRepository.findAllByBreed("a")).thenReturn(dogList);
        List<Dog> result = dogService.getDogsByBreed("a");
        assertThat(result.isEmpty(),equalTo(true));
    }

    @Test
    public void getAllBreedNames() {
        List<String> breedList = Arrays.asList("a","b","c");
        when(dogRepository.findDistinctByBreed()).thenReturn(breedList);
        List<String> result = dogService.getAllBreedNames();
        assertThat(result.get(0),equalTo("a"));
    }

    @Test
    public void getAllBreedNamesWithEmpty() {
        List<String> breedList = new ArrayList<>();
        when(dogRepository.findDistinctByBreed()).thenReturn(breedList);
        List<String> result = dogService.getAllBreedNames();
        assertThat(result.isEmpty(),equalTo(true));
    }

    @Test
    public void createDogs() {

        CreateDogRequest request = new CreateDogRequest();
        request.setBreed("a");

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("https://images.dog.ceo//breeds//sheepdog-english//n02105641_13779.jpg");
        when(apiService.getRandomDog()).thenReturn(apiResponse);

        PutObjectResult putObjectResult = new PutObjectResult();
        ObjectMetadata objectMetadata = new ObjectMetadata();
        putObjectResult.setMetadata(objectMetadata);
        when(s3Service.uploadFile(any(),any(),any())).thenReturn(putObjectResult);

        Dog newDog = dogService.createDogs(request);
        assertThat(newDog.getBreed(),equalTo(("a")));
    }
}