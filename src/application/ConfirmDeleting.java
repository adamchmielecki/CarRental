package application;

import data.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.Statement;

public class ConfirmDeleting {
    public JPanel deleteDataPanel;
    private JButton yesButton;
    private JButton noButton;
    private JTextField textFieldPassword;
    private JButton deleteButton;
    private JLabel inputPasswordLabel;
    String password;
    //////////////wyrównac SIZE okna
    Statement stmt;
    public ConfirmDeleting(int clientID, String clientPassword, Statement stmt) {
        System.out.println(clientPassword);
        this.stmt = stmt;
        textFieldPassword.setVisible(false);
        deleteButton.setVisible(false);
        inputPasswordLabel.setVisible(false);

        yesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textFieldPassword.setVisible(true);
                deleteButton.setVisible(true);
                inputPasswordLabel.setVisible(true);
            }
        });

        noButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //wroc do client view
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(clientPassword.equals(textFieldPassword.getText())) {
                    String sql = "UPDATE PERSONAL_DATA SET FIRST_NAME = 'Klient usunięty', LAST_NAME = 'Klient usunięty', STREET = '-', CITY = '-', PHONE_NUMBER = '-', PESEL = '-', IDENTITY_CARD_NUMBER = '-' WHERE PERSONAL_DATA_ID = " + clientID;
                    try {
                        stmt.executeUpdate(sql);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null,"Niepoprawne hasło");
                }
            }
        });
    }



    private void createUIComponents() {
        deleteDataPanel = new JPanel();
        yesButton = new JButton();
        noButton = new JButton();
        textFieldPassword = new JTextField();
        deleteButton = new JButton();
        inputPasswordLabel = new JLabel();
    }
}
