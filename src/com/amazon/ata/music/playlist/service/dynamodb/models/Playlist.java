package com.amazon.ata.music.playlist.service.dynamodb.models;

import com.amazon.ata.music.playlist.service.converters.AlbumTrackLinkedListConverter;

import com.amazon.ata.music.playlist.service.util.MusicPlaylistServiceUtils;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Represents a record in the playlists table.
 */
@DynamoDBTable(tableName = "playlists")
public class Playlist {
    private String id; //should be assigned by a playlist service
    private List<AlbumTrack> songList;
    private Set<String> tags;
    private String name;
    private String customerId;
    private Integer songCount;
    public Playlist () {}

    @DynamoDBHashKey(attributeName = "id")
    public String getId() {
        return id;
    }
    @DynamoDBAttribute(attributeName = "name")
    public String getName() {
        return name;
    }
    public List<String> getTags() {
        ArrayList<String> tagsList = new ArrayList<>(tags);
        return tagsList;
    }
    @DynamoDBAttribute(attributeName = "tags")
    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    public void setName(String name) {
        this.name = name;
    }
    @DynamoDBAttribute(attributeName = "customerId")
    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
    @DynamoDBAttribute(attributeName = "songCount")
    public Integer getSongCount() {
        return songCount;
    }

    public void setSongCount(Integer songCount) {
        this.songCount = songCount;
    }

    public void setId(String id) {
        this.id = id;
    }

    // PARTICIPANTS: You do not need to modify the songList getters/setters or annotations
    @DynamoDBTypeConverted(converter = AlbumTrackLinkedListConverter.class)
    @DynamoDBAttribute(attributeName = "songList")
    public List<AlbumTrack> getSongList() {
        return songList;
    }

    public void setSongList(List<AlbumTrack> songList) {
        this.songList = songList;
    }




}
