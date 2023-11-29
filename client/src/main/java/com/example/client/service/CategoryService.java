package com.example.client.service;

import com.example.client.domain.Category;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface CategoryService {
    @GET("/api/category")
    Call<List<Category>> getAllCategories();

    @PUT("/api/category")
    Call<Category> updateCategory(@Body Category category);

    @POST("/api/category")
    Call<Category> createCategory(@Body Category category);

    @DELETE("/api/category/{id}")
    Call<Void> removeCategory(@Path("id") long id);

}
