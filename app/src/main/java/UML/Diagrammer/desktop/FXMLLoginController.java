package UML.Diagrammer.desktop;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import java.net.URI;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Class to control the inputs/login for the login and register pages.
 */
public class FXMLLoginController extends App{

    // login page actions found from here on

    /**
     * What happens when the login button is pressed. For now just starts the main UML diagrammer app.
     */
    @FXML private void loginButtonPressed() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/Board.fxml"));
        App.primaryStage.setScene(new Scene(root, 1000, 800));
    }

    /**
     * What happens when the register new user button is pressed.
     * Opens the register UI window.
     * @throws IOException
     */
    @FXML private void loginRegisterNewUserPressed() throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/Register.fxml"));
        App.primaryStage.setScene(new Scene(root, 600, 400));
    }

    /**
     * Used in both the login page and register page to exit the whole app.
     */
    @FXML private void exitAppPressed(){
        Platform.exit();
    }

    @FXML private void loginCreditButtonPressed(){}

    @FXML private void loginReadMePressed() throws URISyntaxException, IOException {}

    @FXML TextField UserNameTextField;
    @FXML TextField PasswordTextField;

    // Register page actions found from here on

    /**
     * When you hit the cancel button on the registration page.
     * Sends to back to the login page.
     * @throws IOException
     */
    @FXML private void registerCancelButtonPressed() throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/UserLogIn.fxml"));
        App.primaryStage.setScene(new Scene(root, 600, 400));
    }

    @FXML private void registerButtonPressed(){
        if (registerNewPassword.getText().equals(registerNewPassword.getText())){
            registerErrorLabel.setText("Passwords do not match");
            registerErrorLabel.setVisible(true);
        }
        System.out.println(registerNewPassword.getText().equals(registerNewPassword.getText()));
    }

    @FXML TextField registerUserName;
    @FXML TextField registerNewPassword;
    @FXML TextField registerConfirmPassword;
    @FXML Label registerErrorLabel;

}
