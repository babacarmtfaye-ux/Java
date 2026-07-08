package com.autofaye.repository;

import java.util.Optional;

import com.autofaye.model.Client;

public interface ClientRepository {

    Optional<Client> trouverParId(String clientId);

    Optional<Client> trouverParTelephoneEtCode(String telephone, String code);

    Optional<Client> trouverParTelephone(String telephone);

    Client creer(Client client);
}