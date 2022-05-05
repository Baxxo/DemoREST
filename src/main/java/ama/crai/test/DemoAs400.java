package ama.crai.test;

public class DemoAs400 {


    public static void main(String[] args) {
        System.out.println("Hello trying connection to AS400");
        try {
            System.out.println("Connecting to AS400");
            java.sql.Connection conn = java.sql.DriverManager.
                    getConnection("jdbc:as400://ASAMA:446", "PCS", "PCS");
        } catch (Exception e) {
            System.out.println("Error connecting to AS400");
            e.printStackTrace();
        }
    }

}
