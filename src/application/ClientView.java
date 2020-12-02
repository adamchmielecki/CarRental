package application;

import data.Client;
import data.Reservation;
import data.Vehicle;
import data.Department;

import java.util.ArrayList;
import java.util.Scanner;

public class ClientView {

    private ArrayList<Vehicle> carList;
    private ArrayList<Department> departmentsList;
    private ArrayList<Reservation> reservationsList;


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
        Client client = new Client();

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter firstName: ");
        String firstName = scanner.nextLine();
        client.setFirstName(firstName);

        System.out.print("Enter lastName: ");
        String lastName = scanner.nextLine();
        client.setLastName(lastName);

        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        client.setEmail(email);

        System.out.print("Enter login: ");
        String login = scanner.nextLine();
        client.setLogin(login);

        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        client.setPassword(password);

        System.out.print("Enter phoneNumber: ");
        String phoneNumber = scanner.nextLine();
        client.setPhoneNumber(phoneNumber);

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
