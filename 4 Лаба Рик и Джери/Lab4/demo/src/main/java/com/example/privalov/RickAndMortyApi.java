package com.example.privalov;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RickAndMortyApi {
    @GET("episode")
    Call<EpisodeResponse> getEpisodes();
}
