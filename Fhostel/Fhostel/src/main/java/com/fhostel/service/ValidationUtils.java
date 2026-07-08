package com.fhostel.service;

public final class ValidationUtils {

    private ValidationUtils() {
    }

    public static void requireNonBlank(String valeur, String messageErreur) {
        if (valeur == null || valeur.isBlank()) {
            throw new FhostelException(messageErreur);
        }
    }

    public static void requirePositif(double valeur, String messageErreur) {
        if (valeur <= 0) {
            throw new FhostelException(messageErreur);
        }
    }

    public static void requireVrai(boolean condition, String messageErreur) {
        if (!condition) {
            throw new FhostelException(messageErreur);
        }
    }

    public static void requireFaux(boolean condition, String messageErreur) {
        if (condition) {
            throw new FhostelException(messageErreur);
        }
    }
}