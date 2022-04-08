import UML.Diagrammer.backend.apis.HTTP_Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;
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
    public void exampleGetRequest() throws IOException, InterruptedException {
       String s = client.exampleGetRequest();

       assertEquals(String.class,s.getClass());

    }

    @Test
    public void examplePutRequest() throws IOException, InterruptedException {
        String testText = "{\"success\":\"true\"}\n";
        String s = client.examplePutRequest();
        assertEquals(testText,s);
    }

    @Test
    public void examplePostRequest() throws IOException, InterruptedException {
        String testText = "{\"success\":\"true\"}\n";
        String s = client.examplePostRequest();
        assertEquals(testText,s);
    }
    /**
     * test tryLoginUser, for now not useful
     */
    @Test
    public void tryLoginUser() {
        assertEquals("FAILED TO LOGIN",client.tryLoginUser());
    }

    /**
     * test try getPage, for now not useful
     */
    @Test
    public void tryGetPage() {
        String s = client.tryGetPage("nothing");
        assertEquals("",s);
    }

    /**
     * tested method returns a class with no data right now
     */
    @Test
    public void getUserpageNames() {
        assertEquals(LinkedList.class,client.getUserpageNames().getClass());
    }

    /*@Test
    public void sendCurrentPageState() {

    }*/
}