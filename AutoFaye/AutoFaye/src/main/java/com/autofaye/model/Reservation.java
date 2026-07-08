package com.autofaye.model;

import java.util.Date;

public class Reservation {

    private final String id;
    private Date date;
    private double prix;
    private final Client client;
    private final Vehicule vehicule;
    private StatutReservation statut;

    public Reservation(String id, Date date, double prix, Client client, Vehicule vehicule) {
        this.id = id;
        this.date = date;
        this.prix = prix;
        this.client = client;
        this.vehicule = vehicule;
        this.statut = StatutReservation.EN_COURS;
    }

    public boolean estEnCours() {
        return statut == StatutReservation.EN_COURS;
    }

    public void terminer() {
        this.statut = StatutReservation.TERMINEE;
    }

    public String getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public double getPrix() {
        return prix;
    }

    public Client getClient() {
        return client;
    }

    public Vehicule getVehicule() {
        return vehicule;
    }

    public StatutReservation getStatut() {
        return statut;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }
}
