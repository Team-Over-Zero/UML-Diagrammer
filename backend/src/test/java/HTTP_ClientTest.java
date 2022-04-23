import UML.Diagrammer.backend.apis.HTTP_Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.LinkedList;

/**
 * Will need to revise these as example methods are removed, page David when its time to change stuff(if you decide not
 * to )
 */
public class HTTP_ClientTest {
    private HTTP_Client client;

    @BeforeEach
    public void setUp() throws Exception {
        client = new HTTP_Client();
    }


    @Test
    public void test() {
        HTTP_Client bork = new HTTP_Client("bork","bork");

    }

    @Test
    public void testExampleGetRequest() throws IOException, InterruptedException {
        String s = client.exampleGetRequest();

        assertEquals(String.class, s.getClass());

    }

    @Test
    public void testExamplePutRequest() throws IOException, InterruptedException {
        String testText = "{\"success\":\"true\"}\n";
        String s = client.examplePutRequest();
        assertEquals(testText, s);
    }

    @Test
    public void testExamplePostRequest() throws IOException, InterruptedException {
        String testText = "{\"success\":\"true\"}\n";
        String s = client.examplePostRequest();
        assertEquals(testText, s);
    }

    /**
     * test tryLoginUser, for now not useful
     */

    @Test
    public void testTryLoginUser() {
        String testUser = "{name:\"httpclient\",password:\"password123\"}";
        String retStr = client.sendCreateUser(testUser);

        assertNotEquals("ERROR: USER NOT FOUND",client.sendLoginUser(testUser));
    }
    /**
     * test try getPage, for now not useful
     */
    @Test
    public void testTryGetPage() {
        String s = client.tryGetPage("nothing");
        assertEquals("", s);
    }

    /**
     * tested method returns a class with no data right now
     */
    @Test
    public void testGetUserpageNames() throws URISyntaxException, IOException {
        assertEquals(String.class, client.getUserPages("{\"id\":\"1\"}").getClass());
    }


}

    /*@Test
    public void sendCurrentPageState() {

    }*/

//import UML.Diagrammer.backend.apis.Database_Client;
//import UML.Diagrammer.backend.apis.HTTP_Client;
//import UML.Diagrammer.backend.objects.EdgeFactory.EdgeFactory;
//import UML.Diagrammer.backend.objects.NodeFactory.DefaultNode;
//import UML.Diagrammer.backend.objects.NodeFactory.NodeFactory;
//import UML.Diagrammer.backend.objects.Page;
//import UML.Diagrammer.backend.objects.User;
//import org.javalite.activejdbc.test.DBSpec;
//import org.junit.jupiter.api.*;
//
//import java.io.IOException;
//import java.net.URISyntaxException;
//
//import static org.junit.jupiter.api.Assertions.*;
///**
// * Will need to revise these as example methods are removed, page David when its time to change stuff(if you decide not
// * to )
// */
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//public class HTTP_ClientTest extends DBSpec {
//    private HTTP_Client client;
//    private EdgeFactory edgeFactory;
//    private NodeFactory nodeFactory;
//    private Database_Client dbClient;
//
//    /*@BeforeAll private void startDB(){
//        dbClient = new Database_Client();
//        dbClient.spinUp();
//    }*/
//
//    @BeforeAll
//    public void setUp() throws Exception {
//        client = new HTTP_Client();
//        edgeFactory = new EdgeFactory();
//        nodeFactory = new NodeFactory();
//        String jUnitUrl = "jdbc:mysql://ls-a9db0e6496e5430883b43e690a26b7676cf9d7af.cuirr4jp1g1o.us-west-2.rds.amazonaws.com/junit?useSSL=false";
//        String databaseUser = "root";
//        String databasePassword = "TeamOverZero";
//        int javalinPort = 8888;
//        dbClient = new Database_Client(jUnitUrl, databaseUser, databasePassword, javalinPort);
//        dbClient.spinUp();
//
//    }
//
//    @AfterAll
//    public void shutDown(){
//        dbClient.spinDown();
//    }
//
//
//    @Test
//    public void testExampleGetRequest() throws IOException, InterruptedException {
//        String testText = "{\"success\":\"true\"}\n";
//        String s = client.exampleGetRequest();
//        assertEquals(testText, s);
//
//    }
//
//    @Test
//    public void testExamplePutRequest() throws IOException, InterruptedException {
//        String testText = "{\"success\":\"true\"}\n";
//        String s = client.examplePutRequest();
//        assertEquals(testText,s);
//    }
//
//    @Test
//    public void testExamplePostRequest() throws IOException, InterruptedException {
//        String testText = "{\"success\":\"true\"}\n";
//        String s = client.examplePostRequest();
//        assertEquals(testText,s);
//    }
//    /**
//     *
//      test tryLoginUser, for now not useful
//     */
//
//    @Test
//    public void testTryLoginUser() {
//        assertEquals("FAILED TO LOGIN",client.tryLoginUser());
//    }
//
//    /**
//     * test try getPage, for now not useful
//     */
//    @Test
//    public void testTryGetPage() {
//        String s = client.tryGetPage("nothing");
//        assertEquals("",s);
//    }
//
//    /**
//     * tested method returns a class with no data right now
//     */
//    @Test
//    public void testGetUserpageNames() throws URISyntaxException, IOException {
//        assertEquals(String.class,client.getUserPages("{\"id\":\"1\"}").getClass());
//    }
//
//    @Test
//    public void testCreateUserT1True() throws IOException, URISyntaxException, InterruptedException {
//        User user = new User();
//        String userJson = user.toJson(true);
//        String s = "";
//        try {
//            client.sendCreateUser(userJson);
//            s = "SUCCESS";
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        assertEquals("SUCCESS", "s");
//    }
//
//    @Test
//    public void testCreateUserT2False() throws IOException, URISyntaxException, InterruptedException {
//        DefaultNode node = nodeFactory.buildNode();
//        String userJson = node.toJson(true);
//        try {
//            client.sendCreateUser(userJson);
//
//        } catch (Exception e) {
//            assertEquals(IllegalArgumentException.class, e.getClass());
//        }
//    }
//
//    @Test
//    public void testCreateUserT3Null() throws IOException, URISyntaxException, InterruptedException {
//        String s = null;
//
//        try {
//            client.sendCreateUser(s);
//
//        } catch (Exception e) {
//            assert true;
//        }
//    }
//
//    @Test
//    public void testCreatePageT1True() {
//        Page page = new Page();
//        String pageJson = page.toJson(true);
//        String s = "";
//        try {
//            client.sendCreateUser(pageJson);
//            s = "SUCCESS";
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        assertEquals("SUCCESS", s);
//    }
//
//    @Test
//    public void testCreatePageT2False() {
//        User page = new User();
//        String pageJson = page.toJson(true);
//        try {
//            client.sendCreatePage(pageJson);
//
//        } catch (Exception e) {
//            assertEquals(IllegalArgumentException.class, e.getClass());
//        }
//
//    }
//
//    @Test
//    public void testCreatePageT3Null() {
//        String s = null;
//        try {
//            client.sendCreatePage(s);
//
//        } catch (Exception e) {
//            assert true;
//        }
//
//    }
//
//    @Test
//    public void testDeletePageT1True() {
//        Page page = new Page();
//        String pageJson = page.toJson(true);
//        try {
//            client.sendCreatePage(pageJson);
//
//        } catch (
//                Exception e) {
//            e.printStackTrace();
//        }
//        boolean success = false;
//        try {
//            client.sendDeletePage(pageJson);
//            success = true;
//
//        } catch (
//                Exception e) {
//            e.printStackTrace();
//        }
//        assertTrue(success);
//    }
//
//    @Test
//    public void testDeletePageT2False() {
//        String badPage = "bad page json";
//        Page page = new Page("test");
//        String pageJson = page.toJson(true);
//
//        try {
//            client.sendCreatePage(pageJson);
//            //assert that an exception is thrown
//
//        } catch (
//                Exception e) {
//            e.printStackTrace();
//        }
//        boolean success = false;
//        try {
//            client.sendDeletePage(badPage);
//            success = true;
//
//        } catch (
//                Exception e) {
//            assertEquals(IllegalArgumentException.class, e.getClass());
//        }
//
//    }
//
//    @Test
//    public void testAddUserToPageT1True() {
//        Page page = new Page();
//        User user = new User();
//        String userJson = user.toJson(true);
//        String pageJson = page.toJson(true);
//        try {
//            client.sendCreatePage(pageJson);
//
//        } catch (
//                Exception e) {
//            e.printStackTrace();
//        }
//        boolean success = false;
//        try {
//            client.sendAddUserToPage(userJson, pageJson);
//            success = true;
//
//        } catch (
//                Exception e) {
//            e.printStackTrace();
//        }
//        assertTrue(success);
//    }
//
//    @Test
//    public void testAddUserToPageT2False() {
//        Page page = new Page();
//
//        String pageJson = page.toJson(true);
//        try {
//            client.sendCreatePage(pageJson);
//
//        } catch (
//                Exception e) {
//            e.printStackTrace();
//        }
//        boolean success = false;
//        try {
//            client.sendAddUserToPage("no", pageJson);
//
//
//        } catch (
//                Exception e) {
//            e.printStackTrace();
//            assertEquals(IllegalArgumentException.class, e.getClass());
//        }
//
//
//    }
//
//    @Test
//    public void testAddUserToPageT3False() {
//        User user = new User();
//        String userJson = user.toJson(true);
//        try {
//            client.sendCreateUser(userJson);
//
//        } catch (
//                Exception e) {
//            e.printStackTrace();
//        }
//        boolean success = false;
//        try {
//            client.sendAddUserToPage(userJson, "nopage");
//        } catch (
//                Exception e) {
//            e.printStackTrace();
//            assertEquals(IllegalArgumentException.class, e.getClass());
//        }
//
//
//    }
//
//    @Test
//    public void testRemoveUserFromPage1True() {
//        Page page = new Page();
//        User user = new User();
//        String userJ = user.toJson(true);
//        String pageJ = page.toJson(true);
//        try {
//            client.sendCreatePage(pageJ);
//
//        } catch (
//                Exception e) {
//            e.printStackTrace();
//        }
//        boolean success = false;
//        try {
//            client.sendAddUserToPage(userJ, pageJ);
//
//
//        } catch (
//                Exception e) {
//            e.printStackTrace();
//        }
//        try {
//            client.sendRemoveUserFromPage(userJ, pageJ);
//            success = true;
//
//
//        } catch (
//                Exception e) {
//            e.printStackTrace();
//        }
//        assertTrue(success);
//
//    }
//
//
//    @Test
//    public void testRemoveUserFromPageT2False() {
//        Page page = new Page();
//        DefaultNode user = nodeFactory.buildNode();
//        String userJ = user.toJson(true);
//        String pageJ = page.toJson(true);
//        try {
//            client.sendCreatePage(pageJ);
//
//        } catch (
//                Exception e) {
//            e.printStackTrace();
//        }
//        boolean success = false;
//        try {
//            client.sendAddUserToPage(userJ, pageJ);
//
//
//        } catch (
//                Exception e) {
//            assertEquals(IllegalArgumentException.class, e.getClass());
//        }
//
//
//    }
//
//    @Test
//    public void testRemoveUserFromPageT3False() {
//        Page page = new Page();
//        User user = new User();
//        String userJ = user.toJson(true);
//        String pageJ = page.toJson(true);
//        try {
//            client.sendCreatePage(pageJ);
//
//        } catch (
//                Exception e) {
//            e.printStackTrace();
//        }
//
//        try {
//            client.sendAddUserToPage(userJ, pageJ);
//
//
//        } catch (
//                Exception e) {
//            e.printStackTrace();
//        }
//        try {
//            client.sendRemoveUserFromPage(userJ, "no");
//
//
//        } catch (
//                Exception e) {
//            assertEquals(IllegalArgumentException.class, e.getClass());
//        }
//
//
//    }
//
//    @Test
//    public void testRemoveUserFromPageT4False() {
//        Page page = new Page();
//        User user = new User();
//        String userJ = user.toJson(true);
//        String pageJ = page.toJson(true);
//        try {
//            client.sendCreatePage(pageJ);
//
//        } catch (
//                Exception e) {
//            e.printStackTrace();
//        }
//
//        try {
//            client.sendAddUserToPage(userJ, "no");
//
//
//        } catch (
//                Exception e) {
//            assertEquals(IllegalArgumentException.class, e.getClass());
//        }
//
//
//    }
//
//
//    @Test
//    public void testPageCreateRequestT1True() {
//        Page page = new Page();
//        User user = new User();
//        String userJson = user.toJson(true);
//        String pageJson = page.toJson(true);
//        try {
//            client.sendCreatePage(pageJson);
//
//        } catch (
//                Exception e) {
//            e.printStackTrace();
//        }
//        try {
//            client.sendCreateUser(userJson);
//
//        } catch (
//                Exception e) {
//            e.printStackTrace();
//        }
//        String s = null;
//        try {
//            s = client.pageCreateRequest(pageJson, user.getId().toString());
//        } catch (Exception e) {
//            fail();
//        }
//        assertEquals("SUCCESS", s);
//    }
//
//    @Test
//    public void testPageCreateRequestT2False() {
//        Page page = new Page();
//        String pageJson = page.toJson(true);
//        try {
//            client.sendCreatePage(pageJson);
//
//        } catch (
//                Exception e) {
//            e.printStackTrace();
//        }
//
//        String s = null;
//        try {
//            s = client.pageCreateRequest(pageJson, "null");
//        } catch (Exception e) {
//            assertEquals(IllegalArgumentException.class, e.getClass());
//        }
//
//    }
//
//    @Test
//    public void testPageCreateRequestT3False() {
//
//        User user = new User();
//        String userJson = user.toJson(true);
//
//        try {
//            client.sendCreateUser(userJson);
//
//        } catch (
//                Exception e) {
//            e.printStackTrace();
//        }
//        String s = null;
//        try {
//            s = client.pageCreateRequest("no page", user.getId().toString());
//        } catch (Exception e) {
//            assertEquals(IllegalArgumentException.class, e.getClass());
//        }
//    }
//
//    /**
//     * not implemented
//     */
//    @Test
//    public void testSendCurrentPageState() {
//        assertEquals("", "");
//    }
//
//    @Test
//    public void testSendNodeCreateRequestT1True() {
//        String s = null;
//        String nodJson = nodeFactory.buildNode().toJson(true);
//        try {
//            s = client.sendNodeCreateRequest(nodJson);
//        } catch (Exception e) {
//            fail();
//        }
//        assertEquals("SUCCESS", s);
//    }
//
//    @Test
//    public void testSendNodeCreateRequestT2False() {
//        String s = null;
//        String nodJson = new User().toJson(true);
//        try {
//            s = client.sendNodeCreateRequest(nodJson);
//        } catch (Exception e) {
//            assertEquals(IllegalArgumentException.class, e.getClass());
//        }
//    }
//
//    @Test
//    public void testSendNodeCreateRequestT3Null() {
//        String s = null;
//        try {
//            s = client.sendNodeCreateRequest(null);
//        } catch (Exception e) {
//            assertEquals(IllegalArgumentException.class, e.getClass());
//        }
//    }
//
//    @Test
//    public void testSendNodeUpdateRequestT1True() {
//        String s = null;
//        String nodJson = nodeFactory.buildNode().toJson(true);
//        try {
//            client.sendNodeCreateRequest(nodJson);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        try {
//            s = client.sendNodeUpdateRequest(nodJson);
//        } catch (Exception e) {
//            fail();
//        }
//        assertEquals("SUCCESS", s);
//    }
//
//    @Test
//    public void testSendNodeUpdateRequestT2False() {
//        String s = null;
//        String nodJson = new User().toJson(true);
//        try {
//            s = client.sendNodeUpdateRequest(nodJson);
//        } catch (Exception e) {
//            assertEquals(IllegalArgumentException.class, e.getClass());
//        }
//    }
//
//    @Test
//    public void testSendEdgeCreateRequestT1True() {
//        String s = null;
//        String edgeJson = edgeFactory.buildEdge().toJson(true);
//        try {
//            s = client.sendEdgeCreateRequest(edgeJson);
//        } catch (Exception e) {
//            fail();
//        }
//        assertEquals("SUCCESS", s);
//    }
//    @Test
//    public void testSendEdgeCreateRequestT2TFalse() {
//        String s = null;
//        String edgeJson = "null";
//        try {
//            s = client.sendEdgeCreateRequest(edgeJson);
//        } catch (Exception e) {
//            assertEquals(IllegalArgumentException.class, e.getClass());
//        }
//    }
//
//    @Test
//    public void testSendAddNodeToPageT1True() {
//        String nodeJson = nodeFactory.buildNode().toJson(true);
//        Page page = new Page();
//        try {
//            client.sendNodeCreateRequest(nodeJson);
//        }
//        catch (Exception ex)
//        {
//            ex.printStackTrace();
//        }
//        try {
//            client.sendCreatePage(page.toJson(true));
//        }
//        catch (Exception ex)
//        {
//            ex.printStackTrace();
//        }
//        String s = client.sendAddNodeToPage(nodeJson, page.getId().toString());
//        assertEquals("SUCCESS", s);
//    }
//    @Test
//    public void testSendAddNodeToPageT2False() {
//        String nodeJson = nodeFactory.buildNode().toJson(true);
//        Page page = new Page();
//        try {
//            client.sendNodeCreateRequest(nodeJson);
//        }
//        catch (Exception ex)
//        {
//            ex.printStackTrace();
//        }
//        String s = client.sendAddNodeToPage(nodeJson, "null");
//        assertEquals("FAIL", s);
//    }
//    @Test
//    public void testSendAddNodeToPageT3False() {
//        String nodeJson = "null";
//        Page page = new Page();
//
//        try {
//            client.sendCreatePage(page.toJson(true));
//        }
//        catch (Exception ex)
//        {
//            ex.printStackTrace();
//        }
//        String s = client.sendAddNodeToPage(nodeJson, page.getId().toString());
//        assertEquals("FAIL", s);
//    }
//
//    @Test
//    public void testSendRemoveNodeFromPageT1True() {
//        String nodeJson = nodeFactory.buildNode().toJson(true);
//        Page page = new Page();
//        try {
//            client.sendNodeCreateRequest(nodeJson);
//        }
//        catch (Exception ex)
//        {
//            ex.printStackTrace();
//        }
//        try {
//            client.sendCreatePage(page.toJson(true));
//        }
//        catch (Exception ex)
//        {
//            ex.printStackTrace();
//        }
//        client.sendAddNodeToPage(nodeJson, page.getId().toString());
//        String s =client.sendRemoveNodeFromPage(nodeJson,page.getId().toString());
//        assertEquals("SUCCESS", s);
//    }
//    @Test
//    public void testSendRemoveNodeFromPageT2False() {
//        String nodeJson = nodeFactory.buildNode().toJson(true);
//        Page page = new Page();
//        try {
//            client.sendNodeCreateRequest(nodeJson);
//        }
//        catch (Exception ex)
//        {
//            ex.printStackTrace();
//        }
//        try {
//            client.sendCreatePage(page.toJson(true));
//        }
//        catch (Exception ex)
//        {
//            ex.printStackTrace();
//        }
//        String s =client.sendRemoveNodeFromPage(nodeJson,page.getId().toString());
//        assertEquals("FAIL", s);
//    }
//
//    @Test
//    public void testSendAddEdgeToPageT1True() {
//        String edgeJson = edgeFactory.buildEdge().toJson(true);
//        Page page = new Page();
//        try {
//            client.sendEdgeCreateRequest(edgeJson);
//        }
//        catch (Exception ex)
//        {
//            ex.printStackTrace();
//        }
//        try {
//            client.sendCreatePage(page.toJson(true));
//        }
//        catch (Exception ex)
//        {
//            ex.printStackTrace();
//        }
//        String s = client.sendAddEdgeToPage(edgeJson, page.getId().toString());
//        assertEquals("SUCCESS", s);
//    }
//    @Test
//    public void testSendAddEdgeToPageT2False() {
//        String edgeJson = edgeFactory.buildEdge().toJson(true);
//        Page page = new Page();
//        try {
//            client.sendEdgeCreateRequest(edgeJson);
//        }
//        catch (Exception ex)
//        {
//            ex.printStackTrace();
//        }
//        String s = client.sendAddEdgeToPage(edgeJson, "null");
//        assertEquals("FAIL", s);
//    }
//    @Test
//    public void testSendAddEdgeToPageT3False() {
//        String edgeJson = "null";
//        Page page = new Page();
//
//        try {
//            client.sendCreatePage(page.toJson(true));
//        }
//        catch (Exception ex)
//        {
//            ex.printStackTrace();
//        }
//        String s = client.sendAddEdgeToPage(edgeJson, page.getId().toString());
//        assertEquals("FAIL", s);
//    }
//    @Test
//    public void testSendRemoveEdgeFromPageT1True() {
//        String edgeJson = edgeFactory.buildEdge().toJson(true);
//        Page page = new Page();
//        try {
//            client.sendEdgeCreateRequest(edgeJson);
//        }
//        catch (Exception ex)
//        {
//            ex.printStackTrace();
//        }
//        try {
//            client.sendCreatePage(page.toJson(true));
//        }
//        catch (Exception ex)
//        {
//            ex.printStackTrace();
//        }
//        client.sendAddEdgeToPage(edgeJson, page.getId().toString());
//        String s =client.sendRemoveEdgeFromPage(edgeJson,page.getId().toString());
//        assertEquals("SUCCESS", s);
//    }
//    @Test
//    public void testSendRemoveEdgeFromPageT2False() {
//        String edgeJson = edgeFactory.buildEdge().toJson(true);
//        Page page = new Page();
//        try {
//            client.sendEdgeCreateRequest(edgeJson);
//        }
//        catch (Exception ex)
//        {
//            ex.printStackTrace();
//        }
//        try {
//            client.sendCreatePage(page.toJson(true));
//        }
//        catch (Exception ex)
//        {
//            ex.printStackTrace();
//        }
//        String s =client.sendRemoveEdgeFromPage(edgeJson,page.getId().toString());
//        assertEquals("FAIL", s);
//    }
//}*/

