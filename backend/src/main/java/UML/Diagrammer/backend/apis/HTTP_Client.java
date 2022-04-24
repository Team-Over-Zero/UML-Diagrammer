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
 * @Author Alex Diviney
 */
package UML.Diagrammer.backend.apis;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.TrustAllStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Flow;

public class HTTP_Client {
    String address;
    String port;
    String serverString;

    public HTTP_Client() {
        address = "127.0.0.1";
        port = "8888";
        serverString = String.format("http://%s:%s",address,port); // Changed to http to stop a javax.net.ssl.SSLException: Unsupported or unrecognized SSL message error

    }

    public HTTP_Client(String addr, String p) {
        address = addr;
        port = p;
        serverString = String.format("https://%s:%s", address, port);
        //serverString = serverString;
    }

    /**
     * This method sends a simple get request to an API site (jsonplaceholder.typicode.com). Should always work.
     *
     * @return A string represenation of the jsonplaceholder webpage
     * @throws IOException          Wrap with try catch for IOexceptions
     * @throws InterruptedException Wrap with try catch for InterruptedException
     */
    public String exampleGetRequest() throws IOException, InterruptedException {
        String tempServerString = "https://jsonplaceholder.typicode.com/posts"; //no port for some reason
        // System.out.println(serverString);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .header("accept", "application/json")
                .uri(URI.create(tempServerString))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    /**
     * This executes a put command. Request is sent to a sie called req.com
     *
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public String examplePutRequest() throws IOException, InterruptedException {
        String tempServerString = "https://reqbin.com/echo/put/json"; //no port for some reason
        // System.out.println(serverString);
        HttpClient client = HttpClient.newHttpClient();
        String data = "{\n  \"Id\": 12345,\n  \"Customer\": \"John Smith\",\n  \"Quantity\": 1,\n  \"Price\": 10.00\n}";
        HttpRequest.BodyPublisher testPub = HttpRequest.BodyPublishers.ofString(data);

        HttpRequest request = HttpRequest.newBuilder()
                .PUT(testPub)
                .header("accept", "application/json")
                .uri(URI.create(tempServerString))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return response.body();

    }

    /**
     * This executes a sample post request. Request is sent to a site called reqbin.com
     *
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public String examplePostRequest() throws IOException, InterruptedException {
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
     * @param pageName name of UML page the user is requesting to edit
     * @return
     * @implNote NOT IMPLEMENTED
     * Simple get Request that asks for a gson of a page object given a pagename.
     */
    public String tryGetPage(String pageName) {
        return "";
    }

    /**
     * Should return a list of all the pages associated with a passed in userid json object
     *
     * @return
     */
    public String getUserPages(String userId) throws URISyntaxException, IOException {
        String returnString = genericGetRequestOneParam("/getuserpages/", "userid", userId);
        return returnString;

    }

    public String pageCreateRequest(String pageJson, String userId) throws URISyntaxException, IOException {
        String returnString = genericPostRequestTwoParams("/createpage/", "page", pageJson, "userid", userId);
        return returnString;
    }



    //Dev Node Requests

    /**
     * @param nodeJson json node with no id
     * @return A string with the new node Id
     * @throws URISyntaxException thrown if input is not json
     * @throws IOException        genericIO exception
     * @deprecated Works for creating pageless nodes but use sendAddNodeToPage instead
     */
    public String sendNodeCreateRequest(String nodeJson) throws URISyntaxException, IOException {
        String returnString = genericPostRequestOneParam("/trycreatenode/", "node", nodeJson);
        return returnString;
    }

    /**
     * * Sends a passed in "updated node", and returns if it was successfully updated in the backend or note.
     *
     * @param nodeJson Json node with id
     * @return
     * @throws IOException
     * @throws InterruptedException
     * @throws URISyntaxException
     */
    public String sendNodeUpdateRequest(String nodeJson) throws IOException, InterruptedException, URISyntaxException {
        String returnString = genericGetRequestOneParam("/updatenode/", "node", nodeJson);
        return returnString;
    }

    //Dev Edge requests.
    public String sendEdgeCreateRequest(String edgeJson) throws IOException, URISyntaxException {
        String paramName = "edge";
        String returnString = genericPostRequestOneParam(("/trycreateedge/"), paramName, edgeJson);
        return returnString;
    }

    //Page Requests


    public String sendCreatePage(String pageJson) {
        String returnString = "";
        try {
            returnString = genericPostRequestOneParam("/createpage/", "page", pageJson);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnString;
    }

    /**
     * Deletes Page
     *
     * @param pageJson
     * @return
     */
    public String sendDeletePage(String pageJson) {
        String returnString = "";
        try {
            returnString = genericPostRequestOneParam("/deletepage/", "page", pageJson);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

    public String sendLoadPage(String pageIdJson){
        String returnString = "";
        try {
            returnString = genericGetRequestOneParam("/loadpage/", "pageid", pageIdJson);

        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return returnString;
    }


    /**
     * Gets a page id (json form)
     * @param pageName Page Json object with a name attribute
     * @return A page object with an ID.
     */
    public String sendGetPageIdByName(String pageName) {
        String returnString = "";
        try {
            returnString = genericGetRequestOneParam("/getpageidbyname/","pagename",pageName);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnString;
    }

    public String sendAddNodeToPage(String nodeJson, String pageIdJson) {
        String returnString = "";
        try {
            returnString = genericPostRequestTwoParams("/pagecreatenode/", "node", nodeJson, "pageid", pageIdJson);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnString;
    }

    public String sendRemoveNodeFromPage(String nodeJson, String pageIdJson) {
        String returnString = "";

        try {
            returnString = genericPostRequestTwoParams("/pageremovenode/", "node", nodeJson, "pageid", pageIdJson);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnString;
    }

    public String sendAddEdgeToPage(String edgeJson, String pageIdJson) {
        String returnString = "";

        try {
            returnString = genericPostRequestTwoParams("/pagecreateedge/", "edge", edgeJson, "pageid", pageIdJson);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnString;
    }

    public String sendRemoveEdgeFromPage(String edgeJson, String pageIdJson) {
        String returnString = "";
        try {
            returnString = genericPostRequestTwoParams("/pageremoveedge/", "edge", edgeJson, "pageid", pageIdJson);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnString;
    }

    public String sendAddUserToPage(String userJson, String pageIdJson) {
        String returnString = "";

        try {
            returnString = genericPostRequestTwoParams("/addusertopage/", "user", userJson, "pageid", pageIdJson);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnString;
    }

    /**
     * @param userJson   user object (UserUI)
     * @param pageIdJson json representation of an id
     * @return Server context.result() string.
     */
    public String sendRemoveUserFromPage(String userJson, String pageIdJson) {
        String returnString = "";
        try {
            returnString = genericPostRequestTwoParams("/removenodefrompage/", "user", userJson, "pageid", pageIdJson);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnString;
    }

    //User Requests
    public String sendCreateUser(String userJson) {
        String returnString = "";
        try {
            returnString = genericPostRequestOneParam("/createuser/", "user", userJson);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnString;
    }

    /**
     * Attempts to login a user (return a user string with an id
     * @param userJson
     * @return
     */
    public String sendLoginUser(String userJson){
        String returnString = "";
        try {
            returnString = genericPostRequestOneParam("/loginuser/", "user", userJson);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnString;
    }


    /**
     *
     * @param nameJson Given in a passed in user name in json format, attempts to find a user, returns:
     *                 "ERROR: USER NOT FOUND" if no user with that name exists.
     * @return
     */
    public String sendFindUserByName(String nameJson){

        String returnString = "";
        try {
            returnString = genericPostRequestOneParam("/finduserbyname/", "username", nameJson);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnString;

    }
    /**
     * Deletes a passed in user and all associated data. Use carefully.
     *
     * @param userJson
     * @return
     */
    public String sendDeleteUser(String userJson) {
        String returnString = "";
        try {
            returnString = genericPostRequestOneParam("/deleteuser/", "user", userJson);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnString;
    }






    /**
     * This method can be used to send a single parameter put request to our database_client.
     *
     * @param relPath    the path parameter, ie. /createnode/
     * @param param1Name the name of the parameter, ie. node
     * @param param1     a passed in json object. ie. {"id:1","type: default_nodes"}
     * @return
     * @throws URISyntaxException
     * @throws IOException
     */
    public String genericPostRequestOneParam(String relPath, String param1Name, String param1) throws URISyntaxException, IOException {

        CloseableHttpClient client = HttpClientBuilder.create().build();

        HttpPost httpPost = new HttpPost(serverString + relPath);

        URI uri = new URIBuilder(httpPost.getURI())
                .addParameter(param1Name, param1)
                .build();

        System.out.println(uri.getQuery());
        System.out.println(uri.toString());
        ((HttpRequestBase) httpPost).setURI(uri);
        CloseableHttpResponse response = client.execute(httpPost);
        HttpEntity resStr = response.getEntity();
        InputStream iS = resStr.getContent();
        String returnString = new String(iS.readAllBytes(), StandardCharsets.UTF_8);
        client.close();
        return returnString;
    }

    /**
     * This method can be used to send a dual parameter put request to our database_client.
     * A query in the format  genericPutRequestTwoParams("/addnodetopage/", "pageid", pageJson, "node", nodeJson) would
     * attempt to instantiate a node on the passed in page.
     *
     * @param relPath    the path parameter, ie. /createnode/
     * @param param1Name the name of the first parameter, ie. node
     * @param param1     a passed in json object. ie. {"id:1","type: default_nodes"}
     * @param param2Name the name of the second parameter
     * @param param2     a second passed in json object.
     * @return
     * @throws URISyntaxException
     * @throws IOException
     */
    public String genericPostRequestTwoParams(String relPath, String param1Name, String param1, String param2Name, String param2) throws URISyntaxException, IOException {
        CloseableHttpClient client = HttpClients
                .custom()
                .build();

        HttpPost httpPost = new HttpPost(serverString + relPath);

        URI uri = new URIBuilder(httpPost.getURI())
                .addParameter(param1Name, param1)
                .addParameter(param2Name, param2)
                .build();
        ((HttpRequestBase) httpPost).setURI(uri);
        CloseableHttpResponse response = client.execute(httpPost);
        HttpEntity resStr = response.getEntity();
        InputStream iS = resStr.getContent();
        String returnString = new String(iS.readAllBytes(), StandardCharsets.UTF_8);
        client.close();
        return returnString;
    }

    /**
     * @param relPath    the path parameter, ie. /getpage/
     * @param param1Name the name of the first parameter, ie. page
     * @param param1     a passed in json object. ie. {"id:1","name:My First Page"}
     * @return A string with the server response body
     * @throws URISyntaxException
     * @throws IOException
     */
    public String genericGetRequestOneParam(String relPath, String param1Name, String param1) throws URISyntaxException, IOException {


        CloseableHttpClient client = HttpClients
                .custom()
                .build();
        HttpGet httpGet = new HttpGet(serverString + relPath);
        URI uri = new URIBuilder(httpGet.getURI())
                .addParameter(param1Name, param1)
                .build();
        ((HttpRequestBase) httpGet).setURI(uri);
        CloseableHttpResponse response = client.execute(httpGet);
        String testStr = response.toString();
        HttpEntity resStr = response.getEntity();
        InputStream iS = resStr.getContent();

        String returnString = new String(iS.readAllBytes(), StandardCharsets.UTF_8);
        client.close();
        return returnString;

    }

    public String genericGetRequestTwoParams(String relPath, String param1Name, String param1, String param2Name, String param2) throws URISyntaxException, IOException {
        CloseableHttpClient client = HttpClients
                .custom()
                .build();

        HttpGet httpGet = new HttpGet(serverString + relPath);

        URI uri = new URIBuilder(httpGet.getURI())
                .addParameter(param1Name, param1)
                .addParameter(param2Name, param2)
                .build();
        ((HttpRequestBase) httpGet).setURI(uri);
        CloseableHttpResponse response = client.execute(httpGet);
        HttpEntity resStr = response.getEntity();
        InputStream iS = resStr.getContent();
        String returnString = new String(iS.readAllBytes(), StandardCharsets.UTF_8);
        client.close();
        return returnString;
    }

}


