import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class AvailabilityInfo {

    private SimpleStringProperty from;
    private SimpleStringProperty to;

    AvailabilityInfo(String from, String to) {
        this.from = new SimpleStringProperty(from);
        this.to = new SimpleStringProperty(to);
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
}
