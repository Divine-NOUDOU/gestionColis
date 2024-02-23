package com.example.gestioncolis.service;

import com.example.gestioncolis.dao.DatabaseConfig;
import com.example.gestioncolis.entities.Agence;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.List;
import java.util.Optional;
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
    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;

    private Alert lert;

    /*public void ajouterAgence(ActionEvent event) throws IOException, SQLException {

        try {
            AlertMessage alert = new AlertMessage();

            if (TextfieldQuartier.getText().isEmpty() || TextFiledVille.getText().isEmpty() || TextFieldLogin.getText().isEmpty() || TextFieldPassword.getText().isEmpty()) {

                alert.errorMessage("Please fill all blank fields");

            } else {

                AgenceServiceImpl agenceService = new AgenceServiceImpl();
                Agence agence = new Agence(TextfieldQuartier.getText(), TextFiledVille.getText(), TextFieldLogin.getText(), TextFieldPassword.getText());


                agence = agenceService.getById(agence.getId());

                prepare = connect.prepareStatement(String.valueOf(agence));
                result = prepare.executeQuery();

                if (result.next()) {

                    alert.errorMessage(TextfieldID.getText() + "is already taken");

                } else {

                    agenceService.create(agence);

                    alert.successMessage("Successfully Added!");
                    MontrerAgence();
                    annuler();

                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }



    }*/

    public void ajouterAgence() throws SQLException {

        String insertData = "INSERT INTO agence(quartier, ville, login, password)" + " VALUES(?, ?, ?, ?)";

        connect = DatabaseConfig.getConnection();

        try{
            AlertMessage alert = new AlertMessage();

            if(TextfieldQuartier.getText().isEmpty() || TextFiledVille.getText().isEmpty() || TextFieldLogin.getText().isEmpty() || TextFieldPassword.getText().isEmpty()){

                alert.errorMessage("Please fill all blank fields");

            }
            else{

                String checkData = "SELECT id FROM agence WHERE id = '"+TextfieldID.getText()+"'";

                prepare = connect.prepareStatement(checkData);
                result = prepare.executeQuery();

                if(result.next()) {

                    alert.errorMessage(TextfieldID.getText() + "is already taken");

                }
                else{

                    prepare = connect.prepareStatement(insertData);
                    prepare.setString(1, TextfieldQuartier.getText());
                    prepare.setString(2, TextFiledVille.getText());
                    prepare.setString(3, TextFieldLogin.getText());
                    prepare.setString(4, TextFieldPassword.getText());

                    prepare.executeUpdate();

                    alert.successMessage("Successfully Added!");
                    MontrerAgence();
                    annuler();

                }


            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void modifierAgence() throws SQLException {

        AlertMessage alert = new AlertMessage();
        connect = DatabaseConfig.getConnection();

        try{

            if(TextfieldID.getText().isEmpty()){

                alert.errorMessage("Please fill all blank fields");

            }
            else{
                lert = new Alert(Alert.AlertType.CONFIRMATION);
                lert.setTitle("Confirmation Message");
                lert.setHeaderText(null);
                lert.setContentText("Are you sure you want to Update id :" + TextfieldID.getText() + "?");
                Optional<ButtonType> option =lert.showAndWait();

                if(option.get().equals(ButtonType.OK)){

                    String UpdateData = "UPDATE agence SET "+ "quartier = '"+ TextfieldQuartier.getText() +"', ville = '"+ TextFiledVille.getText() + "', login = '"+ TextFieldLogin + "', password = '"+ TextFieldPassword.getText() +"' WHERE id = '"+ TextfieldID.getText() + "'";
                    prepare = connect.prepareStatement(UpdateData);
                    prepare.executeUpdate();
                    MontrerAgence();
                    annuler();
                }
                else{

                    alert.errorMessage("Cancelled");

                }

            }

        }catch(Exception e){

            e.printStackTrace();
        }

    }

    /*public void modifierAgence (ActionEvent event) throws IOException, SQLException
    {

        AgenceServiceImpl agenceService = new AgenceServiceImpl();
        Agence agence = new Agence(TextfieldQuartier.getText(), TextFiledVille.getText(), TextFieldLogin.getText(), TextFieldPassword.getText());
        agenceService.update(agence);
    }*/

    public void supprimerAgence (ActionEvent event) throws IOException, SQLException {

        AgenceServiceImpl agenceService = new AgenceServiceImpl();
        Agence agence = new Agence(TextfieldQuartier.getText(), TextFiledVille.getText(), TextFieldLogin.getText(), TextFieldPassword.getText());
        agenceService.delete(agence.getId());
    }

    public void annuler () throws IOException, SQLException {

        TextfieldID.setText("");
        TextfieldQuartier.setText("");
        TextFiledVille.setText("");
        TextFieldLogin.setText("");
        TextFieldPassword.setText("");
    }

    public ObservableList<Agence> AgenceListData () throws SQLException {

        ObservableList<Agence> listData = FXCollections.observableArrayList();

        String selectData = "SELECT * FROM agence";

        Connection connect = DatabaseConfig.getConnection();

        try {

            PreparedStatement prepare = connect.prepareStatement(selectData);
            ResultSet result = prepare.executeQuery();

            Agence sdata;

            while (result.next()) {

                sdata = new Agence(result.getInt("ID"), result.getString("quartier"), result.getString("ville"), result.getString("login"), result.getString("password"));

                listData.add(sdata);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return listData;
    }

    private ObservableList<Agence> agenceData;

    public void MontrerAgence () throws SQLException {
        agenceData = AgenceListData();

        TableColumnID.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumnQuartier.setCellValueFactory(new PropertyValueFactory<>("quartier"));
        TableColumnVille.setCellValueFactory(new PropertyValueFactory<>("ville"));
        TableColumnLogin.setCellValueFactory(new PropertyValueFactory<>("login"));
        TableColumnPassword.setCellValueFactory(new PropertyValueFactory<>("password"));

        tableView.setItems(agenceData);
    }

    public void suivant() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("SwitchFormAgence.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        btnAnnuler.getScene().getWindow().hide();
    }

    @Override
    public void initialize (URL url, ResourceBundle resourceBundle){

    }
}