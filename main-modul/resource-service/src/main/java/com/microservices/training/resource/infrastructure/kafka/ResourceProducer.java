package com.microservices.training.resource.infrastructure.kafka;

import com.microservices.training.resource.config.ResourceProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeTypeUtils;

@Component
@RequiredArgsConstructor
public class ResourceProducer {

    private final ResourceProcessor resourceProcessor;

    public void sendEvent(ResourceEvent event) {
        resourceProcessor.output().send(
                MessageBuilder.withPayload(event)
                        .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
                        .build()
        );
    }

}
