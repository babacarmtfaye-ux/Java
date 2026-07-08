package com.autofaye;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import com.autofaye.controller.ReservationController;
import com.autofaye.controller.VehiculeController;
import com.autofaye.exception.ClientIntrouvableException;
import com.autofaye.exception.DonneesVehiculeInvalidesException;
import com.autofaye.exception.ReservationDejaTermineeException;
import com.autofaye.exception.ReservationIntrouvableException;
import com.autofaye.exception.VehiculeIndisponibleException;
import com.autofaye.exception.VehiculeIntrouvableException;
import com.autofaye.model.Client;
import com.autofaye.model.Reservation;
import com.autofaye.model.Vehicule;
import com.autofaye.repository.ClientRepository;


public class AgenceMenu {

    private static final String ROLE_AGENCE = "agence";
    private static final String ROLE_CLIENT = "client";
    private static final String ROLE_QUITTER = "0";
    private static final String MOT_DE_PASSE_AGENCE = "1234";

    private final ReservationController reservationController;
    private final VehiculeController vehiculeController;
    private final ClientRepository clientRepository;
    private Client clientConnecte;
    private String roleConnecte;

    private final SimpleDateFormat formatDate =
            new SimpleDateFormat("yyyy-MM-dd");


    public AgenceMenu(
            ReservationController reservationController,
            VehiculeController vehiculeController,
            ClientRepository clientRepository
    ) {
        this.reservationController = reservationController;
        this.vehiculeController = vehiculeController;
        this.clientRepository = clientRepository;
    }


    public void afficherMenu() {
        try (Scanner scanner = new Scanner(System.in)) {
            if (!authentifier(scanner)) {
                System.out.println("Accès refusé. Au revoir.");
                return;
            }

            boolean continuer = true;
            while (continuer) {
                afficherOptions();

                String choix = scanner.nextLine();

                switch (choix) {
                    case "1" -> traiterOptionUn(scanner);
                    case "2" -> traiterOptionDeux(scanner);
                    case "3" -> traiterOptionTrois(scanner);
                    case "4" -> traiterOptionQuatre();
                    case ROLE_QUITTER -> {
                        System.out.println("Fermeture de AutoFaye...");
                        continuer = false;
                    }
                    default -> System.out.println("Choix invalide.");
                }
            }
        }
    }


    private boolean authentifier(Scanner scanner) {
        System.out.println("\n===== AUTHENTIFICATION =====");

        while (true) {
            System.out.print("Rôle (agence/client, 0 pour quitter) : ");
            String role = scanner.nextLine().trim().toLowerCase();

            if (ROLE_QUITTER.equals(role)) {
                return false;
            }

            if (ROLE_AGENCE.equals(role)) {
                if (authentifierAgence(scanner)) {
                    return true;
                }
                continue;
            }

            if (ROLE_CLIENT.equals(role)) {
                if (authentifierClient(scanner)) {
                    return true;
                }
                continue;
            }

            System.out.println("Rôle invalide. Veuillez saisir 'agence' ou 'client'.\n");
        }
    }

    private boolean authentifierAgence(Scanner scanner) {
        System.out.print("Mot de passe agence : ");
        String password = scanner.nextLine();
        boolean valide = MOT_DE_PASSE_AGENCE.equals(password) || "admin".equalsIgnoreCase(password);
        if (valide) {
            this.roleConnecte = ROLE_AGENCE;
            return true;
        }

        System.out.println("Mot de passe agence incorrect. Veuillez réessayer.\n");
        return false;
    }

    private boolean authentifierClient(Scanner scanner) {
        System.out.print("Téléphone : ");
        String telephone = scanner.nextLine();
        System.out.print("Code client : ");
        String code = scanner.nextLine();

        Optional<Client> clientExistant = clientRepository.trouverParTelephone(telephone);
        if (clientExistant.isPresent()) {
            return clientRepository.trouverParTelephoneEtCode(telephone, code)
                    .map(client -> {
                        this.clientConnecte = client;
                        this.roleConnecte = ROLE_CLIENT;
                        return true;
                    })
                    .orElseGet(() -> {
                        System.out.println("Téléphone trouvé, mais code incorrect. Veuillez réessayer.\n");
                        return false;
                    });
        }

        System.out.println("Aucun compte trouvé pour ce numéro. Création d'un nouveau compte...\n");
        System.out.print("Prénom : ");
        String prenom = scanner.nextLine();
        System.out.print("Nom : ");
        String nom = scanner.nextLine();

        Client nouveauClient = clientRepository.creer(new Client(null, nom, prenom, telephone, code));
        this.clientConnecte = nouveauClient;
        this.roleConnecte = ROLE_CLIENT;
        System.out.println("Compte créé avec succès pour " + prenom + " " + nom + "\n");
        return true;
    }

    private void traiterOptionUn(Scanner scanner) {
        if (ROLE_CLIENT.equals(roleConnecte)) {
            gererVoirVehiculesDisponibles();
        } else {
            gererAjoutVehicule(scanner);
        }
    }

    private void traiterOptionDeux(Scanner scanner) {
        if (ROLE_CLIENT.equals(roleConnecte)) {
            gererPayerReservation(scanner);
        } else {
            gererEnregistrementReservation(scanner);
        }
    }

    private void traiterOptionTrois(Scanner scanner) {
        if (ROLE_CLIENT.equals(roleConnecte)) {
            gererVoirHistorique();
        } else {
            gererRetourVehicule(scanner);
        }
    }

    private void traiterOptionQuatre() {
        if (ROLE_CLIENT.equals(roleConnecte)) {
            System.out.println("Choix invalide.");
        } else {
            gererListeReservationsEnCours();
        }
    }

    private void afficherOptions() {
        if (ROLE_CLIENT.equals(roleConnecte)) {
            afficherOptionsClient();
            return;
        }

        System.out.println("\n===== AUTO FAYE =====");
        System.out.println("1. Ajouter un véhicule");
        System.out.println("2. Enregistrer une réservation");
        System.out.println("3. Retourner un véhicule");
        System.out.println("4. Voir les réservations en cours");
        System.out.println("0. Quitter");
        System.out.print("Votre choix : ");
    }

    private void afficherOptionsClient() {
        System.out.println("\n===== AUTO FAYE CLIENT =====");
        System.out.println("1. Voir les véhicules disponibles");
        System.out.println("2. Payer une réservation");
        System.out.println("3. Voir l'historique");
        System.out.println("0. Quitter");
        System.out.print("Votre choix : ");
    }


    public void gererAjoutVehicule(Scanner scanner) {

        System.out.print("Immatriculation : ");
        String immatriculation = scanner.nextLine();

        System.out.print("Marque : ");
        String marque = scanner.nextLine();

        System.out.print("Modèle : ");
        String modele = scanner.nextLine();

        try {
            System.out.print("Tarif journalier : ");
            double tarif = Double.parseDouble(scanner.nextLine());

            Vehicule vehicule =
                    vehiculeController.ajouterVehicule(
                            immatriculation,
                            marque,
                            modele,
                            tarif
                    );

            System.out.println(
                    "Véhicule ajouté avec succès : "
                    + vehicule.getId()
            );

        } catch (NumberFormatException e) {
            System.out.println("Erreur : tarif invalide.");
        } catch (DonneesVehiculeInvalidesException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }


    public void gererEnregistrementReservation(Scanner scanner) {

        try {

            System.out.print("ID Client : ");
            String clientId = scanner.nextLine();


            System.out.print("ID Véhicule : ");
            String vehiculeId = scanner.nextLine();


            System.out.print("Date début (AAAA-MM-JJ) : ");
            Date dateDebut =
                    formatDate.parse(scanner.nextLine());


            System.out.print("Date fin (AAAA-MM-JJ) : ");
            Date dateFin =
                    formatDate.parse(scanner.nextLine());


            Reservation reservation =
                    reservationController.enregistrerReservation(
                            clientId,
                            vehiculeId,
                            dateDebut,
                            dateFin
                    );


            System.out.println(
                    "Réservation confirmée : "
                    + reservation.getId()
            );


        } catch (
                ClientIntrouvableException |
                VehiculeIntrouvableException |
                VehiculeIndisponibleException e
        ) {

            System.out.println("Erreur : " + e.getMessage());

        } catch (ParseException e) {

            System.out.println("Erreur de saisie de date.");
        }
    }


    public void gererRetourVehicule(Scanner scanner) {

        System.out.print("ID Réservation : ");
        String reservationId = scanner.nextLine();


        try {

            Reservation reservation =
                    reservationController.retournerVehicule(
                            reservationId
                    );


            System.out.println(
                    "Retour effectué pour la réservation : "
                    + reservation.getId()
            );


        } catch (
                ReservationIntrouvableException |
                ReservationDejaTermineeException e
        ) {

            System.out.println("Erreur : " + e.getMessage());
        }
    }

    public void gererVoirVehiculesDisponibles() {
        java.util.List<Vehicule> vehicules = vehiculeController.listerDisponibles();

        if (vehicules.isEmpty()) {
            System.out.println("Aucun véhicule disponible.");
            return;
        }

        System.out.println("\nVéhicules disponibles :");
        for (Vehicule vehicule : vehicules) {
            System.out.println(
                    "- ID : " + vehicule.getId()
                            + " | Immatriculation : " + vehicule.getImmatriculation()
                            + " | Marque : " + vehicule.getMarque()
                            + " | Modèle : " + vehicule.getModele()
                            + " | Tarif : " + vehicule.getTarif() + " FCFA"
            );
        }
    }

    public void gererPayerReservation(Scanner scanner) {
        System.out.print("ID Réservation : ");
        String reservationId = scanner.nextLine();

        try {
            Reservation reservation = reservationController.payerReservation(reservationId, clientConnecte.getId());
            System.out.println("Paiement effectué pour la réservation : " + reservation.getId());
        } catch (ReservationIntrouvableException | ReservationDejaTermineeException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }

    public void gererVoirHistorique() {
        java.util.List<Reservation> reservations = reservationController.listerHistoriqueClient(clientConnecte.getId());

        if (reservations.isEmpty()) {
            System.out.println("Aucune réservation trouvée pour ce client.");
            return;
        }

        System.out.println("\nHistorique des réservations :");
        for (Reservation reservation : reservations) {
            System.out.println(
                    "- ID : " + reservation.getId()
                            + " | Véhicule : " + reservation.getVehicule().getImmatriculation()
                            + " | Prix : " + reservation.getPrix()
                            + " | Statut : " + reservation.getStatut()
            );
        }
    }

    public void gererListeReservationsEnCours() {

        List<Reservation> reservations =
                reservationController.listerReservationsEnCours();


        if (reservations.isEmpty()) {

            System.out.println(
                    "Aucune réservation en cours."
            );

            return;
        }


        System.out.println("\nRéservations en cours :");


        for (Reservation reservation : reservations) {

            System.out.println(
                    "- ID : " + reservation.getId()
                    + " | Client : "
                    + reservation.getClient().getNom()
                    + " | Véhicule : "
                    + reservation.getVehicule().getImmatriculation()
                    + " | Prix : "
                    + reservation.getPrix()
                    + " FCFA"
            );
        }
    }
}