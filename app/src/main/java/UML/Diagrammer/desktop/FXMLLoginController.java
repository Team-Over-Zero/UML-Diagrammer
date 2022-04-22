package UML.Diagrammer.desktop;

import UML.Diagrammer.backend.objects.UIPage;
import UML.Diagrammer.backend.objects.UIUser;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.net.URI;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

/**
 * Class to control the inputs/login for the login and register pages.
 */
public class FXMLLoginController extends App{

    DatabaseConnection dbConnection = new DatabaseConnection();
    @FXML TextField UserNameTextField;
    @FXML TextField PasswordTextField;
    @FXML Label loginErrorLabel;
    @FXML Label loginSuccessLabel;

    @FXML Label welcomeLabel;
    @FXML Label errorLabelNewPage;
    @FXML TextField newPageNameTextField;
    @FXML Pane successPane;

    public UIUser loggedInUser;
    public FXMLLoginController() {}

    //ObjectRequester requester = new ObjectRequester();
    // login page actions found from here on

    /**
     * What happens when the login button is pressed. For now just starts the main UML diagrammer app.
     */
    @FXML private void loginButtonPressed() {
        tryLogin();
    }

    private void tryLogin() {
        String retString = dbConnection.loginUser(jSonLogin(UserNameTextField.getText(), PasswordTextField.getText()));
        if (!retString.matches(".*\\d.*")){
            loginErrorLabel.setVisible(true);
        }
        else {

            successPane.setVisible(true);
            String userId = retString.replaceAll("[a-z{}\":,]", "");
            loginErrorLabel.setVisible(false);
            loggedInUser = new UIUser(Integer.parseInt(userId), UserNameTextField.getText());
            welcomeLabel.setText("Welcome " + loggedInUser.getName() + " please provide a page name");
        }
    }

    @FXML private void newPageButtonPressed() throws IOException {
        if (newPageNameTextField.getText().equals("") || newPageNameTextField.getText().matches(".*\\d.*")){
            welcomeLabel.setVisible(false);
            //display page name can't be blank or contain numbers
            errorLabelNewPage.setVisible(true);
        }
        else {
            UIPage newPageForUser = dbConnection.createNewPage(loggedInUser, newPageNameTextField.getText()); // Makes a new page upon login for now
            dbConnection.addUserToPage(loggedInUser, newPageForUser);
            Parent board = FXMLLoader.load(getClass().getResource("/Board.fxml"));
            FXMLController.setUpUserPage(loggedInUser, newPageForUser);
            App.primaryStage.setScene(new Scene(board, 1000, 800));
            loginSuccessLabel.setVisible(false);
        }
    }

    private String jSonLogin(String name, String password){
        return "{\"name\":\"" + name + "\",\"password\":\"" + password + "\"}";
    }

    /**
     * What happens when the register new user button is pressed.
     * Opens the register UI window.
     * @throws IOException
     */
    @FXML private void loginRegisterNewUserPressed() throws IOException{
        Parent register = FXMLLoader.load(getClass().getResource("/Register.fxml"));
        App.primaryStage.setScene(new Scene(register, 600, 400));
    }

    /**
     * Used in both the login page and register page to exit the whole app.
     */
    @FXML private void exitAppPressed(){
        Platform.exit();
    }

    @FXML private void loginCreditButtonPressed(){}

    @FXML private void loginReadMePressed() throws URISyntaxException, IOException {}

    // Register page actions found from here on

    @FXML TextField registerUserName;
    @FXML TextField registerNewPassword;
    @FXML TextField registerConfirmPassword;
    @FXML Label registerErrorLabel;
    @FXML Label registerSuccessLabel;

    /**
     * When you hit the cancel button on the registration page.
     * Sends to back to the login page.
     * @throws IOException
     */
    @FXML private void registerCancelButtonPressed() throws IOException{
        Parent login = FXMLLoader.load(getClass().getResource("/UserLogIn.fxml"));
        App.primaryStage.setScene(new Scene(login, 600, 400));
    }

    @FXML private void registerButtonPressed(){
        if (!registerNewPassword.getText().equals(registerConfirmPassword.getText())){
            registerErrorLabel.setText("Passwords do not match");
            registerErrorLabel.setVisible(true);
        }
        else {
            String newUserName = registerUserName.getText();
            String newPassword = registerNewPassword.getText();
            dbConnection.createNewUser(newUserName);
            registerErrorLabel.setVisible(false);
            registerSuccessLabel.setVisible(true);
        }
        //System.out.println(registerNewPassword.getText().equals(registerNewPassword.getText()));
    }

}
