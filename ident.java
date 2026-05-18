import java.util.Scanner;

public class ident{
    public static void main(String[] args){
        try (Scanner sc = new Scanner(System.in)){
            int id, id2;
            String mdp, mdp2;

            System.out.println("Initialiser votre id");
            id = sc.nextInt();

            System.out.println("Initialiser votre mpd");
            mdp = sc.next();

            System.out.println("Entrez votre id");
            id2 = sc.nextInt();

            System.out.println("Entrez votre mpd");
            mdp2 = sc.next();

            if (id == id2){
                if (mdp.equals(mdp2)){
                    System.out.println("Vous pouvez accèder à votre page.");
                } else {
                    System.out.println("Vous ne pouvez pas accèder à votre page");
                }
            } else {
                System.out.println("Vous ne pouvez pas accèder à votre page");
            }

            
        }
    }
}

