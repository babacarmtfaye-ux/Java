package com.autofaye.exception;

public class ClientIntrouvableException extends RuntimeException {
    public ClientIntrouvableException(String clientId) {
        super("Client introuvable avec l'id : " + clientId);
    }
}