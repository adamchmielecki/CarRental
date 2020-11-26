package data;

public class Rental extends Reservation{

    private Worker worker;
    private int ID;
    private Bill bill;

    public void getWorkerID() {
        // TODO - implement Rental.getWorkerID
        throw new UnsupportedOperationException();
    }

    /**
     *
     * @param WorkerID
     */
    public void setWorkerID(int WorkerID) {
        // TODO - implement Rental.setWorkerID
        throw new UnsupportedOperationException();
    }

    public void getID() {
        // TODO - implement Rental.getID
        throw new UnsupportedOperationException();
    }

    /**
     *
     * @param ID
     */
    public void setID(int ID) {
        // TODO - implement Rental.setID
        throw new UnsupportedOperationException();
    }

    public Bill getBill() {
        return this.bill;
    }

    /**
     *
     * @param bill
     */
    public void setBill(Bill bill) {
        this.bill = bill;
    }
}
