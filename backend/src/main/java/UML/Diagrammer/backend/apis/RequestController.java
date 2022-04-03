/*Copyright 2022 Team OverZero
<p>
Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
documentation files (the "Software"), to deal in the Software without restriction, including without limitation
the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and
to permit persons to whom the Software is furnished to do so, subject to the following conditions:
<p>
The above copyright notice and this permission notice shall be included in all copies or substantial portions of
the Software.
<p>
THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.*/
/** RequestController.java
 *
 * This class deals with the bodies of the Database_Client's get and post request listener handlers.
 * @author Alex
 */



package UML.Diagrammer.backend.apis;
import UML.Diagrammer.backend.objects.NodeFactory.*;
import UML.Diagrammer.backend.objects.*;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import org.javalite.activejdbc.Base;

import java.lang.reflect.Type;
import java.util.*;

import io.javalin.http.Context;
import org.javalite.activejdbc.LazyList;
import org.javalite.activejdbc.Model;
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
                //DefaultNode testNode =  nf.buildNode();
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
     * Given a query param of the form /trycreatenode/?node={"json"}
     * @param context
     */
    public static void tryCreateNode(Context context){
        String nodeJson = context.queryParam("node");

        try{

            JsonObject jsonObject = new Gson().fromJson(nodeJson, JsonObject.class);
            Set<Map.Entry<String, JsonElement>> testEntrySet = jsonObject.entrySet();
            String tableName = jsonObject.get("type").getAsString();
            NodeFactory nodeFactory = new NodeFactory();
           AbstractNode newNode=  nodeFactory.buildNode(tableName,0,0,0,0);
           newNode.createIt();
           System.out.println(newNode.saveIt());
            for (Map.Entry<String, JsonElement> entry : testEntrySet) { //Sets the updateNode's values to be the hydrated node map's values
                //System.out.print("Key = {" + entry.getKey().toString() +"} "+", Value = {" + entry.getValue().toString()+"}");
                newNode.set(entry.getKey().replaceAll("\"", ""), entry.getValue().toString().replaceAll("\"", ""));
            }

            String idOfCreatedNode = newNode.getString("id");

            context.result(idOfCreatedNode);
        }
        catch (JsonSyntaxException jsonEx){
            jsonEx.printStackTrace();
            context.result("-1");
        }

    }


    /**
     * Given a query param of the form node = "json", attempts to update an existing node with all the attributes in the passed in node.
     * Takes queries in the form /updatenode/?node={}
     *
     * Alex Note: This Method currently has a bunch of junk code, but I can't remove it until I figure out how reflection actually works.
     * @param context
     */
    public static void updateNode(Context context) {

        String nodeJson = context.queryParam("node");
        //initializes deserializers and lets them know to associate "type" attributes with classes.
        NodeTypeDeserializer customNodeDeserializer = new NodeTypeDeserializer("type");
       //EdgeTypeDeserializer customEdgeDeserializer = new EdgeTypeDeserializer("type");
        //Our Type Registry Object holds all the types of nodes and edges tables that we want to map to classes.
        TypeRegistry typeRegistry = TypeRegistry.getInstance();

        //These are helpers that can attempt to get classes from table names.
        ArrayList<String> registryNodeList = typeRegistry.getTableNodeList();
        HashMap<String,Class> registryNodeMap = typeRegistry.getNodeClassMap();
        //ArrayList<String> registryEdgeList = typeRegistry.getTableEdgeList();
        //HashMap<String,Class> registryEdgeMap = typeRegistry.getEdgeClassMap();

        //registers our node tables
        for (String n:registryNodeList) {
            customNodeDeserializer.registerSubtype(n,registryNodeMap.get(n)); //Registers our class and associates it with a node type.
        }

        //registers our edge tables
//        for (String e: registryEdgeList){
//            customEdgeDeserializer.registerSubtype(e,registryEdgeMap.get(e));
//        }

        Gson nodeBuilder = new GsonBuilder()
                .registerTypeAdapter(AbstractNode.class,customNodeDeserializer)
                .create();

//        Gson edgeBuilder = new GsonBuilder()
//                .registerTypeAdapter(AbstractEdge.class,customEdgeDeserializer)
//                .create();

        try {
            //deserializes our gson as a json object rather than a direct object
            JsonObject jsonObject = new Gson().fromJson(nodeJson, JsonObject.class);
            //A set of key value pairs of attributes and attribute values from the json object.
            Set<Map.Entry<String, JsonElement>> testEntrySet = jsonObject.entrySet();

            //Since Activejdbc doesn't like sererializing with objects we can cheat and get id directly from the json object.
            String fromId = jsonObject.get("id").getAsString(); //gets the id of the passed in object
            String nodeType = jsonObject.get("type").getAsString();
            //Class<AbstractNode> nodeClass = registryNodeMap.get(nodeType);
            //Type testType = new TypeToken<>(){}.getType();
            //instantiates the object (no Id).
            // genericNode fromJsonNode = nodeBuilder.fromJson(nodeJson, new TypeToken<AbstractNode>(){}.getType()); //has no Id?
            //System.out.println(fromJsonNode.getClass());
            //fromJsonNode.getClass();

            LazyList<? extends AbstractNode> dfList = switch (nodeType) {
                case "default_nodes" -> DefaultNode.where("id = ?", fromId);
                case "folder_nodes" -> FolderNode.where("id = ?", fromId);
                case "class_nodes" -> ClassNode.where("id = ?", fromId);
                default -> DefaultNode.where("id = ?", fromId); //just the default list type.
                //There has to be a better way to specify Class type right?
            };

            System.out.println("Passed in edit request node: " + nodeJson);

            //This node is the node that we want to edit the values of.
            AbstractNode updateNode = dfList.get(0);
            System.out.println("Database Node Pre Update: " + updateNode.toJson(true));

            // System.out.println("Map: ");
            for (Map.Entry<String, JsonElement> entry : testEntrySet) { //Sets the updateNode's values to be the hydrated node map's values
                //System.out.print("Key = {" + entry.getKey().toString() +"} "+", Value = {" + entry.getValue().toString()+"}");
                updateNode.set(entry.getKey().replaceAll("\"", ""), entry.getValue().toString().replaceAll("\"", ""));
            }

            //String updatedJson = updateNode.toJson(true);
            //AbstractNode outputNode2 = new Gson().fromJson(updatedJson, nodeClass);
            System.out.println("Database Node Post Update: " + updateNode.toJson(true));
            updateNode.saveIt();
            context.result("success");
        }
        catch (JsonSyntaxException e){
            e.printStackTrace();
            context.result("failure");
        }
        catch(Exception e){
            e.printStackTrace();
            context.result("Generic Exception");
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
     * attaches to the /testpostnode/ post request. looks for the name query parameter and then creates a new node
     * with that parameter and saves it to the database. This is a dev tool, not how we would actually build nodes
     * @param context implicitly passed in context
     */
    public static void createTestDefaultNodeWithPost(Context context){
        NodeFactory nf = new NodeFactory();
        DefaultNode testNode = nf.buildNode();
        testNode.set("name",context.queryParam("name")); //sets the name of the node based on a ? query.
        testNode.saveIt();
        context.result(testNode.getString("name"));
    }
}
