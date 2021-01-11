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
            login =rs.getString("USER_LOGIN");
            password = (rs.getString("USER_PASSWORD"));

            IDtextField.setText(String.valueOf(customer_id));
            firstNameTextField.setText(rs.getString("FIRST_NAME"));
            lastNameTextField.setText(rs.getString("LAST_NAME"));
            peselNameTextField.setText(rs.getString("PESEL"));
            addressTextField.setText(rs.getString("STREET"));
            cityTextField.setText(rs.getString("CITY"));
            phoneTextField.setText(rs.getString("PHONE_NUMBER"));
        }




/*        String[] departments = new String[Facade.getDepartmentsList().size()];
        for (int i=0; i<Facade.getDepartmentsList().size(); i++) {
            departments[i] = Facade.getDepartmentsList().get(i).getCity();
        }

        DefaultComboBoxModel deparmentsModel = new DefaultComboBoxModel(departments);
        comboBox.setModel(deparmentsModel);*/


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
                JFrame deleteDataFrame = new JFrame("Usuń konto");
                deleteDataFrame.setContentPane(new ConfirmDeleting(PD_ID, login, password, stmt).deleteDataPanel);
                deleteDataFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                deleteDataFrame.pack();
                deleteDataFrame.setVisible(true);
            }
        });

/*
        showAvailableCarsBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isDateValid(startDateTextField.getText()) && isDateValid(endDateTextField.getText())) {
                    if (LocalDate.parse(startDateTextField.getText()).isBefore(LocalDate.parse(endDateTextField.getText())) || LocalDate.parse(startDateTextField.getText()).isEqual(LocalDate.parse(endDateTextField.getText()))) {
                        String columns[] = {"Marka","Model","Rok produkcji", "Przebieg"};
                        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
                        Object rowData[] = new Object[4];
                        for (int i=0; i<Facade.getDepartmentsList().get(comboBox.getSelectedIndex()).getVehicles().size(); i++) {
                            if (Facade.getDepartmentsList().get(comboBox.getSelectedIndex()).getVehicles().get(i).getStatus().equals(Status.AVAILABLE)) {
                                rowData[0] = Facade.getDepartmentsList().get(comboBox.getSelectedIndex()).getVehicles().get(i).getBrand();
                                rowData[1] = Facade.getDepartmentsList().get(comboBox.getSelectedIndex()).getVehicles().get(i).getModel();
                                rowData[2] = Facade.getDepartmentsList().get(comboBox.getSelectedIndex()).getVehicles().get(i).getYearOfProduction();
                                rowData[3] = Facade.getDepartmentsList().get(comboBox.getSelectedIndex()).getVehicles().get(i).getCarsMileage();
                                tableModel.addRow(rowData);
                            }
                            else {
                                rowData[0] = "";
                                rowData[1] = "";
                                rowData[2] = "";
                                rowData[3] = "";
                                tableModel.addRow(rowData);
                            }
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
        });*/

        /*reserveVehicle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isDateValid(startDateTextField.getText()) && isDateValid(endDateTextField.getText())) {
                    if (LocalDate.parse(startDateTextField.getText()).isBefore(LocalDate.parse(endDateTextField.getText())) || LocalDate.parse(startDateTextField.getText()).isEqual(LocalDate.parse(endDateTextField.getText()))) {
                        if (carsTable.getSelectedRow() > -1 && Facade.getDepartmentsList().get(comboBox.getSelectedIndex()).getVehicles().get(carsTable.getSelectedRow()).getStatus().equals(Status.AVAILABLE)) {
                            JOptionPane.showMessageDialog(reservePanel, reserveCar(client));
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
        });*/
        logOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clientPanel.setVisible(false);
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

/*    public String reserveCar(Client client) {
        Reservation reservation = new Reservation();
        reservation.setClient(client);
        reservation.setStartDate(LocalDate.parse(startDateTextField.getText()));
        reservation.setEndDate(LocalDate.parse(endDateTextField.getText()));
        reservation.setVehicle(Facade.getDepartmentsList().get(comboBox.getSelectedIndex()).getVehicles().get(carsTable.getSelectedRow()));
        Facade.getReservationList().add(reservation);
        String confirmation = "Pomyślnie zarezerwowano pojazd " + reservation.getVehicle().getBrand() + " " + reservation.getVehicle().getModel() + " w terminie od " + reservation.getStartDate().toString() + " do " + reservation.getEndDate().toString() + ".";
        return confirmation;
    }*/

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
