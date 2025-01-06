package gui;

import api.FileHandler;
import api.Customer;
import api.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateAccount extends JFrame {
    private JTextField textField1;
    private JButton createAccountButton;
    private JPanel createAccount;
    private JTextField textField3;
    private JTextField textField4;
    private JPasswordField passwordField1;

    public CreateAccount() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(450, 300);
        setTitle("Create Account");
        setContentPane(createAccount);
        setLocationRelativeTo(null);
        setVisible(true);

        createAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = textField3.getText();
                String surname = textField4.getText();
                String username = textField1.getText();
                String password = new String(passwordField1.getPassword());

                if (name.isEmpty() || surname.isEmpty() || username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(CreateAccount.this, "Πρέπει να συμπληρώσεις όλα τα πεδία.");
                    return;
                }

                if (FileHandler.userExists(username, password)) {
                    JOptionPane.showMessageDialog(CreateAccount.this, "Αυτό το όνομα χρήστη υπάρχει ήδη.");
                    return;
                }

                User newUser = new Customer(username, password);
                FileHandler.addUser(newUser);

                textField3.setText("");
                textField4.setText("");
                textField1.setText("");
                passwordField1.setText("");

                JOptionPane.showMessageDialog(CreateAccount.this, "Ο λογαριασμός δημιουργήθηκε με επιτυχία.");
            }
        });
    }
}
