package com.example.client.service;

import com.example.client.domain.Genre;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface GenreService {
    @GET("/api/genre")
    Call<List<Genre>> getAllGenres();

    @PUT("/api/genre")
    Call<Genre> updateGenre(@Body Genre genre);

    @POST("/api/genre")
    Call<Genre> createGenre(@Body Genre genre);

    @DELETE("/api/genre/{id}")
    Call<Void> removeGenre(@Path("id") long id);
}
