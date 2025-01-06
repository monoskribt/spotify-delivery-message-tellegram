package com.spotifytelegramdeliverymessage.dto;

public class AlbumRelease {

    private String albumId;
    private String albumName;

    public AlbumRelease(String albumId, String albumName) {
        this.albumId = albumId;
        this.albumName = albumName;
    }

    public String getAlbumId() {
        return albumId;
    }

    public String getAlbumName() {
        return albumName;
    }

}
