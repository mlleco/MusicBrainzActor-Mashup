package com.hajjimlle.musicbrainz;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import akka.japi.pf.ReceiveBuilder;


@Component("CoverArtActor")
@Scope("prototype")
public class CoverArtActor extends AbstractAPIActor<CoverArtActor.AlbumId> {

    private static final String URL = "http://coverartarchive.org/release-group/%s";

    public static class AlbumId {
        private final String id;

        public AlbumId(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }
    }

    public CoverArtActor() {
        receive(ReceiveBuilder.
                match(AlbumId.class, albumId -> {
                    logger.info("Received AlbumId message: {}", albumId.getId());
                    handleRequestMessage(albumId);
                }).
                matchAny(o -> logger.info("Received unknown message: {}",o)).build()
        );
    }

    @Override
    protected String url(AlbumId message) {
        return String.format(URL,message.getId());
    }

}
