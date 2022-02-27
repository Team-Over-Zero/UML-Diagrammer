/*
 * This Java source file was generated by the Gradle 'init' task.
 */
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
        System.out.println(new DefaultNode().getTitle());
        Factory factory = new Factory();
        DefaultNode factoryNode = factory.createSmallDefaultNode("smallFactoryNode",1,2);
        factoryNode.testFunc();
        System.out.println(factoryNode.getDescription());
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