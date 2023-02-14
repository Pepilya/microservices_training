package com.microservices.training.resource.infrastructure.kafka;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ResourceEvent {

    private final Integer resourceId;

}
