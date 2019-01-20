package com.dog.demo.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.dog.demo.exception.S3ErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

@Service
public class S3Service {


    @Autowired
    private AmazonS3 s3Client;

    @Value("${s3.bucket:}")
    private String bucketName;


    /**
     * upload file
     * @param keyName
     * @param content
     * @param fileType
     * @return
     */
    public PutObjectResult uploadFile( String keyName,byte[] content, String fileType){
        try {

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(fileType);
            InputStream stream = new ByteArrayInputStream(content);

            PutObjectRequest request =  new PutObjectRequest(bucketName, keyName, stream,metadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead);
            return s3Client.putObject(request);
        }
        catch(Exception e) {
            throw new S3ErrorException();

        }
    }

    /**
     * get url
     * @param keyName
     * @return
     */
    public String getUrl(String keyName){
        return s3Client.getUrl(bucketName,keyName).toString();
    }


    /**
     * delete file
     * @param fileUrl
     * @return
     */
    public void deleteFileFromS3Bucket(String fileUrl) {
        String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
        s3Client.deleteObject(new DeleteObjectRequest(bucketName, fileName));
    }
}
