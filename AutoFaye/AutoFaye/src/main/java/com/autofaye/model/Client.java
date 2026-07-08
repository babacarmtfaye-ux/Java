package com.autofaye.model;

import java.util.ArrayList;
import java.util.List;

public class Client {
    private final String id;
    private String nom;
    private String prenom;
    private String telephone;
    private String code;
    private final List<Reservation> reservations;

    public Client(String id, String nom, String prenom, String telephone, String code) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        this.code = code;
        this.reservations = new ArrayList<>();
    }

    public void ajouterReservation(Reservation reservation) {
        this.reservations.add(reservation);
    }

    public List<Reservation> getHistoriqueReservations() {
        return reservations;
    }

    public String getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getCode() {
        return code;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setCode(String code) {
        this.code = code;
    }
}