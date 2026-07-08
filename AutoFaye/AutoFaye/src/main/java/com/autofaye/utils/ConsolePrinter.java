package com.autofaye.utils;

import com.autofaye.model.Reservation;
import java.util.List;

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
            return;
        }

        System.out.println("Réservations en cours :");
        for (Reservation r : reservations) {
            System.out.println("- ID : " + r.getId()
                    + " | Client : " + r.getClient().getNom()
                    + " | Véhicule : " + r.getVehicule().getImmatriculation()
                    + " | Prix : " + r.getPrix() + " FCFA");
        }
    }
}