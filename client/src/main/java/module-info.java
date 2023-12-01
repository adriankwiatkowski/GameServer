module com.example.client {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
            
        requires org.controlsfx.controls;
                requires net.synedra.validatorfx;
            requires org.kordamp.ikonli.javafx;
    requires lombok;
    requires retrofit2;
    requires okhttp3;
    requires retrofit2.converter.gson;
    requires okhttp3.logging;
    requires gson;

    opens com.example.client.dto to gson;
    opens com.example.client.controller to javafx.fxml;
    opens com.example.client.domain to gson;
    opens com.example.client to javafx.fxml;
    exports com.example.client.dto;
    exports com.example.client.domain;
    exports com.example.client.controller;
    exports com.example.client;
}