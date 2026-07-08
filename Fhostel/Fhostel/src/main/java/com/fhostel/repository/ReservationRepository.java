package com.fhostel.repository;

import java.util.List;
import java.util.Optional;

import com.fhostel.model.Reservation;

public interface ReservationRepository extends Repository<Reservation> {

    Optional<Reservation> trouverParId(String id);

    List<Reservation> trouverParClient(String cni);
}