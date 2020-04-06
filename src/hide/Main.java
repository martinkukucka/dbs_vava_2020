package hide;

import hide.Login;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {


        Login login = new Login();
        GridPane grid1 = new GridPane();
        GridPane grid2 = new GridPane();
        GridPane grid3 = new GridPane();
        GridPane blankGrid = new GridPane();
        Scene logScene = new Scene(grid1, 300, 450);
        Scene registerScene = new Scene(grid2, 300, 450);
        Scene blank = new Scene(blankGrid, 300, 450);
        Scene datapage = new Scene(grid3, 300, 450);

        TextField emailInput = new TextField();
        emailInput.setPromptText("E-mail");
        GridPane.setConstraints(emailInput, 0, 0);
        PasswordField passwordInput = new PasswordField();
        passwordInput.setPromptText("Password");
        GridPane.setConstraints(passwordInput, 0, 1);
        Button loginButton = new Button("Log in");
        loginButton.setOnAction(e -> {
            if (login.logInto(emailInput.getText(), passwordInput.getText())) {
                primaryStage.setScene(blank);
            }
        });
        GridPane.setConstraints(loginButton, 0, 2);
        Button registerButton = new Button("Sign up");
        registerButton.setOnAction(e -> {
//            if (login.register(emailInput.getText())) {
//                primaryStage.setScene(registerScene);
//            }
            primaryStage.setScene(registerScene);
        });
        GridPane.setConstraints(registerButton, 0, 3);

        TextField newEmailInput = new TextField();
        newEmailInput.setPromptText("E-mail");
        GridPane.setConstraints(newEmailInput, 0, 0);
        PasswordField newPasswordInput = new PasswordField();
        newPasswordInput.setPromptText("Password");
        GridPane.setConstraints(newPasswordInput, 0, 1);
        Button nextButton = new Button("Next");
        nextButton.setOnAction(e -> {
//            login.insertToDatabase(nameInput.getText(), phoneInput.getText(), cisloOp.getText(), emailInput.getText(), passwordInput.getText());
            if (login.register(newEmailInput.getText())) {
                primaryStage.setScene(datapage);
            }
        });
        GridPane.setConstraints(nextButton, 0, 2);

        TextField nameInput = new TextField();
        nameInput.setPromptText("Name");
        GridPane.setConstraints(nameInput, 0, 0);
        TextField phoneNumInput = new TextField();
        phoneNumInput.setPromptText("Phone number");
        GridPane.setConstraints(phoneNumInput, 0, 1);
        TextField idCardNumInput = new TextField();
        idCardNumInput.setPromptText("ID card number");
        GridPane.setConstraints(idCardNumInput, 0, 2);
        TextField cityInput = new TextField();
        cityInput.setPromptText("City");
        GridPane.setConstraints(cityInput, 0, 3);
        TextField streetAdrInput = new TextField();
        streetAdrInput.setPromptText("Street address");
        GridPane.setConstraints(streetAdrInput, 0, 4);
        TextField zipCodeInput = new TextField();
        zipCodeInput.setPromptText("Zip code");
        GridPane.setConstraints(zipCodeInput, 0, 5);
        Button acceptButton = new Button("Accept");
        acceptButton.setOnAction(e -> {
            login.insertToDatabase(nameInput.getText(), phoneNumInput.getText(),
                    idCardNumInput.getText(), newEmailInput.getText(),
                    newPasswordInput.getText(), cityInput.getText(),
                    streetAdrInput.getText(), zipCodeInput.getText());
            primaryStage.setScene(blank);
        });
        GridPane.setConstraints(acceptButton, 0, 6);


        grid1.getChildren().addAll(emailInput, passwordInput, loginButton, registerButton);
        grid2.getChildren().addAll(newEmailInput, newPasswordInput, nextButton);
        grid3.getChildren().addAll(nameInput, phoneNumInput, idCardNumInput, cityInput, streetAdrInput, zipCodeInput, acceptButton);
        primaryStage.setScene(logScene);
        primaryStage.show();

    }
}
