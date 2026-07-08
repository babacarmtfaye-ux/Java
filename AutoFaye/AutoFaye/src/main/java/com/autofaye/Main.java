package com.autofaye;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Locale;

import com.autofaye.controller.ReservationController;
import com.autofaye.controller.VehiculeController;
import com.autofaye.repository.ClientRepositoryJdbc;
import com.autofaye.repository.ReservationRepositoryJdbc;
import com.autofaye.repository.VehiculeRepositoryJdbc;
import com.autofaye.service.ReservationService;
import com.autofaye.service.TarificationService;
import com.autofaye.service.VehiculeService;
import com.autofaye.utils.GenerateurId;
import com.autofaye.utils.Validateur;

public class Main {

    public static void main(String[] args) {
        Connection connection = creerConnection();

        ClientRepositoryJdbc clientRepository = new ClientRepositoryJdbc(connection);
        VehiculeRepositoryJdbc vehiculeRepository = new VehiculeRepositoryJdbc(connection);
        ReservationRepositoryJdbc reservationRepository = new ReservationRepositoryJdbc(connection, clientRepository, vehiculeRepository);

        ReservationService reservationService = new ReservationService(
                clientRepository,
                vehiculeRepository,
                reservationRepository,
                new TarificationService(),
                new GenerateurId()
        );

        VehiculeService vehiculeService = new VehiculeService(
                vehiculeRepository,
                new Validateur(),
                new GenerateurId()
        );

        ReservationController reservationController = new ReservationController(reservationService);
        VehiculeController vehiculeController = new VehiculeController(vehiculeService);

        AgenceMenu agenceMenu = new AgenceMenu(reservationController, vehiculeController, clientRepository);
        agenceMenu.afficherMenu();
    }

    private static Connection creerConnection() {
        String defaultUrl = "jdbc:h2:mem:autofaye;DB_CLOSE_DELAY=-1;INIT=RUNSCRIPT FROM 'classpath:db/schema.sql'\\;RUNSCRIPT FROM 'classpath:db/seed.sql'";
        String url = System.getenv().getOrDefault("DB_URL", defaultUrl);
        String user = System.getenv().getOrDefault("DB_USER", "sa");
        String password = System.getenv().getOrDefault("DB_PASSWORD", "");

        System.out.println("Base utilisée : " + url);
        if (!url.toLowerCase(Locale.ROOT).startsWith("jdbc:h2:")) {
            System.out.println("Connexion PostgreSQL activée.");
            System.out.println("Assure-toi que le serveur PostgreSQL est démarré et accessible.");
        }

        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new IllegalStateException("""
                                            Impossible d'\u00e9tablir la connexion \u00e0 la base de donn\u00e9es
                                            URL = """ + url + "\nUtilisateur = " + user + "\nVérifie que PostgreSQL est bien lancé si tu utilises DB_URL.", e);
        }
    }
}