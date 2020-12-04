package application;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Statement;
import org.mindrot.jbcrypt.BCrypt;

public class ClientView {
    Statement stmt;
    static JFrame frame = new JFrame("Wypożyczalnia pojazdów");
    private JPanel loginPanel;
    private JTextField loginTextField;
    private JPasswordField hasloPasswordField;
    private JButton loginButton;
    private JButton registerButton;

    public ClientView() {
        stmt = new Database().connect();

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClientView.signIn(loginTextField.getText(), hasloPasswordField.getPassword());
            }
        });
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,"Rejestracja w przygotowaniu");
            }
        });
    }

    private static void signIn(String userName, char[] password) {
        String hashed = BCrypt.hashpw("password", BCrypt.gensalt(12));  // https://www.mindrot.org/projects/jBCrypt/
                                                                                          // szyfrowane hasło
        String enteredPassword = String.valueOf(password);

        if("login".equals(userName) &&  BCrypt.checkpw(enteredPassword, hashed)){
            System.out.println("Successful login");
            frame.setVisible(false);
            JFrame clientFrame = new JFrame("Wypożyczalnia pojazdów");
            clientFrame.setContentPane(new ClientPanel().clientPanel);
            clientFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            clientFrame.pack();
            clientFrame.setVisible(true);
        }
        else{
            System.out.println("Incorrect data");
        }
    }

    public void reserveCar() {}

    public void register() {}

    public void editData() {
    }

    public void deleteData() {
    }

    public void cancelReservation() {
    }

    public void chooseDepartment() {
    }

    public void showReservations() {
    }

    public static void runClientView(){
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

        frame.setContentPane(new ClientView().loginPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
