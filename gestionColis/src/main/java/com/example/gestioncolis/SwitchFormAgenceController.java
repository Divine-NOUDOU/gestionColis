package com.example.gestioncolis;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class SwitchFormAgenceController {

    @FXML
    private Button btnAgence;

    @FXML
    private ImageView btnHome;

    @FXML
    private ImageView btnRetour;

    @FXML
    private Button btnType;


    public void suivant() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("admin.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        btnAgence.getScene().getWindow().hide();
    }

    public void nature() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("natureColis.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        btnType.getScene().getWindow().hide();
    }

    public void retour() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("loginPage.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        btnRetour.getScene().getWindow().hide();
    }

    public void Home() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("SwitchFormAgence.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        btnHome.getScene().getWindow().hide();
    }
}
