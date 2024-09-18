import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class BankAccount {
    private double balance;

    public BankAccount(double initialBalance) {
        this.balance = initialBalance;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
        }
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            return true;
        } else {
            return false;
        }
    }
}

public class ATMInterface extends JFrame {
    private BankAccount account;
    private JLabel balanceLabel;
    private JTextField amountField;
    private JButton depositButton;
    private JButton withdrawButton;
    private JButton checkBalanceButton;
    private JTextArea transactionHistory;

    public ATMInterface() {
        account = new BankAccount(1000.00);

        setTitle("ATM Interface");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2, 10, 10));

        JLabel amountLabel = new JLabel("Enter Amount:");
        amountField = new JTextField();

        balanceLabel = new JLabel("Balance: $" + account.getBalance());
        transactionHistory = new JTextArea(5, 20);
        transactionHistory.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(transactionHistory);

        depositButton = new JButton("Deposit");
        depositButton.addActionListener(new DepositListener());

        withdrawButton = new JButton("Withdraw");
        withdrawButton.addActionListener(new WithdrawListener());

        checkBalanceButton = new JButton("Check Balance");
        checkBalanceButton.addActionListener(new CheckBalanceListener());

        panel.add(amountLabel);
        panel.add(amountField);
        panel.add(depositButton);
        panel.add(withdrawButton);
        panel.add(checkBalanceButton);
        panel.add(balanceLabel);
        panel.add(new JLabel("Transaction History:"));
        panel.add(scrollPane);

        add(panel);
    }

    private class DepositListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                double amount = Double.parseDouble(amountField.getText());
                if (amount > 0) {
                    account.deposit(amount);
                    transactionHistory.append("Deposited: $" + amount + "\n");
                    balanceLabel.setText("Balance: $" + account.getBalance());
                } else {
                    JOptionPane.showMessageDialog(null, "Please enter a valid amount.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid input. Please enter a number.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private class WithdrawListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                double amount = Double.parseDouble(amountField.getText());
                if (amount > 0) {
                    if (account.withdraw(amount)) {
                        transactionHistory.append("Withdrew: $" + amount + "\n");
                        balanceLabel.setText("Balance: $" + account.getBalance());
                    } else {
                        JOptionPane.showMessageDialog(null, "Insufficient funds.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please enter a valid amount.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid input. Please enter a number.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private class CheckBalanceListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            balanceLabel.setText("Balance: $" + account.getBalance());
            transactionHistory.append("Checked Balance: $" + account.getBalance() + "\n");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ATMInterface());
    }
}
