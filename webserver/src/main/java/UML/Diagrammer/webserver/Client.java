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
