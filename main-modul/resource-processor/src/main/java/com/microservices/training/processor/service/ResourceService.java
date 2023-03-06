package com.microservices.training.processor.service;

import com.microservices.training.processor.infrastructure.rest.resource.ResourceClient;
import com.microservices.training.processor.infrastructure.rest.song.SongClient;
import com.microservices.training.processor.infrastructure.rest.song.SongResource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.springframework.stereotype.Service;

import org.apache.tika.exception.TikaException;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
@Slf4j
@RequiredArgsConstructor
public class ResourceService {

    private final ResourceClient resourceClient;
    private final SongClient songClient;

    public void process(ResourceEvent event) throws TikaException, IOException, SAXException {
        log.debug("Get event = {}", event);
        byte [] resource = resourceClient.getResource(event.getResourceId());
        Metadata metadata = new Metadata();
        populateMetadata(metadata, resource);
        SongResource song = getSongResource(event.getResourceId(), metadata.get(ServiceConstants.SONG_NAME_TAG), metadata.get(ServiceConstants.ALBUM_TAG),  getMp3Length(metadata.get(ServiceConstants.DURATION_TAG)), metadata.get(ServiceConstants.ARTIST_TAG), metadata.get(ServiceConstants.YEAR_TAG));
        songClient.createSong(song);
    }

    private void populateMetadata(Metadata metadata, byte [] resource) throws TikaException, IOException, SAXException {
        InputStream input = new ByteArrayInputStream(resource);
        ContentHandler handler = new DefaultHandler();
        Parser parser = new Mp3Parser();
        ParseContext parseCtx = new ParseContext();
        parser.parse(input, handler, metadata, parseCtx);
        input.close();
    }

    private SongResource getSongResource(Integer resourceId, String name, String album, String length, String artist, String year) {
        return SongResource.builder()
                .resourceId(resourceId)
                .name(name)
                .album(album)
                .length(length)
                .artist(artist)
                .year(year)
                .build();
    }

    private String getMp3Length(String duration) {
        Double aDouble = Double.valueOf(duration);
        Long aLong = aDouble.longValue();
        int minutes = (int) (aLong / 60);
        int sec = (int) (aLong % 60);
        return minutes + ":" + sec;
    }

}
