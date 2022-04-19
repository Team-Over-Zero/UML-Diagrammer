/*Copyright 2022 Team OverZero
<p>
Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
documentation files (the "Software"), to deal in the Software without restriction, including without limitation
the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and
to permit persons to whom the Software is furnished to do so, subject to the following conditions:
<p>
The above copyright notice and this permission notice shall be included in all copies or substantial portions of
the Software.
<p>
THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.*/
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

import UML.Diagrammer.backend.apis.Database_Client;
import UML.Diagrammer.backend.apis.HTTP_Client;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.checkerframework.checker.units.qual.A;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class App extends Application{

    public static final DatabaseConnection database = new DatabaseConnection();
    public static Stage primaryStage;


    public String getGreeting() {
        return "Hello World!";
    }

    public static void main(String[] args) {
        App.launch();
    }

    /**
     * Starts the main UI. In the future this will launch a login window.
     * @param stage current main stage of the application
     */
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/UserLogIn.fxml"));
        stage.setTitle("UML Diagrammer");
        stage.setScene(new Scene(root, 600, 400));
        stage.show();
        primaryStage = stage;
        //primaryStage.setAlwaysOnTop(true);
    }
}