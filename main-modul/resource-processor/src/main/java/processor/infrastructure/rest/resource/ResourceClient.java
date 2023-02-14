package processor.infrastructure.rest.resource;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "${resources-client.name}", url = "${resources-client.url}")
@RequestMapping("/api/v1/resources/{resourceId}")
public interface ResourceClient {

    @GetMapping
    byte[] getResource(@PathVariable Integer resourceId);

}
