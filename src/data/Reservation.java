package data;

public class Reservation {

    private int ID;
    private Client client;
    private string startDate;
    private string endDate;
    private List<Vehicle> cars;

    public void getID() {
        // TODO - implement Reservation.getID
        throw new UnsupportedOperationException();
    }

    /**
     *
     * @param ID
     */
    public void setID(int ID) {
        // TODO - implement Reservation.setID
        throw new UnsupportedOperationException();
    }

    public Client getClient() {
        return this.client;
    }

    /**
     *
     * @param client
     */
    public void setClient(Client client) {
        this.client = client;
    }

    public void getAttribute() {
        // TODO - implement Reservation.getAttribute
        throw new UnsupportedOperationException();
    }

    /**
     *
     * @param attribute
     */
    public void setAttribute(int attribute) {
        // TODO - implement Reservation.setAttribute
        throw new UnsupportedOperationException();
    }

    public void getEndDate() {
        // TODO - implement Reservation.getEndDate
        throw new UnsupportedOperationException();
    }

    /**
     *
     * @param endDate
     */
    public void setEndDate(int endDate) {
        // TODO - implement Reservation.setEndDate
        throw new UnsupportedOperationException();
    }

    public List<Vehicle> getCars() {
        return this.cars;
    }

    /**
     *
     * @param cars
     */
    public void setCars(List<Vehicle> cars) {
        this.cars = cars;
    }
}
