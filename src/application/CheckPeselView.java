package application;

import data.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CheckPeselView {
    static JFrame registerFrame = new JFrame("Rejestracja klienta");
    public JPanel checkPeselPanel;
    private JTextField enterPESEL;
    private JButton zatwierdzButton;

    public CheckPeselView(Statement stmt) {
        zatwierdzButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String sql = "select PESEL from personal_data where PESEl = '" + enterPESEL.getText() +"'";
                try {
                    ResultSet rs = stmt.executeQuery(sql);
                    System.out.println(rs);
                    if (!rs.next()) {
                        SignInPanel.checkPanel.setVisible(false);
                        registerFrame.setContentPane(new RegisterView(enterPESEL.getText(), stmt).registerPanel);
                        registerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        registerFrame.pack();
                        registerFrame.setVisible(true);
                    }
                    else {

                        JOptionPane.showMessageDialog(SignInPanel.checkPanel,"Istnieje już konto użytkownika o takim samym numerze PESEL!");
                        SignInPanel.checkPanel.setVisible(false);
                        SignInPanel.frame.setContentPane(new SignInPanel().loginPanel);
                        SignInPanel.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        SignInPanel.frame.pack();
                        SignInPanel.frame.setVisible(true);
                    }

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

            }
        });
    }
}
