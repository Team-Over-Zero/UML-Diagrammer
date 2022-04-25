import UML.Diagrammer.backend.objects.*;
import UML.Diagrammer.backend.objects.NodeFactory.*;
import UML.Diagrammer.backend.objects.UINode.*;
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

/**
 * These are the same tests that are found in Factory test but for the UI objects. The same tests should be okay
 * because they are extremely similar.
 * @Author Show, David, Alex
 * C1:node type, C2:width, C3:height
 */
public class UIFactoryTest extends DBSpec {

    UINodeFactory factory;
    @BeforeEach
    public void makeFactory(){
        factory = new UINodeFactory();
    }

    /*Testing factory for testFactory method*/

    @Test
    public void testFactorcyC1FALSEC2TRUEC3TRUE(){
        UIDefaultNode node2 = factory.buildNode("n/a",2,2,2,2);
    }


    @Test
    public void testFactory(){

        //assertTrue(testDB.hasConnection());
        UINode node = factory.buildNode();
        UIClassNode node2 = factory.buildNode("classnodes",2,2,2,2);
        String nameSet = "TESTFACTORY CLASSNODE";
        node2.setName(nameSet);
        assertEquals("DEFAULT NAME", node.getName());
        assertEquals("TESTFACTORY CLASSNODE", node2.getName());
        assertEquals(0,node.getX_coord());
        assertEquals(0,node.getY_coord());

    }

    /*Test factory's sizeNotBroken method*/
    @Test
    public void testSizeNotBroken(){
        UINode node = factory.buildNode();
        assertEquals(3,node.getHeight());
        assertEquals(3,node.getWidth());
    }

    /*Tests factory's setHeighteight*/
    @Test
    public void testsetHeighteight(){
        UINode node = factory.buildNode();
        node.setHeight(3);
        assertEquals(3,node.getHeight());
    }

    /*Tests factory's multiplesetHeighteights*/
    @Test
    public void multiplesetHeighteights(){
        UINode node = factory.buildNode();
        node.setHeight(2);
        node.setHeight(3);
        node.setHeight(1);
        assertEquals(1,node.getHeight());
    }

    /*Tests the factory's setCoords method in this function*/
    @Test
    public void testSetCoords(){
        UINode node = factory.buildNode();
        node.setX_coord(90);
        node.setY_coord(1234);
        assertEquals(90,node.getX_coord());
        assertEquals(1234,node.getY_coord());

    }

    /*Tests the factory's negativeCoords method in this function*/
    @Test
    public void testNegativeCoords(){
        UINode node = factory.buildNode();
        node.setX_coord(-1);
        node.setY_coord(-1);
        assertEquals(-1,node.getX_coord());
        assertEquals(-1,node.getY_coord());
    }

    /*Tests the factory's setSize method in this function*/
    @Test
    public void setSize(){
        UINode node = factory.buildNode();
        node.setHeight(20);
        node.setWidth(20);
        assertEquals(20,node.getWidth());
        assertEquals(20,node.getHeight());
    }

    /*Tests the factory's classNodeSubclass method in this function*/
    @Test
    public void testClassNodeSubclass(){
        UIClassNode node = factory.buildNode("classnodes", 0, 0, 3, 3);
        assertEquals("Class Name", node.getName());
        assertEquals("Class Description", node.getDescription());
        assertEquals(0, node.getX_coord());
        assertEquals(0, node.getY_coord());
        assertEquals(3, node.getWidth());
        assertEquals(3, node.getHeight());
    }

    /*Tests the factory's defaultNodeSubclass method in this function*/
    @Test
    public void testDefaultNodeSubclass(){
        UIDefaultNode node = factory.buildNode();
        assertEquals("DEFAULT NAME", node.getName());
        assertEquals("DEFAULT DESCRIPTION", node.getDescription());
        assertEquals(0, node.getX_coord());
        assertEquals(0, node.getY_coord());
        assertEquals(3, node.getWidth());
        assertEquals(3, node.getHeight());
    }

}
