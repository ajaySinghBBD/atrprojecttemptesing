package com.kindnesskattle.bddAtcProject.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
//public class AmazonS3Config {

//    @Value("${aws.accessKey}")
//    private String accessKey;
//
//    @Value("${aws.secretKey}")
//    private String secretKey;
//
//    @Value("${aws.s3.region}")
//    private String region;
//
//    @Value("${aws.s3.endpoint}")
//    private String endpoint;
//
//    @Bean
//    public AmazonS3 amazonS3Client() {
//        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
//        return AmazonS3ClientBuilder.standard()
//                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
//                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endpoint, region))
//                .withPathStyleAccessEnabled(true)
//                .withClientConfiguration(new com.amazonaws.ClientConfiguration().withProtocol(Protocol.HTTP))
//                .build();
//    }



    @Configuration
    public class StorageConfig {

        @Value("${cloud.aws.credentials.access-key}")
        private String accessKey;

        @Value("${cloud.aws.credentials.secret-key}")
        private String accessSecret;
        @Value("${cloud.aws.region.static}")
        private String region;

        @Bean
        public AmazonS3 s3Client() {
            AWSCredentials credentials = new BasicAWSCredentials(accessKey, accessSecret);
            return AmazonS3ClientBuilder.standard()
                    .withCredentials(new AWSStaticCredentialsProvider(credentials))
                    .withRegion(region).build();
        }

    }

