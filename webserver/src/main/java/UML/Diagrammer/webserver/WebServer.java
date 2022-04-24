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
 * Client.java
 *
 * Serves as the Javalin http server for Vue.js to run on. This class should be able to send strings and Pages back
 * and forth between the database, and also be able to send strings to Vue for processing and process strings that
 * Vue sends it. (According to my understanding, this will serve as an intermediary between the Database and Vue) -Alex
 * @author Michael
 */
package UML.Diagrammer.webserver;
import UML.Diagrammer.backend.apis.Database_Client;
import UML.Diagrammer.backend.apis.HTTP_Client;
import io.javalin.Javalin;
import io.javalin.plugin.rendering.vue.VueComponent;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.connection_config.DBConfiguration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;


public class WebServer {

    Javalin client;
    HTTP_Client http_client;

    public WebServer(){
        init();
    }

    private void init(){

        http_client = new HTTP_Client();
        Database_Client db = new Database_Client();
        System.out.println("DB CLIENT SPINNING UP");
        db.spinUp();

        //String testNode ="{\"description\":\"DEFAULT DESCRIPTION\",\"height\":3,\"id\":1,\"name\":\"GET SHREKED\",\"svg_image\":\"DEFAULT IMAGE\",\"type\":\"default_nodes\",\"width\":3,\"x_coord\":0,\"y_coord\":0}";
        try {
            String response1 = http_client.exampleGetRequest();
           // System.out.println("example get response: "+response1);
           //String response2 = http_client.sendNodeUpdateRequest(testNode);
            //System.out.println("Response: "+response2);


        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("web client started");
        client = Javalin.create(config ->
        {config.enableWebjars();}).start(7777);

        client.get("/", new VueComponent("uml-editor"));

        client.get("/svg/{file}", ctx -> {

            Path filePath = Paths.get("src/main/resources/Images/" + ctx.pathParam("file"));
            Path absPath = filePath.toAbsolutePath();
            String svgString = Files.readString(absPath);

            ctx.result(svgString);
        });

        client.get("/createNode/{node}/{page}", ctx -> {

            String node = ctx.pathParam("node");
            String page = ctx.pathParam("page");
            String response = http_client.sendAddNodeToPage(node, page);
            ctx.result(response);

        });

        client.get("/updateNode/{node}", ctx -> {
            String node = ctx.pathParam("node");
            String response = http_client.sendNodeUpdateRequest(node);
            ctx.result(response);
        });

        client.get("/deleteNode/{node}/{page}", ctx -> {
            String node = ctx.pathParam("node");
            String page = ctx.pathParam("page");
            String response = http_client.sendRemoveNodeFromPage(node, page);
            ctx.result(response);
        });

        client.get("/createPage/{pagejson}", ctx -> {
            String pageJson = ctx.pathParam("pagejson");
            String response = http_client.sendCreatePage(pageJson); //ALEX NOTE. sendCreatePage was poorly written, use pageCreateRequest instead
            System.out.println(response);
            ctx.result(response);
        });



        client.get("/loadPage/{id}", ctx -> {
           String id = ctx.pathParam("id");
           ctx.result(http_client.sendLoadPage(id));
        });

        client.get("/loginUser/{user}", ctx -> {
            String user = ctx.pathParam("user");
            ctx.result(http_client.sendLoginUser(user));
        });

        client.get("/getUserPages/{user}", ctx -> {
            String user = ctx.pathParam("user");
            ctx.result(http_client.getUserPages(user));
        });

    }

    private String sendNodeCreateRequest(String node){
        try{
            return http_client.sendNodeCreateRequest(node);
        }catch(Exception e){
            return "";
        }

    }

    public void close(){
        //Base.close();
        client.close();
    }

}
