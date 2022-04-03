
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



import static org.junit.jupiter.api.Assertions.*;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EdgeTest extends DBSpec{
    private NodeFactory factory;
    private EdgeFactory edgey;

    /*Edge testing for setup method in our Edge function*/
    @BeforeEach
    public void setup(){

//        //Base.open("com.mysql.cj.jdbc.Driver", "jdbc:mysql://localhost/test?serverTimezone=America/Denver", "root", "secret");
//        String databaseURL = "jdbc:mysql://ls-a9db0e6496e5430883b43e690a26b7676cf9d7af.cuirr4jp1g1o.us-west-2.rds.amazonaws.com/test";
//        String databaseUser = "root";
//        String databasePassword = "TeamOverZero";
//        Base.open("com.mysql.cj.jdbc.Driver", databaseURL, databaseUser, databasePassword);
        factory = new NodeFactory();
        edgey = new EdgeFactory();
        //DBConfiguration.loadConfiguration("/database.properties");
        //Base.open();
    }
    /*@AfterEach
    public void takeDown(){
        Base.close();
    }*/

    /*Testing for setNode method*/
    @Test
    public void testBasicEdge() {
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

        DefaultEdge edge = edgey.buildEdge();
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