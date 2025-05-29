package AtmFinal;

import javax.swing.*;
import java.sql.*;
import java.time.LocalDateTime;

public class Deposit extends JPanel {

    public Deposit() {
        initComponents();
        balanceField.setEditable(false);
        loadBalance();
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

    private void deposit() {
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

            try (PreparedStatement stmt1 = conn.prepareStatement("SELECT balance FROM users WHERE full_name = ?")) {
                stmt1.setString(1, AtmFinal.getLoggedInUser());
                try (ResultSet rs = stmt1.executeQuery()) {
                    if (!rs.next()) {
                        JOptionPane.showMessageDialog(this, "User not found!");
                        conn.rollback();
                        return;
                    }
                }
            }

            try (PreparedStatement stmt2 = conn.prepareStatement(
                    "UPDATE users SET balance = balance + ? WHERE full_name = ?")) {
                stmt2.setDouble(1, amount);
                stmt2.setString(2, AtmFinal.getLoggedInUser());
                stmt2.executeUpdate();
            }

            double newBalance = 0.0;
            try (PreparedStatement stmt3 = conn.prepareStatement("SELECT balance FROM users WHERE full_name = ?")) {
                stmt3.setString(1, AtmFinal.getLoggedInUser());
                try (ResultSet rs2 = stmt3.executeQuery()) {
                    if (rs2.next()) {
                        newBalance = rs2.getDouble("balance");
                    }
                }
            }

            try (PreparedStatement stmt4 = conn.prepareStatement(
                    "INSERT INTO transactions (user_name, amount, balance, type, date) VALUES (?, ?, ?, ?, ?)")) {
                stmt4.setString(1, AtmFinal.getLoggedInUser());
                stmt4.setDouble(2, amount);
                stmt4.setDouble(3, newBalance);
                stmt4.setString(4, "Deposit");
                stmt4.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
                stmt4.executeUpdate();
            }

            conn.commit();

            JOptionPane.showMessageDialog(this, "Deposit successful! New balance: " + newBalance);
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
        jLabel7 = new javax.swing.JLabel();
        balanceField = new java.awt.TextField();
        jLabel8 = new javax.swing.JLabel();
        amountField = new java.awt.TextField();
        depositButton = new javax.swing.JButton();
        backButton = new javax.swing.JButton();

        setBackground(new java.awt.Color(153, 153, 153));
        setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel1.setBackground(new java.awt.Color(102, 102, 102));
        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel7.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("Balance");

        balanceField.setEditable(false);
        balanceField.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentHidden(java.awt.event.ComponentEvent evt) {
                balanceFieldComponentHidden(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setText("Deposit Amount");

        depositButton.setBackground(new java.awt.Color(153, 153, 153));
        depositButton.setForeground(new java.awt.Color(0, 0, 0));
        depositButton.setText("Deposit");
        depositButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        depositButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        depositButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                depositButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(215, 215, 215)
                .addComponent(jLabel7)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(64, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(depositButton, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(65, 65, 65))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(amountField, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(55, 55, 55))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(balanceField, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(192, 192, 192))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(balanceField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 118, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(amountField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(38, 38, 38)
                .addComponent(depositButton)
                .addGap(108, 108, 108))
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
                        .addGap(40, 40, 40)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(45, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(backButton)
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(37, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void balanceFieldComponentHidden(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_balanceFieldComponentHidden
        // TODO add your handling code here:
    }//GEN-LAST:event_balanceFieldComponentHidden

    private void depositButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_depositButtonActionPerformed
        deposit();
    }//GEN-LAST:event_depositButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private java.awt.TextField amountField;
    private javax.swing.JButton backButton;
    private java.awt.TextField balanceField;
    private javax.swing.JButton depositButton;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
