package com.microservices.training.processor.infrastructure.rest.song;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "${songs-client.name}", url = "${songs-client.url}")
@RequestMapping("/api/v1/songs")
public interface SongClient {

    @PostMapping
    SongResponse createSong(@RequestBody SongResource resource);

}
