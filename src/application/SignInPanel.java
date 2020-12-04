package application;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.mindrot.jbcrypt.BCrypt;

public class SignInPanel {
    static JFrame frame = new JFrame("Wypożyczalnia pojazdów");
    private JPanel loginPanel;
    private JTextField loginTextField;
    private JPasswordField hasloPasswordField;
    private JButton loginButton;
    private JButton registerButton;

    public SignInPanel() {
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    signIn(loginTextField.getText(), hasloPasswordField.getPassword());
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,"Rejestracja w przygotowaniu");
            }
        });
    }
    private boolean verifyData(Statement stmt, int id_logowania, String enteredPassword) throws SQLException {
        if (id_logowania >= 0) {
            String hashed = "SELECT password FROM Dane_logowania WHERE ID_dane_logowania = " + id_logowania;
            ResultSet rs = stmt.executeQuery(hashed);
            hashed = rs.getString("password");
            if(BCrypt.checkpw(enteredPassword, hashed)) return true;
            else return false;
        }
        else {
            return false;
        }
    }

    //String hashed = BCrypt.hashpw("password", BCrypt.gensalt(12));  // https://www.mindrot.org/projects/jBCrypt/
    // szyfrowane hasło

    private void signIn(String userName, char[] password) throws SQLException {
        Statement stmt = new Database().connect();
        String enteredPassword = String.valueOf(password);
        String login = "SELECT ID_dane_logowania FROM Dane_logowania WHERE login = " + userName;
        ResultSet rs = stmt.executeQuery(login);
        int id_logowania = rs.getInt("ID_dane_logowania");
        if (verifyData(stmt, id_logowania, enteredPassword)) {
            frame.setVisible(false);
            JFrame clientFrame = new JFrame("Wypożyczalnia pojazdów");
            clientFrame.setContentPane(new ClientView().clientPanel);
            clientFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            clientFrame.pack();
            clientFrame.setVisible(true);
        }
        else {
            JOptionPane.showMessageDialog(null,"Niepoprawny login lub/i hasło");
        }
    }

    public void register() {}

    public static void runSignInPanel(){
        /*try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
        }
        } catch (UnsupportedLookAndFeelException e) {
        } catch (ClassNotFoundException e) {
        } catch (InstantiationException e) {
        } catch (IllegalAccessException e) {
        }*/       // interfejs Nimbus

        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");  // This line gives Windows Theme
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        frame.setContentPane(new SignInPanel().loginPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
