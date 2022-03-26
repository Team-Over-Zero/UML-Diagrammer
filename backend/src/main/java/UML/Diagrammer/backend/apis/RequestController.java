/** RequestController.java
 *
 * This class deals with the bodies of the Database_Client's get and post request listener handlers.
 * @author Alex
 */



package UML.Diagrammer.backend.apis;
import UML.Diagrammer.backend.objects.NodeFactory.*;
import UML.Diagrammer.backend.objects.*;
import com.google.gson.Gson;
import org.javalite.activejdbc.Base;

import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import io.javalin.http.Context;
import org.javalite.activejdbc.connection_config.DBConfiguration;
import org.w3c.dom.Node;

public final class RequestController {

    private RequestController(){
    }

    /**
     * This method attaches to the /defaultnodenameid/{objectid} get request. It tries to find a node in the default_nodes table
     * @param context implicitly passed in context
     */
    public static void getNodeNameWithId(Context context){
        //context.status(500);
        //int id = Integer.valueOf(idStr);
        Gson gson = new Gson();
        String nodeStr = null;
        String idStr = context.pathParam("objectid");
        int id = 0;
        try {
            id = Integer.valueOf(idStr);
        }
        catch (ClassCastException ce){
            ce.printStackTrace();
        }
        finally{
          //  id = 404;
        }
        //System.out.println(id);
        List<Map> test = Base.findAll("use dev; SELECT * FROM default_nodes where id = " + "'"+ idStr+ "'"+ ";"); //raw sql query that returns a map

        try {
            for (Map t:test) {
                System.out.println("Map: " +t.toString() +" keyset:" +t.keySet().toString());
            }
            nodeStr = gson.toJson(test.get(0).get("name")); //tries to parse the map for a default node object and serializes it
            System.out.println("The node is: " +nodeStr);
        }
        catch (NullPointerException nulEx){
            System.out.println("A node with the specified ID could not be found.");
            nulEx.printStackTrace();
        }
        //testNode.saveIt(); //Initialization already saves the object so we don't need to handle this.
        if(nodeStr!=null) {
            context.result(nodeStr);
        }
        else{
            context.status(405);
            context.result("NODE NOT FOUND");
        }
    }


    /**
     * This method attaches to the /getdefaultnode/{objectid} get request. It will return a json string representation
     * of a found default node with the given id, or will throw an error code. (405).
     * @param context implicitly passed in context
     */
    public static void getDefaultNode(Context context){
        {

            String nodeIdStr = context.pathParam("objectid");
            int nodeId = Integer.valueOf(nodeIdStr);
            String nodeStr;
            try {
                NodeFactory nf = new NodeFactory();
                DefaultNode testNode =  nf.buildNode();
                //DefaultNode testNode2 = new DefaultNode();
                DefaultNode testNode2 = DefaultNode.findById(nodeId); //MESSY, FIX LATER -Alex
                nodeStr = testNode2.toJson(true);

            }
            catch(Exception il){
                il.printStackTrace();
                nodeStr = null;
            }
            if (nodeStr != null) {
                context.result(nodeStr); //returns the object back to the client.
            } else {
                context.status(405);
            }
        }
    }

    /**
     * Given a two param object get request with id and table name as parameters, attempts to send back an object.
     * attaches to the /getobject/ get request. unlike the path params {} this takes implicit user defined params.
     * These would be structured in the url like : .../getobject/?objectid=foo&objecttable=bar in this method.
     *
     * Note that this will actually return a list of maps not an object.
     * @param context
     */
    public static void getObjsAsMapWithIdandTable(Context context) {
        Gson gson = new Gson();
        String objectId = context.queryParam("objectid");
        String tableName = context.queryParam("objecttable");
        context.status(405);

        List<Map> resultSet = Base.findAll(" SELECT * FROM "+tableName +" WHERE id = "+objectId+";");
        try {
            String rezStr = "";

            ListIterator<Map> lm = resultSet.listIterator();
            while (lm.hasNext()){
                rezStr += lm.next().toString();
            }
            context.result(rezStr);
        }
        catch(NullPointerException np){
            np.printStackTrace();
            System.out.println("No node found");
        }

    }

    /**
     * attaches to the /testpostcode/ post request. looks for the name query parameter and then creates a new node
     * with that parameter and saves it to the database. This is a dev tool, not how we would actually build nodes
     * @param context implicitly passed in context
     */
    public static void createTestDefaultNodeWithPost(Context context){
        NodeFactory nf = new NodeFactory();
        DefaultNode testNode = nf.buildNode();
        testNode.set("name",context.queryParam("name")); //sets the name of the node based on a ? query.
        testNode.saveIt();
        context.result(testNode.toString());
    }
}
