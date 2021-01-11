package data;

public class Client extends User {

    private int clientID;
    private int personalDataID;
    private String login;
    private String password;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

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

    public static void modifyData(Client client, String firstName, String lastName, String phoneNumber, String city, String address) {
        client.setFirstName(firstName);
        client.setLastName(lastName);
        client.setPhoneNumber(phoneNumber);
        client.setAddress(address);
        client.setCity(city);
    }

    public int getPersonalDataID() {
        return personalDataID;
    }

    public void setPersonalDataID(int personalDataID) {
        this.personalDataID = personalDataID;
    }
}
