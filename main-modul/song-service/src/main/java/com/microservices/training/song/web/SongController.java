package com.microservices.training.song.web;

import com.microservices.training.song.service.SongEntity;
import com.microservices.training.song.service.SongService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/songs")
@RequiredArgsConstructor
@Slf4j
public class SongController {

    private final SongService service;

    @GetMapping
    public List<SongResource> getSongs() {
        return service.get().stream()
                .map(this::mapSongResource)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public SongResource getSongById(@PathVariable Integer id) {
        log.info("GET id = {}", id);
        return mapSongResource(service.get(id));
    }

    @PostMapping
    public SongResponse createSong(@RequestBody SongResource songResource) {
        log.info("POST resource = {}", songResource);
        Integer id = service.create(mapSongEntity(songResource));
        return new SongResponse(id);
    }
    @DeleteMapping
    public List<Integer> deleteSongs(@RequestParam("id") String idStr) {
        log.info("DELETE resource = {}", idStr);
        List<Integer> idList = Arrays.stream(idStr.split(","))
                .map(Integer::valueOf)
                .collect(Collectors.toList());
        service.delete(idList);
        return idList;
    }

    private SongResource mapSongResource(SongEntity songEntity) {
        return SongResource.builder()
                .name(songEntity.getName())
                .artist(songEntity.getArtist())
                .album(songEntity.getAlbum())
                .length(songEntity.getLength())
                .resourceId(songEntity.getResourceId())
                .year(songEntity.getYear())
                .build();
    }

    private SongEntity mapSongEntity(SongResource songEntity) {
        return SongEntity.builder()
                .name(songEntity.getName())
                .artist(songEntity.getArtist())
                .album(songEntity.getAlbum())
                .length(songEntity.getLength())
                .resourceId(songEntity.getResourceId())
                .year(songEntity.getYear())
                .build();
    }

}
