package com.fhostel.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.fhostel.model.Chambre;

public class ChambreRepositoryImpl implements ChambreRepository {

    private final List<Chambre> chambres = new ArrayList<>();

    @Override
    public void ajouter(Chambre chambre) {
        chambres.add(chambre);
    }

    @Override
    public Optional<Chambre> trouverParNumero(String numero) {
        return chambres.stream()
                .filter(c -> c.getNumero().equals(numero))
                .findFirst();
    }

    @Override
    public List<Chambre> trouverToutes() {
        return chambres;
    }

    @Override
    public List<Chambre> trouverParStatut(String statut) {
        return chambres.stream()
                .filter(c -> c.getStatut().equals(statut))
                .toList();
    }
}