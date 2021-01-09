package data;

public class Client extends User {

    private int clientID;
    private int personalDataID;

    private String drivingLicenseNumber;

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    public String getDrivingLicenseNumber() {
        return drivingLicenseNumber;
    }

    public void setDrivingLicenseNumber(String drivingLicenseNumber) {
        this.drivingLicenseNumber = drivingLicenseNumber;
    }

    public static void modifyData(Client client, String text, String firstName, String lastName, String phoneNumber, String postCode, String city, String address) {
        client.setFirstName(firstName);
        client.setLastName(lastName);
        client.setPhoneNumber(phoneNumber);
        client.setAddress(address);
        client.setCity(city);
        client.setPostCode(postCode);
    }

    public int getPersonalDataID() {
        return personalDataID;
    }

    public void setPersonalDataID(int personalDataID) {
        this.personalDataID = personalDataID;
    }
}
