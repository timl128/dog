package com.dog.demo.service;

import com.dog.demo.model.api.ApiResponse;
import com.dog.demo.repository.DogRepository;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@TestPropertySource(locations = "/application-test.properties")
@RestClientTest({ApiService.class})
public class ApiServiceTest {

    private WireMockServer wireMockServer;

    @Autowired
    private ApiService apiService;

    @MockBean
    private DogRepository dogRepository;

    @Before
    public void setUp()  {

        wireMockServer = new WireMockServer(wireMockConfig().port(9001));
        wireMockServer.start();
    }

    @After
    public void stop() {
        wireMockServer.stop();
    }

    @Test
    public void getRandomDog() {

        wireMockServer.stubFor(get("/")
                .willReturn(okJson("{\"status\":\"success\",\"message\":\"https:\\/\\/images.dog.ceo\\/breeds\\/poodle-toy\\/n02113624_6868.jpg\"}")));

        ApiResponse response  = apiService.getRandomDog();
        assertThat(response.getStatus(),equalTo("success"));
    }
}