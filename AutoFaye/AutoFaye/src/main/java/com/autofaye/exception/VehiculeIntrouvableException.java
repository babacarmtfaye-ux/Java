package com.autofaye.exception;

public class VehiculeIntrouvableException extends RuntimeException {
    public VehiculeIntrouvableException(String vehiculeId) {
        super("Véhicule introuvable avec l'id : " + vehiculeId);
    }
}