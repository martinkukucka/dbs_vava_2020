import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.*;

public class ServiceInfo {

    private SimpleIntegerProperty servisID;
    private SimpleStringProperty name;
    private SimpleStringProperty phoneNumber;
    private SimpleStringProperty email;
    private SimpleStringProperty region;
    private SimpleStringProperty city;
    private SimpleStringProperty street;
    private SimpleStringProperty housenumber;
    private SimpleIntegerProperty zipCode;

    public ServiceInfo(Integer servisID, String name, String phoneNumber, String email, String region, String city, String street,
                       String housenumber, Integer zipCode)
    {
        this.servisID = new SimpleIntegerProperty(servisID);
        this.name = new SimpleStringProperty(name);
        this.phoneNumber = new SimpleStringProperty(phoneNumber);
        this.email = new SimpleStringProperty(email);
        this.region = new SimpleStringProperty(region);
        this.city = new SimpleStringProperty(city);
        this.street = new SimpleStringProperty(street);
        this.housenumber = new SimpleStringProperty(housenumber);
        this.zipCode = new SimpleIntegerProperty(zipCode);
    }

    public int getServisID() {
        return servisID.get();
    }

    public SimpleIntegerProperty servisIDProperty() {
        return servisID;
    }

    public void setServisID(int servisID) {
        this.servisID.set(servisID);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getPhoneNumber() {
        return phoneNumber.get();
    }

    public SimpleStringProperty phoneNumberProperty() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber.set(phoneNumber);
    }

    public String getEmail() {
        return email.get();
    }

    public SimpleStringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getRegion() {
        return region.get();
    }

    public SimpleStringProperty regionProperty() {
        return region;
    }

    public void setRegion(String region) {
        this.region.set(region);
    }

    public String getCity() {
        return city.get();
    }

    public SimpleStringProperty cityProperty() {
        return city;
    }

    public void setCity(String city) {
        this.city.set(city);
    }

    public String getStreet() {
        return street.get();
    }

    public SimpleStringProperty streetProperty() {
        return street;
    }

    public void setStreet(String street) {
        this.street.set(street);
    }

    public String getHousenumber() {
        return housenumber.get();
    }

    public SimpleStringProperty housenumberProperty() {
        return housenumber;
    }

    public void setHousenumber(String housenumber) {
        this.housenumber.set(housenumber);
    }

    public int getZipCode() {
        return zipCode.get();
    }

    public SimpleIntegerProperty zipCodeProperty() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode.set(zipCode);
    }



}
