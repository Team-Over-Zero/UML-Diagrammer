/**
 * HTTP_CLient.java
 * @Author Alex Diviney Team OverZero
 *
 * This class offers helper methods for client applications to use. Since UML_Diagrammer only needs a few specific
 * HTTP requests, this will help clients easily build those requests, helping with separation of responsibilites.
 */
package UML.Diagrammer.backend.objects;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Flow;

public class HTTP_Client {
    String address;
    String port;
    String serverString;
    public HTTP_Client(){
        address = "127.0.0.1";
        port = "7778";
        serverString = String.format("https://%s:%s",address,port);
    }

    public HTTP_Client(String addr, String p){
        address = addr;
        port = p;
        serverString = String.format("https://%s:%s",address,port);
    }

    /**
     * This method sends a simple get request to an API site (jsonplaceholder.typicode.com). Should always work.
     *
     * @return A string represenation of the jsonplaceholder webpage
     * @throws IOException Wrap with try catch for IOexceptions
     * @throws InterruptedException Wrap with try catch for InterruptedException
     */
    public String exampleGetRequest()throws IOException,InterruptedException {
        String tempServerString = "https://jsonplaceholder.typicode.com/posts"; //no port for some reason
       // System.out.println(serverString);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .header("accept","application/json")
                .uri(URI.create(tempServerString))
                .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    /**
     * This executes a put command. Request is sent to a sie called req.com
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public String examplePutRequest()throws IOException, InterruptedException{
        String tempServerString = "https://reqbin.com/echo/put/json"; //no port for some reason
        // System.out.println(serverString);
        HttpClient client = HttpClient.newHttpClient();
        String data = "{\n  \"Id\": 12345,\n  \"Customer\": \"John Smith\",\n  \"Quantity\": 1,\n  \"Price\": 10.00\n}";
        HttpRequest.BodyPublisher testPub = HttpRequest.BodyPublishers.ofString(data);

        HttpRequest request = HttpRequest.newBuilder()
                .PUT(testPub)
                .header("accept","application/json")
                .uri(URI.create(tempServerString))
                .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return response.body();

    }

    /**
     * This executes a sample post request. Request is sent to a site called reqbin.com
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public String examplePostRequest()throws IOException, InterruptedException {
        String tempServerString = "https://reqbin.com/echo/post/json"; //no port for some reason
        // System.out.println(serverString);
        HttpClient client = HttpClient.newHttpClient();
        String data = "{\n  \"Id\": 12345,\n  \"Customer\": \"John Smith\",\n  \"Quantity\": 1,\n  \"Price\": 10.00\n}";
        HttpRequest.BodyPublisher testPub = HttpRequest.BodyPublishers.ofString(data);

        HttpRequest request = HttpRequest.newBuilder()
                .POST(testPub)
                .header("accept", "application/json")
                .uri(URI.create(tempServerString))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();

    }

    /**
     * Should send a post request with the username and password (we can deal with encryption later) and should get a response that says whether the user was successfully logged in.
     * We need to store credentials somehow so that that user can send continuous updates after logging in.
     * Need to make sure other methods here can't run without this one being correct.
     * @return
     */
    public String tryLoginUser(){

        return "FAILED TO LOGIN";
    }

    /**
     * Simple get Request that asks for a gson of a page object given a pagename.
     * @param pageName name of UML page the user is requesting to edit
     * @return
     */
    public String tryGetPage(String pageName){
        return "";
    }

    /**
     * Should return a list of names of all of the pages the user has created.
     * @return
     */
    public List<String> getUserpageNames(){
        LinkedList<String> userPageList = new LinkedList<>();

        return userPageList;
    }

    /**
     * Should send a post or put request (still not sure) to the database with the current page state.
     * May want to change from void return to test errors and such.
     */
    public void sendCurrentPageState(){}


}
