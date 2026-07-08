package com.fhostel.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {

    public static final String EN_COURS = "En cours";
    public static final String TERMINEE = "Terminee";

    private static final LocalTime HEURE_LIMITE = LocalTime.of(11, 0);
    private static final double TAUX_SUPPLEMENT = 0.5;

    private final String id;
    private final Client client;
    private final Chambre chambre;
    private final LocalDate dateArrivee;
    private final LocalDate dateDepart;
    private String statut;
    private double montantFacture;
    private boolean reglee;

    public Reservation(String id, Client client, Chambre chambre, LocalDate dateArrivee, LocalDate dateDepart) {
        this.id = id;
        this.client = client;
        this.chambre = chambre;
        this.dateArrivee = dateArrivee;
        this.dateDepart = dateDepart;
        this.statut = EN_COURS;
        this.reglee = false;
    }

    public double calculerSupplement(LocalTime heureDepart) {
        if (heureDepart.isAfter(HEURE_LIMITE)) {
            return chambre.getPrixNuitee() * TAUX_SUPPLEMENT;
        }
        return 0;
    }

    public void cloturer() {
        this.statut = TERMINEE;
    }

    public void marquerCommeReglee() {
        this.reglee = true;
    }

    public String getDetail() {
        return "Reservation " + id + " - Chambre " + chambre.getNumero()
                + " du " + dateArrivee + " au " + dateDepart + " - statut: " + statut;
    }

    public String getId() {
        return id;
    }

    public Client getClient() {
        return client;
    }

    public Chambre getChambre() {
        return chambre;
    }

    public LocalDate getDateArrivee() {
        return dateArrivee;
    }

    public LocalDate getDateDepart() {
        return dateDepart;
    }

    public String getStatut() {
        return statut;
    }

    public double getMontantFacture() {
        return montantFacture;
    }

    public void setMontantFacture(double montantFacture) {
        this.montantFacture = montantFacture;
    }

    public boolean isReglee() {
        return reglee;
    }
}