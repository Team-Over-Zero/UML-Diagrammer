
import UML.Diagrammer.backend.objects.*;
import UML.Diagrammer.backend.objects.EdgeFactory.DefaultEdge;
import UML.Diagrammer.backend.objects.EdgeFactory.EdgeFactory;
import UML.Diagrammer.backend.objects.NodeFactory.DefaultNode;
import UML.Diagrammer.backend.objects.NodeFactory.NodeFactory;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;


import static org.junit.jupiter.api.Assertions.*;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EdgeTest {
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
    public void testBasicEdge() {
        DefaultNode nodeOne = factory.buildNode();
        DefaultNode nodeTwo = factory.buildNode();
        DefaultEdge edgar = edgey.buildEdge();
        edgar.setNodes(nodeOne,nodeTwo);
        assertEquals(nodeOne,edgar.getN1());
        assertEquals(nodeTwo,edgar.getN2());


    }

    /*EdgeTest testing for getID method*/
    @Test
    public void testGetID() {

        DefaultEdge edge = edgey.buildEdge();
        edge.setID(0);
        assertEquals(0,edge.getID());
    }

    @Test
    public void testSetNodes(){
        DefaultEdge edge = edgey.buildEdge();
        DefaultNode nodeOne = factory.buildNode();
        DefaultNode nodeTwo = factory.buildNode();

        edge.setN1(nodeOne);
        edge.setN2(nodeTwo);

        assertEquals(nodeOne,edge.getN1());
        assertEquals(nodeTwo,edge.getN2());

    }

    @Test
    public void testToString(){
        DefaultNode nodeOne = factory.buildNode();
        nodeOne.setID(0);
        DefaultNode nodeTwo = factory.buildNode();
        nodeTwo.setID(1);
        DefaultEdge edgar = edgey.buildEdge();
        edgar.setNodes(nodeOne,nodeTwo);
        String string = "Edge has attributes:" + "\n" + "ID: " + edgar.getID() + "\n" + "Node 1: " + nodeOne.getID() + "\n" + "Node 2: " + nodeTwo.getID();
        assertEquals(string,edgar.toString());
    }
}