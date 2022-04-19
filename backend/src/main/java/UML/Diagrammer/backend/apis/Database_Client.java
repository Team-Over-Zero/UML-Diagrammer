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
 * Database_Client.java
 * This class should act as a javalin server that takes post and get requests formatted in a certain way,
 * and is able to parse the request, and edit/send data from the opened activeJDBC connection database.
 * @author Alex
 */


package UML.Diagrammer.backend.apis;

import UML.Diagrammer.backend.objects.NodeFactory.DefaultNode;
import UML.Diagrammer.backend.objects.NodeFactory.NodeFactory;
import com.google.gson.Gson;
import io.javalin.http.Context;
import io.javalin.websocket.WsContext;
import lombok.Getter;
import lombok.Setter;
import org.javalite.activejdbc.Base;
import io.javalin.Javalin;
import UML.Diagrammer.backend.objects.*;
import org.javalite.activejdbc.connection_config.DBConfiguration;

import java.util.List;
import java.util.Map;
@Getter @Setter
public class Database_Client {

    private String databaseURL;
    private String databaseUser;
    private String databasePassword;
    private int javalinPort;
    private Javalin httpServer;
    /**
     * The default Database constructor. Since this is an internal API I am hardcoding our dev server connection in the
     * default constructor.
     * @author Alex
     */
    public Database_Client(){
         databaseURL = "jdbc:mysql://ls-a9db0e6496e5430883b43e690a26b7676cf9d7af.cuirr4jp1g1o.us-west-2.rds.amazonaws.com/dev?useSSL=false";
         databaseUser = "root";
         databasePassword = "TeamOverZero";
         javalinPort = 8888;

        //Base.open("com.mysql.cj.jdbc.Driver", databaseURL, databaseUser, databasePassword);
    }

    /**
     *
     * @param url The mysql url
     * @param userName the mysql user
     * @param password  the mysql password
     */
    public Database_Client(String url, String userName, String password,int port ){
        //initializes ActiveJDBC
        databaseURL = url;
        databaseUser = userName;
        databasePassword = password;
        javalinPort = port;

       // Base.open("com.mysql.cj.jdbc.Driver", databaseURL, databaseUser, databasePassword);
    }

    /**
     * This method initializes activeJDBC and our javalin server.
     */
    public void spinUp(){

        DBConfiguration.loadConfiguration("/database.properties");

       // Base.open();
        httpServer = Javalin.create(config ->
        {config.enableDevLogging();}).start(javalinPort);

         //initializes javalin listeners

        //dev listeners
         devGetParamInit();
         devGetDefaultNode();
         devGetAnyNode();
         devGetAnyEdge();
         devTryCreateNode();
         devTryCreateEdge();
         devUpdateNode();
         devPostStatusCodeInit();
         devTestCreateNode();
         devGetObjectsAsMap();

         //main listeners
        createPage();
        deletePage();
        loadPageElements();
        getPageIdByName();

        createEdgeOnPage();
        deleteEdgeFromPage();
        createNodeOnPage();
        deleteNodeFromPage();
        updateNodeOnPage();

        createUser();
        getUserPages();
        addUserToPage();
        removeUserFromPage();


        //This handler will run before every single request handler. This will ensure
        //that our database
        httpServer.before(ctx -> {
            // calls before("/*", handler)
            DBConfiguration.loadConfiguration("/database.properties");
            Base.open();
        });
        httpServer.after(ctx -> {
            Base.close();
        });

    }

    /**
     * closes the javalin server.
     */
    public void spinDown(){
        httpServer.close();
    }


    //Node Methods
    void devGetDefaultNode(){
        httpServer.get("/getdefaultnode/{objectid}",RequestController::getDefaultNode);
    }
    void devTryCreateNode(){
        httpServer.post("/trycreatenode/",RequestController::tryCreateNode);
    }

    void devUpdateNode(){
        httpServer.get("/updatenode/", RequestController::updateNode);
    }

    /**
     * Adds a get request handler that takes a link in the form .../getnode/?objectid=x&tablename=y and returns the node
     * json string if found.
     */
    void devGetAnyNode(){
        httpServer.get("/getnode/",RequestController::getAnyNode);
    }


    /**
     * this will create a test default node with the name being a user defined query parameter in the post request.
     */
    void devTestCreateNode(){
        httpServer.post("/testpostnode/",RequestController::createTestDefaultNodeWithPost);
    }



    //Pageless Edge Methods

    /**
     * Adds a get request handler that will get an id from a hardcoded default_nodes table.
     */
    void devTryCreateEdge(){httpServer.post("/trycreateedge/",RequestController::tryCreateEdge);}


    void devGetAnyEdge(){httpServer.get("/getedge/",RequestController::getAnyEdge);}




    //Page requests. the "paths" of these represent the paths that must be called by a http client or postman.
    //Note that all of these are post request listeners.
    void createPage(){
        httpServer.post("/createpage/",RequestController::createPage);
    }
    void deletePage(){
        httpServer.post("/deletepage/",RequestController::deletePage);
    }
    void loadPageElements(){
        httpServer.post("/loadpage/",RequestController::loadPageElements);

    }

    void getPageIdByName(){
        httpServer.post("/getpageidbyname/",RequestController::getPageIdByName);

    }

    void createNodeOnPage(){
        httpServer.post("/pagecreatenode/",RequestController::createNodeOnPage);
    }
    void deleteNodeFromPage(){
        httpServer.post("/pageremovenode/",RequestController::removeNodeFromPage);
    }
    void createEdgeOnPage(){ httpServer.post("/pagecreateedge/",RequestController::createEdgeOnPage);}
    void deleteEdgeFromPage(){
        httpServer.post("/pageremoveedge/",RequestController::removeEdgeFromPage);
    }
    void updateNodeOnPage(){httpServer.post("/pageupdatenode/",RequestController::updateNodeOnPage);}

    void addUserToPage(){
        httpServer.post("/addusertopage/",RequestController::addUserToPage);
    }
    void removeUserFromPage(){
        httpServer.post("/removeuserfrompage/",RequestController::removeUserFromPage);
    }

    //User Requests
    void createUser(){
        httpServer.post("/createuser/", RequestController::createUser);
    }
    void getUserPages(){
        httpServer.get("/getuserpages/",RequestController::getUserPages);
    }

    //Misc.

    //DEPRECATED. Use carefully.
    void devGetObjectsAsMap(){
        httpServer.get("/getobjectsasmap/",RequestController::getObjsAsMapWithIdandTable);
    }

    /**
     * Status code test
     */
    void devPostStatusCodeInit(){

        httpServer.post("/status/", ctx -> {
            // some code
            ctx.status(201);
            ctx.result("STATUS 201");
        });
    }
    /**
     * This handler uses lambda expressions, most of the following methods pass off the context duties to RequestController methods
     * This will take a url get request in the form .../paramtest/foo and return the result "foo" to the requester, with a status code for good measure.
     */
    void devGetParamInit(){
        //context.result(context.pathParam("name"));
        httpServer.get("/paramtest/{name}", ctx ->
        { // the {} syntax does not allow slashes ('/') as part of the parameter
            ctx.result("Param Value is : " + ctx.pathParam("name")); //returns the value back to the client
        });
        //retString = "Hello: "+ctx.pathParam("name");

    }

}
