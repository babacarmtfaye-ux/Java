package com.fhostel.service;

import java.util.List;

import com.fhostel.model.Chambre;
import com.fhostel.repository.ChambreRepository;

public class ChambreService {

    private final ChambreRepository chambreRepository;

    public ChambreService(ChambreRepository chambreRepository) {
        this.chambreRepository = chambreRepository;
    }

    public void ajouterChambre(String numero, String type, double prix) {
        ValidationUtils.requireFaux(chambreRepository.trouverParNumero(numero).isPresent(),
                "Le numero de chambre est deja utilise");
        ValidationUtils.requireNonBlank(type, "Le type de chambre est obligatoire");
        ValidationUtils.requirePositif(prix, "Le prix doit etre positif");

        chambreRepository.ajouter(new Chambre(numero, type, prix));
    }

    public List<Chambre> listerChambresOccupees() {
        return chambreRepository.trouverParStatut(Chambre.OCCUPEE);
    }

    public List<Chambre> voirChambresDisponibles() {
        return chambreRepository.trouverParStatut(Chambre.DISPONIBLE);
    }
}
