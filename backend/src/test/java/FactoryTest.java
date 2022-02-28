import UML.Diagrammer.backend.objects.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import static org.junit.jupiter.api.Assertions.*;
public class FactoryTest {
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

}
