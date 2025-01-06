package com.spotifytelegramdeliverymessage.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.spotifytelegramdeliverymessage.service.impl.RabbitMQServiceImpl;

import java.util.List;

public class Release {

    private String email;

    @JsonProperty("release")
    private List<AlbumRelease> releaseList;

    public Release(String email, List<AlbumRelease> releaseList) {
        this.email = email;
        this.releaseList = releaseList;
    }

    public String getEmail() {
        return email;
    }

    public List<AlbumRelease> getReleaseList() {
        return releaseList;
    }
}
