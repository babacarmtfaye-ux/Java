package com.fhostel.repository;

import java.util.List;
import java.util.Optional;

import com.fhostel.model.Chambre;

public interface ChambreRepository {

        void ajouter (Chambre chambre);

        Optional<Chambre> trouverParNumero(String numero);

        List<Chambre> trouverToutes();

        List<Chambre> trouverParStatut(String statut);
}