package data;

import java.time.LocalDate;

public class Vehicle {

    private int vehicleID;
    private String brand;
    private String model;
    private int yearOfProduction;
    private int carsMileage;
    private String registrationNumber;
    private int engineCapacity;
    private int power;
    private String typeOfFuel;
    private LocalDate nextOverviewDate;
    private LocalDate endOfInsurancePolicy;
    private String segment;
    private String bodyType;
    private Department department;

    private enum status{
        AVAILABLE,
        UNAVAILABLE
    }
    private enum stateOfCar{
        FUNCTIONAL,
        DAMAGED,
        OVERVIEW_REQUIRED,
        SERVICE_REQUIRED
    }

    public int getVehicleID() {
        return vehicleID;
    }

    public void setVehicleID(int vehicleID) {
        this.vehicleID = vehicleID;
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

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public int getEngineCapacity() {
        return engineCapacity;
    }

    public void setEngineCapacity(int engineCapacity) {
        this.engineCapacity = engineCapacity;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public String getTypeOfFuel() {
        return typeOfFuel;
    }

    public void setTypeOfFuel(String typeOfFuel) {
        this.typeOfFuel = typeOfFuel;
    }

    public LocalDate getNextOverviewDate() {
        return nextOverviewDate;
    }

    public void setNextOverviewDate(LocalDate nextOverviewDate) {
        this.nextOverviewDate = nextOverviewDate;
    }

    public LocalDate getEndOfInsurancePolicy() {
        return endOfInsurancePolicy;
    }

    public void setEndOfInsurancePolicy(LocalDate endOfInsurancePolicy) {
        this.endOfInsurancePolicy = endOfInsurancePolicy;
    }

    public String getSegment() {
        return segment;
    }

    public void setSegment(String segment) {
        this.segment = segment;
    }

    public String getBodyType() {
        return bodyType;
    }

    public void setBodyType(String bodyType) {
        this.bodyType = bodyType;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}
