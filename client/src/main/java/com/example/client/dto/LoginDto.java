package com.example.client.dto;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor()
@Data
public class LoginDto {
    @SerializedName("username")
    private String username;
    @SerializedName("password")
    private String password;
}
