/**
 * Client.java
 *
 * Serves as the Javalin http server for Vue.js to run on. This class should be able to send strings and Pages back
 * and forth between the database, and also be able to send strings to Vue for processing and process strings that
 * Vue sends it. (According to my understanding, this will serve as an intermediary between the Database and Vue) -Alex
 * @author Michael
 */
package UML.Diagrammer.webserver;
import io.javalin.Javalin;
import io.javalin.plugin.rendering.vue.VueComponent;

public class Client {

    Javalin client;

    public Client(){
        init();
    }

    private void init(){

        System.out.println("web client started");
        client = Javalin.create(config ->
        {config.enableWebjars();}).start(7777);


        client.get("/", new VueComponent("uml-editor"));
    }

    public void close(){
        client.close();
    }

}
