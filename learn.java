public class learn {
    public static void main(String[] args) {
       String name = " Babacar";
       var lastname = "Fayefay";

       int age = 21;
        int maths = 25 +85;


       System.out.println("Hello my name is " + lastname +" "+ name+" " + "i am " + age);
       System.out.println(name.length());
       System.out.println(maths);
       System.out.println(name.toLowerCase());
       System.out.println(name.equals(lastname));
       System.out.println(name.trim());

       String a = "Java ";
String b = "is ";
String c = "fun!";
String result = a.concat(b).concat(c);
System.out.println(result);
    }
}