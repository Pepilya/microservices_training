package com.microservices.training.resource.infrastructure.aws;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties("aws")
public class AwsProperties {

    private String url;
    private String bucketName;
    private String accessKey;
    private String secretKey;

}
