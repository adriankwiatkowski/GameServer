package com.example.client.service;

import com.example.client.domain.Developer;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface DeveloperService {
    @GET("/api/developer")
    Call<List<Developer>> getAllDevelopers();

    @PUT("/api/developer")
    Call<Developer> updateDeveloper(@Body Developer developer);

    @POST("/api/developer")
    Call<Developer> createDeveloper(@Body Developer developer);

    @DELETE("/api/developer/{id}")
    Call<Void> removeDeveloper(@Path("id") long id);
}
