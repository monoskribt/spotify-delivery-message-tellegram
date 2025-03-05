package com.spotifytelegramdeliverymessage.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AlbumRelease {

    @JsonProperty("id")
    private String albumId;

    @JsonProperty("name")
    private String albumName;
}
