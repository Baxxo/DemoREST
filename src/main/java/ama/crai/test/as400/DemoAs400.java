package ama.crai.test.as400;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

@SpringBootApplication
public class DemoAs400 {


    public static void main(String[] args) {
        System.out.println("Hello trying connection to AS400");

        String resourceName = "connection.properties"; // could also be a constant
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        Properties props = new Properties();
        try (InputStream resourceStream = loader.getResourceAsStream(resourceName)) {
            props.load(resourceStream);

            String local_system = props.getProperty("local_system");
            String userId = props.getProperty("userId");
            String password = props.getProperty("password");

            System.out.println("local_system: " + local_system);
            System.out.println("userId: " + userId);
            System.out.println("password: " + password);

            String DRIVER = "com.ibm.as400.access.AS400JDBCDriver";
            String URL = "jdbc:as400://" + local_system.trim() + ";naming=system;errors=full";

            //Connect to iSeries
            Class.forName(DRIVER);

            System.out.println("Connecting to iSeries...");

            Connection conn = DriverManager.getConnection(URL, userId.trim(), password.trim());

            System.out.println("Connected to iSeries");

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from AMS5DAT.clienti");

            while (rs.next()) {
                System.out.println(rs + "\n");
            }

            rs.close();

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

//        try {
//            System.out.println("Connecting to AS400");
//            java.sql.Connection conn = java.sql.DriverManager.
//                    getConnection("jdbc:as400://ASAMA:446", "PCS", "PCS");
//        } catch (Exception e) {
//            System.out.println("Error connecting to AS400");
//            e.printStackTrace();
//        }
    }

}
