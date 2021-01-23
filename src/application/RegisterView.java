package application;



import org.mindrot.jbcrypt.BCrypt;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

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
                    JOptionPane.showMessageDialog(SignInPanel.frame,"Pomyślnie zarejestrowano nowego użytkownika!");
                    SignInPanel.frame.setContentPane(new SignInPanel().loginPanel);
                    SignInPanel.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    SignInPanel.frame.pack();
                }
            }
        });
    }

    private boolean verifyUserName(String login){
        String sql = "select USER_LOGIN from LOGIN_DATA where USER_LOGIN = '" + login +"'";
        try {
            ResultSet rs = stmt.executeQuery(sql);
            System.out.println(rs);
            if (!rs.next()) {
                return true;
            }
            else {
                JOptionPane.showMessageDialog(SignInPanel.frame,"Wybierz inny login");
                return false;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    private static boolean verifyPassword(char[] password){
        if(!(Arrays.toString(password).matches("(?=.*[0-9])(?=.*[A-Z]).{8,}$"))) {
            JOptionPane.showMessageDialog(SignInPanel.frame, "Hasło za słabe (musi zawierać minimum: cyfrę, dużą literę i 8 znaków)");
            return false;
        }
        return true;
    }

    private static boolean checkData(String firstName, String lastName, String phoneNumber, String idNumber, String drivingLicenseNumber){
        if((firstName.length() == 0) || !(firstName.matches("[A-Z][a-zA-ZżźćńółęąśŻŹĆŚŁÓŃ]+"))){
            JOptionPane.showMessageDialog(SignInPanel.frame, "Niepoprawne dane(IMIĘ).");
            return false;
        }
        if((lastName.length() == 0) || !(lastName.matches("[A-Z][a-zA-ZżźćńółęąśŻŹĆĄŚĘŁÓŃ]+"))){
            JOptionPane.showMessageDialog(SignInPanel.frame, "Niepoprawne dane(NAZWISKO).");
            return false;
        }
        if((phoneNumber.length() != 9) || !(phoneNumber.matches("\\d+"))){
            JOptionPane.showMessageDialog(SignInPanel.frame, "Niepoprawne dane(TELEFON).");
            return false;
        }
        if(idNumber.length() == 0){
            JOptionPane.showMessageDialog(SignInPanel.frame, "Pole NR DOWODU OSOBISTEGO nie moze być puste.");
            return false;
        }
        if(drivingLicenseNumber.length() == 0){
            JOptionPane.showMessageDialog(SignInPanel.frame, "Pole NR PRAWA JAZDY nie moze być puste.");
            return false;
        }
        return true;
    }

    private void registerClient(String PESEL, String firstName, String lastName, String phoneNumber, String city, String address, String identityCardNumber, String drivingLicenseNumber, String login, char[] password) throws SQLException {

        String sql = "select max(PERSONAL_DATA_ID)+1 as maxID from PERSONAL_DATA";
        ResultSet rs = stmt.executeQuery(sql);
        int nextPersonalDataID = 1000;
        while(rs.next()){
            nextPersonalDataID = rs.getInt("maxID");
        }
        sql = "INSERT INTO PERSONAL_DATA (PERSONAL_DATA_ID, FIRST_NAME, LAST_NAME, PESEL, PHONE_NUMBER, CITY, STREET, IDENTITY_CARD_NUMBER) VALUES ("
                +nextPersonalDataID + ",'"
                +firstName + "','"
                +lastName + "','"
                +PESEL + "','"
                +phoneNumber + "','"
                +city + "','"
                +address + "','"
                +identityCardNumber + "')";
        stmt.executeUpdate(sql);
        sql = "select max(LOGIN_DATA_ID)+1 as maxID from LOGIN_DATA";
        rs = stmt.executeQuery(sql);
        int nextLoginDataID = 1000;
        while(rs.next()){
            nextLoginDataID = rs.getInt("maxID");
        }
        String enteredPassword = String.valueOf(password);
        String hashedPassword = BCrypt.hashpw(enteredPassword, BCrypt.gensalt(10));
        sql = "INSERT INTO LOGIN_DATA (LOGIN_DATA_ID,USER_LOGIN,USER_PASSWORD) VALUES ("
                +nextLoginDataID + ",'"
                +login + "','"
                +hashedPassword + "')";
        stmt.executeUpdate(sql);
        sql = "select max(CUSTOMER_ID)+1 as maxID from CUSTOMER";
        rs = stmt.executeQuery(sql);
        int nextCustomerID = 1000;
        while(rs.next()){
            nextCustomerID = rs.getInt("maxID");
        }
        sql = "INSERT INTO CUSTOMER (CUSTOMER_ID, CUSTOMER_PERSONAL_DATA, DRIVING_LICENSE_NUMBER, CUSTOMER_LOGIN_DATA_ID) VALUES ("
                +nextCustomerID + ","
                +nextPersonalDataID + ",'"
                +drivingLicenseNumber + "',"
                +nextLoginDataID + ")";
        stmt.executeUpdate(sql);
    }

}
