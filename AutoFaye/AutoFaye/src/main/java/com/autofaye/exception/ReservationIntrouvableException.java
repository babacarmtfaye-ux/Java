package com.autofaye.exception;

public class ReservationIntrouvableException extends RuntimeException {
    public ReservationIntrouvableException(String reservationId) {
        super("Réservation introuvable avec l'id : " + reservationId);
    }
}