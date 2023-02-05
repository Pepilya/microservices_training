package com.microservices.training.song.service;

import java.util.List;

public interface SongService {

    Integer create(SongEntity song);

    List<SongEntity> get();

    SongEntity get(Integer id);

    void delete(List<Integer> ids);

}
