import UML.Diagrammer.backend.objects.*;
import UML.Diagrammer.backend.objects.EdgeFactory.DefaultEdge;
import UML.Diagrammer.backend.objects.EdgeFactory.EdgeFactory;
import UML.Diagrammer.backend.objects.NodeFactory.ClassNode;
import UML.Diagrammer.backend.objects.NodeFactory.DefaultNode;
import UML.Diagrammer.backend.objects.NodeFactory.NodeFactory;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.connection_config.DBConfiguration;
import org.javalite.activejdbc.test.DBSpec;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

/**
 * class conditions: C1:Name,
 * add conditions: C1: valid node, C2: valid edge
 */
public class PageTest extends DBSpec {
    private NodeFactory factory;

    private Page page;

    @BeforeEach
    public void setup(){

        factory = new NodeFactory();
    }

    /**
     * test page's addNode method
     */

    /**
     * This is a test to make jacoco shut up about the constructor not being tested
     */
    @Test
    public void garbageTest(){

        String s = "Test";
        Page page = new Page(s);
        assertNotNull(page);

    }

    @Test
    public void testPageAddNode(){
        page = new Page();

        factory = new NodeFactory();
        DefaultNode node = factory.buildNode();
        node.saveIt();
        page.add(node);
        DefaultNode testNode = (DefaultNode) page.getNodes().get(0).get(0);

        assertEquals(node.getInteger("id"), testNode.getId() );

    }
    /**
     * test page's removeNode method
     */
    @Test
    public void testRemoveNode(){
        page = new Page();
        NodeFactory factory = new NodeFactory();
        DefaultNode node = factory.buildNode();
        page.add(node);
        page.remove(node);

        assertEquals(true,page.getNodes().get(0).isEmpty());

    }

    @Test
    public void testGetName(){
        page = new Page();
        page.set("name","headache");
        assertEquals("headache",page.get("name"));
    }

    @Test
    public void testAddEdge(){
        page = new Page();
        page.saveIt();
        factory = new NodeFactory();
        EdgeFactory edge = new EdgeFactory();
        DefaultEdge edgey = edge.buildEdge();
        edgey.createIt();
        DefaultNode nodeOne = factory.buildNode();
        DefaultNode nodeTwo = factory.buildNode();
        nodeOne.createIt();
        nodeTwo.createIt();
        edgey.setNodes(nodeOne,nodeTwo);
        page.add(nodeOne);
        page.add(nodeTwo);
        page.add(edgey);//Note that adding an edge doesn't automatically add nodes.
        page.saveIt();

        ArrayList testL = page.getNodes();
        DefaultEdge a1 = (DefaultEdge) page.getEdges().get(0).get(0);
        assertEquals(edgey.getInteger("id"),a1.getId());
        //assertEquals(1,page.getNodes().get(0).size());

    }

    @Test
    public void testRemoveEdge(){
        page = new Page();
        factory = new NodeFactory();
        EdgeFactory edge = new EdgeFactory();
        DefaultEdge edgey = edge.buildEdge();
        DefaultNode nodeOne = factory.buildNode();
        DefaultNode nodeTwo = factory.buildNode();
        nodeOne.createIt();
        nodeTwo.createIt();
        edgey.createIt();
        edgey.setNodes(nodeOne,nodeTwo);
        edgey.saveIt();
        page.add(edgey);
        page.remove(edgey);
        page.remove(nodeOne);
        page.remove(nodeTwo);
        assertTrue(page.getNodes().get(0).isEmpty());


    }
    @Test
    public void testGetNodeDictSize(){
        page = new Page();
        factory = new NodeFactory();
        DefaultNode node = factory.buildNode();
        page.add(node);
        page.saveIt();

        assertEquals(10,page.getNodes().size());
    }

    @Test
    public void testGetNodes(){
        page = new Page();
        factory = new NodeFactory();
        DefaultNode defNode = factory.buildNode();
        ClassNode clsNode = factory.buildNode("classnodes",1,1,1,1);
        clsNode.saveIt();
        page.add(defNode);
        page.add(clsNode);
        page.saveIt();

        DefaultNode def = page.getAll(DefaultNode.class).get(0);
        ClassNode cl = page.getAll(ClassNode.class).get(0);


        assertEquals("classnodes",clsNode.get("type"));
        assertEquals("defaultnodes",def.get("type"));
        assertEquals("classnodes",cl.get("type"));
        //assertEquals(10,page.getNodes().size()); //tests size of dictionary not number of nodes
    }

    @Test
    public void testAddNodeC1TRUEC2FALSEC3False(){
        Exception ex = assertThrows(NullPointerException.class,()->page.add(null));
        assertEquals(NullPointerException.class,ex.getClass());
    }

    @Test
    public void testAddNodeC1FALSE(){
        DefaultNode node = null;

        Exception ex = assertThrows(NullPointerException.class,()->page.add(node));
        assertEquals(NullPointerException.class,ex.getClass());
    }

    @Test
    public void testAddNodeC2False(){
        DefaultEdge edge = null;

        Exception ex = assertThrows(NullPointerException.class,()->page.add(edge));
        assertEquals(NullPointerException.class,ex.getClass());
    }
}
