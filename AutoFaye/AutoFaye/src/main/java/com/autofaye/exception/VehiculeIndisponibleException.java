package com.autofaye.exception;

public class VehiculeIndisponibleException extends RuntimeException {
    public VehiculeIndisponibleException(String vehiculeId) {
        super("Véhicule indisponible : " + vehiculeId);
    }
}