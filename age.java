import java.util.Scanner;

public class age{

    public static void main(String[] args) {
        try(Scanner sc = new Scanner(System.in)){

            int age;
            String nat;


            System.out.println("Entrez votre âge");
            age = sc.nextInt();

            System.out.println("Entrez votre nationalité");
            nat = sc.next();

            if ("senegalais".equals(nat)){
                if (age >= 18){
                    System.out.println("Vous pouvez voter");
                } else{
                    System.out.println("Vous ne pouvez pas voter");
                }
            } else {
                System.out.println("Vous ne pouvez pas voter");
            }
            
            
        }
    }
}