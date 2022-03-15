
import UML.Diagrammer.backend.objects.*;
import UML.Diagrammer.backend.objects.NodeFactory.NodeFactory;
import org.javalite.activejdbc.Base;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
public class NodeTest {

    private NodeFactory factory;

    private AbstractNode node;

    /*Testing the node setup method*/
    @BeforeEach
    public void setup(){

        String databaseURL = "jdbc:mysql://ls-a9db0e6496e5430883b43e690a26b7676cf9d7af.cuirr4jp1g1o.us-west-2.rds.amazonaws.com/test";
        String databaseUser = "root";
        String databasePassword = "TeamOverZero";
        Base.open("com.mysql.cj.jdbc.Driver", databaseURL, databaseUser, databasePassword);
        factory = new NodeFactory();
        node = factory.buildNode();

    }

    @AfterEach
    public void tearDown(){
        Base.close();
    }
    /*Node test for setCoords method*/
    @Test
    public void setCoords() {
        node.setCoords(1,1);
        node.setCoords(123,123);
        node.saveIt();
        assertEquals(123,node.get("x_coord"));
        assertEquals(123,node.get("y_coord"));
    }

    /*Test for the getID method*/
    @Test
    public void getID() {
        node.setId(1000);
        node.setId(9999);
        node.saveIt();

        assertEquals(9999,node.getId());
    }

    /*Testing for the getDescription method*/
    @Test
    public void setDescription() {


        node.set("description","This should be gettable");
        String s = (String)node.get("description"); //Can't guarantee that get returns a string so we have to cast it.
        assertEquals("This should be gettable",s);
    }

    /*Test Node's hashCode method*/
    @Test
    public void testHashCode() {

    }
}