package com.autofaye.repository;

import java.util.List;
import java.util.Optional;

import com.autofaye.model.Vehicule;

public interface VehiculeRepository {

    Vehicule ajouter(Vehicule vehicule);
    
    Optional<Vehicule> trouverParId(String vehiculeId);

    List<Vehicule> trouverDisponibles();
}