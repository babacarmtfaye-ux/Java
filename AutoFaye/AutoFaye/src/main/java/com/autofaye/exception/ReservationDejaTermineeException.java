package com.autofaye.exception;

public class ReservationDejaTermineeException extends RuntimeException {
    public ReservationDejaTermineeException(String reservationId) {
        super("Réservation déjà terminée : " + reservationId);
    }
}