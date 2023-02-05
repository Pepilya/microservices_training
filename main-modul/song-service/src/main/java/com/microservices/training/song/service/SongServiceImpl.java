package com.microservices.training.song.service;

import com.microservices.training.song.infrastructure.persistence.SongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SongServiceImpl implements SongService {

    private final SongRepository repo;

    @Override
    public Integer create(SongEntity song) {
        return repo.save(song).getId();
    }

    @Override
    public List<SongEntity> get() {
        return (List<SongEntity>) repo.findAll();
    }

    @Override
    public SongEntity get(Integer id) {
        return repo.findById(id)
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public void delete(List<Integer> ids) {
        ids.forEach(repo::deleteById);
    }
}
