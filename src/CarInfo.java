import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

// Pomocna trieda pre vkladanie informacii o vozidle do tabulky v gui
// Obsahuje aj gettre a settre, cize informacie mozu byt pouzite v roznych metodach
public class CarInfo {

    private SimpleIntegerProperty modelID;
    private SimpleStringProperty category;
    private SimpleStringProperty carBrand;
    private SimpleStringProperty carModel;
    private SimpleStringProperty transmission;
    private SimpleStringProperty fuel;
    private SimpleIntegerProperty kw;
    private SimpleIntegerProperty seats;
    private SimpleIntegerProperty carID;
    private SimpleStringProperty licensePlateNumber;
    private SimpleStringProperty color;
    private SimpleIntegerProperty yearOfProduction;
    private SimpleIntegerProperty price;


    CarInfo(Integer carID, String category, String carBrand, String carModel, String transmission,
            String fuel, Integer kw, Integer seats, Integer modelID, String licensePlateNumber,
            String color, Integer yearOfProduction, Integer price) {
        this.carID = new SimpleIntegerProperty(carID);
        this.category = new SimpleStringProperty(category);
        this.carBrand = new SimpleStringProperty(carBrand);
        this.carModel = new SimpleStringProperty(carModel);
        this.transmission = new SimpleStringProperty(transmission);
        this.fuel = new SimpleStringProperty(fuel);
        this.kw = new SimpleIntegerProperty(kw);
        this.seats = new SimpleIntegerProperty(seats);
        this.modelID = new SimpleIntegerProperty(modelID);
        this.licensePlateNumber = new SimpleStringProperty(licensePlateNumber);
        this.color = new SimpleStringProperty(color);
        this.yearOfProduction = new SimpleIntegerProperty(yearOfProduction);
        this.price = new SimpleIntegerProperty(price);
    }

    public int getModelID() {
        return modelID.get();
    }

    public SimpleIntegerProperty modelIDProperty() {
        return modelID;
    }

    public void setModelID(int modelID) {
        this.modelID.set(modelID);
    }

    public String getCategory() {
        return category.get();
    }

    public SimpleStringProperty categoryProperty() {
        return category;
    }

    public void setCategory(String category) {
        this.category.set(category);
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

    public int getSeats() {
        return seats.get();
    }

    public SimpleIntegerProperty seatsProperty() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats.set(seats);
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

    public String getColor() {
        return color.get();
    }

    public SimpleStringProperty colorProperty() {
        return color;
    }

    public void setColor(String color) {
        this.color.set(color);
    }

    public int getYearOfProduction() {
        return yearOfProduction.get();
    }

    public SimpleIntegerProperty yearOfProductionProperty() {
        return yearOfProduction;
    }

    public void setYearOfProduction(int yearOfProduction) {
        this.yearOfProduction.set(yearOfProduction);
    }

    public float getPrice() {
        return price.get();
    }

    public SimpleIntegerProperty priceProperty() {
        return price;
    }

    public void setPrice(int price) {
        this.price.set(price);
    }
}