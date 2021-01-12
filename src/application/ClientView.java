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

    int clicked = 0;
    int selected_dep = -1;
    int cusomer_id = -1;
    String sql = "";
    Statement stmt;
    int c_ID;
    int PD_ID;
    String login;
    String password;
    public ClientView(int customer_id, Statement stmt) throws SQLException {
        this.stmt = stmt;
        System.out.println(customer_id);
        this.cusomer_id = customer_id;
        System.out.println(customer_id);

        tabbedPane1.addComponentListener(new ComponentAdapter() {
        });
        reservePanel.addComponentListener(new ComponentAdapter() {
        });
        myDataPanel.addComponentListener(new ComponentAdapter() {
        });


        String columns[] = {"Marka","Model","Data rozpoczęcia", "Data zakończenia", "Koszt"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
        Object rowData[] = new Object[5];

        sql = "select count(CUSTOMER_ID) as s from client_rental_history where customer_id = " + customer_id;
        ResultSet rs = stmt.executeQuery(sql);
        int size = 0;
        while(rs.next()){
            size = rs.getInt("s");
        }
        sql = "select * from client_rental_history where customer_id = " + customer_id;
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



        sql = "select count(*) as c from departments";
        rs = stmt.executeQuery(sql);
        int departmentsCount = 0;
        while(rs.next()){
            departmentsCount = rs.getInt("c");
        }
        String[] departments = new String[departmentsCount];

        sql = "select * from departments";
        rs = stmt.executeQuery(sql);
        int departmentID;
        String departmentCity;
        for (int i=0; i<departmentsCount; i++) {
            rs.next();
            departmentID = rs.getInt("DEPARTMENT_ID");
            departmentCity = rs.getString("CITY");
            departments[i] = String.valueOf(departmentID) + " - " + departmentCity;
        }

        DefaultComboBoxModel deparmentsModel = new DefaultComboBoxModel(departments);
        comboBox.setModel(deparmentsModel);


        modifyDataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(clicked==0) editData(e);
                else {
                    try {
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
                if (isDateValid(startDateTextField.getText()) && isDateValid(endDateTextField.getText())) {
                    if (LocalDate.parse(startDateTextField.getText()).isBefore(LocalDate.parse(endDateTextField.getText())) || LocalDate.parse(startDateTextField.getText()).isEqual(LocalDate.parse(endDateTextField.getText()))) {
                        String columns[] = {"Marka","Model","Rok produkcji", "Przebieg", "ID pojazdu"};
                        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
                        Object rowData[] = new Object[5];
                        sql = "select VEHICLE_ID, DEPARTMENT_ID, STATUS, CAR_BRAND, CAR_MODEL, YEAR_OF_PRODUCTION, CARS_MILEAGE from cars natural join data_of_vehicle";
                        try {
                            ResultSet rs = stmt.executeQuery(sql);
                            String[] department = String.valueOf(comboBox.getSelectedItem()).split(" ");
                            while(rs.next()) {
                                if (rs.getInt("DEPARTMENT_ID")==(Integer.parseInt(department[0])) && rs.getString("STATUS").equals("dostepny")) {
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
                    JOptionPane.showMessageDialog(reservePanel, "Zły format dat!");
                }
            }
        });

        reserveVehicle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isDateValid(startDateTextField.getText()) && isDateValid(endDateTextField.getText())) {
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
        rentalsTable.addContainerListener(new ContainerAdapter() {
        });
    }

    public boolean isDateValid(String dateStr) {
        try {
            LocalDate.parse(dateStr);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }

    public void editData(ActionEvent e) {
        firstNameTextField.setEditable(true);
        lastNameTextField.setEditable(true);
        addressTextField.setEditable(true);
        cityTextField.setEditable(true);
        phoneTextField.setEditable(true);
        modifyDataButton.setText("Zapisz zmiany");
        clicked = 1;
    }

    public void saveData(ActionEvent e) throws SQLException {

        sql = "UPDATE PERSONAL_DATA SET FIRST_NAME = '"
                + firstNameTextField.getText() + "', LAST_NAME = '"
                + lastNameTextField.getText() + "', STREET = '"
                + addressTextField.getText() + "', CITY = '"
                + cityTextField.getText() + "', PHONE_NUMBER = '"
                + phoneTextField.getText() + "' WHERE PERSONAL_DATA_ID = " + PD_ID;
        System.out.println(sql);
        stmt.executeUpdate(sql);

        firstNameTextField.setEditable(false);
        lastNameTextField.setEditable(false);
        addressTextField.setEditable(false);
        cityTextField.setEditable(false);
        phoneTextField.setEditable(false);
        modifyDataButton.setText("Edytuj dane");
        clicked=0;

    }

    public void logOut() {
    }

    public void cancelReservation() {
    }

    public void showReservations() {
    }

    public String reserveCar(int departmentID) throws SQLException {
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
        return confirmation;
    }

    private void createUIComponents() {
        tabbedPane1 = new JTabbedPane();
        rentalHistory = new JPanel();
        //showAvailableCarsBtn = new JButton();
        carsTable = new JTable();
        rentalsTable = new JTable();
        comboBox = new JComboBox();
        deleteData = new JButton();
    }
}
