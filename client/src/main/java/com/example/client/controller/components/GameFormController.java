package com.example.client.controller.components;

import com.example.client.domain.*;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GameFormController {
    private TextField avgPlaytimeField;
    private ComboBox<Category> categoriesField;
    private TextField descriptionField;
    private ComboBox<Developer> developersField;
    private ComboBox<Genre> genresField;
    private TextField medianPlaytimeField;
    private TextField nameField;
    private TextField negativeRateField;
    private TextField owners;
    private ComboBox<Platform> platformsField;
    private ComboBox<Publisher> publishersField;
    private TextField positiveRateField;
    private TextField priceField;
    private DatePicker releaseDateField;


}
