package application;



import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class RegisterView {

    public JPanel registerPanel;
    private JTextField textFieldFirstName;
    private JTextField textFieldLastName;
    private JTextField textFieldIDNumber;
    private JButton registerButton;
    private JTextField textFieldLogin;
    private JPasswordField passwordField;
    private JTextField textFieldDrivingLic;
    private JTextField textFieldPhone;
    private JTextField textFieldAddress;
    private JTextField textFieldCity;
    Statement stmt;
    public RegisterView(String PESEL, Statement stmt) {
        this.stmt = stmt;
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (verifyUserName(textFieldLogin.getText()) && verifyPassword(passwordField.getPassword()) && checkData(textFieldFirstName.getText(), textFieldLastName.getText(), textFieldPhone.getText(), textFieldIDNumber.getText(), textFieldDrivingLic.getText())) {
                    try {
                        registerClient(PESEL, textFieldFirstName.getText(), textFieldLastName.getText(), textFieldPhone.getText(), textFieldCity.getText(), textFieldAddress.getText(), textFieldIDNumber.getText(), textFieldDrivingLic.getText(), textFieldLogin.getText(), passwordField.getPassword());
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    CheckPeselView.registerFrame.setVisible(false);
                    JOptionPane.showMessageDialog(CheckPeselView.registerFrame,"Pomyślnie zarejestrowano nowego użytkownika!");
                    SignInPanel.frame.setContentPane(new SignInPanel().loginPanel);
                    SignInPanel.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    SignInPanel.frame.pack();
                    SignInPanel.frame.setVisible(true);
                }
            }
        });
    }

    public boolean verifyUserName(String login){
        String sql = "select USER_LOGIN from LOGIN_DATA where USER_LOGIN = '" + login +"'";
        try {
            ResultSet rs = stmt.executeQuery(sql);
            System.out.println(rs);
            if (!rs.next()) {
                return true;
            }
            else {
                JOptionPane.showMessageDialog(null,"Wybierz inny login");
                return false;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public static boolean verifyPassword(char[] password){
        if(password.length < 8) {
            JOptionPane.showMessageDialog(null, "Hasło za krótkie");
            return false;
        }
        return true;
    }

    public static boolean checkData(String firstName, String lastName, String phoneNumber, String idNumber, String drivingLicenseNumber){
        if((firstName.length() == 0)/* || !(firstName.matches("[A-Za-z]"))*/){
            JOptionPane.showMessageDialog(null, "Niepoprawne dane(IMIĘ).");
            return false;
        }
        if((lastName.length() == 0)/* || !(lastName.matches("[A-Za-z]"))*/){
            JOptionPane.showMessageDialog(null, "Niepoprawne dane(NAZWISKO).");
            return false;
        }
        if((phoneNumber.length() != 9) || !(phoneNumber.matches("\\d+"))){
            JOptionPane.showMessageDialog(null, "Niepoprawne dane(TELEFON).");
            return false;
        }
        if(idNumber.length() == 0){
            JOptionPane.showMessageDialog(null, "Pole NR DOWODU OSOBISTEGO nie moze być puste.");
            return false;
        }
        if(drivingLicenseNumber.length() == 0){
            JOptionPane.showMessageDialog(null, "Pole NR PRAWA JAZDY nie moze być puste.");
            return false;
        }
        return true;
    }

    private void registerClient(String PESEL, String firstName, String lastName, String phoneNumber, String city, String address, String identityCardNumber, String drivingLicenseNumber, String login, char[] password) throws SQLException {
        String sql = "INSERT INTO PERSONAL_DATA (PERSONAL_DATA_ID, FIRST_NAME, LAST_NAME, PESEL, PHONE_NUMBER, CITY, STREET, IDENTITY_CARD_NUMBER) VALUES (100, '"
                +firstName + "','"
                +lastName + "','"
                +PESEL + "','"
                +phoneNumber + "','"
                +city + "','"
                +address + "','"
                +identityCardNumber + "')";
        System.out.println(sql);
        stmt.executeUpdate(sql);
        sql = "INSERT INTO LOGIN_DATA (LOGIN_DATA_ID,USER_LOGIN,USER_PASSWORD) VALUES (100, '"
                +login + "','"
                +password + "')";
        System.out.println(sql);
        stmt.executeUpdate(sql);
        sql = "INSERT INTO CUSTOMER (CUSTOMER_ID, CUSTOMER_PERSONAL_DATA, DRIVING_LICENSE_NUMBER, CUSTOMER_LOGIN_DATA_ID) VALUES (100, 100, '999', 100)";
        System.out.println(sql);
        stmt.executeUpdate(sql);
    }

}
