package com.example.client.service;

import com.example.client.domain.Publisher;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface PublisherService {
    @GET("/api/publisher")
    Call<List<Publisher>> getAllPublishers();

    @PUT("/api/publisher")
    Call<Publisher> updatePublisher(@Body Publisher publisher);

    @POST("/api/publisher")
    Call<Publisher> createPublisher(@Body Publisher publisher);

    @DELETE("/api/publisher/{id}")
    Call<Void> removePublisher(@Path("id") long id);
}
