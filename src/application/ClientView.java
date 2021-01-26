package application;
//import data.Status;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ContainerAdapter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ClientView {
    public JPanel clientPanel;
    private JTabbedPane tabbedPane1;
    private JPanel reservePanel;
    private JPanel myDataPanel;
    private JLabel IDLabel;
    private JLabel firstNameLabel;
    private JLabel lastNameLabel;
    private JLabel peselLabel;
    private JButton modifyDataButton;
    private JTextField IDtextField;
    private JTextField firstNameTextField;
    private JTextField lastNameTextField;
    private JTextField peselNameTextField;
    private JComboBox comboBox;
    private JTextField startDateTextField;
    private JTextField endDateTextField;
    private JLabel inputDate;
    private JButton showAvailableCarsBtn;
    private JTextField addressTextField;
    private JTextField cityTextField;
    private JTextField phoneTextField;
    private JButton deleteData;
    private JTable carsTable;
    private JButton reserveVehicle;
    private JButton logOutButton;
    static JFrame deleteDataFrame = new JFrame("Usuwanie konta");
    public JPanel rentalHistory;
    private JTable rentalsTable;
    private JPanel reservationsPanel;
    private JTable reservationsTable;
    private JButton cancelReservationButton;

    int clicked = 0;
    int cusomer_id = -1;
    String sql = "";
    Statement stmt;
    int PD_ID;
    String login;
    String password;
    ResultSet rs;
    public ClientView(int customer_id, Statement stmt) throws SQLException {
        this.stmt = stmt;
        this.cusomer_id = customer_id;

        tabbedPane1.addComponentListener(new ComponentAdapter() {
        });
        reservePanel.addComponentListener(new ComponentAdapter() {
        });
        myDataPanel.addComponentListener(new ComponentAdapter() {
        });
        rentalsTable.addContainerListener(new ContainerAdapter() {
        });



        showRentals(customer_id);

        showReservations(customer_id);

        setCustomerData(customer_id);

        setDepartments();



        modifyDataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(clicked==0) editData(e);
                else {
                    try {
                        if(checkData(firstNameTextField.getText(), lastNameTextField.getText(), phoneTextField.getText()))
                            saveData(e);

                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            }
        });

        deleteData.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SignInPanel.clientFrame.setVisible(false);
                deleteDataFrame.setContentPane(new ConfirmDeleting(PD_ID, login, password, stmt).deleteDataPanel);
                deleteDataFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                deleteDataFrame.pack();
                deleteDataFrame.setVisible(true);
            }
        });

        showAvailableCarsBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)  {
                showAvailableCars();
            }
        });

        reserveVehicle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isDateValid(startDateTextField.getText()) && isDateValid(endDateTextField.getText())) {
                    if (LocalDate.now().isBefore(LocalDate.parse(startDateTextField.getText())) || LocalDate.parse(startDateTextField.getText()).isEqual(LocalDate.now())) {
                        if (LocalDate.parse(startDateTextField.getText()).isBefore(LocalDate.parse(endDateTextField.getText())) || LocalDate.parse(startDateTextField.getText()).isEqual(LocalDate.parse(endDateTextField.getText()))) {
                            if (carsTable.getSelectedRow() > -1) {
                                String[] department = String.valueOf(comboBox.getSelectedItem()).split(" ");
                                try {
                                    JOptionPane.showMessageDialog(reservePanel, reserveCar(Integer.parseInt(department[0])));
                                } catch (SQLException throwables) {
                                    throwables.printStackTrace();
                                }
                            }
                            else {
                                JOptionPane.showMessageDialog(reservePanel, "Kliknij na wiersz z wybranym pojazdem!");
                            }
                        }
                        else {
                            JOptionPane.showMessageDialog(reservePanel, "Błędna chronologia dat!");
                        }
                    }
                    else {
                        JOptionPane.showMessageDialog(reservePanel, "Wprowadzono datę rozpoczęcia z przeszłości!");
                    }
                }
                else {
                    JOptionPane.showMessageDialog(reservePanel, "Zły format dat!");
                }
            }
        });

        logOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SignInPanel.clientFrame.setVisible(false);
                JOptionPane.showMessageDialog(null,"Wylogowano");
                SignInPanel.frame.setContentPane(new SignInPanel().loginPanel);
                SignInPanel.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                SignInPanel.frame.pack();
                SignInPanel.frame.setVisible(true);
            }
        });

        cancelReservationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    cancelReservation();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
    }

    private boolean isDateValid(String dateStr) {
        try {
            LocalDate.parse(dateStr);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }

    private void editData(ActionEvent e) {
        firstNameTextField.setEditable(true);
        lastNameTextField.setEditable(true);
        addressTextField.setEditable(true);
        cityTextField.setEditable(true);
        phoneTextField.setEditable(true);
        modifyDataButton.setText("Zapisz zmiany");
        clicked = 1;
    }

    private static boolean checkData(String firstName, String lastName, String phoneNumber){
        if((firstName.length() == 0) || !(firstName.matches("[A-Z][a-zA-ZżźćńółęąśŻŹĆŚŁÓŃ]+"))){
            JOptionPane.showMessageDialog(null, "Niepoprawne dane(IMIĘ).");
            return false;
        }
        if((lastName.length() == 0) || !(lastName.matches("[A-Z][a-zA-ZżźćńółęąśŻŹĆĄŚĘŁÓŃ]+"))){
            JOptionPane.showMessageDialog(null, "Niepoprawne dane(NAZWISKO).");
            return false;
        }
        if((phoneNumber.length() < 9) || (phoneNumber.length() > 10) || !(phoneNumber.trim().matches("\\d+"))){
            JOptionPane.showMessageDialog(null, "Niepoprawne dane(TELEFON).");
            return false;
        }
        return true;
    }

    private void saveData(ActionEvent e) throws SQLException {

        sql = "UPDATE PERSONAL_DATA SET FIRST_NAME = '"
                + firstNameTextField.getText() + "', LAST_NAME = '"
                + lastNameTextField.getText() + "', STREET = '"
                + addressTextField.getText() + "', CITY = '"
                + cityTextField.getText() + "', PHONE_NUMBER = '"
                + phoneTextField.getText() + "' WHERE PERSONAL_DATA_ID = " + PD_ID;
        stmt.executeUpdate(sql);

        firstNameTextField.setEditable(false);
        lastNameTextField.setEditable(false);
        addressTextField.setEditable(false);
        cityTextField.setEditable(false);
        phoneTextField.setEditable(false);
        modifyDataButton.setText("Edytuj dane");
        clicked=0;

    }

    private void cancelReservation() throws SQLException {
        int selectedRow = reservationsTable.getSelectedRow();
        if(selectedRow >= 0){
            sql = "select max(RESERVATION_ID)+1 as maxID from RESERVATION";
            rs = stmt.executeQuery(sql);
            sql = "delete from reservation where reservation_id =" + reservationsTable.getValueAt(selectedRow, 0);
            stmt.executeUpdate(sql);

            String confirmation = "Anulowano rezerwacje.";
            showReservations(cusomer_id);
        }

        else{
            JOptionPane.showMessageDialog(null,"Nie wybrano rezerwacji.");
        }

    }

    private void setCustomerData(int customer_id) throws SQLException {
        sql = "SELECT * from customer c \n" +
                "join personal_data pd on pd.personal_data_id  = c.customer_personal_data \n" +
                "join login_data l on l.login_data_id = c.customer_login_data_id\n" +
                "where c.customer_id =" + customer_id;
        rs = stmt.executeQuery(sql);
        while(rs.next()){

            PD_ID = rs.getInt("CUSTOMER_PERSONAL_DATA");
            login = rs.getString("USER_LOGIN");
            password = rs.getString("USER_PASSWORD");

            IDtextField.setText(String.valueOf(customer_id));
            firstNameTextField.setText(rs.getString("FIRST_NAME"));
            lastNameTextField.setText(rs.getString("LAST_NAME"));
            peselNameTextField.setText(rs.getString("PESEL"));
            addressTextField.setText(rs.getString("STREET"));
            cityTextField.setText(rs.getString("CITY"));
            phoneTextField.setText(rs.getString("PHONE_NUMBER"));
        }

    }

    private String reserveCar(int departmentID) throws SQLException {
        int selectedRow = carsTable.getSelectedRow();
        sql = "select max(RESERVATION_ID)+1 as maxID from RESERVATION";
        ResultSet rs = stmt.executeQuery(sql);
        int nextReservationID = 1000;
        while(rs.next()){
            nextReservationID = rs.getInt("maxID");
        }
        sql = "INSERT INTO RESERVATION (RESERVATION_ID,CUSTOMER_ID,DEPARTMENT_ID,START_DATE,END_DATE,VEHICLE_ID) VALUES ("
                +nextReservationID + ",'"
                +cusomer_id + "','"
                +departmentID+ "',to_date('" + startDateTextField.getText() + "', 'yyyy-mm-dd'),to_date('" + endDateTextField.getText() + "', 'yyyy-mm-dd'),"
                +String.valueOf(carsTable.getValueAt(selectedRow, 4)) + ")";
        stmt.executeUpdate(sql);
        sql = "UPDATE data_of_vehicle SET STATUS = 'zarezerwowany' WHERE VEHICLE_ID = " + carsTable.getValueAt(selectedRow, 4);
        stmt.executeUpdate(sql);

        sql = "select * from reservation where reservation_id = " + nextReservationID;
        rs = stmt.executeQuery(sql);
        rs.next();
        String confirmation = "Pomyślnie zarezerwowano pojazd nr " + rs.getInt("VEHICLE_ID") + " w terminie od " + rs.getDate("START_DATE") + " do " + rs.getDate("END_DATE") + ".";
        showReservations(cusomer_id);
        showAvailableCars();
        return confirmation;
    }

    private void setDepartments() throws SQLException {

        sql = "select count(*) as c from departments";
        rs = stmt.executeQuery(sql);
        int departmentsCount = 0;
        while(rs.next()){
            departmentsCount = rs.getInt("c");
        }
        String[] departments = new String[departmentsCount];

        sql = "select * from departments";
        rs = stmt.executeQuery(sql);
        int departmentID = 1;
        String departmentCity;
        for (int i=0; i<departmentsCount; i++) {
            rs.next();
            departmentID = rs.getInt("DEPARTMENT_ID");
            departmentCity = rs.getString("CITY");
            departments[i] = String.valueOf(departmentID) + " - " + departmentCity;
        }

        DefaultComboBoxModel deparmentsModel = new DefaultComboBoxModel(departments);
        comboBox.setModel(deparmentsModel);
    }

    private void showAvailableCars(){
        if (isDateValid(startDateTextField.getText()) && isDateValid(endDateTextField.getText())) {
            if (LocalDate.now().isBefore(LocalDate.parse(startDateTextField.getText())) || LocalDate.parse(startDateTextField.getText()).isEqual(LocalDate.now())) {
                if (LocalDate.parse(startDateTextField.getText()).isBefore(LocalDate.parse(endDateTextField.getText())) || LocalDate.parse(startDateTextField.getText()).isEqual(LocalDate.parse(endDateTextField.getText()))) {
                    int dpID = comboBox.getSelectedIndex()+1;
                    String start_date = startDateTextField.getText();
                    String end_date = endDateTextField.getText();
                    String columns[] = {"Marka","Model","Rok produkcji", "Przebieg", "ID pojazdu"};
                    DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
                    Object rowData[] = new Object[5];
                    sql = "SELECT c.car_brand AS CAR_BRAND, c.car_model AS CAR_MODEL, dt.year_of_production AS YEAR_OF_PRODUCTION, dt.cars_mileage AS CARS_MILEAGE, dt.vehicle_id AS VEHICLE_ID, dt.department_id as department_id \n" +
                            "FROM data_of_vehicle dt \n" +
                            "join cars c on c.car_id = dt.car_id \n" +
                            "where dt.department_id = " + dpID + " and dt.state_of_car = 'sprawny' and  dt.vehicle_id \n" +
                            "not in (select (vehicle_id) from reservation where ((start_date <= '" + start_date + "' and end_date >= '" + end_date + "') or (start_date >= '" + start_date + "' and start_date <= '" + end_date + "') or (end_date >= '" + start_date + "' and end_date <= '" + end_date + "'))) and dt.vehicle_id \n" +
                            "not in (select (vehicle_id) from rental where ((start_date <= '" + start_date + "' and end_date >= '" + end_date + "') or (start_date >= '" + start_date + "' and start_date <= '" + end_date + "') or (end_date >= '" + start_date + "' and end_date <= '" + end_date + "')))";
                    try {
                        ResultSet rs = stmt.executeQuery(sql);
                        String[] department = String.valueOf(comboBox.getSelectedItem()).split(" ");
                        while(rs.next()) {
                            if (rs.getInt("DEPARTMENT_ID")==(Integer.parseInt(department[0]))) {
                                rowData[0] = rs.getString("CAR_BRAND");
                                rowData[1] = rs.getString("CAR_MODEL");
                                rowData[2] = rs.getInt("YEAR_OF_PRODUCTION");
                                rowData[3] = rs.getInt("CARS_MILEAGE");
                                rowData[4] = rs.getInt("VEHICLE_ID");
                                tableModel.addRow(rowData);
                            }
                        }
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    carsTable.setModel(tableModel);
                }

                else {
                    JOptionPane.showMessageDialog(reservePanel, "Błędna chronologia dat!");
                }
            }

            else {
                JOptionPane.showMessageDialog(reservePanel, "Wprowadzono datę rozpoczęcia z przeszłości!");
            }
        }

        else {
            JOptionPane.showMessageDialog(reservePanel, "Zły format dat!");
        }
    }

    private void showRentals(int customer_id) throws SQLException {
        String columns[] = {"Marka","Model","Data rozpoczęcia", "Data zakończenia", "Koszt"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
        Object rowData[] = new Object[5];

        sql = "select count(CUSTOMER_ID) as s from client_rental_history where customer_id = " + customer_id;
        rs = stmt.executeQuery(sql);
        int size = 0;
        while(rs.next()){
            size = rs.getInt("s");
        }
        sql = "select CAR_BRAND, CAR_MODEL, to_char(START_DATE, 'YYYY-MM-DD') AS START_DATE, to_char(END_DATE, 'YYYY-MM-DD') AS END_DATE, COST_OF_RENT  from client_rental_history where customer_id = " + customer_id;
        rs = stmt.executeQuery(sql);
        for (int i=0; i<size; i++) {
            if(size>0){
                rs.next();
                rowData[0] = rs.getString("CAR_BRAND");
                rowData[1] = rs.getString("CAR_MODEL");
                rowData[2] = rs.getString("START_DATE");
                rowData[3] = rs.getString("END_DATE");
                rowData[4] = rs.getString("COST_OF_RENT");
                tableModel.addRow(rowData);
            }
            else {
                rowData[0] = "";
                rowData[1] = "";
                rowData[2] = "";
                rowData[3] = "";
                rowData[4] = "";
                tableModel.addRow(rowData);
            }
        }
        rentalsTable.setModel(tableModel);
    }

    private void showReservations(int customer_id) throws SQLException {
        String columns2[] = {"Numer rezerwacji","Marka","Model","Data rozpoczęcia", "Data zakończenia"};
        DefaultTableModel tableModel2 = new DefaultTableModel(columns2, 0);
        Object rowData2[] = new Object[5];

        sql = "select count(CUSTOMER_ID) as s from CLIENT_RESERVATIONS where customer_id = " + customer_id + " and end_date > sysdate";
        rs = stmt.executeQuery(sql);
        int size2 = 0;
        while(rs.next()){
            size2 = rs.getInt("s");
        }
        sql = "select RESERVATION_ID, CAR_BRAND, CAR_MODEL, to_char(START_DATE, 'YYYY-MM-DD') AS START_DATE, to_char(END_DATE, 'YYYY-MM-DD') AS END_DATE from CLIENT_RESERVATIONS where customer_id = " + customer_id  + " and end_date > sysdate";
        rs = stmt.executeQuery(sql);
        for (int i=0; i<size2; i++) {
            if(size2>0){
                rs.next();
                rowData2[0] = rs.getString("RESERVATION_ID");
                rowData2[1] = rs.getString("CAR_BRAND");
                rowData2[2] = rs.getString("CAR_MODEL");
                rowData2[3] = rs.getString("START_DATE");
                rowData2[4] = rs.getString("END_DATE");
                tableModel2.addRow(rowData2);
            }
            else {
                rowData2[0] = "";
                rowData2[1] = "";
                rowData2[2] = "";
                rowData2[3] = "";
                rowData2[4] = "";
                tableModel2.addRow(rowData2);
            }
        }
        reservationsTable.setModel(tableModel2);
    }

    private void createUIComponents() {
        tabbedPane1 = new JTabbedPane();
        rentalHistory = new JPanel();
        reservationsTable = new JTable();
        carsTable = new JTable();
        rentalsTable = new JTable();
        comboBox = new JComboBox();
        deleteData = new JButton();
    }
}
