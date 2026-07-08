package com.fhostel.repository;

import com.fhostel.model.Client;

import java.util.Optional;

public interface ClientRepository extends Repository<Client> {

    Optional<Client> trouverParCNI(String cni);
}