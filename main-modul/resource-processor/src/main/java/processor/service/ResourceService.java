package processor.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.springframework.stereotype.Service;
import processor.infrastructure.rest.resource.ResourceClient;
import processor.infrastructure.rest.song.SongClient;

import org.apache.tika.exception.TikaException;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import processor.infrastructure.rest.song.SongResource;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static processor.service.ServiceConstants.*;

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
        SongResource song = getSongResource(event.getResourceId(), metadata.get(SONG_NAME_TAG), metadata.get(ALBUM_TAG),  getMp3Length(metadata.get(DURATION_TAG)), metadata.get(ARTIST_TAG), metadata.get(YEAR_TAG));
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
