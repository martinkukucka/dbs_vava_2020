import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

// Pomocna trieda pre vkladanie informacii o servisovanych vozidlach do tabulky v gui
// Obsahuje aj gettre a settre, cize informacie mozu byt pouzite v roznych metodach
public class ServiceCarInfo {

    private SimpleIntegerProperty carID;
    private SimpleStringProperty licensePlateNumber;
    private SimpleStringProperty carBrand;
    private SimpleStringProperty carModel;
    private SimpleStringProperty color;
    private SimpleStringProperty yearOfProduction;
    private SimpleStringProperty transmission;
    private SimpleStringProperty fuel;
    private SimpleIntegerProperty kw;
    private SimpleStringProperty serviceName;
    private SimpleStringProperty vehicleState;


    ServiceCarInfo(Integer carID, String licensePlateNumber, String carBrand, String carModel, String color,
                   String yearOfProduction, String transmission, String fuel, Integer kw, String serviceName,
                   String vehicleState) {
        this.carID = new SimpleIntegerProperty(carID);
        this.licensePlateNumber = new SimpleStringProperty(licensePlateNumber);
        this.carBrand = new SimpleStringProperty(carBrand);
        this.carModel = new SimpleStringProperty(carModel);
        this.color = new SimpleStringProperty(color);
        this.yearOfProduction = new SimpleStringProperty(yearOfProduction);
        this.transmission = new SimpleStringProperty(transmission);
        this.fuel = new SimpleStringProperty(fuel);
        this.kw = new SimpleIntegerProperty(kw);
        this.serviceName = new SimpleStringProperty(serviceName);
        this.vehicleState = new SimpleStringProperty(vehicleState);
    }

    public int getCarID() {
        return carID.get();
    }

    public SimpleIntegerProperty carIDProperty() {
        return carID;
    }

    public void setCarID(int carID) {
        this.carID.set(carID);
    }

    public String getLicensePlateNumber() {
        return licensePlateNumber.get();
    }

    public SimpleStringProperty licensePlateNumberProperty() {
        return licensePlateNumber;
    }

    public void setLicensePlateNumber(String licensePlateNumber) {
        this.licensePlateNumber.set(licensePlateNumber);
    }

    public String getCarBrand() {
        return carBrand.get();
    }

    public SimpleStringProperty carBrandProperty() {
        return carBrand;
    }

    public void setCarBrand(String carBrand) {
        this.carBrand.set(carBrand);
    }

    public String getCarModel() {
        return carModel.get();
    }

    public SimpleStringProperty carModelProperty() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel.set(carModel);
    }

    public String getColor() {
        return color.get();
    }

    public SimpleStringProperty colorProperty() {
        return color;
    }

    public void setColor(String color) {
        this.color.set(color);
    }

    public String getYearOfProduction() {
        return yearOfProduction.get();
    }

    public SimpleStringProperty yearOfProductionProperty() {
        return yearOfProduction;
    }

    public void setYearOfProduction(String yearOfProduction) {
        this.yearOfProduction.set(yearOfProduction);
    }

    public String getTransmission() {
        return transmission.get();
    }

    public SimpleStringProperty transmissionProperty() {
        return transmission;
    }

    public void setTransmission(String transmission) {
        this.transmission.set(transmission);
    }

    public String getFuel() {
        return fuel.get();
    }

    public SimpleStringProperty fuelProperty() {
        return fuel;
    }

    public void setFuel(String fuel) {
        this.fuel.set(fuel);
    }

    public int getKw() {
        return kw.get();
    }

    public SimpleIntegerProperty kwProperty() {
        return kw;
    }

    public void setKw(int kw) {
        this.kw.set(kw);
    }

    public String getServiceName() {
        return serviceName.get();
    }

    public SimpleStringProperty serviceNameProperty() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName.set(serviceName);
    }

    public String getVehicleState() {
        return vehicleState.get();
    }

    public SimpleStringProperty vehicleStateProperty() {
        return vehicleState;
    }

    public void setVehicleState(String vehicleState) {
        this.vehicleState.set(vehicleState);
    }
}