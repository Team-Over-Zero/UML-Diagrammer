
import UML.Diagrammer.backend.objects.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DefaultNodeTest {
    private DefaultNode myNode;

    @Test
    public void testSetHeight(){
        DefaultNode node = new DefaultNode();
        node.setHeight(3);
        assertEquals(3,node.getHeight());
    }
    @Test
    public void multipleSetHeights(){
        DefaultNode node = new DefaultNode();
        node.setHeight(2);
        node.setHeight(3);
        node.setHeight(1);
        assertEquals(1,node.getHeight());
    }

    @Test
    public void testSetCoords(){
        DefaultNode node = new DefaultNode();
        node.setCoords(90,1234);
        assertEquals(90,node.getXCoord());
        assertEquals(1234,node.getYCoord());

    }
    @Test
    public void testNegativeCoords(){
        DefaultNode node = new DefaultNode();
        node.setCoords(-1,-1);
        assertEquals(-1,node.getXCoord());
        assertEquals(-1,node.getYCoord());
    }

    @Test
    public void setSize(){
        DefaultNode node = new DefaultNode();
        node.setSize(20,20);
        assertEquals(20,node.getWidth());
        assertEquals(20,node.getHeight());
    }



}
