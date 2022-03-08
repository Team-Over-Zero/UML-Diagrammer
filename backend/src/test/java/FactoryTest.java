import UML.Diagrammer.backend.objects.*;
import UML.Diagrammer.backend.objects.NodeFactory.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FactoryTest {

    NodeFactory factory;
    @BeforeEach
    public void makeFactory(){
        factory = new NodeFactory();
    }

    /*Testing factory for testFactory method*/
    @Test
    public void testFactory(){
        AbstractNode node = factory.buildNode();
        assertEquals("DEFAULT NAME",node.getName());
        assertEquals(0,node.getXCoord());
        assertEquals(0,node.getYCoord());
    }

    /*Test factory's sizeNotBroken method*/
    @Test
    public void testSizeNotBroken(){
        AbstractNode node = factory.buildNode();
        assertNotNull(node.getHeight());
        assertNotNull(node.getWidth());
    }

    /*Tests factory's setHeight*/
    @Test
    public void testSetHeight(){
        AbstractNode node = factory.buildNode();
        node.setHeight(3);
        assertEquals(3,node.getHeight());
    }

    /*Tests factory's multipleSetHeights*/
    @Test
    public void multipleSetHeights(){
        AbstractNode node = factory.buildNode();
        node.setHeight(2);
        node.setHeight(3);
        node.setHeight(1);
        assertEquals(1,node.getHeight());
    }

    /*Tests the factory's setCoords method in this function*/
    @Test
    public void testSetCoords(){
        AbstractNode node = factory.buildNode();
        node.setCoords(90,1234);
        assertEquals(90,node.getXCoord());
        assertEquals(1234,node.getYCoord());

    }

    /*Tests the factory's negativeCoords method in this function*/
    @Test
    public void testNegativeCoords(){
        AbstractNode node = factory.buildNode();
        node.setCoords(-1,-1);
        assertEquals(-1,node.getXCoord());
        assertEquals(-1,node.getYCoord());
    }

    /*Tests the factory's setSize method in this function*/
    @Test
    public void setSize(){
        AbstractNode node = factory.buildNode();
        node.setSize(20,20);
        assertEquals(20,node.getWidth());
        assertEquals(20,node.getHeight());
    }

    /**
     * ClassNode subclass test
     * You can reference what type of classes there are in NodeFactory.java
     * To make different node types simply call 'NODETYPE node = factory.buildNode(NODETYPE, rest of params)'
     * The switch block in NodeFactory class tells the different strings to call other subclasses.
     * @author Show
     */

    /*Tests the factory's classNodeSubclass method in this function*/
    @Test
    public void testClassNodeSubclass(){
        ClassNode node = factory.buildNode("CLASS", 0, 0, 3, 3);
        assertEquals("Class Name", node.getName());
        assertEquals("Class Description", node.getDescription());
        assertEquals(0, node.getXCoord());
        assertEquals(0, node.getYCoord());
        assertEquals(3, node.getWidth());
        assertEquals(3, node.getHeight());
    }

    /**
     * Default constructor test
     * @author Show
     */

    /*Tests the factory's defaultNodeSubclass method in this function*/
    @Test
    public void testDefaultNodeSubclass(){
        DefaultNode node = factory.buildNode();
        assertEquals("DEFAULT NAME", node.getName());
        assertEquals("DEFAULT DESCRIPTION", node.getDescription());
        assertEquals(0, node.getXCoord());
        assertEquals(0, node.getYCoord());
        assertEquals(3, node.getWidth());
        assertEquals(3, node.getHeight());
    }

}
