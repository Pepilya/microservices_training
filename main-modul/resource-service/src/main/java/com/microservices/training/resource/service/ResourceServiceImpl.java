package com.microservices.training.resource.service;

import com.microservices.training.resource.config.AwsProperties;
import com.microservices.training.resource.infrastructure.aws.FileStore;
import com.microservices.training.resource.infrastructure.kafka.ResourceEvent;
import com.microservices.training.resource.infrastructure.kafka.ResourceProducer;
import com.microservices.training.resource.infrastructure.persistence.ResourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;


@Service
@RequiredArgsConstructor
public class ResourceServiceImpl implements ResourceService {

    private final FileStore fileStore;
    private final AwsProperties awsProperties;
    private final ResourceRepository repo;
    private final ResourceProducer resourceProducer;

    @Override
    @Transactional
    public Integer create(MultipartFile file) throws IOException {
        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Length", String.valueOf(file.getSize()));
        String bucketName = awsProperties.getBucketName();
        String path = String.format("%s/%s", bucketName, UUID.randomUUID());
        String fileName = String.format("%s", file.getOriginalFilename());
        fileStore.upload(path, fileName, Optional.of(metadata), file.getInputStream());
        ResourceEntity entity = repo.save(build(fileName, path));
        resourceProducer.sendEvent(new ResourceEvent(entity.getId()));
        return entity.getId();
    }

    private ResourceEntity build(String fileName, String path) {
        return ResourceEntity.builder()
                .fileName(fileName)
                .path(path)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public byte[] get(Integer id) {
        ResourceEntity resource = repo.findById(id)
                .orElseThrow(NotFoundException::new);
        return fileStore.download(resource.getPath(), resource.getFileName());
    }

    @Override
    @Transactional
    public List<Integer> delete(List<Integer> ids) {
        ids.forEach(id -> {
            ResourceEntity resource = repo.findById(id)
                    .orElseThrow(NotFoundException::new);
            fileStore.delete(awsProperties.getBucketName(), resource.getFileName());
            repo.delete(resource);
        });
        return ids;
    }

}
