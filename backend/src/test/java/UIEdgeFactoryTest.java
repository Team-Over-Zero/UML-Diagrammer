import UML.Diagrammer.backend.objects.EdgeFactory.EdgeFactory;
import UML.Diagrammer.backend.objects.EdgeFactory.NormalEdge;
import UML.Diagrammer.backend.objects.UIEdge.*;
import UML.Diagrammer.backend.objects.UINode.*;
import org.javalite.activejdbc.test.DBSpec;

import UML.Diagrammer.backend.objects.*;
import UML.Diagrammer.backend.objects.NodeFactory.*;
import org.javalite.activejdbc.DB;
import org.javalite.activejdbc.InitException;
import org.javalite.activejdbc.test.DBSpec;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.DB;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UIEdgeFactoryTest extends DBSpec {
    private UIEdgeFactory factory;
    private UINodeFactory nodeFactory;

    @BeforeAll
    public void setUp(){
        factory = new UIEdgeFactory();
        nodeFactory= new UINodeFactory();
    }

    @Test
    public void testNormalEdge(){
        UIDefaultNode node = nodeFactory.buildNode();
        UIDefaultNode node2 = nodeFactory.buildNode();
        UINormalEdge edge = factory.buildEdge("normaledges", node, node2);
        assertEquals( node.getId(), edge.getN1().getId());
        assertEquals((node2.getId()), edge.getN2().getId());
    }


}
