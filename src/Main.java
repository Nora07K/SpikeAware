import java.io.Console;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.InputMismatchException;

public class Main {

    public static void main(String[] args) {

        String db_host = "jdbc:mysql://localhost:3306/persistantstorageuni";
        String db_user = "root";
        String db_password = "Vollyball_07";

        String P_host = System.getProperty("db_host");
        String P_user = System.getProperty("db_user");
        String P_password = System.getProperty("db_password");

        if (P_host != null) db_host = P_host;
        if (P_user != null) db_user = P_user;
        if (P_password != null) db_password = P_password;
        System.out.println("I am logging into MySQL with: " + db_host + ", " + db_user + ", " + db_password + ".");



        Controller MyController = new Controller(db_host, db_password, db_user);
        MyController.Run(10);

    }
}
