package com.fhostel.model;

public class Client extends Utilisateur {

    private final String numeroPiece;

    public Client(String id, String nom, String telephone, String code, String numeroPiece) {
        super(id, nom, telephone, code);
        this.numeroPiece = numeroPiece;
    }

    public String getNumeroPiece() {
        return numeroPiece;
    }
}

