import UML.Diagrammer.backend.apis.Database_Client;
import UML.Diagrammer.backend.apis.HTTP_Client;
import UML.Diagrammer.backend.objects.EdgeFactory.EdgeFactory;
import UML.Diagrammer.backend.objects.EdgeFactory.NormalEdge;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.javalite.activejdbc.test.DBSpec;

import UML.Diagrammer.backend.objects.*;
import UML.Diagrammer.backend.objects.NodeFactory.*;
import org.javalite.activejdbc.DB;
import org.javalite.activejdbc.InitException;
import org.javalite.activejdbc.test.DBSpec;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.DB;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * For most tests, C1 is that you have given a valid choice of object
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class Database_ClientTest extends DBSpec {
    private EdgeFactory factory;
    private NodeFactory nodeFactory;
    private HTTP_Client http_client;
    private Database_Client dbClient;


    @BeforeAll
    public void setUp() {
        factory = new EdgeFactory();
        nodeFactory = new NodeFactory();
        http_client = new HTTP_Client();
        String jUnitUrl = "jdbc:mysql://ls-a9db0e6496e5430883b43e690a26b7676cf9d7af.cuirr4jp1g1o.us-west-2.rds.amazonaws.com/junit?useSSL=false";
        String databaseUser = "root";
        String databasePassword = "TeamOverZero";
        int javalinPort = 8888;
        dbClient = new Database_Client(jUnitUrl, databaseUser, databasePassword, javalinPort);
        dbClient.spinUp();

    }

    @Test

    public void testCreateUserC1True() throws IOException, URISyntaxException, InterruptedException {

        User user = new User();
        String userJ = user.toJson(true);
        String s = "";
        try {
            http_client.sendCreateUser(userJ);
            s = "SUCCESS";


        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals("SUCCESS", s);

    }
    @Test
    public void testCreateUserC1False() throws IOException, URISyntaxException, InterruptedException {

        DefaultNode node = nodeFactory.buildNode();
        String userJ = node.toJson(true);

        try {
           http_client.sendCreateUser(userJ);


        } catch (Exception e){

           assertEquals(IllegalArgumentException.class,e.getClass());
        }





    }

    @Test
    public void testCreateUserC1Null() throws IOException, URISyntaxException, InterruptedException {

        String s = null;

        try {
            http_client.sendCreateUser(s);


        } catch (Exception e){


        }





    }

    @Test
    public void testCreatePageC1True() {
        Page page = new Page();
        String pageJ = page.toJson(true);
        String s = "";
        try

        {
            http_client.sendCreatePage(pageJ);
            s = "SUCCESS";
        } catch(
                Exception e)
        {
            e.printStackTrace();
        }
        assertEquals("SUCCESS",s);
    }

    @Test
    public void testCreateC1False() {
        User page = new User();
        String pageJ = page.toJson(true);

        try

        {
            http_client.sendCreatePage(pageJ);

        } catch(
                Exception e)
        {
           assertEquals(IllegalArgumentException.class,e.getClass());
        }

    }

    @Test
    public void testCreateC1Null() {
        String s = null;

        try

        {
            http_client.sendCreatePage(s);

        } catch(
                Exception e)
        {
            //assertEquals(IllegalArgumentException.class,e.getClass());
        }

    }

    @Test
    public void testDeletePageC1True(){
        Page page = new Page();
        String pageJ = page.toJson(true);
        try

        {
            http_client.sendCreatePage(pageJ);

        } catch(
                Exception e)
        {
            e.printStackTrace();
        }
        boolean success = false;
        try

        {
            http_client.sendDeletePage(pageJ);
            success = true;

        } catch(
                Exception e)
        {
            e.printStackTrace();
        }
        assertTrue(success);
    }

    @Test
    public void testDeletePageC1False(){
        String shouldntWork = "This should throw an exeption";
        Page page = new Page("test");
        String pageJ = page.toJson(true);

        try

        {
            http_client.sendCreatePage(pageJ);
            //assert that an exception is thrown

        } catch(
                Exception e)
        {
            e.printStackTrace();
        }
        boolean success = false;
        try

        {
            http_client.sendDeletePage(shouldntWork);
            success = true;

        } catch(
                Exception e)
        {
            assertEquals(IllegalArgumentException.class,e.getClass());
        }

    }

    @Test
    public void testAddUserToPageC1True(){
        Page page = new Page();
        User user = new User();
        String userJ = user.toJson(true);
        String pageJ = page.toJson(true);
        try

        {
            http_client.sendCreatePage(pageJ);

        } catch(
                Exception e)
        {
            e.printStackTrace();
        }
        boolean success = false;
        try

        {
            http_client.sendAddUserToPage(userJ,pageJ);
            success = true;

        } catch(
                Exception e)
        {
            e.printStackTrace();
        }
        assertTrue(success);
    }

    @Test
    public void testAddUserToPageC1False(){
        Page page = new Page();

        String pageJ = page.toJson(true);
        try

        {
            http_client.sendCreatePage(pageJ);

        } catch(
                Exception e)
        {
            e.printStackTrace();
        }
        boolean success = false;
        try

        {
            http_client.sendAddUserToPage("no",pageJ);


        } catch(
                Exception e)
        {
            e.printStackTrace();
            assertEquals(IllegalArgumentException.class,e.getClass());
        }


    }


    @Test
    public void testRemoveUserFromPageC1True(){
        Page page = new Page();
        User user = new User();
        String userJ = user.toJson(true);
        String pageJ = page.toJson(true);
        try

        {
            http_client.sendCreatePage(pageJ);

        } catch(
                Exception e)
        {
            e.printStackTrace();
        }
        boolean success = false;
        try

        {
            http_client.sendAddUserToPage(userJ,pageJ);


        } catch(
                Exception e)
        {
            e.printStackTrace();
        }
        try

        {
            http_client.sendRemoveUserFromPage(userJ,pageJ);
            success = true;


        } catch(
                Exception e)
        {
            e.printStackTrace();
        }
        assertTrue(success);

    }


    @Test
    public void testRemoveUserFromPageC1FalseC2TRUE(){
        Page page = new Page();
        DefaultNode user = nodeFactory.buildNode();
        String userJ = user.toJson(true);
        String pageJ = page.toJson(true);
        try

        {
            http_client.sendCreatePage(pageJ);

        } catch(
                Exception e)
        {
            e.printStackTrace();
        }
        boolean success = false;
        try

        {
            http_client.sendAddUserToPage(userJ,pageJ);


        } catch(
                Exception e)

        {
            assertEquals(IllegalArgumentException.class,e.getClass());
        }




    }

    @Test
    public void testRemoveUserFromPageC1TrueC2TrueThenFalse(){
        Page page = new Page();
        User user = new User();
        String userJ = user.toJson(true);
        String pageJ = page.toJson(true);
        try

        {
            http_client.sendCreatePage(pageJ);

        } catch(
                Exception e)
        {
            e.printStackTrace();
        }

        try

        {
            http_client.sendAddUserToPage(userJ,pageJ);


        } catch(
                Exception e)
        {
            e.printStackTrace();
        }
        try

        {
            http_client.sendRemoveUserFromPage(userJ,"no");


        } catch(
                Exception e)
        {
            assertEquals(IllegalArgumentException.class,e.getClass());
        }


    }

    @Test
    public void testRemoveUserFromPageC1TrueC2False(){
        Page page = new Page();
        User user = new User();
        String userJ = user.toJson(true);
        String pageJ = page.toJson(true);
        try

        {
            http_client.sendCreatePage(pageJ);

        } catch(
                Exception e)
        {
            e.printStackTrace();
        }

        try

        {
            http_client.sendAddUserToPage(userJ,"no");


        } catch(
                Exception e)
        {
            assertEquals(IllegalArgumentException.class,e.getClass());
        }



    }




}

