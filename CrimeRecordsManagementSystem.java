import javax.swing.*;
import java.sql.*;

public class CrimeRecordsManagementSystem {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/CrimeRecords";
    private static final String DB_USER = "root"; 
    private static final String DB_PASSWORD = "Chaitu895@"; 
    public static Connection conn;

    public static void main(String[] args) {
        try {
            // Establishing database connection
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            JFrame frame = new JFrame("Crime Records Management System");

            // Launch login screen
            LoginScreen.showLoginScreen(frame);

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600, 400);
            frame.setVisible(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
