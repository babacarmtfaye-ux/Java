package com.fhostel.controller;

import java.util.Scanner;

public class MenuPrincipal {

    private final ReceptionnisteController receptionnisteController;
    private final ClientController clientController;
    private final Scanner scanner;

    public MenuPrincipal(ReceptionnisteController receptionnisteController, ClientController clientController,
            Scanner scanner) {
        this.receptionnisteController = receptionnisteController;
        this.clientController = clientController;
        this.scanner = scanner;
    }

    public void demarrer() {
        System.out.println("1. Menu Receptionniste");
        System.out.println("2. Menu Client");
        System.out.print("Choix: ");
        String choix = scanner.nextLine();

        if (choix.equals("1")) {
            menuReceptionniste();
        } else if (choix.equals("2")) {
            menuClient();
        }
    }

    private void menuReceptionniste() {
        int option = -1;
        while (option != 0) {
            System.out.println("\n--- Menu Receptionniste ---");
            System.out.println("1. Ajouter une Chambre");
            System.out.println("2. Enregistrer une Reservation (Check-in)");
            System.out.println("3. Liberer une Chambre (Check-out)");
            System.out.println("4. Lister les Chambres occupees");
            System.out.println("0. Quitter");
            System.out.print("Choix: ");
            option = Integer.parseInt(scanner.nextLine());

            switch (option) {
                case 1 ->
                    receptionnisteController.ajouterChambre();
                case 2 ->
                    receptionnisteController.checkIn();
                case 3 ->
                    receptionnisteController.checkOut();
                case 4 ->
                    receptionnisteController.listerChambresOccupees();
                case 0 ->
                    System.out.println("Au revoir.");
                default ->
                    System.out.println("Option invalide.");
            }
        }
    }

    private void menuClient() {
        int option = -1;
        while (option != 0) {
            System.out.println("\n--- Menu Client ---");
            System.out.println("1. Voir les Chambres Disponibles");
            System.out.println("2. Regler une Facture");
            System.out.println("3. Historique de mes Sejours");
            System.out.println("0. Quitter");
            System.out.print("Choix: ");
            option = Integer.parseInt(scanner.nextLine());

            switch (option) {
                case 1 ->
                    clientController.voirChambresDisponibles();
                case 2 ->
                    clientController.reglerFacture();
                case 3 ->
                    clientController.historiqueSejours();
                case 0 ->
                    System.out.println("Au revoir.");
                default ->
                    System.out.println("Option invalide.");
            }
        }
    }
}
