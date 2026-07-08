package com.autofaye.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.autofaye.exception.ClientIntrouvableException;
import com.autofaye.exception.ReservationDejaTermineeException;
import com.autofaye.exception.ReservationIntrouvableException;
import com.autofaye.exception.VehiculeIndisponibleException;
import com.autofaye.exception.VehiculeIntrouvableException;
import com.autofaye.model.Client;
import com.autofaye.model.Reservation;
import com.autofaye.model.Vehicule;
import com.autofaye.repository.ClientRepository;
import com.autofaye.repository.ReservationRepository;
import com.autofaye.repository.VehiculeRepository;
import com.autofaye.utils.GenerateurId;

public class ReservationService {

    private final ClientRepository clientRepository;
    private final VehiculeRepository vehiculeRepository;
    private final ReservationRepository reservationRepository;
    private final TarificationService tarificationService;
    private final GenerateurId generateurId;

    public ReservationService(ClientRepository clientRepository,
            VehiculeRepository vehiculeRepository,
            ReservationRepository reservationRepository,
            TarificationService tarificationService,
            GenerateurId generateurId) {
        this.clientRepository = clientRepository;
        this.vehiculeRepository = vehiculeRepository;
        this.reservationRepository = reservationRepository;
        this.tarificationService = tarificationService;
        this.generateurId = generateurId;
    }

    public Reservation creerReservation(String clientId, String vehiculeId, Date dateDebut, Date dateFin) {

        Client client = clientRepository.trouverParId(clientId)
                .orElseThrow(() -> new ClientIntrouvableException(clientId));

        Vehicule vehicule = vehiculeRepository.trouverParId(vehiculeId)
                .orElseThrow(() -> new VehiculeIntrouvableException(vehiculeId));

        if (!vehicule.estDisponible()) {
            throw new VehiculeIndisponibleException(vehiculeId);
        }

        double prix = tarificationService.calculerPrix(vehicule, dateDebut, dateFin);
        String reservationId = generateurId.genererId("RES");

        Reservation reservation = new Reservation(reservationId, dateDebut, prix, client, vehicule);
        Reservation reservationSauvegardee = reservationRepository.ajouter(reservation);

        vehicule.marquerCommeReserve();
        vehicule.ajouterReservation(reservationSauvegardee);
        client.ajouterReservation(reservationSauvegardee);

        return reservationSauvegardee;
    }

    public Reservation traiterRetour(String reservationId) {

        Reservation reservation = reservationRepository.trouverParId(reservationId)
                .orElseThrow(() -> new ReservationIntrouvableException(reservationId));

        if (!reservation.estEnCours()) {
            throw new ReservationDejaTermineeException(reservationId);
        }

        reservation.terminer();
        reservation.getVehicule().marquerCommeDisponible();

        return reservationRepository.modifier(reservation);
    }

    public List<Reservation> recupererReservationsEnCours() {
        return reservationRepository.trouverToutes().stream()
                .filter(Reservation::estEnCours)
                .collect(Collectors.toList());
    }

    public Reservation payerReservation(String reservationId, String clientId) {
        Reservation reservation = reservationRepository.trouverParId(reservationId)
                .orElseThrow(() -> new ReservationIntrouvableException(reservationId));

        if (!reservation.getClient().getId().equals(clientId)) {
            throw new ReservationIntrouvableException(reservationId);
        }

        if (!reservation.estEnCours()) {
            throw new ReservationDejaTermineeException(reservationId);
        }

        reservation.terminer();
        reservation.getVehicule().marquerCommeDisponible();

        return reservationRepository.modifier(reservation);
    }

    public List<Reservation> recupererHistorique(String clientId) {
        return reservationRepository.trouverParClientId(clientId);
    }
}