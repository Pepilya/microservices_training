package com.microservices.training.processor.infrastructure.kafka;

import com.microservices.training.processor.config.Sink;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.exception.TikaException;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;
import com.microservices.training.processor.service.ResourceEvent;
import com.microservices.training.processor.service.ResourceService;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class ResourceConsumer {

    private final ResourceService service;

    @StreamListener(Sink.RESOURCE_TOPIC)
    public void processResourceEvent(Message<ResourceEvent> message) throws TikaException, IOException, SAXException {
        //acknowledgment before process order establishing "at most one" semantic
        service.process(message.getPayload());
        Acknowledgment acknowledgment = message.getHeaders().get(KafkaHeaders.ACKNOWLEDGMENT, Acknowledgment.class);
        if (acknowledgment != null) {
            log.info("Acknowledgment provided");
            acknowledgment.acknowledge();
        }

    }

}
