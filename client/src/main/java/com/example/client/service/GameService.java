package com.example.client.service;

import com.example.client.domain.Game;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface GameService {
    @GET("/api/game")
    Call<List<Game>> getAllGames();

    @PUT("/api/game")
    Call<Game> updateGame(@Body Game gamer);

    @POST("/api/game")
    Call<Game> createGame(@Body Game game);

    @GET("/api/game/{id}")
    Call<Game> getGameDetails(@Path("id") long id);

    @DELETE("/api/game/{id}")
    Call<Void> removeGame(@Path("id") long id);

    @GET("/api/game/name/{name}")
    Call<Game> getGameDetailsByName(@Path("name") String name);
}
