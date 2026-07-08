package com.fhostel.utils;

public class Validateur {
    public boolean estTexteValide(String valeur) {
        return valeur != null && !valeur.isBlank();
    }
}