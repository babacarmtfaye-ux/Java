import java.util.Scanner;

public class num {

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)){
            int N;
            int resultat, i;

            System.out.println("Entrez la valeur");
            N = sc.nextInt();

            resultat = 0;

            for(i = 1; i<=N; i++){
                resultat = resultat + i;
            }

            System.out.println("Le resultat est de " + resultat);
        }
    }
}