package com.example.privalov;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown = true)
public class EpisodeResponse {
    @JsonProperty("results")
    private List<Episode> episodes;
    public List<Episode> getEpisodes() { return episodes; }
}
