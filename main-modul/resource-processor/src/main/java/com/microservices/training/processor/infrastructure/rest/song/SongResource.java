package com.microservices.training.processor.infrastructure.rest.song;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class SongResource {

    private String name;
    private String artist;
    private String album;
    private String length;
    private Integer resourceId;
    private String year;

}

