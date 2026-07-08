package com.autofaye.utils;

public class GenerateurId {
    public String genererId(String prefixe) {
        return prefixe + "-" + System.currentTimeMillis();
    }
}