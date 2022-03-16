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
     */
     @Test
    public void testTEXTBOX(){
         TextBoxNode test = factory.buildNode("TEXTBOX",1,2,3,4);
         test.saveIt();
         assertEquals(1,test.get("x_coord"));
         assertEquals(2,test.get("y_coord"));
         assertEquals(3,test.get("width"));
         assertEquals(4,test.get("height"));
         assertNotNull(test);
     }

    @Test
    public void testNOTE(){
        NoteNode test = factory.buildNode("NOTE",100,-1,1,1000);
        test.saveIt();
        assertEquals(100,test.get("x_coord"));
        assertEquals(-1,test.get("y_coord"));
        assertEquals(1,test.get("width"));
        assertEquals(1000,test.get("height"));
        assertNotNull(test);
    }

    /**
     * May need to add error checking to all nodes so they can't have  a height/width of 0
     */
    @Test
    public void testFOLDER(){
        FolderNode test = factory.buildNode("FOLDER",0,0,0,0);
        test.saveIt();
        assertEquals(0,test.get("x_coord"));
        assertEquals(0,test.get("y_coord"));
        assertEquals(0,test.get("width"));
        assertEquals(0,test.get("height"));
        assertNotNull(test);
    }

    @Test
    public void testSQUARE(){
        SquareNode test = factory.buildNode("SQUARE",1,1,1,1);
        test.saveIt();
        assertNotNull(test);
    }

    @Test
    public void testsSTICKFIGURE(){
        StickFigureNode test = factory.buildNode("STICKFIGURE",1,1,1,1);
        test.saveIt();
        assertNotNull(test);
    }

    @Test
    public void testOVAL(){
        OvalNode test = factory.buildNode("OVAL",1,1,1,1);
        test.saveIt();
        assertNotNull(test);
    }

    @Test
    public void testLIFELINE(){
        LifeLineNode test = factory.buildNode("LIFELINE",1,1,1,1);
        test.saveIt();
        assertNotNull(test);
    }

    @Test
    public void testLOOP(){
        LoopNode test = factory.buildNode("LOOP",1,1,1,1);
        test.saveIt();
        assertNotNull(test);
    }

    @Test
    public void testDefault(){
        DefaultNode node = factory.buildNode("test",1,2,3,4);
        assertNotNull(node);
    }
}
