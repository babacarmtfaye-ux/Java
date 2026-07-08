package com.fhostel.utils;

import java.util.List;

import com.fhostel.model.Reservation;

public class ConsolePrinter {

    public void afficherSucces(String message) {
        System.out.println(message);
    }

    public void afficherErreur(String message) {
        System.out.println("Erreur : " + message);
    }

    public void afficherReservations(List<Reservation> reservations) {
        if (reservations.isEmpty()) {
            System.out.println("Aucune réservation en cours.");
        }


    }
}