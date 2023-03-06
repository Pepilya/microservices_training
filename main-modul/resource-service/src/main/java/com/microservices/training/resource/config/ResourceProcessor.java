package com.microservices.training.resource.config;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface ResourceProcessor {

    String RESOURCES = "resources.dev";

    @Output(RESOURCES)
    MessageChannel output();

}
