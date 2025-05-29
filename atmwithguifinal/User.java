package atmwithguifinal;

import java.sql.Timestamp;
    import java.util.ArrayList;
    import java.util.Date;

public class User {
    private final String fullName;
    private final int age;
    private final String mobileNumber;
    private final String address;
    private double balance;
    private final String username;
    private final String password;
    private final TransactionManager transactionManager;

    public User(String fullName, int age, String mobileNumber, String address, double balance, String username, String password, TransactionManager transactionManager) {
        this.fullName = fullName;
        this.age = age;
        this.mobileNumber = mobileNumber;
        this.address = address;
        this.balance = balance;
        this.username = username;
        this.password = password;
        this.transactionManager = transactionManager;
    }

    public String getFullName() {
        return fullName;
    }

    public int getAge() {
        return age;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public String getAddress() {
        return address;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) throws IllegalArgumentException, Exception {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }
        balance += amount;
        transactionManager.addTransaction(username, amount, balance, "Deposit", new Timestamp(System.currentTimeMillis()));
    }

    public boolean withdraw(double amount) throws IllegalArgumentException, Exception {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive");
        }
        if (amount > balance) {
            return false;
        }
        balance -= amount;
        transactionManager.addTransaction(username, amount, balance, "Withdraw", new Timestamp(System.currentTimeMillis()));
        return true;
    }

    public ArrayList<Transaction> getTransactionHistory() throws Exception {
        return transactionManager.getTransactionHistory(username);
    }

    public void updateBalance(double newBalance) throws Exception {
        this.balance = newBalance;
        transactionManager.updateUserBalance(username, newBalance);
    }

    public static class Transaction {
        private final double amount;
        private final double balanceAfter;
        private final String type;
        private final Date date;

        public Transaction(double amount, double balanceAfter, String type, Date date) {
            this.amount = amount;
            this.balanceAfter = balanceAfter;
            this.type = type;
            this.date = date;
        }

        public double getAmount() {
            return amount;
        }

        public double getBalanceAfter() {
            return balanceAfter;
        }

        public String getType() {
            return type;
        }

        public Date getDate() {
            return date;
        }
    }
}