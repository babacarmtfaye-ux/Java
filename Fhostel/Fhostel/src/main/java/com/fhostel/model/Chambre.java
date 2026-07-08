package com.fhostel.model;

public class Chambre {

    public static final String DISPONIBLE = "Disponible";
    public static final String OCCUPEE = "Occupee";
    public static final String EN_NETTOYAGE = "En Nettoyage";

    private final String numero;
    private final String type;
    private final double prixNuitee;
    private String statut;

    public Chambre(String numero, String type, double prixNuitee) {
        this.numero = numero;
        this.type = type;
        this.prixNuitee = prixNuitee;
        this.statut = DISPONIBLE;
    }

    public void changerStatut(String nouveauStatut) {
        this.statut = nouveauStatut;
    }

    public String getNumero() {
        return numero;
    }

    public String getType() {
        return type;
    }

    public double getPrixNuitee() {
        return prixNuitee;
    }

    public String getStatut() {
        return statut;
    }
}