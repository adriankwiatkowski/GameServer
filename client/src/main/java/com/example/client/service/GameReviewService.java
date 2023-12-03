package com.example.client.service;

import com.example.client.domain.GameReview;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface GameReviewService {
    @GET("/api/gamereview/{gameId}")
    Call<List<GameReview>> getGameReviews();

    @PUT("/api/gamereview")
    Call<GameReview> updateGameReview(@Body GameReview gameReview);

    @POST("/api/gamereview")
    Call<GameReview> createGameReview(@Body GameReview gameReview);

    @DELETE("/api/gamereview/{id}")
    Call<Void> removeGameReview(@Path("id") long id);
}
