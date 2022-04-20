
import UML.Diagrammer.backend.objects.NodeFactory.DefaultNode;
import UML.Diagrammer.backend.objects.NodeFactory.NodeFactory;
import UML.Diagrammer.backend.objects.UIEdge.UIEdge;
import UML.Diagrammer.backend.objects.UIEdge.UIEdgeFactory;
import UML.Diagrammer.backend.objects.UINode.UINode;
import UML.Diagrammer.backend.objects.UINode.UINodeFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import static org.junit.jupiter.api.Assertions.*;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UIEdgeTest {
    private UIEdge edge;
    private UIEdgeFactory ed;
    private UINode node;
    private UINode node2;
    private UINodeFactory nf;
    @BeforeEach
    public void setUp(){
        ed = new UIEdgeFactory();
        nf = new UINodeFactory();
        edge = ed.buildEdge();
        node = nf.buildNode();
        node.setId(1);

        node2 = nf.buildNode();

        node2.setId(2);

    }

    @Test
    public void testSetN1SetN2(){
        edge.setN1(node);
        edge.setN2(node2);
        assertEquals(node,edge.getN1());
        assertEquals(node2,edge.getN2());

    }
    @Test
    public void testToString(){
        edge.setId(0);
        edge.setN1(node);
        edge.setN2(node2);
        assertEquals("Edge has attributes:" + "\n" +
                "ID: " + "0" + "\n" +
                "Node 1 ID: " + "1" + "\n" +
                "Node 2 ID: " + "2" + "\n" +
                "Node 1 Type: "+ "defaultnodes" + "\n" +
                "Node 2 Type: "+ "defaultnodes",edge.toString());

    }

    @Test
    public void testGetEdgeAsJson(){
        edge.setId(0);
        edge.setN1(node);
        edge.setN2(node2);
        assertEquals("{\"id\":\"" + "0" + "\"," +
                "\"type\":\"" + "default_edges" + "\"," + // I only use normal edges for UI. Otherwise, I would need to add a type to edge.
                "\"from_node_id\":\"" +"1" + "\"," +
                "\"from_node_type\":\"" + "defaultnodes" + "\"," +
                "\"to_node_id\":\"" + "2" + "\"," +
                "\"to_node_type\":\"" + "defaultnodes" + "\"" +
                "}",edge.getEdgeAsJSon());
    }

}
