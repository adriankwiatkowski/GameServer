//package com.example.client.model;
//
//
//import javafx.beans.property.SimpleStringProperty;
//import javafx.beans.property.StringProperty;
//
//public class Person {
//    private final StringProperty firstName = new SimpleStringProperty();
//
//    public final StringProperty firstNameProperty() {
//        return this.firstName;
//    }
//
//    public final String getFirstName() {
//        return this.firstNameProperty().get();
//    }
//
//    public final void setFirstName(final String firstName) {
//        this.firstNameProperty().set(firstName);
//    }
//
//    private final StringProperty lastName = new SimpleStringProperty();
//
//    public final StringProperty lastNameProperty() {
//        return this.lastName;
//    }
//
//    public final String getLastName() {
//        return this.lastNameProperty().get();
//    }
//
//    public final void setLastName(final String lastName) {
//        this.lastNameProperty().set(lastName);
//    }
//
//    private final StringProperty email = new SimpleStringProperty();
//
//    public final StringProperty emailProperty() {
//        return this.email;
//    }
//
//    public final String getEmail() {
//        return this.emailProperty().get();
//    }
//
//    public final void setEmail(final String email) {
//        this.emailProperty().set(email);
//    }
//
//    public Person(String firstName, String lastName, String email) {
//        setFirstName(firstName);
//        setLastName(lastName);
//        setEmail(email);
//    }
//}

package com.example.client.model;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Profile {
    private final LongProperty id = new SimpleLongProperty();

    public final LongProperty idProperty() {
        return this.id;
    }

    public final Long getId() {
        return this.idProperty().get();
    }

    public final void setId(final Long id) {
        this.idProperty().set(id);
    }

    private final StringProperty username = new SimpleStringProperty();

    public final StringProperty usernameProperty() {
        return this.username;
    }

    public final String getUsername() {
        return this.usernameProperty().get();
    }

    public final void setUsername(final String username) {
        this.usernameProperty().set(username);
    }

    private final StringProperty name = new SimpleStringProperty();

    public final StringProperty nameProperty() {
        return this.name;
    }

    public final String getName() {
        return this.nameProperty().get();
    }

    public final void setName(final String name) {
        this.nameProperty().set(name);
    }

    private final StringProperty surname = new SimpleStringProperty();

    public final StringProperty surnameProperty() {
        return this.surname;
    }

    public final String getSurname() {
        return this.surnameProperty().get();
    }

    public final void setSurname(final String surname) {
        this.surnameProperty().set(surname);
    }

    private final StringProperty accessToken = new SimpleStringProperty();

    public final StringProperty accessTokenProperty() {
        return this.accessToken;
    }

    public final String getAccessToken() {
        return this.accessTokenProperty().get();
    }

    public final void setAccessToken(final String accessToken) {
        this.accessTokenProperty().set(accessToken);
    }

    public Profile(Long id, String username, String name, String surname, String accessToken) {
        setAccessToken(accessToken);
        setId(id);
        setName(name);
        setUsername(username);
        setSurname(surname);
    }

    public Profile(String accessToken) {
        setAccessToken(accessToken);
    }

    public Profile() {
        setAccessToken("");
        setId(-1l);
        setName("");
        setUsername("");
        setSurname("");
    }
}
