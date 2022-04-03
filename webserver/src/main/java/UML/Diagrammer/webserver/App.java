/**
 * webserver\App.java
 *
 * This class serves as the main class for the webserver package. Should initialize a client.
 * @author Alex
 */

package UML.Diagrammer.webserver;
import UML.Diagrammer.backend.apis.HTTP_Client;

import java.io.IOException;
public class App {

    public static void main(String[] args){
        WebServer webServer = new WebServer();
        System.out.println("SERVER STARTED");

    }
}
