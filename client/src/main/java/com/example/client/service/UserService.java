package com.example.client.service;

import com.example.client.domain.User;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;

import java.util.List;

public interface UserService {
    @GET("/api/user/details")
    Call<User> getUserDetails();

    @GET("/api/user")
    Call<List<User>> getAllUsers();

    @DELETE("/api/user")
    Call<Void> removeUser();
}
