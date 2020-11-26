package application;

import data.Reservation;
import data.Vehicle;
import data.Department;

import java.util.ArrayList;

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
    }

    public void signIn() {
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
