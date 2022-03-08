import UML.Diagrammer.backend.objects.*;
import UML.Diagrammer.backend.objects.NodeFactory.NodeFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PageTest {
    private NodeFactory factory;

    private Page page;


    /**
     * test page's addNode method
     */
    @Test
    public void testPageAddNode(){
        page = new Page();
        factory = new NodeFactory();
        AbstractNode node = factory.buildNode();
        page.addNode(node);
        assertNotNull(page);
    }
    /**
     * test page's removeNode method
     */
    @Test
    public void testRemoveNode(){
        Page page = new Page();
        NodeFactory factory = new NodeFactory();
        AbstractNode node = factory.buildNode();
        page.addNode(node);
        page.removeNode(node);
        assertEquals(0,page.getEdgeDictSize());

    }
}
