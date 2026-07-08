package com.fhostel.repository;

import java.util.Optional;

import com.fhostel.model.Client;

public class ClientRepositoryImpl extends RepositoryImpl<Client> implements ClientRepository {

    @Override
    public Optional<Client> trouverParCNI(String cni) {
        return elements.stream()
                .filter(c -> c.getNumeroPiece().equals(cni))
                .findFirst();
    }
}