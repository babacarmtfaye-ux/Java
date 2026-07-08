package com.fhostel.controller;

import java.util.List;
import java.util.Scanner;

import com.fhostel.model.Chambre;
import com.fhostel.model.Reservation;
import com.fhostel.service.ChambreService;
import com.fhostel.service.FhostelException;
import com.fhostel.service.ReservationService;

public class ClientController {

    private final ChambreService chambreService;
    private final ReservationService reservationService;
    private final Scanner scanner;

    public ClientController(ChambreService chambreService, ReservationService reservationService,
            Scanner scanner) {
        this.chambreService = chambreService;
        this.reservationService = reservationService;
        this.scanner = scanner;
    }

    public void voirChambresDisponibles() {
        List<Chambre> chambres = chambreService.voirChambresDisponibles();
        for (Chambre chambre : chambres) {
            System.out.println(chambre.getNumero() + " - " + chambre.getType() + " - " + chambre.getPrixNuitee());
        }
    }

    public void reglerFacture() {
        System.out.print("Numero de reservation: ");
        String numeroReservation = scanner.nextLine();
        System.out.print("Montant paye: ");
        double montant = Double.parseDouble(scanner.nextLine());

        try {
            reservationService.reglerFacture(numeroReservation, montant);
            System.out.println("Facture reglee.");
        } catch (FhostelException e) {
            System.out.println("Erreur: " + e.getMessage());
        }
    }

    public void historiqueSejours() {
        System.out.print("CNI: ");
        String cni = scanner.nextLine();

        List<Reservation> reservations = reservationService.historiqueSejours(cni);
        for (Reservation reservation : reservations) {
            System.out.println(reservation.getDetail());
        }
    }
}
