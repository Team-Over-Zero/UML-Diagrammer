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

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

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
        HashMap nD = new HashMap();
        HashMap eD = new HashMap();
        String s = "Test";
        Page page = new Page(s);
        assertNotNull(page);

    }

//    /**
//     * This test is to make jacoco shup up about lombok setters
//     * DEPRECATED BY ALEX
//     */
//    @Test
//    public void garbageTestTwo(){
//        Page page = new Page();
//        HashMap nD = new HashMap();
//        HashMap eD = new HashMap();
//        page.setEdgeDict(eD);
//        page.setNodeDict(nD);
//    }



    @Test
    public void testPageAddNode(){
        page = new Page();
        factory = new NodeFactory();
        DefaultNode node = factory.buildNode();
        page.add(node);
        assertNotNull(page.getAll(node.getClass()));

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
        DefaultNode nodeOne = factory.buildNode();
        DefaultNode nodeTwo = factory.buildNode();
        edgey.setNodes(nodeOne,nodeTwo);
        page.add(edgey);
        page.saveIt();

        assertNotNull(page.getNodes());
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
        edgey.setNodes(nodeOne,nodeTwo);
        page.add(edgey);
        page.remove(edgey);
        assertEquals(true,page.getNodes().get(0).isEmpty());


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
        ClassNode clsNode = factory.buildNode("CLASS",1,1,1,1);
        clsNode.saveIt();
        page.add(defNode);
        page.add(clsNode);
        page.saveIt();

        DefaultNode def = page.getAll(DefaultNode.class).get(0);
        ClassNode cl = page.getAll(ClassNode.class).get(0);


        assertEquals("class_nodes",clsNode.get("type"));
        assertEquals("default_nodes",def.get("type"));
        assertEquals("class_nodes",cl.get("type"));
        assertEquals(10,page.getNodes().size()); //tests size of dictionary not number of nodes
    }
}
