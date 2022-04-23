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
import java.nio.file.Path;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    @FXML MenuButton loadSession;

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
            // Magic regex from stackoverflow. Gets the first repeated instance of integers
            String userId = retString.replaceAll("^\\D*(\\d+).*", "$1");
            System.out.println(userId);
            loginErrorLabel.setVisible(false);
            loggedInUser = new UIUser(Integer.parseInt(userId), UserNameTextField.getText());
            welcomeLabel.setText("Welcome " + loggedInUser.getName() + " please provide a page name");
            populateLoadButton(loggedInUser);
        }
    }

    /**
     * Populates the load button after logging in to resume previous work.
     * The user will need to hit "refresh" after getting into the main board. This is because of how fxml deals with
     * controllers in combination with my variable scope. I can't load until I set up a current page/user but I can't
     * set up a current page/user until I call FXMLLoader.load. but once it's loaded I can't set the current page/user.
     * So the refresh button works well enough for the mvp and the refresh allows for semi-collaborative editing.
     * @param user The current user who just logged in.
     */
    @FXML private void populateLoadButton(UIUser user){
        ObjectRequester oR = new ObjectRequester();
        oR.setCurrentUser(user);
        Map<Integer, String> loadedPages = oR.getUserPages();
        for (Map.Entry<Integer, String> curPage : loadedPages.entrySet()) {
            if (!containsPage(curPage.getValue())){
                MenuItem newItem = new MenuItem(curPage.getValue());

                newItem.setOnAction(a -> {
                    UIPage page = new UIPage(curPage.getKey(), curPage.getValue());
                    try {
                        Parent board = FXMLLoader.load(getClass().getResource("/Board.fxml"));
                        FXMLController.setUpUserPage(loggedInUser, page);
                        App.primaryStage.setScene(new Scene(board, 1000, 800));
                        loginSuccessLabel.setVisible(false);

                    } catch (IOException e) {e.printStackTrace();}
                });

                loadSession.getItems().add(newItem);
            }
        }
        }

    /**
     * Duplicate of what is in object request(I know duplicate is bad, but I don't have time to refactor).
     * Finds any duplicate name in a menu item.
     * @param value  The value you are trying to match.
     * @return If there is a matching value.
     */
    private Boolean containsPage(String value){
        for (MenuItem curItem: loadSession.getItems()) {
            if(value.equals(curItem.getText())){
                return true;
            }
        }
        return false;
    }

    /**
     * What happens when the user creates a new page from the login ui.
     */
    @FXML private void newPageButtonPressed() throws IOException {
        if (newPageNameTextField.getText().equals("") || newPageNameTextField.getText().matches(".*\\d.*")){
            welcomeLabel.setVisible(false);
            //display "page name can't be blank or contain numbers"
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

    /**
     * Jsons the login to send to the db.
     * @param name user's username
     * @param password user's password
     * @return The Json version of the username/password for the db to use.
     */
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
        String foundUser = dbConnection.findUserViaName(registerUserName.getText());

        System.out.println(foundUser);

        if (!foundUser.equals("ERROR: USER NOT FOUND")){
            registerErrorLabel.setText("Username already taken");
            registerErrorLabel.setVisible(true);
        }
        else if (!registerNewPassword.getText().equals(registerConfirmPassword.getText())){
            registerErrorLabel.setText("Passwords do not match");
            registerErrorLabel.setVisible(true);
        }
        else {
            dbConnection.createNewUser(registerUserName.getText(), registerNewPassword.getText());
            registerErrorLabel.setVisible(false);
            registerSuccessLabel.setVisible(true);
            System.out.println("MADE USER");
        }
        //System.out.println(registerNewPassword.getText().equals(registerNewPassword.getText()));
    }

}
