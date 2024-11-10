package com.example.privalov;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Episode {
    private int id;
    private String name;
    private String air_date;
    private String episode;
    
    @JsonProperty("characters")
    private List<String> characters;

    public int getId() { return id; }
    public String getName() { return name; }
    public String getAirDate() { return air_date; }
    public String getEpisode() { return episode; }
    public List<String> getCharacters() { return characters; }
}



