package com.autofaye.model;

import java.util.ArrayList;
import java.util.List;

public class Vehicule {

    private final String id;
    private String immatriculation;
    private String marque;
    private String modele;
    private double tarif;
    private boolean disponible;
    private final List<Reservation> reservations;

    public Vehicule(String id, String immatriculation, String marque, String modele, double tarif) {
        this.id = id;
        this.immatriculation = immatriculation;
        this.marque = marque;
        this.modele = modele;
        this.tarif = tarif;
        this.disponible = true;
        this.reservations = new ArrayList<>();
    }

    public boolean estDisponible() {
        return disponible;
    }

    public void marquerCommeReserve() {
        this.disponible = false;
    }

    public void marquerCommeDisponible() {
        this.disponible = true;
    }

    public void ajouterReservation(Reservation reservation) {
        this.reservations.add(reservation);
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public String getId() {
        return id;
    }

    public String getImmatriculation() {
        return immatriculation;
    }

    public String getMarque() {
        return marque;
    }

    public String getModele() {
        return modele;
    }

    public double getTarif() {
        return tarif;
    }

    public void setImmatriculation(String immatriculation) {
        this.immatriculation = immatriculation;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public void setModele(String modele) {
        this.modele = modele;
    }

    public void setTarif(double tarif) {
        this.tarif = tarif;
    }
}
