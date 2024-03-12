package com.kindnesskattle.bddAtcProject.Services;


import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
@Slf4j
public class StorageService {

    @Value("${application.bucket.name}")
    private String bucketName;

    @Autowired
    private AmazonS3 s3Client;

    public String uploadFile(MultipartFile file) {
        File fileObj = convertMultiPartFileToFile(file);
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        s3Client.putObject(new PutObjectRequest(bucketName, fileName, fileObj));
        fileObj.delete();
        return "File uploaded : " + fileName;
    }


    public byte[] downloadFile(String fileName) {
        S3Object s3Object = s3Client.getObject(bucketName, fileName);
        S3ObjectInputStream inputStream = s3Object.getObjectContent();
        try {
            byte[] content = IOUtils.toByteArray(inputStream);
            return content;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public String deleteFile(String fileName) {
        s3Client.deleteObject(bucketName, fileName);
        return fileName + " removed ...";
    }


    private File convertMultiPartFileToFile(MultipartFile file) {
        File convertedFile = new File(file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        } catch (IOException e) {
            log.error("Error converting multipartFile to file", e);
        }
        return convertedFile;
    }
}

//import com.amazonaws.services.s3.AmazonS3;
//import com.amazonaws.services.s3.model.DeleteObjectRequest;
//import com.amazonaws.services.s3.model.ObjectMetadata;
//import com.amazonaws.services.s3.model.PutObjectRequest;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//import java.io.IOException;
//
//@Service
//public class S3Service {
//
//    @Autowired
//    private AmazonS3 amazonS3Client;
//
//    @Value("${aws.s3.bucketName}")
//    private String bucketName;
//
//    public void uploadFile(MultipartFile file, String xAmzContentSha256) throws IOException {
//        // Create an ObjectMetadata object to set custom headers
//        ObjectMetadata metadata = new ObjectMetadata();
//        metadata.setHeader("x-amz-content-sha256", xAmzContentSha256);
//
//        // Create a PutObjectRequest and set the metadata
//        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, file.getOriginalFilename(), file.getInputStream(), metadata);
//
//        // Make the S3 request to upload the file
//        amazonS3Client.putObject(putObjectRequest);
//    }
//    public void deleteFile(String fileName) {
//        amazonS3Client.deleteObject(new DeleteObjectRequest(bucketName, fileName));
//    }
//}
