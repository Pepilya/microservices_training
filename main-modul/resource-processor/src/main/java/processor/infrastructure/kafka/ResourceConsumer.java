package processor.infrastructure.kafka;

import lombok.RequiredArgsConstructor;
import org.apache.tika.exception.TikaException;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;
import processor.config.Sink;
import processor.service.ResourceEvent;
import processor.service.ResourceService;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class ResourceConsumer {

    private final ResourceService service;

    @StreamListener(Sink.RESOURCE_TOPIC)
    public void processResourceEvent(ResourceEvent event) throws TikaException, IOException, SAXException {
        service.process(event);
    }

}
