package com.example.client.dto;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor()
@Getter
public class RegisterDto {
    @SerializedName("name")
    private String name;
    @SerializedName("surname")
    private String surname;
    @SerializedName("username")
    private String username;
    @SerializedName("password")
    private String password;
}