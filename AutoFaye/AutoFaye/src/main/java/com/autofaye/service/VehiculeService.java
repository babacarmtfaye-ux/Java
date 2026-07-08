package com.autofaye.service;

import com.autofaye.exception.DonneesVehiculeInvalidesException;
import com.autofaye.model.Vehicule;
import com.autofaye.repository.VehiculeRepository;
import com.autofaye.utils.GenerateurId;
import com.autofaye.utils.Validateur;

public class VehiculeService {

    private final VehiculeRepository vehiculeRepository;
    private final Validateur validateur;
    private final GenerateurId generateurId;

    public VehiculeService(VehiculeRepository vehiculeRepository,
                            Validateur validateur,
                            GenerateurId generateurId) {
        this.vehiculeRepository = vehiculeRepository;
        this.validateur = validateur;
        this.generateurId = generateurId;
    }

    public Vehicule creerVehicule(String immatriculation, String marque, String modele, double tarif) {

        boolean donneesValides = validateur.estTexteValide(immatriculation)
                && validateur.estTexteValide(marque)
                && validateur.estTexteValide(modele)
                && tarif > 0;

        if (!donneesValides) {
            throw new DonneesVehiculeInvalidesException("Données du véhicule invalides");
        }

        String vehiculeId = generateurId.genererId("VH");
        Vehicule vehicule = new Vehicule(vehiculeId, immatriculation, marque, modele, tarif);

        return vehiculeRepository.ajouter(vehicule);
    }

    public java.util.List<Vehicule> listerDisponibles() {
        return vehiculeRepository.trouverDisponibles();
    }
}