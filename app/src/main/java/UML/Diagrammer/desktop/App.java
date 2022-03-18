/**
 * desktop\App.java
 *
 * This class acts as our JavaFX frontend. Should handle user input, display/edit diagrams, and be able to send and recieve
 * data from a database. Uses backend objects.
 *
 * @author Show
 *
 **/
package UML.Diagrammer.desktop;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class App extends Application{

    public static final DatabaseConnection database = new DatabaseConnection();
    public static Stage primaryStage;

    public String getGreeting() {
        return "Hello World!";
    }

    public static void main(String[] args) {
    	//SvgImageLoaderFactory.install();
    	//App.launch();
        //Base.open("com.mysql.cj.jdbc.Driver", "jdbc:mysql://localhost/test?serverTimezone=America/Denver", "root", "secret");

        App.launch();
    }

    public void start(Stage stage) throws Exception {
        database.openConnection();
        Parent root = FXMLLoader.load(getClass().getResource("/Board.fxml"));
        stage.setTitle("UML Diagrammer");
        stage.setScene(new Scene(root, 1000, 800));
        stage.show();
        primaryStage = stage;
    }

    /**
     * This is what happens on app exit, can also call this via Platform.exit()
     * Just closes out the database before the app closes.
     */
    @Override
    public void stop(){
        System.out.println("Stage is closing");
        database.closeConnection();
    }

}