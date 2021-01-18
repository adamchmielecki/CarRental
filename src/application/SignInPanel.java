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
    public JPanel loginPanel;
    static JFrame checkPanel = new JFrame("Wypożyczalnia pojazdów");
    static JFrame clientFrame = new JFrame("Wypożyczalnia pojazdów");
    private JTextField loginTextField;
    private JPasswordField hasloPasswordField;
    private JButton loginButton;
    private JButton registerButton;
    Statement stmt;
    public SignInPanel() {
        stmt = new Database().connect();
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
                //frame.setVisible(false);
                frame.setContentPane(new CheckPeselView(stmt).checkPeselPanel);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                //checkPanel.setVisible(true);
            }
        });
    }
    private boolean verifyData(Statement stmt, int id_logowania, String enteredPassword) throws SQLException {
        if (id_logowania >= 0) {
            String hashed = "SELECT USER_PASSWORD FROM LOGIN_DATA WHERE LOGIN_DATA_ID = " + id_logowania;
            ResultSet rs = stmt.executeQuery(hashed);
            String correct_password = "";
            while(rs.next()){
                correct_password = rs.getString("USER_PASSWORD");
            }

            /*if(BCrypt.checkpw(enteredPassword, "$2a$10$/hJ/Kl0r1C69PQskIMaXmuFizJeNF66J310uEk/2DbPfyHwQP1LWG")) return true;
            else return false;*/
            if(BCrypt.checkpw(enteredPassword, correct_password)) return true;
            else return false;
        }
        else {
            return false;
        }
    }

    // String hashed = BCrypt.hashpw("password", BCrypt.gensalt(10));  // https://www.mindrot.org/projects/jBCrypt/
    // tworzenie szyfrowanego hasła

    private void signIn(String userName, char[] password) throws SQLException {

        String enteredPassword = String.valueOf(password);
        String login = "SELECT LOGIN_DATA_ID FROM LOGIN_DATA WHERE USER_LOGIN = '" + userName + "'";
        ResultSet rs = stmt.executeQuery(login);
        int id_logowania = 0;
        while(rs.next()){
            id_logowania = rs.getInt("LOGIN_DATA_ID");
        }
        if (id_logowania != 0) {
            if (verifyData(stmt, id_logowania, enteredPassword)) {
                String sql = "SELECT CUSTOMER_ID FROM CUSTOMER WHERE CUSTOMER_LOGIN_DATA_ID = " + id_logowania;
                rs = stmt.executeQuery(sql);
                int customer_id = 0;
                while(rs.next()){
                    customer_id = rs.getInt("CUSTOMER_ID");
                    System.out.println(customer_id);
                }
                frame.setVisible(false);
                clientFrame.setContentPane(new ClientView(customer_id, stmt).clientPanel);
                clientFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                clientFrame.pack();
                clientFrame.setVisible(true);
            }
            else {
                JOptionPane.showMessageDialog(frame,"Niepoprawny login lub/i hasło");
            }
        }
        else {
            JOptionPane.showMessageDialog(frame,"Niepoprawny login lub/i hasło");
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
