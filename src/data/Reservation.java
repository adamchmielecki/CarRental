package data;
import sun.util.resources.LocaleData;
import java.util.List;

public class Reservation {

    private int ID;
    private Client client;
    private LocaleData startDate;
    private LocaleData endDate;
    private List<Vehicle> cars;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public LocaleData getStartDate() {
        return startDate;
    }

    public void setStartDate(LocaleData startDate) {
        this.startDate = startDate;
    }

    public LocaleData getEndDate() {
        return endDate;
    }

    public void setEndDate(LocaleData endDate) {
        this.endDate = endDate;
    }

    public List<Vehicle> getCars() {
        return cars;
    }

    public void setCars(List<Vehicle> cars) {
        this.cars = cars;
    }
}
