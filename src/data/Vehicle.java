package data;

public class Vehicle {

    private int ID;
    private String brand;
    private String model;
    private int yearOfProduction;
    private int carsMileage;
    private enum status{};
    private enum stateOfCar{};
    private Department department;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYearOfProduction() {
        return yearOfProduction;
    }

    public void setYearOfProduction(int yearOfProduction) {
        this.yearOfProduction = yearOfProduction;
    }

    public int getCarsMileage() {
        return carsMileage;
    }

    public void setCarsMileage(int carsMileage) {
        this.carsMileage = carsMileage;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}
