package com.dog.demo.service;

import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.util.IOUtils;
import com.dog.demo.exception.ImageErrorException;
import com.dog.demo.exception.InvalidDogIdException;
import com.dog.demo.model.Dog;
import com.dog.demo.model.api.ApiResponse;
import com.dog.demo.model.request.CreateDogRequest;
import com.dog.demo.repository.DogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DogService {

    @Autowired
    private DogRepository dogRepository;


    @Autowired
    private ApiService apiService;

    @Autowired
    private S3Service s3Service;

    /**
     * get dog by Id
     * @param id
     * @return
     */
    public Dog getDogById(long id){
       Optional<Dog> optional =  dogRepository.findById(id);

       if(!optional.isPresent())
           throw new InvalidDogIdException(id);

       return optional.get();
    }


    /**
     * remove dog by Id
     * @param id
     * @return
     */
    public boolean removeDogById(long id){

        Dog dog = getDogById(id);
        s3Service.deleteFileFromS3Bucket(dog.getS3Link());
        dogRepository.delete(dog);
        return true;
    }

    /**
     * get dogs by breed name
     * @param name
     * @return
     */
    public List<Dog> getDogsByBreed(String name){
        return dogRepository.findAllByBreed(name);
    }

    /**
     * get all breed names
     * @return
     */
    public List<String> getAllBreedNames(){
        return dogRepository.findDistinctByBreed();
    }


    /**
     * create dog
     * @param request
     * @return
     */
    public Dog createDogs(CreateDogRequest request){

        Dog newDog = new Dog(request);

        ApiResponse  response = apiService.getRandomDog();
        String uuid = UUID.randomUUID().toString();

        try {
            byte[] imageContent = getImage(response.getMessage());
            s3Service.uploadFile(uuid,imageContent,"image/jpeg");
            Date date = new Date();
            newDog.setUploadDate(date);
            newDog.setS3Link(s3Service.getUrl(uuid));

        } catch (Exception e) {
            throw new ImageErrorException();
        }

        dogRepository.save(newDog);
        return newDog;

    }

    /**
     * convert image into byte
     * @param imageUrl
     * @return
     * @throws Exception
     */
    private byte[] getImage(String imageUrl)  throws Exception{

        URL url = new URL(imageUrl);
        InputStream is = url.openStream();
        return IOUtils.toByteArray(is);

    }
}
