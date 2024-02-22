package com.example.gestioncolis.service;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class NatureColisController {

    @FXML
    private TableColumn<?, ?> TableColumnNumeroType;

    @FXML
    private Button btnAjouter;

    @FXML
    private Button btnAnnuler;

    @FXML
    private Button btnModifier;

    @FXML
    private Button btnSupprimer;

    @FXML
    private TableColumn<?, ?> tableColumnType;

    @FXML
    private TableView<?> tableView;

    @FXML
    private TextField textFieldNomType;

    @FXML
    private TextField textFieldNumeroType;

}
