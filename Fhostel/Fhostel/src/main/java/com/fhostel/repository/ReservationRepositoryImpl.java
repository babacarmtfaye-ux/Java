package com.fhostel.repository;

import com.fhostel.model.Reservation;

import java.util.List;
import java.util.Optional;

public class ReservationRepositoryImpl extends RepositoryImpl<Reservation> implements ReservationRepository {

    @Override
    public Optional<Reservation> trouverParId(String id) {
        return elements.stream()
                .filter(r -> r.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Reservation> trouverParClient(String cni) {
        return elements.stream()
                .filter(r -> r.getClient().getNumeroPiece().equals(cni))
                .toList();
    }
}