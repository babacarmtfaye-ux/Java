package com.fhostel.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class SaisieUtilisateur {

    private final Scanner scanner;
    private final SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");

    public SaisieUtilisateur(Scanner scanner) {
        this.scanner = scanner;
    }

    public String lireTexte(String label) {
        System.out.print(label + " : ");
        return scanner.nextLine();
    }

    public double lireDouble(String label) {
        System.out.print(label + " : ");
        try {
            return Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Valeur invalide, veuillez entrer un nombre.");
            return lireDouble(label);
        }
    }

    public Date lireDate(String label) {
        System.out.print(label + " (AAAA-MM-JJ) : ");
        try {
            return formatDate.parse(scanner.nextLine());
        } catch (ParseException e) {
            System.out.println("Format de date invalide, veuillez utiliser AAAA-MM-JJ.");
            return lireDate(label);
        }
    }
}