import UML.Diagrammer.backend.objects.*;
import UML.Diagrammer.backend.objects.EdgeFactory.DefaultEdge;
import UML.Diagrammer.backend.objects.EdgeFactory.EdgeFactory;
import UML.Diagrammer.backend.objects.NodeFactory.NodeFactory;
import org.javalite.activejdbc.Base;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PageTest {
    private NodeFactory factory;

    private Page page;
    private AbstractNode node;

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
    public void teardown() {
        Base.close();
    }
    /**
     * test page's addNode method
     */
    @Test
    public void testPageAddNode(){
        page = new Page();
        factory = new NodeFactory();
        AbstractNode node = factory.buildNode();
        page.addNode(node);
        assertNotNull(page.getNodeDict());

    }
    /**
     * test page's removeNode method
     */
    @Test
    public void testRemoveNode(){
        page = new Page();
        NodeFactory factory = new NodeFactory();
        AbstractNode node = factory.buildNode();
        page.addNode(node);
        page.removeNode(node);
        assertEquals(0,page.getEdgeDictSize());

    }

    @Test
    public void testGetname(){
        page = new Page();
        page.setPageName("headache");
        assertEquals("headache",page.getPageName());
    }

    @Test
    public void testAddEdge(){
        page = new Page();
        factory = new NodeFactory();
        EdgeFactory edge = new EdgeFactory();
        DefaultEdge edgey = edge.buildEdge();
        AbstractNode nodeOne = factory.buildNode();
        AbstractNode nodeTwo = factory.buildNode();
        edgey.setNodes(nodeOne,nodeTwo);
        page.addEdge(edgey);
        assertEquals(1,page.getEdgeDictSize());
        assertNotNull(page.getEdgeDict());


    }

    @Test
    public void testRemoveEdge(){
        page = new Page();
        factory = new NodeFactory();
        EdgeFactory edge = new EdgeFactory();
        DefaultEdge edgey = edge.buildEdge();
        AbstractNode nodeOne = factory.buildNode();
        AbstractNode nodeTwo = factory.buildNode();
        edgey.setNodes(nodeOne,nodeTwo);
        page.addEdge(edgey);
        page.removeEdge(edgey);
        assertEquals(0,page.getEdgeDictSize());


    }


}
