package gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import api.FileHandler;

public class Login extends JFrame {
    private JTextArea usernameTextArea;
    private JTextArea passwordTextArea;
    private JButton loginButton;
    private JPanel loginPanel;
    private JButton createAccountButton;


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
                        JOptionPane.showMessageDialog(Login.this, "Welcome Admin!");
                        new AdminScreen();
                    } else {
                        JOptionPane.showMessageDialog(Login.this, "Welcome User!");
                        new CustomerScreen();
                    }
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(Login.this, "Invalid credentials. Please try again.");
                    usernameTextArea.setText("");
                    passwordTextArea.setText("");
                }
            }
        });

    }

}
