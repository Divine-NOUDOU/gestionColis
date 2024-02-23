package com.example.gestioncolis.dao;

import com.example.gestioncolis.entities.Agence;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AgenceDAO {
    private final Connection connection;

    public AgenceDAO() throws SQLException {
        this.connection = DatabaseConfig.getConnection();
    }

    public void create(Agence agence) throws SQLException {
        String query = "INSERT INTO agence (quartier, ville, login, password) VALUES (?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, agence.getQuartier());
            statement.setString(2, agence.getVille());
            statement.setString(3, agence.getLogin());
            statement.setString(4, agence.getPassword());
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                agence.setId(rs.getInt(1));
            }
        }
    }

    public void update(Agence agence) throws SQLException {
        String query = "UPDATE agence SET quartier = ?, ville = ?, login = ?, password = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, agence.getQuartier());
            statement.setString(2, agence.getVille());
            statement.setString(3, agence.getLogin());
            statement.setString(4, agence.getPassword());
            statement.setInt(5, agence.getId());
            statement.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String query = "DELETE FROM agence WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    public Agence getById(int id) throws SQLException {
        String query = "SELECT * FROM agence WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return extractAgenceFromResultSet(resultSet);
            }
        }
        return null;
    }

    public List<Agence> getAll() throws SQLException {
        List<Agence> agenceList = new ArrayList<>();
        String query = "SELECT * FROM agence";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Agence agence = extractAgenceFromResultSet(resultSet);
                agenceList.add(agence);
            }
        }
        return agenceList;
    }

    private Agence extractAgenceFromResultSet(ResultSet resultSet) throws SQLException {
        Agence agence = new Agence(resultSet.getInt("id"),resultSet.getString("quartier"),resultSet.getString("ville"),resultSet.getString("login"),resultSet.getString("password") );

        return agence;
    }
}
