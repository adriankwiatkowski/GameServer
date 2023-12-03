package com.example.client.model;

import com.example.client.domain.Token;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TokenProperty {
    private final StringProperty token = new SimpleStringProperty("");

    public TokenProperty(String accessToken) {
        setToken(accessToken);
    }

    public TokenProperty() {
        setToken("");
    }

    public TokenProperty(Token token) {
        setToken(token.getAccessToken());
    }

    public StringProperty tokenProperty() {
        return this.token;
    }

    public final String getToken() {
        return tokenProperty().get();
    }

    public final void setToken(String token) {
        tokenProperty().set(token);
    }
}
