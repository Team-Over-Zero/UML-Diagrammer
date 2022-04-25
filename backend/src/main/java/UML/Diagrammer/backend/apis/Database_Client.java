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
import org.javalite.activejdbc.DB;
import org.javalite.activejdbc.connection_config.DBConfiguration;

import java.util.List;
import java.util.Map;
@Getter @Setter
/**
 * This class serves as an intermediary between a mysql database and our HTTP_Client class. Even though this class
 * upon first glance seems to be a server, as it spins up a javalin server object and then initializes listeners, we
 * are semantically noting it as a client, see: "Database_Client". This is because logically what is happening is that
 * this processes HTTP request, then queries data from an SQL server, then processes that data and returns data to
 * frontend applications. So while from the perspective of a frontend application it is a server, from the perspective
 * of this class, it is a client.
 *
 * This class works in tandem with HTTP_Client to implement a loose version of the command pattern. Note that
 * most of the actual logic of this class happens in RequestController, which is a method class.
 * @author Alex Diviney
 */
public class Database_Client {

    private String databaseURL;
    private String databaseUser;
    private String databasePassword;
    private int javalinPort;
    private Javalin httpServer;
    /**
     * The default Database constructor. Since this is an internal API I am hardcoding our dev server connection in the
     * default constructor.
     *
     */
    public Database_Client(){
         databaseURL = "jdbc:mysql://ls-a9db0e6496e5430883b43e690a26b7676cf9d7af.cuirr4jp1g1o.us-west-2.rds.amazonaws.com/dev?useSSL=false";
         databaseUser = "root";
         databasePassword = "TeamOverZero";
         javalinPort = 8888;

        //Base.open("com.mysql.cj.jdbc.Driver", databaseURL, databaseUser, databasePassword);
    }

    /**
     * Custom Constructor that allows this client to connect to an arbitrary mysql server.
     * Note that we are using this constructor internally to specify the schema that tests should perform queries on
     *
     * @param url The mysql url
     * @param userName the mysql user
     * @param password  the mysql password
     *
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
     * Initializes activeJDBC and our javalin server.
     */
    public void spinUp(){
        httpServer = Javalin.create(config ->
        {config.enableDevLogging();}).start(javalinPort);

        //spins up listeners.
        initalizeListeners();
        //This handler will run before every single request handler. This will ensure
        //that our database
        httpServer.before(ctx -> {
            // calls before("/*", handler)
            Base.open("com.mysql.cj.jdbc.Driver",databaseURL,databaseUser,databasePassword);

        });
        httpServer.after(ctx -> {
            Base.close();
        });

    }

    /**
     * Spins up the query listeners.
     */
    private void initalizeListeners(){
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

        //page listeners
        createPage();
        deletePage();
        loadPageElements();
        getPageIdByName();

        //page object listeners
        createEdgeOnPage();
        deleteEdgeFromPage();
        createNodeOnPage();
        deleteNodeFromPage();
        updateNodeOnPage();

        //user listeners
        createUser();
        deleteUser();
        loginUser();
        findUserByName();
        getUserPages();
        addUserToPage();
        removeUserFromPage();
    }

    /**
     * closes the javalin server.
     */
    public void spinDown(){
        httpServer.close();
    }


    //Node Methods. Only used for internal testing, NOT by our frontend applications.

    /**
     * Adds a get request handler to "/getdefaultnode/{objectid}, where objectid is a plain string of (not json represntation of) a default node id"
     */
    public void devGetDefaultNode(){
        httpServer.get("/getdefaultnode/{objectid}",RequestController::getDefaultNode);
    }

    /**
     * Adds a post request handler to /trycreatenode/. Requires query parameter of the name "node" and a json'd
     * node value. The id of the passed in json node will from the perspective of the user get overridden and a jsoned node
     * with this new value and the passed in attributes will get returned.
     */
    void devTryCreateNode(){
        httpServer.post("/trycreatenode/",RequestController::tryCreateNode);
    }

    /**
     *  Adds a get request handler to /updatenode/.
     *  Takes queries in the form /updatenode/?node={}
     *  Given a query param of the form node = "json", attempts to update an existing node with all the attributes in the passed in node.
     *
     */
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
     * Adds a get request handler to /testpostnode/
     * this will create a test default node with the name being a user defined query parameter in the post request.
     */
    void devTestCreateNode(){
        httpServer.post("/testpostnode/",RequestController::createTestDefaultNodeWithPost);
    }



    //Pageless Edge Methods. Only used for internal testing.

    /**
     * Adds a get request handler to /trycreateedge/ that will get an id from a hardcoded default_nodes table.
     */
    void devTryCreateEdge(){httpServer.post("/trycreateedge/",RequestController::tryCreateEdge);}


    /**
     * Adds a get request handler to /getedge/ that will attempt to find and return an edge of any type given two params:
     * objectid: id of the edge
     * objecttype: type of object.
     */
    void devGetAnyEdge(){httpServer.get("/getedge/",RequestController::getAnyEdge);}

    //DEPRECATED. Use carefully. Gets a map of an object.
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

    //Page requests. the "paths" of these represent the paths that must be called by a http client or postman.
   //Most, but not all non "get" prefixed methods are post requests. (loadPageElements is a get request)

    /**
     * Adds a post request handler to /createpage/ that will attempt to create a page given the following query parameters:
     * "page", where "page" is a jsoned representation of a created page (with no id or a dummy id.)
     * "userid", where "user" is a jsoned representation of a user id. ex {id:\"10\"}
     * On success, returns a page object with a database id, indicating that the page was created and associated with the user.
     */
    void createPage(){
        httpServer.post("/createpage/",RequestController::createPage);
    }

    /**
     * Adds a post request handler to /deletepage/ that will attempt to delete a page given the following query parameter:
     * "pageid", where "page" is a jsoned representation of a page id. ex {id:\"10\"}
     * On success, returns "SUCCESS"
     */
    void deletePage(){
        httpServer.post("/deletepage/",RequestController::deletePage);
    }

    /**
     * Adds a get request handler to /loadpage/ that will attempt to return all of the elements of a given page in a
     * 2d json list where position 0 of the outer array is a list of nodes and position 1 is a list of edges. Both are (due to stringification) type agnostic but not typeless.
     * Takes the following query parameter:
     * name: "pageid" where "pageid" has the value of a jsoned id object, ex. {id:\"10\"}
     */
    void loadPageElements(){
        httpServer.get("/loadpage/",RequestController::loadPageElements);

    }

    /**
     *
     * Adds a get request handler to /getpageidbyname/
     * Gets the id of a page given the passed in query parameter:
     * "pagename" where pagename is a json string in the form {name:\"foobar\"}"
     * This is a lazy method that was added last minute and never used, as soon as two pages of the same name are created
     * on a database, becomes useless.
     */
    void getPageIdByName(){httpServer.get("/getpageidbyname/",RequestController::getPageIdByName);}

    /**
     * Attaches a post request handler to /pagecreatenode/
     * Creates a node on a page given the following parameters:
     * "pageid" where pageid is in the format {id:\"10\"}
     * "node" where node is a node is a client defined json string with an id that will be overriden
     * Returns a node with set id and page_id attributes.
     */
    void createNodeOnPage(){
        httpServer.post("/pagecreatenode/",RequestController::createNodeOnPage);
    }

    /**
     * Attaches a post request handler to /pageremovenode/
     * Deletes a node on a page given two parameters:
     * "pageid" where pageid is in the format {id:\"10\"}
     * "node" where node is a json'd node with a valid id.
     */
    void deleteNodeFromPage(){
        httpServer.post("/pageremovenode/",RequestController::removeNodeFromPage);
    }

    /**
     * Attaches a post request handler to /pagecreateedge/
     * Creates an edge on a page given two parameters:
     * "pageid" where pageid is in the format {id:\"10\"}
     * "edge" where edge is a json'd edge with an id that will be overriden
     */
    void createEdgeOnPage(){ httpServer.post("/pagecreateedge/",RequestController::createEdgeOnPage);}

    /**
     * Attaches a post request handler to /pageremoveedge/
     * Deletes an edge given two parameters:
     * "pageid" where pageid is in the format {id:\"10\"}
     * "edge" where edge is a json'd edgge with a valid id.
     */
    void deleteEdgeFromPage(){
        httpServer.post("/pageremoveedge/",RequestController::removeEdgeFromPage);
    }
    /**
    * Attaches a handler to /pageupdatenode/
     * Given the query params pageid and node where pageid
     * is a jsoned page id and node is a passed in already created node, updates the node in the database
     * to match all values contained in the passed in node. Note that if you pass in a node that
     * does not exist, will return "GENERIC EXCEPTION
     */
    void updateNodeOnPage(){httpServer.post("/pageupdatenode/",RequestController::updateNodeOnPage);}

    /**
     * Attaches a handler to /addusertopage/
     * Adds a passed in user json to a passed in pageid in json format.
     * Takes the following query parameters:
     * "pageid" where "pageid" is jsoned page with an id attribute
     * "user" where user is a passed in user with an id attribute.
     */
    void addUserToPage(){
        httpServer.post("/addusertopage/",RequestController::addUserToPage);
    }

    /**
     * Attaches to the /removeuserfrompage/ post request.
     * removes a user from a page. does not delete the user since the user to page relationship is many to many.
     * Takes the following query parameters:
     * "user" where user is a json with an id
     * "pageid" where pageid is a json with an id
     */
    void removeUserFromPage(){
        httpServer.post("/removeuserfrompage/",RequestController::removeUserFromPage);
    }

    //User Requests

    /**
     * Attaches a handler to /createuser/
     * Attempts to create a User in the backend. Takes the following query parameters:
     * "user" where user has attributes and an id that will be overridden.
     * Returns the user on success.
     * */
    void createUser(){
        httpServer.post("/createuser/", RequestController::createUser);
    }

    /**
     * Attaches a handler to /deleteuser/
     * Attempts to delete a User in the backend. Takes the following query parameters:
     * "user" where user is a json string of a user.
     * Returns "SUCCESS" on success.
     */
    void deleteUser(){httpServer.post("/deleteuser",RequestController::deleteUser);}

    /**
     * Attaches a handler to /getuserpages/
     * Attempts to get a list of names of pages that is associated with a user
     * Takes The following query parameters:
     * "userid" where userid is an object with an id attribute.
     * Returns a json list of strings where every string is the name of a page associated with a user.
     */
    void getUserPages(){
        httpServer.get("/getuserpages/",RequestController::getUserPages);
    }

    /**
     * Attaches a handler to /loginuser/
     * Validates that a username and password are associated with a specific user object.
     * Takes the following query parameters:
     * "user" where user is a json'd user object with a name and password attribute (but no id)
     * Returns a user json if successful, otherwise sends back "INVALID CREDENTIALS"
     */
    void loginUser(){httpServer.post("/loginuser/",RequestController::loginUser);}

    /**
     * Attaches a handler to /finduserbyname/
     * Attempts to find a user by only their username.
     * Takes the following query parameters:
     * "username" where username is in json format, ex. {name:\"foo\"}
     */
    void findUserByName(){httpServer.post("/finduserbyname/",RequestController::findUserByName);}


}
