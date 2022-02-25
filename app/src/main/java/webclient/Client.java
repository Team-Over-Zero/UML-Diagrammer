package webclient;

import io.javalin.Javalin;

public class Client {

    Javalin client;

    public Client(){
        init();
    }

    private void init(){

        System.out.println("web client started");
        client = Javalin.create().start(7777);

        client.get("/", ctx -> ctx.result("Hello World"));
    }

    public void close(){
        client.close();
    }

}
