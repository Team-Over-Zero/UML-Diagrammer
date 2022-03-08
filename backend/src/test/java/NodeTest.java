
import UML.Diagrammer.backend.objects.*;
import UML.Diagrammer.backend.objects.NodeFactory.NodeFactory;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
public class NodeTest {

    private NodeFactory factory;

    private AbstractNode node;

    /*Testing the node setup method*/
    @BeforeEach
    public void setup(){
        factory = new NodeFactory();
        node = factory.buildNode();
    }

    /*Node test for setCoords method*/
    @Test
    public void setCoords() {
        node.setCoords(1,1);
        node.setCoords(123,123);
        assertEquals(123,node.getXCoord());
        assertEquals(123,node.getYCoord());
    }

    /*Test for the getID method*/
    @Test
    public void getID() {
        node.setID(9999);
        node.setID(1);
        assertEquals(1,node.getID());
    }

    /*Testing for the getDescription method*/
    @Test
    public void getDescription() {
        node.setDescription("This should be gettable");
        String s = node.getDescription();
        assertEquals("This should be gettable",s);
    }

    /*Test Node's hashCode method*/
    @Test
    public void testHashCode() {

    }
}