package com.example.client.model;

import com.example.client.domain.User;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class UserProperty {

    private final LongProperty id = new SimpleLongProperty(0l);

    public LongProperty idProperty() {
        return id;
    }

    public final Long getId() {
        return idProperty().get();
    }

    public final void setId(Long id) {
        idProperty().set(id);
    }

    private final StringProperty username = new SimpleStringProperty("");

    public StringProperty usernameProperty() {
        return this.username;
    }

    public final String getUsername() {
        return this.usernameProperty().get();
    }

    public final void setUsername(String username) {
        this.usernameProperty().set(username);
    }

    private final StringProperty name = new SimpleStringProperty("");

    public StringProperty nameProperty() {
        return this.name;
    }

    public final String getName() {
        return this.nameProperty().get();
    }

    public final void setName(final String name) {
        this.nameProperty().set(name);
    }

    private final StringProperty surname = new SimpleStringProperty("");

    public StringProperty surnameProperty() {
        return this.surname;
    }

    public final String getSurname() {
        return this.surnameProperty().get();
    }

    public final void setSurname(final String surname) {
        this.surnameProperty().set(surname);
    }

    //    private final SetProperty<Role> roles = new SimpleSetProperty<Role>();
//
//    public final SetProperty<Role> rolesProperty() {
//        return this.roles;
//    }
//
//    public final Set<Role> getRoles() {
//        return this.rolesProperty().get();
//    }
//
//    public final void setRoles(final Set<Role> roles) {
//        this.rolesProperty().set((ObservableSet<Role>) roles);
//    }
    public UserProperty() {
        setId(0l);
        setUsername("");
        setName("");
        setSurname("");
    }

    public UserProperty(User user) {
        setId(user.getId());
        setUsername(user.getUsername());
        setName(user.getName());
        setSurname(user.getSurname());
    }
}
