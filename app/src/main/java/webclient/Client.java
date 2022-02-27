package webclient;

import io.javalin.Javalin;
import io.javalin.plugin.rendering.vue.VueComponent;

public class Client {

    Javalin client;

    public Client(){
        init();
    }

    private void init(){

        System.out.println("web client started");
        client = Javalin.create().start(7777);

        //client.get("/", ctx -> ctx.result("Hello World"));

        client.get("/", new VueComponent("hello-world"));
    }

    public void close(){
        client.close();
    }

}
