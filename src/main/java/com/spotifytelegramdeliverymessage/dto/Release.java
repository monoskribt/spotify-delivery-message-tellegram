package com.spotifytelegramdeliverymessage.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class Release {

    @JsonProperty("email")
    private String email;

    @JsonProperty("releases")
    private List<AlbumRelease> releaseList;
}
