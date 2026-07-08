package com.fhostel.model;

public abstract class Utilisateur {

    private final String id;
    private final String nom;
    private final String telephone;
    private final String code;

    public Utilisateur(String id, String nom, String telephone, String code) {
        this.id = id;
        this.nom = nom;
        this.telephone = telephone;
        this.code = code;
    }
    
    public boolean authentifier(String codeSaisi) {
        return this.code.equals(codeSaisi);
    }

    public String getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getCode () {
        return code;
    }
}
