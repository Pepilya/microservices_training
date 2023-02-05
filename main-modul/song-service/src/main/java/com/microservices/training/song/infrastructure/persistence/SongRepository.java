package com.microservices.training.song.infrastructure.persistence;

import com.microservices.training.song.service.SongEntity;
import org.springframework.data.repository.CrudRepository;

public interface SongRepository extends CrudRepository<SongEntity, Integer> {

}
