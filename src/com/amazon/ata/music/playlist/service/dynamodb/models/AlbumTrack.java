package com.amazon.ata.music.playlist.service.dynamodb.models;

import com.amazon.ata.music.playlist.service.models.PlaylistModel;
import com.amazonaws.internal.config.Builder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.List;

/**
 * Represents a record in the album_tracks table.
 */
@DynamoDBTable(tableName = "album_tracks")
public class AlbumTrack {
    private String asin;
    private Integer track_number;
    private String album_name;
    private String song_title;
    public AlbumTrack() {}

    @DynamoDBAttribute(attributeName = "asin")
    public String getAsin() {
        return asin;
    }

    public void setAsin(String asin) {
        this.asin = asin;
    }
    @DynamoDBAttribute(attributeName = "track_number")
    public Integer getTrackNumber() {
        return track_number;
    }

    public void setTrackNumber(Integer track_number) {
        this.track_number = track_number;
    }
    @DynamoDBAttribute(attributeName = "album_name")
    public String getAlbumName() {
        return album_name;
    }

    public void setAlbumName(String album_name) {
        this.album_name = album_name;
    }
    @DynamoDBAttribute(attributeName = "song_title")
    public String getSongTitle() {
        return song_title;
    }

    public void setSongTitle(String song_title) {
        this.song_title = song_title;
    }


}
