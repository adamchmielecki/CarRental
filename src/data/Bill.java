package data;

import sun.util.resources.LocaleData;

public class Bill {

    private int ID;
    private float cost;
    private float discount;
    private LocaleData date;
    private float penalty;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public LocaleData getDate() {
        return date;
    }

    public void setDate(LocaleData date) {
        this.date = date;
    }

    public float getPenalty() {
        return penalty;
    }

    public void setPenalty(float penalty) {
        this.penalty = penalty;
    }
}
