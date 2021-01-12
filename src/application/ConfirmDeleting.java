package application;


import org.mindrot.jbcrypt.BCrypt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.Statement;

public class ConfirmDeleting {
    public JPanel deleteDataPanel;
    private JButton deleteButton;
    private JLabel inputPasswordLabel;
    private JButton backButton;
    private JPasswordField passwordField;

    Statement stmt;
    public ConfirmDeleting(int clientID,String clientLogin, String clientPassword, Statement stmt) {

        this.stmt = stmt;

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String enteredPassword = String.valueOf(passwordField.getPassword());
                if(BCrypt.checkpw(enteredPassword, clientPassword))  {
                    ClientView.deleteDataFrame.setContentPane(new LastConfirm(clientID, clientLogin, clientPassword, stmt).confirmPanel);
                    ClientView.deleteDataFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    ClientView.deleteDataFrame.pack();
                }
                else{
                    JOptionPane.showMessageDialog(deleteDataPanel,"Niepoprawne hasło");
                }
            }
        });


        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClientView.deleteDataFrame.setVisible(false);
                SignInPanel.clientFrame.setVisible(true);
            }
        });
    }


    private void createUIComponents() {
        deleteDataPanel = new JPanel();
    }
}
