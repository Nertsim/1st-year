package atmwithguifinal;

import java.sql.*;

public class DatabaseManager {
    private static final String URL = "jdbc:mysql://localhost:3306/atm_db";
    private static final String USER = "root"; // Replace with your MySQL username
    private static final String PASSWORD = "password"; // Replace with your MySQL password

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public void addUser(User user) throws SQLException {
        String sql = "INSERT INTO users (username, full_name, age, mobile_number, address, balance, password) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getFullName());
            stmt.setInt(3, user.getAge());
            stmt.setString(4, user.getMobileNumber());
            stmt.setString(5, user.getAddress());
            stmt.setDouble(6, user.getBalance());
            stmt.setString(7, user.getPassword());
            stmt.executeUpdate();
        }
    }

    public User getUser(String username, TransactionManager transactionManager) throws SQLException {
        String sql = "SELECT * FROM users WHERE username = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(
                    rs.getString("full_name"),
                    rs.getInt("age"),
                    rs.getString("mobile_number"),
                    rs.getString("address"),
                    rs.getDouble("balance"),
                    rs.getString("username"),
                    rs.getString("password"),
                    transactionManager
                );
            }
        }
        return null;
    }

    public void updateBalance(String username, double balance) throws SQLException {
        String sql = "UPDATE users SET balance = ? WHERE username = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, balance);
            stmt.setString(2, username);
            stmt.executeUpdate();
        }
    }

    public void addTransaction(String username, double amount, double balanceAfter, String type, Timestamp date) throws SQLException {
        String sql = "INSERT INTO transactions (username, amount, balance_after, transaction_type, transaction_date) " +
                     "VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setDouble(2, amount);
            stmt.setDouble(3, balanceAfter);
            stmt.setString(4, type);
            stmt.setTimestamp(5, date);
            stmt.executeUpdate();
        }
    }

    public ResultSet getTransactionHistory(String username) throws SQLException {
        String sql = "SELECT * FROM transactions WHERE username = ? ORDER BY transaction_date DESC";
        Connection conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, username);
        return stmt.executeQuery();
    }
}