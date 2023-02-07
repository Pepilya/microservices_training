package com.microservices.training.resource.infrastructure.persistence;

import com.microservices.training.resource.service.ResourceEntity;
import org.springframework.data.repository.CrudRepository;

public interface ResourceRepository extends CrudRepository<ResourceEntity, Integer> {
}
