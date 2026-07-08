package com.fhostel.config;

import com.fhostel.controller.ClientController;
import com.fhostel.controller.MenuPrincipal;
import com.fhostel.controller.ReceptionnisteController;
import com.fhostel.repository.ChambreRepository;
import com.fhostel.repository.ChambreRepositoryImpl;
import com.fhostel.repository.ClientRepository;
import com.fhostel.repository.ClientRepositoryImpl;
import com.fhostel.repository.ReservationRepository;
import com.fhostel.repository.ReservationRepositoryImpl;
import com.fhostel.service.ChambreService;
import com.fhostel.service.ReservationService;

import java.util.Scanner;

public class ApplicationContext {

    public static MenuPrincipal creerMenu(Scanner scanner) {
        ChambreRepository chambreRepository = new ChambreRepositoryImpl();
        ClientRepository clientRepository = new ClientRepositoryImpl();
        ReservationRepository reservationRepository = new ReservationRepositoryImpl();

        ChambreService chambreService = new ChambreService(chambreRepository);
        ReservationService reservationService = new ReservationService(chambreRepository, clientRepository,
                reservationRepository);

        ReceptionnisteController receptionnisteController = new ReceptionnisteController(chambreService,
                reservationService, scanner);
        ClientController clientController = new ClientController(chambreService, reservationService, scanner);

        return new MenuPrincipal(receptionnisteController, clientController, scanner);
    }
}