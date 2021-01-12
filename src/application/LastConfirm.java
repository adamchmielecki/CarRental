package application;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.Statement;

public class LastConfirm {
    private JButton yesButton;
    private JButton noButton;
    public JPanel confirmPanel;
    Statement stmt;

    public LastConfirm(int clientID,String clientLogin, String clientPassword, Statement stmt) {
        this.stmt = stmt;

        yesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String sql = "UPDATE PERSONAL_DATA SET FIRST_NAME = 'Klient usunięty', LAST_NAME = 'Klient usunięty', STREET = '-', CITY = '-', PHONE_NUMBER = '-', PESEL = '-', IDENTITY_CARD_NUMBER = '-' WHERE PERSONAL_DATA_ID = " + clientID;
                    stmt.executeUpdate(sql);
                    sql = "UPDATE CUSTOMER SET DRIVING_LICENSE_NUMBER = '-', CUSTOMER_LOGIN_DATA_ID = NULL WHERE CUSTOMER_PERSONAL_DATA = " + clientID;
                    stmt.executeUpdate(sql);
                    sql = "DELETE FROM LOGIN_DATA WHERE USER_LOGIN = '" + clientLogin + "'";
                    stmt.executeUpdate(sql);
                    JOptionPane.showMessageDialog(confirmPanel,"Twoje konto zostało usunięte");

                    ClientView.deleteDataFrame.setVisible(false);
                    SignInPanel.frame.setContentPane(new SignInPanel().loginPanel);
                    SignInPanel.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    SignInPanel.frame.pack();
                    SignInPanel.frame.setVisible(true);

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
        noButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClientView.deleteDataFrame.setVisible(false);
                SignInPanel.clientFrame.setVisible(true);
            }
        });
    }
}
