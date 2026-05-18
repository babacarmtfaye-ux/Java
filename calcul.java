import java.util.Scanner;

public class calcul {

    public static void main(String[] args) {

       try( Scanner sc = new Scanner(System.in)){


        int a, b, resultat;
        String o = "";

        do {
            System.out.println("Entrez la valeur de a (0-100): ");
            a = sc.nextInt();
        } while (a < 0 || a > 100);

        do {
            System.out.println("Entrez l'operation (+ - * /): ");
            o = sc.next();
        } while (!o.equals("+") && !o.equals("-") && !o.equals("*") && !o.equals("/"));

        do {
            System.out.println("Entrez la valeur de b: ");
            b = sc.nextInt();
        } while (o.equals("/") && b == 0);

        /*if (o.equals("+")) {
            resultat = a + b;
        } else if (o.equals("-")) {
            resultat = a - b;
        } else if (o.equals("*")) {
            resultat = a * b;
        } else {
            resultat = a / b;
        }*/

       /*switch (o) {
            case "+":
                resultat = a + b;
                break;
            case "-":
                resultat = a - b;
                break;
            case "*":
                resultat = a * b;
                break;
            default:
                resultat = a / b;
                break;
        }*/


        resultat = switch (o) {
            case "+" -> a + b;
            case "-" -> a - b;
            case "*" -> a * b;
            default -> a / b;
        };

        System.out.println("Le résultat est : " + resultat);

        sc.close();
       }
    }
}