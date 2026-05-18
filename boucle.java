import java.util.Scanner;

public class boucle{
        public static void main(String[] args){
        try (Scanner sc = new Scanner(System.in)){

            String mot = "";

            while (!mot.equals("stop")){
                System.out.println("Entrez un mot");
                mot = sc.next();
            }
        }
    }

}
