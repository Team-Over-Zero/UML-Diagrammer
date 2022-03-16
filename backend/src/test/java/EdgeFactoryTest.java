import UML.Diagrammer.backend.objects.EdgeFactory.EdgeFactory;
import UML.Diagrammer.backend.objects.EdgeFactory.NormalEdge;
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
public class EdgeFactoryTest extends DBSpec {
    private EdgeFactory factory;
    private NodeFactory nodeFactory;

    @BeforeAll
    public void setUp(){
        factory = new EdgeFactory();
        nodeFactory= new NodeFactory();
    }

    @Test
    public void testNormalEdge(){
        DefaultNode node = nodeFactory.buildNode();
        DefaultNode node2 = nodeFactory.buildNode();
        NormalEdge edge = factory.buildEdge(node,node2);
        assertEquals(node,edge.getN1());
        assertEquals(node2,edge.getN2());
    }


}
