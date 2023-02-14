package com.microservices.training.resource.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.profile.internal.ProfileKeyConstants;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class S3Config {

    @Bean
    public AmazonS3 s3(AwsProperties properties) {
        AWSCredentials awsCredentials =
                new BasicAWSCredentials(properties.getAccessKey(), properties.getSecretKey());
        AwsClientBuilder.EndpointConfiguration configuration = new AwsClientBuilder.EndpointConfiguration(properties.getUrl(), ProfileKeyConstants.REGION);
        return AmazonS3ClientBuilder
                .standard()
                .withEndpointConfiguration(configuration)
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();

    }
}
