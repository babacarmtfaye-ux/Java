package com.fhostel.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;

import com.fhostel.model.Chambre;
import com.fhostel.service.ChambreService;
import com.fhostel.service.FhostelException;
import com.fhostel.service.ReservationService;

public class ReceptionnisteController {

    private final ChambreService chambreService;
    private final ReservationService reservationService;
    private final Scanner scanner;

    public ReceptionnisteController(ChambreService chambreService, ReservationService reservationService,
            Scanner scanner) {
        this.chambreService = chambreService;
        this.reservationService = reservationService;
        this.scanner = scanner;
    }

    public void ajouterChambre() {
        System.out.print("Numero: ");
        String numero = scanner.nextLine();
        System.out.print("Type: ");
        String type = scanner.nextLine();
        System.out.print("Prix par nuitee: ");
        double prix = Double.parseDouble(scanner.nextLine());

        try {
            chambreService.ajouterChambre(numero, type, prix);
            System.out.println("Chambre ajoutee.");
        } catch (FhostelException e) {
            System.out.println("Erreur: " + e.getMessage());
        }
    }

    public void checkIn() {
        System.out.print("Numero de chambre: ");
        String numeroChambre = scanner.nextLine();
        System.out.print("CNI du client: ");
        String cni = scanner.nextLine();
        System.out.print("Nom du client: ");
        String nom = scanner.nextLine();
        System.out.print("Telephone: ");
        String telephone = scanner.nextLine();
        System.out.print("Date d'arrivee (AAAA-MM-JJ): ");
        LocalDate dateArrivee = LocalDate.parse(scanner.nextLine());
        System.out.print("Date de depart (AAAA-MM-JJ): ");
        LocalDate dateDepart = LocalDate.parse(scanner.nextLine());

        try {
            reservationService.checkIn(numeroChambre, cni, nom, telephone, dateArrivee, dateDepart);
            System.out.println("Reservation enregistree.");
        } catch (FhostelException e) {
            System.out.println("Erreur: " + e.getMessage());
        }
    }

    public void checkOut() {
        System.out.print("Numero de reservation: ");
        String numeroReservation = scanner.nextLine();
        System.out.print("Heure de depart (HH:MM): ");
        LocalTime heureDepart = LocalTime.parse(scanner.nextLine());

        try {
            double montant = reservationService.checkOut(numeroReservation, heureDepart);
            System.out.println("Facture finale: " + montant);
        } catch (FhostelException e) {
            System.out.println("Erreur: " + e.getMessage());
        }
    }

    public void listerChambresOccupees(){
        List<Chambre> chambres = chambreService.listerChambresOccupees();
        for (Chambre chambre : chambres) {
            System.out.println(chambre.getNumero() + " - " +chambre.getType());
        }
    }
}
