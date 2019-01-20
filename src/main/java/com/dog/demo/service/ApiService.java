package com.dog.demo.service;

import com.dog.demo.exception.ExternalApiException;
import com.dog.demo.model.api.ApiResponse;
import com.dog.demo.repository.DogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ApiService {

    @Autowired
    private DogRepository dogRepository;

    @Value("${dog.random.api:}")
    private String url;

    /**
     * get random dog info
     * @return
     */
    public ApiResponse getRandomDog(){

        RestTemplate restTemplate = new RestTemplate();
        ApiResponse apiResponse = null;

        try{
            apiResponse = restTemplate.getForObject(url,ApiResponse.class);
        }
        catch (Exception e){
            throw  new ExternalApiException();
        }


        return apiResponse;
    }


}
