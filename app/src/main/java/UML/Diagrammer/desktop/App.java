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


import UML.Diagrammer.backend.objects.NodeFactory.ClassNode;
import UML.Diagrammer.backend.objects.NodeFactory.NodeFactory;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.javalite.activejdbc.Base;


public class App extends Application{

    public String getGreeting() {
        return "Hello World!";
    }

    public static void main(String[] args) {
        String databaseURL = "jdbc:mysql://ls-a9db0e6496e5430883b43e690a26b7676cf9d7af.cuirr4jp1g1o.us-west-2.rds.amazonaws.com/test";
        String databaseUser = "root";
        String databasePassword = "TeamOverZero";
        Base.open("com.mysql.cj.jdbc.Driver", databaseURL, databaseUser, databasePassword);

        System.out.println(new App().getGreeting());
        NodeFactory fac = new NodeFactory();
        ClassNode myNode = fac.buildNode("CLASS", 0, 0, 3,3);
        myNode.testFunc();
        System.out.println(myNode.getName());
        App.launch();
        Base.close();
    }

    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/Board.fxml"));
        primaryStage.setTitle("UML Diagrammer");
        primaryStage.setScene(new Scene(root, 1000, 800));
        primaryStage.show();
    }

}