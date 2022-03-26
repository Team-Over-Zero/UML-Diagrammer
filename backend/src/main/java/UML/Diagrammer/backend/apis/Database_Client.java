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
         devGetParamInit();
         devGetNode();
         devPostStatusCodeInit();
         devTestCreateNode();
         devGetNodeNameWithId();
         devGetObjectsAsMap();

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


    //Get requests

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

    /**
     * Adds a get request handler that will get an id from a hardcoded default_nodes table.
     */
    void devGetNode(){

        httpServer.get("/getdefaultnode/{objectid}",RequestController::getDefaultNode);
    }

    /**
     * Adds a get request handler that takes a link in the form .../defaultnodenameid/{objectid} and returns the name
     * of the node with that id if found in the database.
     */
    void devGetNodeNameWithId(){
        httpServer.get("/defaultnodenameid/{objectid}",RequestController::getNodeNameWithId);
    }

    void devGetObjectsAsMap(){
        httpServer.get("/getobjectsasmap/",RequestController::getObjsAsMapWithIdandTable);
    }

    //Post Requests

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
     * this will create a test default node with the name being a user defined query parameter in the post request.
     */
    void devTestCreateNode(){
        httpServer.post("/testpostnode/",RequestController::createTestDefaultNodeWithPost);
    }





}
