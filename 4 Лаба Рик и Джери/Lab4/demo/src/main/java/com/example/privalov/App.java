package com.example.privalov;


import java.io.IOException;
import java.util.Comparator;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class App {
    public static void main(String[] args) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://rickandmortyapi.com/api/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        RickAndMortyApi api = retrofit.create(RickAndMortyApi.class);

        try {
            EpisodeResponse response = api.getEpisodes().execute().body();

            if (response != null && response.getEpisodes() != null) {
                Episode maxCharactersEpisode = response.getEpisodes().stream()
                        .max(Comparator.comparingInt(ep -> ep.getCharacters().size()))
                        .orElse(null);

                if (maxCharactersEpisode != null) {
                    System.out.println("Эпизод с максимальным количеством персонажей:");
                    System.out.println("Название: " + maxCharactersEpisode.getName());
                    System.out.println("Дата выхода: " + maxCharactersEpisode.getAirDate());
                    System.out.println("Количество персонажей: " + maxCharactersEpisode.getCharacters().size());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
