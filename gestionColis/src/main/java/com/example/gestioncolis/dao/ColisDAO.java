package com.example.gestioncolis.dao;

import com.example.gestioncolis.entities.Colis;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ColisDAO {
    private final Connection connection;

    public ColisDAO() throws SQLException {
        this.connection = DatabaseConfig.getConnection();
    }

    public void create(Colis colis) throws SQLException {
        String query = "INSERT INTO colis (type, CNI_destinataire, CNI_expediteur, etat, id_agence_destination) " +
                "VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, colis.getType());
            statement.setInt(2, colis.getCNIDestinataire());
            statement.setInt(3, colis.getCNIExpediteur());
            statement.setString(4, colis.getEtat());
            statement.setInt(5, colis.getIdAgenceDestination());
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                colis.setNumeroColis(rs.getInt(1));
            }
        }
    }

    public void update(Colis colis) throws SQLException {
        String query = "UPDATE colis SET type = ?, CNI_destinataire = ?, CNI_expediteur = ?, " +
                "etat = ?, id_agence_destination = ? WHERE numero_colis = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, colis.getType());
            statement.setInt(2, colis.getCNIDestinataire());
            statement.setInt(3, colis.getCNIExpediteur());
            statement.setString(4, colis.getEtat());
            statement.setInt(5, colis.getIdAgenceDestination());
            statement.setInt(6, colis.getNumeroColis());
            statement.executeUpdate();
        }
    }

    public void delete(int numeroColis) throws SQLException {
        String query = "DELETE FROM colis WHERE numero_colis = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, numeroColis);
            statement.executeUpdate();
        }
    }

    public Colis getByNumeroColis(int numeroColis) throws SQLException {
        String query = "SELECT * FROM colis WHERE numero_colis = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, numeroColis);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return extractColisFromResultSet(resultSet);
            }
        }
        return null;
    }

    public List<Colis> getAll() throws SQLException {
        List<Colis> colisList = new ArrayList<>();
        String query = "SELECT * FROM colis";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Colis colis = extractColisFromResultSet(resultSet);
                colisList.add(colis);
            }
        }
        return colisList;
    }

    private Colis extractColisFromResultSet(ResultSet resultSet) throws SQLException {
        Colis colis = new Colis(resultSet.getInt("numero_colis"), resultSet.getInt("type"), resultSet.getInt("CNI_destinataire"), resultSet.getInt("CNI_expediteur"), resultSet.getString("etat"), resultSet.getInt("id_agence_destination"));
        return colis;
    }
}
