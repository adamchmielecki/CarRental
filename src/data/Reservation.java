package data;

import java.time.LocalDateTime;
import java.util.List;

public class Reservation {

    private int reservationID;
    private Client client;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private List<Vehicle> cars;

    public int getReservationID() {
        return reservationID;
    }

    public void setReservationID(int reservationID) {
        this.reservationID = reservationID;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public List<Vehicle> getCars() {
        return cars;
    }

    public void setCars(List<Vehicle> cars) {
        this.cars = cars;
    }
}
