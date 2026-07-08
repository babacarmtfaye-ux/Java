package com.autofaye.controller;

import java.util.List;

import com.autofaye.model.Vehicule;
import com.autofaye.service.VehiculeService;

public class VehiculeController {

    private final VehiculeService vehiculeService;

    public VehiculeController(VehiculeService vehiculeService) {
        this.vehiculeService = vehiculeService;
    }

    public Vehicule ajouterVehicule(String immatriculation, String marque, String modele, double tarif) {
        return vehiculeService.creerVehicule(immatriculation, marque, modele, tarif);
    }

    public List<Vehicule> listerDisponibles() {
        return vehiculeService.listerDisponibles();
    }
}