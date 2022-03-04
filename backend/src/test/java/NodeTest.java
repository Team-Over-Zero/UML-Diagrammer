
import UML.Diagrammer.backend.objects.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
public class NodeTest {

    private NodeFactory factory;

    private AbstractNode node;

    @BeforeEach
    public void setup(){
        factory = new NodeFactory();
        node = factory.buildNode();
    }

    @Test
    public void setCoords() {
        node.setCoords(1,1);
        node.setCoords(123,123);
        assertEquals(123,node.getXCoord());
        assertEquals(123,node.getYCoord());
    }

    @Test
    public void getID() {
        node.setID(9999);
        node.setID(1);
        assertEquals(1,node.getID());
    }

    @Test
    public void getDescription() {
        node.setDescription("This should be gettable");
        String s = node.getDescription();
        assertEquals("This should be gettable",s);
    }

    @Test
    public void testHashCode() {

    }
}