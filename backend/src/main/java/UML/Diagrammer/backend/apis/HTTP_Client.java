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
 * HTTP_CLient.java
 *
 * This class offers helper methods for client applications to use. Since UML_Diagrammer only needs a few specific
 * HTTP requests, this will help clients easily build those requests, helping with separation of responsibilities.
 * @Author Alex
 */
package UML.Diagrammer.backend.apis;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
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
        port = "8888";
        serverString = String.format("https://%s:%s",address,port);
    }

    public HTTP_Client(String addr, String p){
        address = addr;
        port = p;
        serverString = String.format("https://%s:%s",address,port);
        //serverString = serverString;
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
     * Takes a passed in user Json object with an arbitrary id and returns a user json object with a unique Id
     * @return
     */
    public String usercreaterequest(){
       String newUser = "";


        return newUser;
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
     * Should send a post req to the database with the current page state.
     * May want to change from void return to test errors and such.
     */
    public void sendCurrentPageState(String page){}


    //Dev Node Requests

    /**
     *
     * @param nodeJson json node with no id
     * @return A string with the new node Id
     * @throws URISyntaxException thrown if input is not json
     * @throws IOException genericIO exception
     */
    public String sendNodeCreateRequest(String nodeJson) throws URISyntaxException, IOException {

        String returnString = genericPutRequestOneParam("/trycreatenode/","node", nodeJson);
        return returnString;
    }

    /**
     * * Sends a passed in "updated node", and returns if it was successfully updated in the backend or note.
     * @param nodeJson Json node with id
     * @return
     * @throws IOException
     * @throws InterruptedException
     * @throws URISyntaxException
     */
    public String sendNodeUpdateRequest(String nodeJson) throws IOException, InterruptedException, URISyntaxException {
        String returnString = genericGetRequestOneParam("/updatenode/","node",nodeJson);
        return returnString;
    }

    //Dev Edge requests.
    public String sendEdgeCreateRequest(String edgeJson) throws IOException,InterruptedException,URISyntaxException{
        String paramName = "edge";
        String returnString = genericPutRequestOneParam(("/trycreateedge/"),paramName, edgeJson);
        return returnString;
    }

    //Page Requests


    public String sendCreatePage(String pageJson){
        String returnString = "";
        try {
            returnString = genericPutRequestOneParam("/createpage/","page",pageJson);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnString;
    }

    /**
     * Not yet implemented.
     * @param pageJson
     * @return
     */
    public String sendRemovePage(String pageJson){return "";}; //may not be implemented yet

    public String sendAddNodeToPage(String nodeJson, String pageIdJson){
        String returnString = "";
        try {
            returnString = genericPutRequestTwoParams("/addnodetopage/","node",nodeJson,"pageid",pageIdJson);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnString;
    }

    public String sendRemoveNodeFromPage(String nodeJson, String pageIdJson){
        String returnString = "";

        try {
            returnString = genericPutRequestTwoParams("/removenodefrompage/","node",nodeJson,"pageid",pageIdJson);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnString;
    }

    public String sendAddEdgeToPage(String edgeJson, String pageIdJson){
        String returnString = "";

        try {
            returnString = genericPutRequestTwoParams("/addedgetopage/","edge",edgeJson,"pageid",pageIdJson);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnString;
    }

    public String sendRemoveEdgeFromPage(String edgeJson, String pageIdJson){
        String returnString = "";
        try {
            returnString = genericPutRequestTwoParams("/removeedgefrompage/","edge",edgeJson,"pageid",pageIdJson);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnString;
    }

    public String sendAddUserToPage(String userJson, String pageIdJson){
        String returnString = "";

        try {
            returnString = genericPutRequestTwoParams("/addusertopage/","user",userJson,"pageid",pageIdJson);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnString;
    }

    /**
     *
     *
     * @param userJson user object (UserUI)
     * @param pageIdJson json representation of an id
     * @return Server context.result() string.
     */
    public String sendRemoveUserFromPage(String userJson, String pageIdJson){
        String returnString = "";
        try {
            returnString = genericPutRequestTwoParams("/removenodefrompage/","user",userJson,"pageid",pageIdJson);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnString;
    }

    //User Requests
    public String sendCreateUser(String userJson){
        String returnString = "";
        try {
            returnString = genericPutRequestOneParam("/createuser/","user",userJson);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnString;
    }

    /**
     * May not be properly implemented yet.
     * @param userJson
     * @return
     */
    public String sendDeleteUser(String userJson){
        String returnString = "";
        try {
            returnString = genericPutRequestOneParam("/deleteuser/","user",userJson);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnString;
      }



    /**
     *  This method can be used to send a single parameter put request to our database_client.
     *
     *
     * @param relPath the path parameter, ie. /createnode/
     * @param param1Name the name of the parameter, ie. node
     * @param param1 a passed in json object. ie. {"id:1","type: default_nodes"}
     * @return
     * @throws URISyntaxException
     * @throws IOException
     */
    public String genericPutRequestOneParam(String relPath,String param1Name,String param1) throws URISyntaxException, IOException {

        CloseableHttpClient client = HttpClientBuilder.create().build();

        HttpPut httpPut = new HttpPut(serverString+relPath);

        URI uri = new URIBuilder(httpPut.getURI())
                .addParameter(param1Name, param1)
                .build();
        ((HttpRequestBase) httpPut).setURI(uri);
        CloseableHttpResponse response = client.execute(httpPut);
        HttpEntity resStr = response.getEntity();
        InputStream iS=resStr.getContent();
        String returnString = new String(iS.readAllBytes(), StandardCharsets.UTF_8);
        client.close();
        return returnString;
    }

    /**
     * This method can be used to send a dual parameter put request to our database_client.
     * A query in the format  genericPutRequestTwoParams("/addnodetopage/", "pageid", pageJson, "node", nodeJson) would
     * attempt to instantiate a node on the passed in page.
     *
     * @param relPath the path parameter, ie. /createnode/
     * @param param1Name the name of the first parameter, ie. node
     * @param param1 a passed in json object. ie. {"id:1","type: default_nodes"}
     * @param param2Name the name of the second parameter
     * @param param2 a second passed in json object.
     * @return
     * @throws URISyntaxException
     * @throws IOException
     */
    public String genericPutRequestTwoParams(String relPath, String param1Name, String param1, String param2Name, String param2) throws URISyntaxException, IOException {
        CloseableHttpClient client = HttpClientBuilder.create().build();

        HttpPut httpPut = new HttpPut(serverString+relPath);

        URI uri = new URIBuilder(httpPut.getURI())
                .addParameter(param1Name, param1)
                .addParameter(param2Name,param2)
                .build();
        ((HttpRequestBase) httpPut).setURI(uri);
        CloseableHttpResponse response = client.execute(httpPut);
        HttpEntity resStr = response.getEntity();
        InputStream iS=resStr.getContent();
        String returnString = new String(iS.readAllBytes(), StandardCharsets.UTF_8);
        client.close();
        return returnString;

    }

    public String genericGetRequestOneParam(String relPath, String param1name,String param1) throws URISyntaxException, IOException {
        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet(serverString+ relPath);
        URI uri = new URIBuilder(httpGet.getURI())
                .addParameter(param1name, param1)
                .build();
        ((HttpRequestBase) httpGet).setURI(uri);
        CloseableHttpResponse response = client.execute(httpGet);
        String testStr = response.toString();
        HttpEntity resStr = response.getEntity();
        InputStream iS=resStr.getContent();

        String returnString = new String(iS.readAllBytes(), StandardCharsets.UTF_8);
        client.close();

        return returnString;
    }





}


