import animatefx.animation.FadeIn;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Entry {

    @FXML
    private AnchorPane loginAnchorPane;

    @FXML
    private Button entryLoginButton;

    @FXML
    private Button entryRegisterButton;

    @FXML
    private Button entryExitButton;

    @FXML
    private void entryLoginButtonAction(ActionEvent event) throws Exception{
        Stage stage = (Stage) entryLoginButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("GUI/login.fxml"));
        stage.setScene(new Scene(root, entryLoginButton.getScene().getWidth(), entryLoginButton.getScene().getHeight()));
//        new FadeIn(root).play();
    }

    @FXML
    private void entryRegisterButtonAction(ActionEvent event) throws Exception{
        Stage stage = (Stage) entryRegisterButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("GUI/register.fxml"));
        stage.setScene(new Scene(root, entryRegisterButton.getScene().getWidth(), entryRegisterButton.getScene().getHeight()));
    }

    @FXML
    private void entryExitButtonAction(ActionEvent event) throws Exception{
        System.exit(0);
    }
}
