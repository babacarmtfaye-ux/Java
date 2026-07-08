package com.autofaye.repository;

import java.util.List;
import java.util.Optional;

import com.autofaye.model.Reservation;

public interface ReservationRepository {

    Reservation ajouter(Reservation reservation);

    Reservation modifier(Reservation reservation);

    List<Reservation> trouverToutes();

    List<Reservation> trouverParClientId(String clientId);

    Optional<Reservation> trouverParId(String id);
}