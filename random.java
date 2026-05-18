import java.util.Scanner;

public class random {
    public static void main(String[] args){

        Scanner sc = new Scanner(System.in);
    
        int secret = (int)(Math.random() * 100) + 1;
        int a;

        do{
            System.out.println("Trouvez la valeur ");
            a = sc.nextInt();
            if (a < secret){
                System.out.println("Trop petit");
            } else if (a > secret) {
            System.out.println("Trop grand");
            } else {
            System.out.println("Trouvé Géniale Hourra!!!");
            }
        } while (a != secret);

    }
}