package com.team1.qrnavigationproject.configuration;


import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class S3Configuration {

    @Value("${s3.access.key.id}")
    private String accessKeyId;

    @Value("${s3.access.key.secret}")
    private String secret;

    @Value("${s3.region.name}")
    private String region;

    @Bean
    public AmazonS3 amazonS3Client(){
        //create an aws credential
        BasicAWSCredentials credentials = new BasicAWSCredentials(accessKeyId, secret);
        //create s3 amazon client object
        return AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(region)
                .build();
    }
}
