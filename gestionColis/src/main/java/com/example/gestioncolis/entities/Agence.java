package com.example.gestioncolis.entities;

public class Agence {
    // Attributs
    private int id;
    private String quartier;
    private String ville;
    private String login;
    private String password;

    // Constructeur
    public Agence(int id, String quartier, String ville, String login, String password) {
        this.id = id;
        this.quartier = quartier;
        this.ville = ville;
        this.login = login;
        this.password = password;
    }
    public Agence(String quartier, String ville, String login, String password) {
        this.quartier = quartier;
        this.ville = ville;
        this.login = login;
        this.password = password;
    }

    // Getters et setters (si nécessaire)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuartier() {
        return quartier;
    }

    public void setQuartier(String quartier) {
        this.quartier = quartier;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}