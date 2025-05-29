package AtmFinal;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.time.LocalDateTime;

public class Withdraw extends JPanel {

    public Withdraw() {
        initComponents();

        balanceField.setEditable(false);
        loadBalance();

        withdrawButton.addActionListener(e -> withdraw());
        backButton.addActionListener(e -> AtmFinal.showPanel(new Menu()));
    }

    private void loadBalance() {
        String user = AtmFinal.getLoggedInUser();
        try (Connection conn = AtmFinal.getConnection(); PreparedStatement stmt = conn.prepareStatement("SELECT balance FROM users WHERE full_name = ?")) {
            stmt.setString(1, user);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    double balance = rs.getDouble("balance");
                    balanceField.setText(String.format("%.2f", balance));
                } else {
                    balanceField.setText("0.00");
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading balance: " + e.getMessage());
            balanceField.setText("0.00");
        }
    }

    private void withdraw() {
        String amountText = amountField.getText().trim();

        if (amountText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Amount is required!");
            return;
        }

        double amount;
        try {
            amount = Double.parseDouble(amountText);
            if (amount <= 0) {
                JOptionPane.showMessageDialog(this, "Amount must be positive!");
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid amount format!");
            return;
        }

        Connection conn = null;
        try {
            conn = AtmFinal.getConnection();
            conn.setAutoCommit(false);

            double currentBalance = 0.0;
            try (PreparedStatement stmt1 = conn.prepareStatement("SELECT balance FROM users WHERE full_name = ?")) {
                stmt1.setString(1, AtmFinal.getLoggedInUser());
                try (ResultSet rs = stmt1.executeQuery()) {
                    if (rs.next()) {
                        currentBalance = rs.getDouble("balance");
                    } else {
                        JOptionPane.showMessageDialog(this, "User not found!");
                        conn.rollback();
                        return;
                    }
                }
            }

            if (amount > currentBalance) {
                JOptionPane.showMessageDialog(this, "Insufficient balance!");
                conn.rollback();
                return;
            }

            try (PreparedStatement stmt2 = conn.prepareStatement(
                    "UPDATE users SET balance = balance - ? WHERE full_name = ?")) {
                stmt2.setDouble(1, amount);
                stmt2.setString(2, AtmFinal.getLoggedInUser());
                stmt2.executeUpdate();
            }

            double newBalance = currentBalance - amount;

            try (PreparedStatement stmt3 = conn.prepareStatement(
                    "INSERT INTO transactions (user_name, amount, balance, type, date) VALUES (?, ?, ?, ?, ?)")) {
                stmt3.setString(1, AtmFinal.getLoggedInUser());
                stmt3.setDouble(2, amount);
                stmt3.setDouble(3, newBalance);
                stmt3.setString(4, "Withdraw");
                stmt3.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
                stmt3.executeUpdate();
            }

            conn.commit();

            JOptionPane.showMessageDialog(this, "Withdrawal successful! New balance: " + newBalance);
            balanceField.setText(String.format("%.2f", newBalance));
            amountField.setText("");
            AtmFinal.showPanel(new Menu());

        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Rollback error: " + ex.getMessage());
                }
            }
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(this, "Error resetting auto-commit: " + e.getMessage());
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        balanceField = new java.awt.TextField();
        amountField = new java.awt.TextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        withdrawButton = new javax.swing.JButton();
        backButton = new javax.swing.JButton();

        setBackground(new java.awt.Color(153, 153, 153));
        setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel1.setBackground(new java.awt.Color(102, 102, 102));
        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        balanceField.setEditable(false);
        balanceField.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentHidden(java.awt.event.ComponentEvent evt) {
                balanceFieldComponentHidden(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("Withdraw Amount");

        jLabel2.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Balance");

        withdrawButton.setBackground(new java.awt.Color(153, 153, 153));
        withdrawButton.setForeground(new java.awt.Color(0, 0, 0));
        withdrawButton.setText("Withdraw");
        withdrawButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        withdrawButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        withdrawButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                withdrawButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(65, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(withdrawButton, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(balanceField, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(amountField, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(85, 85, 85))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(210, 210, 210))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(balanceField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 136, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(amountField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addComponent(withdrawButton)
                .addGap(95, 95, 95))
        );

        backButton.setText("Back");
        backButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(backButton))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(38, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(backButton)
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(34, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void balanceFieldComponentHidden(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_balanceFieldComponentHidden
        // TODO add your handling code here:
    }//GEN-LAST:event_balanceFieldComponentHidden

    private void withdrawButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_withdrawButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_withdrawButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private java.awt.TextField amountField;
    private javax.swing.JButton backButton;
    private java.awt.TextField balanceField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton withdrawButton;
    // End of variables declaration//GEN-END:variables
}
