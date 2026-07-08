// ajout modifier supprimer lister

//ajout = numero sen valide + nom; (numero unique ) (nom peut avoir plusieur numero)
// modifier = modifier le contact
// supprimer = supprimer contact
// lister = lister selon nom ou num orange num free num expresso
// utiliser que des tableau pas de class
//un contact peut avoir plusieurs numero mais un numero ne peut appartenir qu'à un seul contact
//modifier un numero d'un contact
//supprimer un numero d'un contact
import java.util.Scanner;

public class Telephone {

    static final int MAX = 100;
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        String[] noms = new String[MAX];
        String[][] numeros = new String[MAX][5];
        int[] nbNumeros = new int[MAX];

        int total = 0;
        int choix;

        do {
            afficherMenu();

            choix = sc.nextInt();
            sc.nextLine();

            switch (choix) {

                case 1 ->
                    total = ajouterContact(noms, numeros, nbNumeros, total);

                case 2 ->
                    total = supprimerContact(noms, numeros, nbNumeros, total);

                case 3 ->
                    modifierContact(noms, numeros, nbNumeros, total);

                case 4 ->
                    listerContacts(noms, numeros, nbNumeros, total);

                case 5 ->
                    rechercherParNom(noms, numeros, nbNumeros, total);

                case 6 ->
                    rechercherParNumero(noms, numeros, nbNumeros, total);

                case 7 ->
                    modifierNumeroContact(noms, numeros, nbNumeros, total);

                case 8 ->
                    supprimerNumeroContact(noms, numeros, nbNumeros, total);

                case 9 ->
                    ajouterNumeroContact(noms, numeros, nbNumeros, total);

                case 0 ->
                    System.out.println("Au revoir !");

                default ->
                    System.out.println("Choix invalide.");
            }

        } while (choix != 0);

        sc.close();
    }

    // ================= MENU =================
    public static void afficherMenu() {
        System.out.println("\n========== MENU ==========");
        System.out.println("1 - Ajouter un contact");
        System.out.println("2 - Supprimer un contact");
        System.out.println("3 - Modifier un contact");
        System.out.println("4 - Lister les contacts");
        System.out.println("5 - Rechercher par nom");
        System.out.println("6 - Rechercher par numéro");
        System.out.println("7 - Modifier un numéro d'un contact");
        System.out.println("8 - Supprimer un numéro d'un contact");
        System.out.println("9 - Ajouter un numéro à un contact");
        System.out.println("0 - Quitter");
        System.out.print("Votre choix : ");
    }

    // ================= AJOUT =================
    public static int ajouterContact(
            String[] noms,
            String[][] numeros,
            int[] nbNumeros,
            int total) {

        if (total >= MAX) {
            return total;
        }

        System.out.print("Nom : ");
        String nom = sc.nextLine();

        noms[total] = nom;
        nbNumeros[total] = 0;

        String num;

        do {
            System.out.print("Numéro principal : ");
            num = sc.nextLine();

        } while (!numValide(num) || numeroExiste(numeros, nbNumeros, total, num));

        numeros[total][nbNumeros[total]++] = num;

        System.out.println("Contact ajouté.");

        return total + 1;
    }

    public static void ajouterNumeroContact(
            String[] noms,
            String[][] numeros,
            int[] nbNumeros,
            int total) {

        if (total == 0) {
            System.out.println("Aucun contact.");
            return;
        }

        for (int i = 0; i < total; i++) {
            System.out.println((i + 1) + " - " + noms[i]);
        }

        System.out.print("Choisir contact : ");
        int c = sc.nextInt();
        sc.nextLine();
        c--;

        if (c < 0 || c >= total) {
            return;
        }

        String num;

        do {
            System.out.print("Nouveau numéro : ");
            num = sc.nextLine();

            if (!numValide(num)) {
                System.out.println("Numéro invalide.");
                num = "";
            }

        } while (num.isEmpty() || numeroExiste(numeros, nbNumeros, total, num));

        numeros[c][nbNumeros[c]++] = num;

        System.out.println("Numéro ajouté.");
    }

    // ================= SUPPRESSION CONTACT =================
    public static int supprimerContact(
            String[] noms,
            String[][] numeros,
            int[] nbNumeros,
            int total) {

        if (total == 0) {
            return 0;
        }

        for (int i = 0; i < total; i++) {
            afficherContact(i, noms, numeros, nbNumeros);
        }

        System.out.print("Indice à supprimer : ");
        int indice = sc.nextInt();
        sc.nextLine();

        indice--;

        if (indice < 0 || indice >= total) {
            return total;
        }

        for (int i = indice; i < total - 1; i++) {
            noms[i] = noms[i + 1];
            numeros[i] = numeros[i + 1];
            nbNumeros[i] = nbNumeros[i + 1];
        }

        return total - 1;
    }

    // ================= MODIFIER CONTACT =================
    public static void modifierContact(
            String[] noms,
            String[][] numeros,
            int[] nbNumeros,
            int total) {

        if (total == 0) {
            return;
        }

        for (int i = 0; i < total; i++) {
            afficherContact(i, noms, numeros, nbNumeros);
        }

        System.out.print("Contact à modifier : ");
        int c = sc.nextInt();
        sc.nextLine();
        c--;

        if (c < 0 || c >= total) {
            return;
        }

        System.out.print("Nouveau nom : ");
        noms[c] = sc.nextLine();

        System.out.print("Indice numéro : ");
        int idx = sc.nextInt();
        sc.nextLine();

        if (idx < 0 || idx >= nbNumeros[c]) {
            return;
        }

        String num;

        do {
            System.out.print("Nouveau numéro : ");
            num = sc.nextLine();
        } while (!numValide(num) || numeroExiste(numeros, nbNumeros, total, num));

        numeros[c][idx] = num;

        System.out.println("Contact modifié.");
    }

    // ================= SUPPRIMER NUMÉRO =================
    public static void supprimerNumeroContact(
            String[] noms,
            String[][] numeros,
            int[] nbNumeros,
            int total) {

        if (total == 0) {
            System.out.println("Aucun contact.");
            return;
        }

        for (int i = 0; i < total; i++) {
            System.out.println((i + 1) + " - " + noms[i]);
        }

        System.out.print("Choisir un contact : ");
        int c = sc.nextInt();
        sc.nextLine();
        c--;

        if (c < 0 || c >= total) {
            System.out.println("Contact invalide.");
            return;
        }

        if (nbNumeros[c] == 0) {
            System.out.println("Ce contact n'a aucun numéro.");
            return;
        }

        for (int i = 0; i < nbNumeros[c]; i++) {
            System.out.println((i + 1) + " - " + numeros[c][i]);
        }

        System.out.print("Choisir le numéro à supprimer : ");
        int idx = sc.nextInt();
        sc.nextLine();
        idx--;

        if (idx < 0 || idx >= nbNumeros[c]) {
            System.out.println("Numéro invalide.");
            return;
        }

        for (int i = idx; i < nbNumeros[c] - 1; i++) {
            numeros[c][i] = numeros[c][i + 1];
        }

        nbNumeros[c]--;

        System.out.println("Numéro supprimé.");
    }

    // ================= MODIFIER NUMÉRO =================
    public static void modifierNumeroContact(
            String[] noms,
            String[][] numeros,
            int[] nbNumeros,
            int total) {

        if (total == 0) {
            System.out.println("Aucun contact.");
            return;
        }

        for (int i = 0; i < total; i++) {
            System.out.println((i + 1) + " - " + noms[i]);
        }

        System.out.print("Choisir un contact : ");
        int c = sc.nextInt();
        sc.nextLine();
        c--;

        if (c < 0 || c >= total) {
            System.out.println("Contact invalide.");
            return;
        }

        if (nbNumeros[c] == 0) {
            System.out.println("Ce contact n'a aucun numéro.");
            return;
        }

        for (int i = 0; i < nbNumeros[c]; i++) {
            System.out.println((i + 1) + " - " + numeros[c][i]);
        }

        System.out.print("Choisir le numéro à modifier : ");
        int idx = sc.nextInt();
        sc.nextLine();
        idx--;

        if (idx < 0 || idx >= nbNumeros[c]) {
            System.out.println("Numéro invalide.");
            return;
        }

        String nouveau;

        do {
            System.out.print("Nouveau numéro : ");
            nouveau = sc.nextLine();

            if (!numValide(nouveau)) {
                System.out.println("Numéro invalide.");
                nouveau = "";
            }

        } while (nouveau.isEmpty() || numeroExiste(numeros, nbNumeros, total, nouveau));

        numeros[c][idx] = nouveau;

        System.out.println("Numéro modifié.");
    }

    // ================= LISTE =================
    public static void listerContacts(
            String[] noms,
            String[][] numeros,
            int[] nbNumeros,
            int total) {

        for (int i = 0; i < total; i++) {
            afficherContact(i, noms, numeros, nbNumeros);
        }
    }

    // ================= AFFICHAGE =================
    public static void afficherContact(
            int i,
            String[] noms,
            String[][] numeros,
            int[] nbNumeros) {

        System.out.println("Nom : " + noms[i]);

        for (int j = 0; j < nbNumeros[i]; j++) {
            System.out.println("   - " + numeros[i][j]);
        }
    }

    // ================= RECHERCHE =================
    public static void rechercherParNom(
            String[] noms,
            String[][] numeros,
            int[] nbNumeros,
            int total) {

        System.out.print("Nom : ");
        String nom = sc.nextLine();

        for (int i = 0; i < total; i++) {
            if (noms[i].equalsIgnoreCase(nom)) {
                afficherContact(i, noms, numeros, nbNumeros);
            }
        }
    }

    public static void rechercherParNumero(
            String[] noms,
            String[][] numeros,
            int[] nbNumeros,
            int total) {

        System.out.print("Numéro : ");
        String num = sc.nextLine();

        for (int i = 0; i < total; i++) {
            for (int j = 0; j < nbNumeros[i]; j++) {
                if (numeros[i][j].equals(num)) {
                    afficherContact(i, noms, numeros, nbNumeros);
                    return;
                }
            }
        }

        System.out.println("Introuvable");
    }

    // ================= VALIDATION =================
    public static boolean numValide(String numero) {
        return numero.matches("\\d{9}")
                && (numero.startsWith("70")
                || numero.startsWith("75")
                || numero.startsWith("76")
                || numero.startsWith("77")
                || numero.startsWith("78"));
    }

    public static boolean numeroExiste(
            String[][] numeros,
            int[] nbNumeros,
            int total,
            String num) {

        for (int i = 0; i < total; i++) {
            for (int j = 0; j < nbNumeros[i]; j++) {
                if (numeros[i][j].equals(num)) {
                    return true;
                }
            }
        }
        return false;
    }
}
