import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class RentalInfo {

    private SimpleStringProperty from;
    private SimpleStringProperty to;
    private SimpleStringProperty carBrandU;
    private SimpleStringProperty carModelU;
    private SimpleDoubleProperty rentalPrice;

    public RentalInfo(String from, String to, String carBrandU,
                      String carModelU, double rentalPrice) {
        this.from = new SimpleStringProperty(from);
        this.to = new SimpleStringProperty(to);
        this.carBrandU = new SimpleStringProperty(carBrandU);
        this.carModelU = new SimpleStringProperty(carModelU);
        this.rentalPrice = new SimpleDoubleProperty(rentalPrice);
    }

    public String getFrom() {
        return from.get();
    }

    public SimpleStringProperty fromProperty() {
        return from;
    }

    public void setFrom(String from) {
        this.from.set(from);
    }

    public String getTo() {
        return to.get();
    }

    public SimpleStringProperty toProperty() {
        return to;
    }

    public void setTo(String to) {
        this.to.set(to);
    }

    public String getCarBrandU() {
        return carBrandU.get();
    }

    public SimpleStringProperty carBrandUProperty() {
        return carBrandU;
    }

    public void setCarBrandU(String carBrandU) {
        this.carBrandU.set(carBrandU);
    }

    public String getCarModelU() {
        return carModelU.get();
    }

    public SimpleStringProperty carModelUProperty() {
        return carModelU;
    }

    public void setCarModelU(String carModelU) {
        this.carModelU.set(carModelU);
    }

    public double getRentalPrice() {
        return rentalPrice.get();
    }

    public SimpleDoubleProperty rentalPriceProperty() {
        return rentalPrice;
    }

    public void setRentalPrice(double rentalPrice) {
        this.rentalPrice.set(rentalPrice);
    }
}
