package com.example.client.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class ProfileModel {
    private final ObjectProperty<UserProperty> currentUser = new SimpleObjectProperty<>(null);
    private final ObjectProperty<TokenProperty> currentToken = new SimpleObjectProperty<>(null);

    public ObjectProperty<UserProperty> currentUserProperty() {
        return currentUser;
    }

    public ObjectProperty<TokenProperty> currentTokenProperty() {
        return currentToken;
    }

    public final UserProperty getCurrentUser() {
        return currentUserProperty().get();
    }

    public final TokenProperty getCurrentToken() {
        return currentTokenProperty().get();
    }

    public final void setCurrentToken(TokenProperty tokenProperty) {
        currentTokenProperty().set(tokenProperty);
    }

    public final void setCurrentUser(UserProperty userProperty) {
        currentUserProperty().set(userProperty);
    }
}
