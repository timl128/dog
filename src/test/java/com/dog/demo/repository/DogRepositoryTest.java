package com.dog.demo.repository;

import com.dog.demo.model.Dog;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class DogRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private DogRepository dogRepository;


    @Before
    public void setup(){

        Dog dog1 = new Dog("breed","abc");
        entityManager.persist(dog1);

        Dog dog2 = new Dog("breed","abb");
        entityManager.persist(dog2);

        Dog dog3 = new Dog("breed1","abb");
        entityManager.persist(dog3);

    }

    @Test
    public void findAllByBreed() {

        List<Dog> dogList =  dogRepository.findAllByBreed("breed");
        assertThat(dogList.size(),equalTo(2));
    }

    @Test
    public void findDistinctByBreed() {

        List<String> breedList= dogRepository.findDistinctByBreed();
        assertThat(breedList.size(),equalTo(2));
    }
}