package application;

import data.Client;
import data.Reservation;
import data.Vehicle;
import data.Department;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class ClientView {

    private ArrayList<Vehicle> carList;
    private ArrayList<Department> departmentsList;
    private ArrayList<Reservation> reservationsList;
    Statement stmt;


    public ClientView() {
        stmt = new Database().connect();
    }


    public ArrayList<Vehicle> getCarList() {
        return carList;
    }

    public void setCarList(ArrayList<Vehicle> carList) {
        this.carList = carList;
    }

    public ArrayList<Department> getDepartmentsList() {
        return departmentsList;
    }

    public void setDepartmentsList(ArrayList<Department> departmentsList) {
        this.departmentsList = departmentsList;
    }

    public ArrayList<Reservation> getReservationsList() {
        return reservationsList;
    }

    public void setReservationsList(ArrayList<Reservation> reservationsList) {
        this.reservationsList = reservationsList;
    }

    public void reserveCar() {

    }

    public void register() {

        Scanner scanner = new Scanner(System.in);
        try{

            System.out.print("Enter first name: ");
            String firstName = scanner.nextLine();
            System.out.print("Enter last name: ");
            String lastName = scanner.nextLine();
            System.out.print("Enter PESEL: ");
            String PESEL = scanner.nextLine();
            System.out.print("Enter phone number: ");
            String city = scanner.nextLine();
            System.out.print("Enter city: ");
            String phoneNumber = scanner.nextLine();
            System.out.print("Enter street: ");
            String street = scanner.nextLine();
            System.out.print("Enter street number: ");
            String streetNumber = scanner.nextLine();
            System.out.print("Enter identity card number: ");
            String idNumber = scanner.nextLine();
            String q1 = "insert into PERSONAL_DATA values('" +12+ "', '" +firstName+ "', '" +lastName+ "', '" +PESEL+ "', '" +phoneNumber+ "', '" +city+ "', '" +street+ "', '" +streetNumber+ "', '" +idNumber+ "')";
            stmt.executeUpdate(q1);
        }catch(Exception e){System.out.println("ewffefwf");}
    }

    public void signIn() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter username: ");
        String userName = scanner.nextLine();

        System.out.print("Enter username: ");
        String password = scanner.nextLine();

        if("login".equals(userName) && "password".equals(password)){
            System.out.println("Login successfully");
        }
        else{
            System.out.println("Incorrect data");
        }
    }

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
}
