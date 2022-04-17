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
 */
public class UIFactoryTest extends DBSpec {

    UINodeFactory factory;
    @BeforeEach
    public void makeFactory(){
        factory = new UINodeFactory();
    }

    /*Testing factory for testFactory method*/

    @Test
    public void testFactory(){

        //assertTrue(testDB.hasConnection());
        UINode node = factory.buildNode();
        UIClassNode node2 = factory.buildNode("classnodes",2,2,2,2);
        String nameSet = "TESTFACTORY CLASSNODE";
        node2.setName(nameSet);
        assertEquals("DEFAULT NAME", node.getName());
        assertEquals("TESTFACTORY CLASSNODE", node2.getName());
        assertEquals(0,node.getX());
        assertEquals(0,node.getY());

    }

    /*Test factory's sizeNotBroken method*/
    @Test
    public void testSizeNotBroken(){
        UINode node = factory.buildNode();
        assertEquals(3,node.getH());
        assertEquals(3,node.getW());
    }

    /*Tests factory's setHeight*/
    @Test
    public void testSetHeight(){
        UINode node = factory.buildNode();
        node.setH(3);
        assertEquals(3,node.getH());
    }

    /*Tests factory's multipleSetHeights*/
    @Test
    public void multipleSetHeights(){
        UINode node = factory.buildNode();
        node.setH(2);
        node.setH(3);
        node.setH(1);
        assertEquals(1,node.getH());
    }

    /*Tests the factory's setCoords method in this function*/
    @Test
    public void testSetCoords(){
        UINode node = factory.buildNode();
        node.setX(90);
        node.setY(1234);
        assertEquals(90,node.getX());
        assertEquals(1234,node.getY());

    }

    /*Tests the factory's negativeCoords method in this function*/
    @Test
    public void testNegativeCoords(){
        UINode node = factory.buildNode();
        node.setX(-1);
        node.setY(-1);
        assertEquals(-1,node.getX());
        assertEquals(-1,node.getY());
    }

    /*Tests the factory's setSize method in this function*/
    @Test
    public void setSize(){
        UINode node = factory.buildNode();
        node.setH(20);
        node.setW(20);
        assertEquals(20,node.getW());
        assertEquals(20,node.getH());
    }

    /*Tests the factory's classNodeSubclass method in this function*/
    @Test
    public void testClassNodeSubclass(){
        UIClassNode node = factory.buildNode("classnodes", 0, 0, 3, 3);
        assertEquals("Class Name", node.getName());
        assertEquals("Class Description", node.getDesc());
        assertEquals(0, node.getX());
        assertEquals(0, node.getY());
        assertEquals(3, node.getW());
        assertEquals(3, node.getH());
    }

    /*Tests the factory's defaultNodeSubclass method in this function*/
    @Test
    public void testDefaultNodeSubclass(){
        UIDefaultNode node = factory.buildNode();
        assertEquals("DEFAULT NAME", node.getName());
        assertEquals("DEFAULT DESCRIPTION", node.getDesc());
        assertEquals(0, node.getX());
        assertEquals(0, node.getY());
        assertEquals(3, node.getW());
        assertEquals(3, node.getH());
    }

}
