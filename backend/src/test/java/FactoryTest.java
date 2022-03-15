import UML.Diagrammer.backend.objects.*;
import UML.Diagrammer.backend.objects.NodeFactory.*;
import org.javalite.activejdbc.DB;
import org.javalite.activejdbc.InitException;
import org.javalite.activejdbc.test.DBSpec;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.javalite.activejdbc.Base;
//import org.javalite.activejdbc.test.DBSpec;
import org.javalite.activejdbc.DB;



import static org.junit.jupiter.api.Assertions.*;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FactoryTest extends DBSpec {

    NodeFactory factory;
    @BeforeEach
    public void makeFactory(){
        //NOTE: Griffith uses com.mysql.cj.jdbc.Driver NOT com.mysql.jdbc.Driver
        factory = new NodeFactory();
    }

    /**
     * This method opens a connection to our test database. Note that you need to be running MYSQL on your local machine
     * Your mysql must have the username "root" (which is docker's default) and the password "secret".
     * You should also use the sql command "create schema test;" to create your schema. (database).
     * @author Alex
     */
    @BeforeEach
    public void openConnection(){

        //DB testDB = new DB("default"); Alex Note: Need to add a database.configuration file at some point to do it the "right" way.
//        Base.open("com.mysql.cj.jdbc.Driver", "jdbc:mysql://localhost/test?serverTimezone=America/Denver", "root", "secret");
//        String databaseURL = "jdbc:mysql://ls-a9db0e6496e5430883b43e690a26b7676cf9d7af.cuirr4jp1g1o.us-west-2.rds.amazonaws.com/test";
//        String databaseUser = "root";
//        String databasePassword = "TeamOverZero";
//        Base.open("com.mysql.cj.jdbc.Driver", databaseURL, databaseUser, databasePassword);
    }

    @AfterEach
    public void closeConnection(){
        //new DB("default").rollbackTransaction();
//        Base.close();
    }

    /*Testing factory for testFactory method*/

    @Test
    public void testFactory(){

            //assertTrue(testDB.hasConnection());
            AbstractNode node = factory.buildNode();
            ClassNode node2 = factory.buildNode("CLASS",2,2,2,2);
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
        node.setCoords(90,1234);
        node.saveIt();
        assertEquals(90,node.get("x_coord"));
        assertEquals(1234,node.get("y_coord"));

    }

    /*Tests the factory's negativeCoords method in this function*/
    @Test
    public void testNegativeCoords(){

        AbstractNode node = factory.buildNode();
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
        ClassNode node = factory.buildNode("CLASS", 0, 0, 3, 3);
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
