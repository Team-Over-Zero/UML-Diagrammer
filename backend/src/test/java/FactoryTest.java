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

/**
 * This test class utilizes DBSpec. This means that every test method essentially opens a "transaction", which is a set of
 * mysql commands. A transaction allows you to test the placing of items in a database, then the calling of those items, while
 * not having to actually store the items there between tests. After a test method ends, the transaction is "reverted" automatically thanks to DBSpec.
 * You can read the DBSpec documentation for additional information.
 *
 * Note that DBspec searches the database.properties file for its connection information.
 *
 * @Author Mixed, DBSpec Implemented by Alex. Various tests Implemented by David.
 */
public class FactoryTest extends DBSpec {

    NodeFactory factory;
    @BeforeEach
    public void makeFactory(){
        factory = new NodeFactory();
    }

    /*Testing factory for testFactory method*/

    @Test
    public void testFactory(){

            //assertTrue(testDB.hasConnection());
            AbstractNode node = factory.buildNode();
            ClassNode node2 = factory.buildNode("classnodes",2,2,2,2);
            String nameSet = "TESTFACTORY CLASSNODE";
            node2.set("name",nameSet);
            node.saveIt();
            node2.saveIt();
            assertEquals("DEFAULT NAME",node.get("name"));
            assertEquals("TESTFACTORY CLASSNODE",node2.get("name"));
            assertEquals(0,node.get("x_coord"));
            assertEquals(0,node.get("y_coord"));

    }

    /*Test factory's sizeNotBroken method*/
    @Test
    public void testSizeNotBroken(){
        AbstractNode node = factory.buildNode();
        node.createIt();
        assertEquals(3,node.get("height"));
        assertEquals(3,node.get("width"));
    }

    /*Tests factory's setHeight*/
    @Test
    public void testSetHeight(){
        AbstractNode node = factory.buildNode();
        node.set("height",3);
        node.saveIt();
        assertEquals(3,node.get("height"));
    }

    /*Tests factory's multipleSetHeights*/
    @Test
    public void multipleSetHeights(){
        AbstractNode node = factory.buildNode();
        node.set("height",2);
        node.set("height",3);
        node.set("height",1);
        node.saveIt();
        assertEquals(1,node.get("height"));
    }

    /*Tests the factory's setCoords method in this function*/
    @Test
    public void testSetCoords(){
        AbstractNode node = factory.buildNode();
        node.createIt();
        node.setCoords(90,1234);
        node.saveIt();
        assertEquals(90,node.get("x_coord"));
        assertEquals(1234,node.get("y_coord"));

    }

    /*Tests the factory's negativeCoords method in this function*/
    @Test
    public void testNegativeCoords(){

        AbstractNode node = factory.buildNode();
        node.createIt();
        node.setCoords(-1,-1);
        node.saveIt();
        assertEquals(-1,node.get("x_coord"));
        assertEquals(-1,node.get("y_coord"));
    }

    /*Tests the factory's setSize method in this function*/
    @Test
    public void setSize(){
        AbstractNode node = factory.buildNode();
        node.setSize(20,20);
        assertEquals(20,node.get("width"));
        assertEquals(20,node.get("height"));
    }

    /**
     * ClassNode subclass test
     * You can reference what type of classes there are in NodeFactory.java
     * To make different node types simply call 'NODETYPE node = factory.buildNode(NODETYPE, rest of params)'
     * The switch block in NodeFactory class tells the different strings to call other subclasses.
     * @author Show
     */

    /*Tests the factory's classNodeSubclass method in this function*/
    @Test
    public void testClassNodeSubclass(){
        ClassNode node = factory.buildNode("classnodes", 0, 0, 3, 3);
        assertEquals("Class Name", node.get("name"));
        assertEquals("Class Description", node.get("description"));
        assertEquals(0, node.get("x_coord"));
        assertEquals(0, node.get("y_coord"));
        assertEquals(3, node.get("width"));
        assertEquals(3, node.get("height"));
    }

    /**
     * Default constructor test
     * @author Show
     */

    /*Tests the factory's defaultNodeSubclass method in this function*/
    @Test
    public void testDefaultNodeSubclass(){
        DefaultNode node = factory.buildNode();
        assertEquals("DEFAULT NAME", node.get("name"));
        assertEquals("DEFAULT DESCRIPTION", node.get("description"));
        assertEquals(0, node.get("x_coord"));
        assertEquals(0, node.get("y_coord"));
        assertEquals(3, node.get("width"));
        assertEquals(3, node.get("height"));
    }

}
