// ajout modifier supprimer lister - version avec classes et objets

// ajout = numero sen valide + nom ; (numero unique) (nom peut avoir plusieurs numeros)
// modifier = modifier le contact
// supprimer = supprimer contact
// lister = lister selon nom ou par operateur (Orange / Free / Expresso)
// un contact peut avoir plusieurs numeros mais un numero ne peut appartenir qu'a un seul contact
// modifier un numero d'un contact
// supprimer un numero d'un contact

import java.util.ArrayList;
import java.util.Scanner;

// ===================== CLASSE CONTACT =====================
class Contact {

    private String nom;
    private ArrayList<String> numeros;

    public Contact(String nom) {
        this.nom = nom;
        this.numeros = new ArrayList<>();
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public ArrayList<String> getNumeros() {
        return numeros;
    }

    public void ajouterNumero(String numero) {
        numeros.add(numero);
    }

    public void modifierNumero(int idx, String nouveau) {
        numeros.set(idx, nouveau);
    }

    public void supprimerNumero(int idx) {
        numeros.remove(idx);
    }

    public boolean contientNumero(String numero) {
        return numeros.contains(numero);
    }

    public boolean aDesNumeros() {
        return !numeros.isEmpty();
    }

    public void afficher() {
        System.out.println("Nom : " + nom);
        afficherNumeros();
    }

    public void afficherNumeros() {
        for (int i = 0; i < numeros.size(); i++) {
            System.out.println("   " + (i + 1) + " - " + numeros.get(i));
        }
    }
}

// ===================== CLASSE REPERTOIRE =====================
class Repertoire {

    private ArrayList<Contact> contacts;
    private Scanner sc;

    public Repertoire(Scanner sc) {
        this.contacts = new ArrayList<>();
        this.sc = sc;
    }

    // ---------------- AJOUT ----------------
    public void ajouterContact() {
        System.out.print("Nom : ");
        String nom = sc.nextLine();

        Contact c = new Contact(nom);
        String num = demanderNumeroUnique("Numero principal : ", null);

        c.ajouterNumero(num);
        contacts.add(c);

        System.out.println("Contact ajoute.");
    }

    public void ajouterNumeroContact() {
        Contact c = choisirContact();
        if (c == null) {
            return;
        }

        String num = demanderNumeroUnique("Nouveau numero : ", null);
        c.ajouterNumero(num);
        System.out.println("Numero ajoute.");
    }

    // ---------------- SUPPRESSION CONTACT ----------------
    public void supprimerContact() {
        Contact c = choisirContactIndex("Indice a supprimer : ", "Indice invalide.");
        if (c == null) {
            return;
        }

        contacts.remove(c);
        System.out.println("Contact supprime.");
    }

    // ---------------- MODIFIER CONTACT (NOM) ----------------
    public void modifierContact() {
        Contact c = choisirContactIndex("Contact a modifier : ", "Contact invalide.");
        if (c == null) {
            return;
        }

        System.out.print("Nouveau nom : ");
        c.setNom(sc.nextLine());
        System.out.println("Contact modifie.");
    }

    // ---------------- MODIFIER UN NUMERO ----------------
    public void modifierNumeroContact() {
        Contact c = choisirContact();
        if (c == null || !c.aDesNumeros()) {
            if (c != null) {
                System.out.println("Ce contact n'a aucun numero.");
            }
            return;
        }

        afficherNumeros(c);

        int idx = lireIndice("Choisir le numero a modifier : ", c.getNumeros().size(), "Numero invalide.");
        if (idx < 0) {
            return;
        }

        String nouveau = demanderNumeroUnique("Nouveau numero : ", c);
        c.modifierNumero(idx, nouveau);
        System.out.println("Numero modifie.");
    }

    // ---------------- SUPPRIMER UN NUMERO ----------------
    public void supprimerNumeroContact() {
        Contact c = choisirContact();
        if (c == null || !c.aDesNumeros()) {
            if (c != null) {
                System.out.println("Ce contact n'a aucun numero.");
            }
            return;
        }

        afficherNumeros(c);

        int idx = lireIndice("Choisir le numero a supprimer : ", c.getNumeros().size(), "Numero invalide.");
        if (idx < 0) {
            return;
        }

        c.supprimerNumero(idx);
        System.out.println("Numero supprime.");
    }

    // ---------------- LISTE ----------------
    public void lister() {
        if (contacts.isEmpty()) {
            System.out.println("Aucun contact.");
            return;
        }

        afficherContacts();
    }

    // ---------------- RECHERCHE ----------------
    public void rechercherParNom() {
        System.out.print("Nom : ");
        String nom = sc.nextLine();

        boolean trouve = false;
        for (Contact c : contacts) {
            if (c.getNom().equalsIgnoreCase(nom)) {
                c.afficher();
                trouve = true;
            }
        }

        if (!trouve) {
            System.out.println("Introuvable");
        }
    }

    public void rechercherParNumero() {
        System.out.print("Numero : ");
        String num = sc.nextLine();

        Contact c = trouverContactParNumero(num);
        if (c == null) {
            System.out.println("Introuvable");
        } else {
            c.afficher();
        }
    }

    // ---------------- LISTER PAR OPERATEUR ----------------
    public void listerParOperateur(String operateur) {
        boolean trouve = false;

        for (Contact c : contacts) {
            for (String num : c.getNumeros()) {
                if (getOperateur(num).equalsIgnoreCase(operateur)) {
                    System.out.println(c.getNom() + " - " + num);
                    trouve = true;
                }
            }
        }

        if (!trouve) {
            System.out.println("Aucun numero " + operateur + " trouve.");
        }
    }

    // ---------------- OUTILS ----------------
    private Contact choisirContactIndex(String invite, String messageErreur) {
        if (contacts.isEmpty()) {
            System.out.println("Aucun contact.");
            return null;
        }

        lister();

        int idx = lireIndice(invite, contacts.size(), messageErreur);
        return idx >= 0 ? contacts.get(idx) : null;
    }

    private Contact choisirContact() {
        if (contacts.isEmpty()) {
            System.out.println("Aucun contact.");
            return null;
        }

        afficherContactsNom();

        int idx = lireIndice("Choisir un contact : ", contacts.size(), "Contact invalide.");
        return idx >= 0 ? contacts.get(idx) : null;
    }

    private void afficherContacts() {
        for (int i = 0; i < contacts.size(); i++) {
            System.out.print((i + 1) + " - ");
            contacts.get(i).afficher();
        }
    }

    private void afficherContactsNom() {
        for (int i = 0; i < contacts.size(); i++) {
            System.out.println((i + 1) + " - " + contacts.get(i).getNom());
        }
    }

    private void afficherNumeros(Contact c) {
        c.afficherNumeros();
    }

    private int lireIndice(String invite, int max, String messageErreur) {
        System.out.print(invite);
        int idx = sc.nextInt() - 1;
        sc.nextLine();

        if (idx < 0 || idx >= max) {
            System.out.println(messageErreur);
            return -1;
        }

        return idx;
    }

    private String demanderNumeroUnique(String invite, Contact contactIgnore) {
        String num;
        do {
            System.out.print(invite);
            num = sc.nextLine();

            if (!numValide(num)) {
                System.out.println("Numero invalide.");
            } else if (numeroExiste(num, contactIgnore)) {
                System.out.println("Numero deja utilise.");
            }
        } while (!numValide(num) || numeroExiste(num, contactIgnore));

        return num;
    }

    private Contact trouverContactParNumero(String num) {
        for (Contact c : contacts) {
            if (c.contientNumero(num)) {
                return c;
            }
        }
        return null;
    }

    // ---------------- VALIDATION ----------------
    public boolean numValide(String numero) {
        return numero.matches("\\d{9}") && prefixeValide(numero);
    }

    private boolean prefixeValide(String numero) {
        return numero.startsWith("70")
                || numero.startsWith("75")
                || numero.startsWith("76")
                || numero.startsWith("77")
                || numero.startsWith("78");
    }

    public boolean numeroExiste(String num) {
        return numeroExiste(num, null);
    }

    private boolean numeroExiste(String num, Contact contactIgnore) {
        for (Contact c : contacts) {
            if (c != contactIgnore && c.contientNumero(num)) {
                return true;
            }
        }
        return false;
    }

    // Mapping indicatif des prefixes vers les operateurs senegalais
    public String getOperateur(String num) {
        if (num.startsWith("77") || num.startsWith("78") || num.startsWith("70")) {
            return "Orange";
        } else if (num.startsWith("76")) {
            return "Free";
        } else if (num.startsWith("75")) {
            return "Expresso";
        }
        return "Inconnu";
    }
}

// ===================== CLASSE PRINCIPALE =====================
public class Telephonev2 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Repertoire repertoire = new Repertoire(sc);

        int choix;

        do {
            afficherMenu();
            choix = lireChoix(sc);

            switch (choix) {

                case 1 ->
                    repertoire.ajouterContact();

                case 2 ->
                    repertoire.supprimerContact();

                case 3 ->
                    repertoire.modifierContact();

                case 4 ->
                    repertoire.lister();

                case 5 ->
                    repertoire.rechercherParNom();

                case 6 ->
                    repertoire.rechercherParNumero();

                case 7 ->
                    repertoire.modifierNumeroContact();

                case 8 ->
                    repertoire.supprimerNumeroContact();

                case 9 ->
                    repertoire.ajouterNumeroContact();

                case 10 -> {
                    System.out.print("Operateur (Orange / Free / Expresso) : ");
                    String op = sc.nextLine();
                    repertoire.listerParOperateur(op);
                }

                case 0 ->
                    System.out.println("Au revoir !");

                default ->
                    System.out.println("Choix invalide.");
            }

        } while (choix != 0);

        sc.close();
    }

    private static int lireChoix(Scanner sc) {
        int choix = sc.nextInt();
        sc.nextLine();
        return choix;
    }

    public static void afficherMenu() {
        System.out.println("\n========== MENU ==========");
        System.out.println("1 - Ajouter un contact");
        System.out.println("2 - Supprimer un contact");
        System.out.println("3 - Modifier un contact");
        System.out.println("4 - Lister les contacts");
        System.out.println("5 - Rechercher par nom");
        System.out.println("6 - Rechercher par numero");
        System.out.println("7 - Modifier un numero d'un contact");
        System.out.println("8 - Supprimer un numero d'un contact");
        System.out.println("9 - Ajouter un numero a un contact");
        System.out.println("10 - Lister par operateur (Orange / Free / Expresso)");
        System.out.println("0 - Quitter");
        System.out.print("Votre choix : ");
    }
}
