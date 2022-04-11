/**
 * NodeTest.java
 *
 * This class tests node creation.
 */


import UML.Diagrammer.backend.objects.*;
import UML.Diagrammer.backend.objects.NodeFactory.ClassNode;
import UML.Diagrammer.backend.objects.NodeFactory.DefaultNode;
import UML.Diagrammer.backend.objects.NodeFactory.NodeFactory;
import UML.Diagrammer.backend.objects.tools.NodeTypeDeserializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.javalite.activejdbc.test.DBSpec;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
public class NodeTest extends DBSpec {

    private NodeFactory factory;

    private AbstractNode node;

    /**
     * @author Alex
     * Currently, this class does not implement DBSpec. This is to illustrate the difference between how a normal test and a
     * dbspec test works. This does NOT use the junit database for its calls but instead the dev database. This means that data can
     * persist. Additional work would have to be done by the tester to remove created rows. Should deprecate this soon, as we dont want test data being
     * stored in the same place as user data.
     */
    @BeforeEach
    public void setup(){

        //DBConfiguration.loadConfiguration("/database.properties"); //Loads our default development configuration. NOT our test configuration.
        //Base.open();
        factory = new NodeFactory();
        node = factory.buildNode();

    }

    /**
     * @author Alex
     */
    /*@AfterEach
    public void tearDown(){
        Base.close();
    }*/
    /*Node test for setCoords method*/
    @Test
    public void setCoords() {
        node.setCoords(1,1);
        node.setCoords(123,123);
        node.saveIt();
        assertEquals(123,node.get("x_coord"));
        assertEquals(123,node.get("y_coord"));
    }

    /**
     * Test for the getID method
     * Alex Note: be careful setting ID's. Probably should check if a row exists first.
     */
    @Test
    public void getID() {
        node.setId(1000);
        node.setId(9999);
        node.saveIt();

        assertEquals(9999,node.getId());
    }

    /*Testing for the getDescription method*/
    @Test
    public void setDescription() {


        node.set("description","This should be gettable");
        String s = (String)node.get("description"); //Can't guarantee that get returns a string so we have to cast it.
        assertEquals("This should be gettable",s);
    }

    /*Test Node's hashCode method*/
    @Test
    public void testHashCode() {

    }

    @Test
    /**
     * Based off of : https://www.baeldung.com/gson-list
     * @Author Alex
     * ALEX NOTE: Can someone figure out how to do TypeTokens of a list? ie. TypeToken<List<AbstractNode>>
     */
    public void nodeHydrationTest(){
        //node.set("id",1);
        node.saveIt();
        System.out.println("id="+node.getId());
        String gsonStrDefNode = node.toJson(true);
        ClassNode classNode = factory.buildNode("class_nodes",2,1,1,1);
        String gsonStrClassNode = classNode.toJson(false);

        List<String> gsonStrList = new ArrayList<String>();
        gsonStrList.add(gsonStrDefNode);
        gsonStrList.add(gsonStrClassNode);

        NodeTypeDeserializer customNodeDeserializer = new NodeTypeDeserializer("type");

        customNodeDeserializer.registerSubtype( "default_nodes",DefaultNode.class);
        customNodeDeserializer.registerSubtype( "class_nodes", ClassNode.class);

        Gson gBuilder = new GsonBuilder()
                .registerTypeAdapter(AbstractNode.class,customNodeDeserializer)
                .create();


        List<AbstractNode> aList= new ArrayList<>();

        for (String n : gsonStrList) {
            aList.add(gBuilder.fromJson(n, new TypeToken<AbstractNode>(){}.getType())); //adds a hydrated string to our list
            System.out.println(n.toString());
        }
                //gBuilder.fromJson(arrStr, new TypeToken<AbstractNode>(){}.getType());
       // assertEquals("{}",gsonStrDefNode);
        assertTrue(aList.get(0) instanceof DefaultNode);
        assertTrue(aList.get(1) instanceof ClassNode);

    }
    }
