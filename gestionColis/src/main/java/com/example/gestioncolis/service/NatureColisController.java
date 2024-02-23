package com.example.gestioncolis.service;

import com.example.gestioncolis.dao.DatabaseConfig;
import com.example.gestioncolis.entities.Agence;
import com.example.gestioncolis.entities.Nature;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.Optional;

public class NatureColisController {

    @FXML
    private TableColumn<Nature, Integer> TableColumnNumeroType;

    @FXML
    private Button btnAjouter;

    @FXML
    private Button btnAnnuler;

    @FXML
    private Button btnModifier;

    @FXML
    private Button btnSupprimer;

    @FXML
    private TableColumn<Nature, String> tableColumnType;

    @FXML
    private TableView<Nature> tableView;

    @FXML
    private TextField textFieldNomType;

    @FXML
    private TextField textFieldNumeroType;

    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;

    private Alert lert;

    public void ajouterAgence() throws SQLException {

        String insertData = "INSERT INTO nature(id_nature, type)" + " VALUES(?, ?)";

        connect = DatabaseConfig.getConnection();

        try{
            AlertMessage alert = new AlertMessage();

            if(textFieldNomType.getText().isEmpty() || textFieldNumeroType.getText().isEmpty()){

                alert.errorMessage("Please fill all blank fields");

            }
            else{

                String checkData = "SELECT id_nature FROM nature WHERE id = '"+textFieldNumeroType.getText()+"'";

                prepare = connect.prepareStatement(checkData);
                result = prepare.executeQuery();

                if(result.next()) {

                    alert.errorMessage(textFieldNumeroType.getText() + "is already taken");

                }
                else{

                    prepare = connect.prepareStatement(insertData);
                    prepare.setString(1, textFieldNumeroType.getText());
                    prepare.setString(2, textFieldNomType.getText());

                    prepare.executeUpdate();

                    alert.successMessage("Successfully Added!");
                    MontrerNature();
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

            if(textFieldNumeroType.getText().isEmpty()){

                alert.errorMessage("Please fill all blank fields");

            }
            else{
                lert = new Alert(Alert.AlertType.CONFIRMATION);
                lert.setTitle("Confirmation Message");
                lert.setHeaderText(null);
                lert.setContentText("Are you sure you want to Update id :" + textFieldNumeroType.getText() + "?");
                Optional<ButtonType> option =lert.showAndWait();

                if(option.get().equals(ButtonType.OK)){

                    String UpdateData = "UPDATE nature SET "+ "id_nature = '"+ textFieldNumeroType.getText() +"', nature = '"+ textFieldNomType.getText() +"'";
                    prepare = connect.prepareStatement(UpdateData);
                    prepare.executeUpdate();
                    MontrerNature();
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

    public void annuler () throws IOException, SQLException {

        textFieldNumeroType.setText("");
        textFieldNomType.setText("");
    }

    public ObservableList<Nature> NatureListData () throws SQLException {

        ObservableList<Nature> listData = FXCollections.observableArrayList();

        String selectData = "SELECT * FROM nature";

        Connection connect = DatabaseConfig.getConnection();

        try {

            PreparedStatement prepare = connect.prepareStatement(selectData);
            ResultSet result = prepare.executeQuery();

            Nature sdata;

            while (result.next()) {

                sdata = new Nature(result.getInt("idNature"), result.getString("type"));

                listData.add(sdata);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return listData;
    }

    private ObservableList<Nature> agenceData;

    public void MontrerNature () throws SQLException {
        agenceData = NatureListData();


        tableColumnType.setCellValueFactory(new PropertyValueFactory<>("Type"));
        TableColumnNumeroType.setCellValueFactory(new PropertyValueFactory<>("NumeroType"));

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

}
