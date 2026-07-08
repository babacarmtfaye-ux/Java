package com.autofaye.model;

import java.util.ArrayList;
import java.util.List;

public class Agence {
    private final String id;
    private String nom;
    private String code;
    private final List<Vehicule> vehicules;

    public Agence(String id, String nom, String code) {
        this.id = id;
        this.nom = nom;
        this.code = code;
        this.vehicules = new ArrayList<>();
    }

    public void ajouterVehicule(Vehicule vehicule) {
        vehicules.add(vehicule);
    }

    public void retirerVehicule(Vehicule vehicule) {
        vehicules.remove(vehicule);
    }

    public String getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getCode() {
        return code;
    }

    public List<Vehicule> getVehicules() {
        return vehicules;
    }

    
    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setCode(String code) {
        this.code = code;
    }
}