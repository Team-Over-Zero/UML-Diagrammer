import UML.Diagrammer.backend.objects.*;
import UML.Diagrammer.backend.objects.EdgeFactory.DefaultEdge;
import UML.Diagrammer.backend.objects.EdgeFactory.EdgeFactory;
import UML.Diagrammer.backend.objects.NodeFactory.*;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.DB;
import org.javalite.activejdbc.test.DBSpec;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.javalite.activejdbc.connection_config.DBConfiguration;
import static org.junit.jupiter.api.Assertions.*;
public class NodeFactoryTest extends DBSpec{
    private NodeFactory factory;
     @BeforeEach
    public void setUp(){
         factory = new NodeFactory();
     }

    /**
     * Basic tests to ensure objects are being created correctly
     * Since they all share the same attributes the assertions are repeated
     * C1: Coords, C2: height, C3: width, C4:TableName
     */

    /**
     * This test should capture incorrect behavior witht the generation of a node with any bad info
     *
     */
    @Test
    public void testC1TrueC2FalseC3TrueC4True(){

        TextBoxNode test = factory.buildNode("text_box_nodes",1,2,3,4);
        Exception ex = assertThrows(IllegalArgumentException.class,()->{
            test.setSize(-1,0);});
        assertEquals(IllegalArgumentException.class,ex.getClass());
    }

    /**
     * Ensures that a default node is made when given a bad table name
     */
    @Test
    public void testC1TrueC2TrueC3TrueC4False(){
        DefaultNode n  = factory.buildNode("fakeTable",1,2,3,4);
    }





     @Test
    public void testTEXTBOX(){
         TextBoxNode test = factory.buildNode("textboxnodes",1,2,3,4);
         test.saveIt();
         assertEquals(1,test.get("x_coord"));
         assertEquals(2,test.get("y_coord"));
         assertEquals(3,test.get("width"));
         assertEquals(4,test.get("height"));
         assertNotNull(test);
     }

    @Test
    public void testNOTE(){
        NoteNode test = factory.buildNode("notenodes",100,-1,1,1000);
        test.saveIt();
        assertEquals(100,test.get("x_coord"));
        assertEquals(-1,test.get("y_coord"));
        assertEquals(1,test.get("width"));
        assertEquals(1000,test.get("height"));
        assertNotNull(test);
    }


    public void testFOLDER(){
        FolderNode test = factory.buildNode("foldernodes",0,0,0,0);
        test.saveIt();
        assertEquals(0,test.get("x_coord"));
        assertEquals(0,test.get("y_coord"));
        assertEquals(0,test.get("width"));
        assertEquals(0,test.get("height"));
        assertNotNull(test);
    }

    @Test
    public void testSQUARE(){
        SquareNode test = factory.buildNode("squarenodes",1,1,1,1);
        test.saveIt();
        assertNotNull(test);
    }

    @Test
    public void testsSTICKFIGURE(){
        StickFigureNode test = factory.buildNode("stickfigurenodes",1,1,1,1);
        test.saveIt();
        assertNotNull(test);
    }

    @Test
    public void testOVAL(){
        OvalNode test = factory.buildNode("ovalnodes",1,1,1,1);
        test.saveIt();
        assertNotNull(test);
    }

    @Test
    public void testLIFELINE(){
        LifeLineNode test = factory.buildNode("lifelinenodes",1,1,1,1);
        test.saveIt();
        assertNotNull(test);
    }

    @Test
    public void testLOOP(){
        LoopNode test = factory.buildNode("loopnodes",1,1,1,1);
        test.saveIt();
        assertNotNull(test);
    }

    @Test
    public void testDefault(){
        DefaultNode node = factory.buildNode();
        assertNotNull(node);
    }
}
