import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

// ?
public class Entry {

    @FXML
    private Button entryLoginButton;

    @FXML
    private Button entryRegisterButton;

    @FXML
    private void entryLoginButtonAction() throws Exception {
        Stage stage = (Stage) entryLoginButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("GUI/login.fxml"));
        stage.setScene(new Scene(root, entryLoginButton.getScene().getWidth(), entryLoginButton.getScene().getHeight()));
    }

    @FXML
    private void entryRegisterButtonAction() throws Exception {
        Stage stage = (Stage) entryRegisterButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("GUI/register.fxml"));
        stage.setScene(new Scene(root, entryRegisterButton.getScene().getWidth(), entryRegisterButton.getScene().getHeight()));
    }

    @FXML
    private void entryExitButtonAction() {
        System.exit(0);
    }
}
