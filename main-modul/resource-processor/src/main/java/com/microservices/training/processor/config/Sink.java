package com.microservices.training.processor.config;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface Sink {
    String RESOURCE_TOPIC = "resources";

    @Input(RESOURCE_TOPIC)
    SubscribableChannel getOrder();

}
