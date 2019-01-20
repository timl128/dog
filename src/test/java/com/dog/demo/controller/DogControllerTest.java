package com.dog.demo.controller;

import com.dog.demo.model.Dog;
import com.dog.demo.service.DogService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(DogController.class)
public class DogControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private DogService dogService;

    @Test
    public void getDog() throws Exception{

        Dog dog = new Dog("a","a");
        when(dogService.getDogById(1L)).thenReturn(dog);

        mvc.perform(get("/dog/id/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.breed", is(dog.getBreed())));
    }


    @Test
    public void deleteDog() throws Exception{

        when(dogService.removeDogById(1L)).thenReturn(true);

        mvc.perform(delete("/dog/id/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.operation", is(true)));
    }

    @Test
    public void getBreed() throws Exception{

        List<Dog> dogList = Arrays.asList(new Dog("a","a"));
        when(dogService.getDogsByBreed("a")).thenReturn(dogList);

        mvc.perform(get("/dog/breed/a")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].breed", is(dogList.get(0).getBreed())));

    }

    @Test
    public void listBreed() throws Exception{

        List<String> breedList = Arrays.asList("a","b");
        when(dogService.getAllBreedNames()).thenReturn(breedList);

        mvc.perform(get("/dog/list/breed")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]", is(breedList.get(0))));

    }

    @Test
    public void createDog() throws  Exception{


        Dog dog = new Dog("a","a");
        when(dogService.createDogs(any())).thenReturn(dog);

        mvc.perform(post("/dog")
                .contentType(MediaType.APPLICATION_JSON).content("{\"breed\":\"a\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.breed", is("a")));
    }
}