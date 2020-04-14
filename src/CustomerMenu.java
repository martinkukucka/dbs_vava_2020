import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class CustomerMenu {

    @FXML
    private AnchorPane loginAnchorPane;

    @FXML
    private Button createOrderButton;

    @FXML
    private Button changePasswordButton;

    @FXML
    private Button backButton;

    @FXML
    void backButtonAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) backButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("GUI/entry.fxml"));
        stage.setScene(new Scene(root, backButton.getScene().getWidth(), backButton.getScene().getHeight()));
    }

    @FXML
    void changeButtonAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) changePasswordButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("GUI/passwordchange.fxml"));
        stage.setScene(new Scene(root, changePasswordButton.getScene().getWidth(), changePasswordButton.getScene().getHeight()));
    }

    @FXML
    void creteOrderButtonAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) createOrderButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("GUI/createorder.fxml"));
        stage.setScene(new Scene(root, createOrderButton.getScene().getWidth(), createOrderButton.getScene().getHeight()));
    }

}
