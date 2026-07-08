import java.util.Scanner;

public class calculatrice {

    public static void main(String[] args) {

        int a, b;
        String op;

        try (Scanner sc = new Scanner(System.in)) {
            System.out.println("Entrez a : ");
            a = sc.nextInt();

            System.out.println("Entrez b : ");
            b = sc.nextInt();

            System.out.println("Opération (+ - * /) : ");
            op = sc.next();

            int resultat = 0;
            boolean valide = true;

            switch (op) {
                case "+" -> resultat = addition(a, b);
                case "-" -> resultat = soustraction(a, b);
                case "*" -> resultat = multiplication(a, b);
                case "/" -> {
                    if (b == 0) {
                        System.out.println("Erreur : division par zéro");
                        valide = false;
                    } else {
                        resultat = division(a, b);
                    }
                }
                default -> {
                    System.out.println("Opération non valide");
                    valide = false;
                }
            }

            if (valide) {
                System.out.println("Résultat : " + resultat);
            }
        }
    }


    static int addition(int a, int b) {
        return a + b;
    }

    static int soustraction(int a, int b) {
        return a - b;
    }

    static int multiplication(int a, int b) {
        return a * b;
    }

    static int division(int a, int b) {
        return a / b;
    }
}



