import UML.Diagrammer.backend.objects.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
public class FactoryTest {

    Factory factory;
    @BeforeEach
    public void makeFactory(){
        factory = new Factory();
    }

    @Test
    public void testFactory(){
        Factory factory = new Factory();
        DefaultNode node = factory.createSmallDefaultNode("test",0,0);
        assertEquals("test",node.getName());
        assertEquals(0,node.getXCoord());
        assertEquals(0,node.getYCoord());
    }

    @Test
    public void testSizeNotBroken(){
        Factory factory = new Factory();
        DefaultNode node = factory.createSmallDefaultNode("test",0,0);
        assertNotNull(node.getHeight());
        assertNotNull(node.getWidth());
    }

    @Test
    public void testGenericNodeWithName(){
        DefaultNode node = factory.createGenericNode("customName", 0, 0);
        assertEquals("customName", node.getName());
        assertEquals("input a description", node.getDescription());
        assertEquals(0, node.getXCoord());
        assertEquals(0, node.getYCoord());
        assertEquals(2, node.getWidth());
        assertEquals(2, node.getHeight());
    }

    @Test
    public void testGenericNodeWithNoName(){
        DefaultNode node = factory.createGenericNode(0, 0);
        assertEquals("New object", node.getName());
        assertEquals("input a description", node.getDescription());
        assertEquals(0, node.getXCoord());
        assertEquals(0, node.getYCoord());
        assertEquals(2, node.getWidth());
        assertEquals(2, node.getHeight());
    }

}
