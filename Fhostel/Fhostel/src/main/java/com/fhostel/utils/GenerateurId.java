package com.fhostel.utils;

public class GenerateurId {
    public String genererId(String prefixe) {
        return prefixe + "-" + System.currentTimeMillis();
    }
}