package com.fhostel;

import java.util.Scanner;

import com.fhostel.config.ApplicationContext;
import com.fhostel.controller.MenuPrincipal;

public class Main {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            MenuPrincipal menu = ApplicationContext.creerMenu(scanner);
            menu.demarrer();
        }
    }
}