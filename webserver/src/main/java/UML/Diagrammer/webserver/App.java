/**
 * webserver\App.java
 *
 * This class serves as the main class for the webserver package. Should initialize a client.
 * @author Alex
 */

package UML.Diagrammer.webserver;
import UML.Diagrammer.backend.objects.*;
import java.io.IOException;
public class App {

    public static void main(String[] args){
        WebServer webServer = new WebServer();
        System.out.println("SERVER STARTED");
        HTTP_Client http_req_client = new HTTP_Client("jsonplaceholder.typicode.com/posts","80");
        try {
            //System.out.print(http_req_client.exampleGetRequest()); //commented this out to to avoid log spam. Get request does work.
            System.out.println(http_req_client.examplePutRequest());
            System.out.println(http_req_client.examplePostRequest());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
