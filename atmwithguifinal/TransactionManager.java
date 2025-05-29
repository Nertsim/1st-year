package atmwithguifinal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

public class TransactionManager {
    private final DatabaseManager dbManager;

    public TransactionManager(DatabaseManager dbManager) {
        this.dbManager = dbManager;
    }

    public void addTransaction(String username, double amount, double balanceAfter, String type, Timestamp date) throws SQLException {
        dbManager.addTransaction(username, amount, balanceAfter, type, date);
    }

    public ArrayList<User.Transaction> getTransactionHistory(String username) throws SQLException {
        ArrayList<User.Transaction> transactions = new ArrayList<>();
        try (ResultSet rs = dbManager.getTransactionHistory(username)) {
            while (rs.next()) {
                transactions.add(new User.Transaction(
                    rs.getDouble("amount"),
                    rs.getDouble("balance_after"),
                    rs.getString("transaction_type"),
                    rs.getTimestamp("transaction_date")
                ));
            }
        }
        return transactions;
    }

    public void updateUserBalance(String username, double balance) throws SQLException {
        dbManager.updateBalance(username, balance);
    }
}