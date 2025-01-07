package gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import api.FileHandler;

/**
 * Η κλάση Login παρέχει την οθόνη εισόδου για τον χρήστη, με δυνατότητες σύνδεσης ή δημιουργίας νέου λογαριασμού.
 */
public class Login extends JFrame {
    private JTextArea usernameTextArea;
    private JTextArea passwordTextArea;
    private JButton loginButton;
    private JPanel loginPanel;
    private JButton createAccountButton;

    /**
     * Κατασκευαστής για την οθόνη εισόδου. Δημιουργεί και διαχειρίζεται τα οπτικά στοιχεία της οθόνης.
     */
    public Login(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        setSize(450,300);
        setTitle("Login Account");
        setContentPane(loginPanel);
        setLocationRelativeTo(null);
        setVisible(true);

        createAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CreateAccount cr = new CreateAccount();
                dispose();
            }
        });
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameTextArea.getText();
                String password = passwordTextArea.getText();

                if (FileHandler.userExists(username, password)) {
                    if (FileHandler.isAdmin(username)) {
                        JOptionPane.showMessageDialog(Login.this, "Καλωσήρθες Admin!");
                        new AdminScreen();
                    } else {
                        JOptionPane.showMessageDialog(Login.this, "Welcome Χρήστη!");
                        new CustomerScreen();
                    }
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(Login.this, "Λάθος,προσπάθησε ξανά.");
                    usernameTextArea.setText("");
                    passwordTextArea.setText("");
                }
            }
        });

    }

}