import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import UML.Diagrammer.backend.apis.Database_Client;
import UML.Diagrammer.backend.apis.HTTP_Client;
import UML.Diagrammer.backend.apis.RequestController;
import UML.Diagrammer.backend.objects.EdgeFactory.EdgeFactory;
import UML.Diagrammer.backend.objects.EdgeFactory.NormalEdge;
import UML.Diagrammer.backend.objects.NodeFactory.*;
import UML.Diagrammer.backend.objects.Page;
import UML.Diagrammer.backend.objects.User;
import io.javalin.http.Handler;
import org.javalite.activejdbc.test.DBSpec;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.io.IOException;
import java.net.URISyntaxException;

public class RequestControllerTest extends DBSpec {
    private EdgeFactory factory;
    private NodeFactory nodeFactory;
    private HTTP_Client http_client;
    private Database_Client dbClient;
    private Database_Client db;
    private RequestController req;

    @BeforeEach
    public void setUp(){
        factory = new EdgeFactory();
        nodeFactory = new NodeFactory();
        http_client = new HTTP_Client();
        String jUnitUrl = "jdbc:mysql://ls-a9db0e6496e5430883b43e690a26b7676cf9d7af.cuirr4jp1g1o.us-west-2.rds.amazonaws.com/junit?useSSL=false";
        String databaseUser = "root";
        String databasePassword = "TeamOverZero";
        int javalinPort = 8888;
        dbClient = new Database_Client(jUnitUrl, databaseUser, databasePassword, javalinPort);
        //dbClient.spinUp();
    }

    @Test
    public void testRealData(){
        DefaultNode node = nodeFactory.buildNode();
        node.set("id","test");
        Page page = new Page();
        page.set("name","1234");

        User user = new User();
        user.set("name","test");
        user.set("id",999);
        user.saveIt();
        String userJ = http_client.sendCreateUser(user.toJson(true));
        http_client.sendCreatePage(page.toJson(true));
        http_client.sendAddUserToPage(user.toJson(true),page.toJson(true));
        http_client.sendAddNodeToPage(node.toJson(true),page.toJson(true));
        ClassNode cn = nodeFactory.buildNode("classnodes",0,0,30,40);
        cn.set("id",999);

        FolderNode fn = nodeFactory.buildNode("foldernodes",40,40,30,40);

        fn.set("id",998);
        http_client.sendAddNodeToPage(cn.toJson(true),page.toJson(true));

        http_client.sendAddNodeToPage(fn.toJson(true),page.toJson(true));

        NormalEdge edge = factory.buildEdge("normaledges",999,"classnodes",998,"foldernodes");

        TextBoxNode tn = nodeFactory.buildNode("textboxnodes",40,2,100,90);
        tn.set("name", "name");
        tn.set("description", "ark barky ardvark bark");

        http_client.sendAddNodeToPage(tn.toJson(true),page.toJson(true));

        try {
            http_client.sendNodeCreateRequest(node.toJson(true));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            http_client.sendEdgeCreateRequest(edge.toJson(true));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        try {
            http_client.sendEdgeCreateRequest("");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        http_client.tryGetPage(page.toJson(true));

        http_client.sendLoadPage(page.toJson(true));
        http_client.sendGetPageIdByName("1234");


        try {
            http_client.getUserPages(user.toJson(true));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        node.setCoords(90,90);
        try {
            http_client.sendNodeUpdateRequest(node.toJson(true));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        String s = "hellworld";
        http_client.sendAddNodeToPage(s,page.toJson(true));


        //delete instantiated user for cleanup
        http_client.sendDeleteUser(userJ);

        http_client.sendCreateUser(null);
        http_client.sendRemoveNodeFromPage(node.toJson(true),page.toJson(true));

        http_client.sendDeleteUser(user.toJson(true));

       http_client.sendDeletePage(page.toJson(true));


        //NormalEdge edge = factory.buildEdge();



    }



}
