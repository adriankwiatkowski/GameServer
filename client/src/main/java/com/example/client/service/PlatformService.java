package com.example.client.service;

import com.example.client.domain.Platform;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface PlatformService {
    @GET("/api/platform")
    Call<List<Platform>> getAllPlatforms();

    @PUT("/api/platform")
    Call<Platform> updatePlatform(@Body Platform platform);

    @POST("/api/platform")
    Call<Platform> createPlatform(@Body Platform platform);

    @DELETE("/api/platform/{id}")
    Call<Void> removePlatform(@Path("id") long id);
}
