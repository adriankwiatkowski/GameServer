package com.example.client.service;

import com.example.client.domain.Token;
import com.example.client.dto.LoginDto;
import com.example.client.dto.RegisterDto;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthService {
    @POST("/api/auth/register")
    Call<ResponseBody> registerUser(@Body RegisterDto registerDto);

    @POST("/api/auth/login")
    Call<Token> loginUser(@Body LoginDto loginDTO);
}
