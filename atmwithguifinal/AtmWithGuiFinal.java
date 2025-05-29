package atmwithguifinal;

import javax.swing.*;

public class AtmWithGuiFinal {
    private static User currentUser;
    private static JFrame frame;
    private static Login loginPanel;
    private static SignUp signUpPanel;
    private static loggedin loggedInPanel;
    private static Deposit depositPanel;
    private static Withdraw withdrawPanel;
    private static History historyPanel;
    private static DatabaseManager dbManager;
    private static TransactionManager transactionManager;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                dbManager = new DatabaseManager();
                transactionManager = new TransactionManager(dbManager);
                frame = new JFrame("ATM");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(600, 400);

                loginPanel = new Login();
                signUpPanel = new SignUp();
                loggedInPanel = new loggedin();
                depositPanel = new Deposit();
                withdrawPanel = new Withdraw();
                historyPanel = new History();

                showPanel(loginPanel.getPanel());
                frame.setVisible(true);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Database connection failed: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                System.exit(1);
            }
        });
    }

    public static void showPanel(JPanel panel) {
        frame.getContentPane().removeAll();
        frame.getContentPane().add(panel);
        frame.revalidate();
        frame.repaint();
    }

    public static DatabaseManager getDbManager() {
        return dbManager;
    }

    public static TransactionManager getTransactionManager() {
        return transactionManager;
    }

    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static Login getLoginPanel() {
        return loginPanel;
    }

    public static SignUp getSignUpPanel() {
        return signUpPanel;
    }

    public static loggedin getLoggedInPanel() {
        return loggedInPanel;
    }

    public static Deposit getDepositPanel() {
        return depositPanel;
    }

    public static Withdraw getWithdrawPanel() {
        return withdrawPanel;
    }

    public static History getHistoryPanel() {
        return historyPanel;
    }
}