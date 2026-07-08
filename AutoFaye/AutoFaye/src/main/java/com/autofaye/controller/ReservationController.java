package com.autofaye.controller;

import java.util.Date;
import java.util.List;

import com.autofaye.model.Reservation;
import com.autofaye.service.ReservationService;

public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    public Reservation enregistrerReservation(String clientId, String vehiculeId, Date dateDebut, Date dateFin) {
        return reservationService.creerReservation(clientId, vehiculeId, dateDebut, dateFin);
    }

    public Reservation retournerVehicule(String reservationId) {
        return reservationService.traiterRetour(reservationId);
    }

    public List<Reservation> listerReservationsEnCours() {
        return reservationService.recupererReservationsEnCours();
    }

    public Reservation payerReservation(String reservationId, String clientId) {
        return reservationService.payerReservation(reservationId, clientId);
    }

    public List<Reservation> listerHistoriqueClient(String clientId) {
        return reservationService.recupererHistorique(clientId);
    }
}