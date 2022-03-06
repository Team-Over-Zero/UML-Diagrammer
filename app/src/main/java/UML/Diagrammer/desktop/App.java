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
import UML.Diagrammer.backend.objects.*;

import java.lang.reflect.Type;

public class App extends Application{

    public String getGreeting() {
        return "Hello World!";
    }

    public static void main(String[] args) {
        System.out.println(new App().getGreeting());
        NodeFactory fac = new NodeFactory();
        AbstractNode myNode = fac.buildNode("CLASS", 0, 0, 3,3);
        AbstractNode testNodeInheritance = fac.buildNode("Test_SVG",1,1,2,2);
        System.out.println(myNode.getName());
        System.out.println(testNodeInheritance.getName()+ " " + testNodeInheritance.getDescription());
        App.launch();
//        Javalin app = Javalin.create().start(7070);
//        app.get("/", ctx -> ctx.result("Hello this is a test"));


    }

    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/Board.fxml"));
        primaryStage.setTitle("UML Diagrammer");
        primaryStage.setScene(new Scene(root, 1000, 800));
        primaryStage.show();

    }
}