package com.example.gestioncolis.service;

import com.example.gestioncolis.dao.DatabaseConfig;
import com.example.gestioncolis.entities.Agence;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class AdminController implements Initializable {

    @FXML
    private TableColumn<Agence, Integer> TableColumnID;

    @FXML
    private TableColumn<Agence, String> TableColumnLogin;

    @FXML
    private TableColumn<Agence, String> TableColumnPassword;

    @FXML
    private TableColumn<Agence, String> TableColumnQuartier;

    @FXML
    private TableColumn<Agence, String> TableColumnVille;

    @FXML
    private TextField TextFieldLogin;

    @FXML
    private TextField TextFieldPassword;

    @FXML
    private TextField TextFiledVille;

    @FXML
    private TextField TextfieldID;

    @FXML
    private TextField TextfieldQuartier;

    @FXML
    private Button btnAjouter;

    @FXML
    private Button btnAnnuler;

    @FXML
    private Button btnModifier;

    @FXML
    private Button btnSupprimer;

    @FXML
    private TableView<Agence> tableView;

    public void ajouterAgence(ActionEvent event) throws IOException, SQLException {

        AgenceServiceImpl agenceService = new AgenceServiceImpl();
        Agence agence = new Agence(TextfieldQuartier.getText(), TextFiledVille.getText(), TextFieldLogin.getText(), TextFieldPassword.getText());
        agenceService.create(agence);

        MontrerAgence();
        annuler();
    }

    public void modifierAgence(ActionEvent event) throws IOException, SQLException {

        AgenceServiceImpl agenceService = new AgenceServiceImpl();
        Agence agence = new Agence(TextfieldQuartier.getText(), TextFiledVille.getText(), TextFieldLogin.getText(), TextFieldPassword.getText());
        agenceService.update(agence);
    }

    public void supprimerAgence(ActionEvent event) throws IOException, SQLException {

        AgenceServiceImpl agenceService = new AgenceServiceImpl();
        Agence agence = new Agence(TextfieldQuartier.getText(), TextFiledVille.getText(), TextFieldLogin.getText(), TextFieldPassword.getText());
        agenceService.delete(agence.getId());
    }

    public void annuler() throws IOException, SQLException {

        TableColumnID.setText("");
        TableColumnQuartier.setText("");
        TableColumnVille.setText("");
        TableColumnLogin.setText("");
        TableColumnPassword.setText("");
    }

    public ObservableList<Agence> AgenceListData() throws SQLException {

        ObservableList<Agence> listData = FXCollections.observableArrayList();

        String selectData = "SELECT * FROM agence";

        Connection connect = DatabaseConfig.getConnection();

        try{

            PreparedStatement prepare = connect.prepareStatement(selectData);
            ResultSet result = prepare.executeQuery();

            Agence sdata;

            while(result.next()){

                sdata = new Agence(result.getInt("ID"), result.getString("quartier"), result.getString("ville"), result.getString("login"), result.getString("password"));

                listData.add(sdata);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }

        return listData;
    }

    private ObservableList<Agence> agenceData;

    public void MontrerAgence() throws SQLException {
        agenceData = AgenceListData();

        TableColumnID.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumnQuartier.setCellValueFactory(new PropertyValueFactory<>("quartier"));
        TableColumnVille.setCellValueFactory(new PropertyValueFactory<>("ville"));
        TableColumnLogin.setCellValueFactory(new PropertyValueFactory<>("login"));
        TableColumnPassword.setCellValueFactory(new PropertyValueFactory<>("password"));

        tableView.setItems(agenceData);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }
}
