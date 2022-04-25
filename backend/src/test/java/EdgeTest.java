
import UML.Diagrammer.backend.objects.*;
import UML.Diagrammer.backend.objects.EdgeFactory.DefaultEdge;
import UML.Diagrammer.backend.objects.EdgeFactory.EdgeFactory;
import UML.Diagrammer.backend.objects.NodeFactory.DefaultNode;
import UML.Diagrammer.backend.objects.NodeFactory.NodeFactory;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.DB;
import org.javalite.activejdbc.test.DBSpec;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.javalite.activejdbc.connection_config.DBConfiguration;


/**
 * C1:valid n1,C2:valid n2
 * I'm using all caps because I hate using the shift key    -David
 * Impossible to make either C1 or C2 invalid?
 */
import java.lang.reflect.Array;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EdgeTest extends DBSpec{
    private NodeFactory factory;
    private EdgeFactory edgey;

    /*Edge testing for setup method in our Edge function*/
    @BeforeEach
    public void setup(){

        factory = new NodeFactory();
        edgey = new EdgeFactory();

    }

    /*Testing for setNode method*/
    @Test
    public void testBasicEdgeC1TRUEC2TRUE() {
        DefaultNode nodeOne = factory.buildNode();
        nodeOne.set("name","TESTONE");
        nodeOne.saveIt();
        DefaultNode nodeTwo = factory.buildNode();
        nodeTwo.set("name","TESTONE");
        nodeTwo.saveIt();
        DefaultEdge edgar = edgey.buildEdge();

        nodeOne.saveIt();
        nodeTwo.saveIt();
        edgar.setNodes(nodeOne,nodeTwo);
        edgar.saveIt();
        assertTrue(edgar.exists());
        assertEquals(nodeOne.getInteger("id"),edgar.getInteger("from_node_id"));
        assertEquals(nodeTwo.getInteger("id"),edgar.getInteger("to_node_id"));


    }



    /*EdgeTest testing for getID method*/
    @Test
    public void testGetID() {

        DefaultNode node1 = factory.buildNode();
        DefaultNode node2 = factory.buildNode();
        node1.saveIt();
        node2.saveIt();
        DefaultEdge edge = new DefaultEdge();
        edge.setNodes(node1.getInteger("id"),node1.getString("type"),node2.getInteger("id"),node2.getString("type"));
       // DefaultEdge edge = edgey.buildEdge(node1,node2); //Alex note: trouble with creating a node without first creating nodes atm
        edge.saveIt();
        edge.setId(100);
        assertEquals(100,edge.getId());
    }

    @Test
    public void testSetNodes(){
        DefaultEdge edge = edgey.buildEdge();
        DefaultNode nodeOne = factory.buildNode();
        DefaultNode nodeTwo = factory.buildNode();
        nodeOne.createIt();
        nodeTwo.createIt();
        int node1Id = nodeOne.getId();
        int node2Id = nodeTwo.getId();
        edge.setNodes(nodeOne,nodeTwo);
        edge.saveIt();

        assertEquals(node1Id,edge.get("from_node_id"));
        assertEquals(node2Id,edge.get("to_node_id"));

    }

    @Test
    public void testToString(){
        DefaultNode nodeOne = factory.buildNode();
        nodeOne.saveIt();
        //nodeOne.setId(0);
        DefaultNode nodeTwo = factory.buildNode();
        nodeTwo.saveIt();
        //nodeTwo.setId(1);
        DefaultEdge edgar = edgey.buildEdge();
        edgar.setNodes(nodeOne,nodeTwo);
        edgar.saveIt();
        String string = "Edge has attributes:" + "\n" + "ID: " + edgar.getId() + "\n" + "Node 1 ID: " + nodeOne.getId() + "\n" + "Node 2 ID: " + nodeTwo.getId()+"\n"+
                "Node 1 Type: "+edgar.getString("from_node_type")+"\n"+"Node 2 Type: "+edgar.getString("to_node_type");
        assertEquals(string,edgar.toString());
    }

//    @Test
//    public void testEquals(){
//        DefaultEdge nullEdge = null;
//        DefaultEdge nonNullEdge = edgey.buildEdge();
//        assertEquals(false,nonNullEdge.equals(nullEdge));
//        assertEquals(true,nonNullEdge.equals(nonNullEdge));
//
//    }
}