package AtmFinal;

import javax.swing.*;
import java.sql.*;

public class AtmFinal {

    private static JFrame frame;
    private static String loggedInUser;

    public static void main(String[] args) {
        frame = new JFrame("ATM NI ANGELO");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLocationRelativeTo(null);

        showPanel(new Login());
        frame.setVisible(true);
    }

    // Always returns a fresh connection
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Optional after JDBC 4.0
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "MySQL Driver not found: " + e.getMessage());
            System.exit(1);
        }

        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/atm_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC",
                "root",
                "070426"
        );
    }

    public static void showPanel(JPanel panel) {
        frame.getContentPane().removeAll();
        frame.getContentPane().add(panel);
        frame.revalidate();
        frame.repaint();
    }

    public static void setLoggedInUser(String user) {
        loggedInUser = user;
    }

    public static String getLoggedInUser() {
        return loggedInUser;
    }
}
