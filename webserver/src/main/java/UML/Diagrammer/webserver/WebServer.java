/**
 * Client.java
 *
 * Serves as the Javalin http server for Vue.js to run on. This class should be able to send strings and Pages back
 * and forth between the database, and also be able to send strings to Vue for processing and process strings that
 * Vue sends it. (According to my understanding, this will serve as an intermediary between the Database and Vue) -Alex
 * @author Michael
 */
package UML.Diagrammer.webserver;
import UML.Diagrammer.backend.objects.HTTP_Client;
import io.javalin.Javalin;
import io.javalin.plugin.rendering.vue.VueComponent;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.connection_config.DBConfiguration;


public class WebServer {

    Javalin client;
    HTTP_Client http_client;

    public WebServer(){
        init();
    }

    private void init(){

        String databaseURL = "jdbc:mysql://ls-a9db0e6496e5430883b43e690a26b7676cf9d7af.cuirr4jp1g1o.us-west-2.rds.amazonaws.com/test";
        String databaseUser = "root";
        String databasePassword = "TeamOverZero";
        //Base.open("com.mysql.cj.jdbc.Driver", databaseURL, databaseUser, databasePassword);
        DBConfiguration.loadConfiguration("/database.properties");
        Base.open();
        http_client = new HTTP_Client();

        System.out.println("web client started");
        client = Javalin.create(config ->
        {config.enableWebjars();}).start(7777);

        client.get("/testGetRequest", ctx -> {
            ctx.result(http_client.exampleGetRequest());
        });
        client.get("/", new VueComponent("uml-editor"));
    }

    public void close(){
        Base.close();
        client.close();
    }

}
