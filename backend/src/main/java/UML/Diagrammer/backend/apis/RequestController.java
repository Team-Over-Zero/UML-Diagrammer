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
import UML.Diagrammer.backend.objects.EdgeFactory.DefaultEdge;
import UML.Diagrammer.backend.objects.EdgeFactory.EdgeFactory;
import UML.Diagrammer.backend.objects.EdgeFactory.NormalEdge;
import UML.Diagrammer.backend.objects.NodeFactory.*;
import UML.Diagrammer.backend.objects.*;
import UML.Diagrammer.backend.objects.tools.CustomJsonHelper;
import com.google.gson.*;
import org.javalite.activejdbc.Base;

import java.util.*;

import io.javalin.http.Context;
import org.javalite.activejdbc.LazyList;

public final class RequestController {

    private RequestController(){
    }

    /**
     * This method attaches to the /getanynode/ get request. It tries to find a node with the id {objectid} and the table name {type}
     * @param context implicitly passed in context
     */
    @SuppressWarnings({"UnnecessaryBreak", "DuplicateBranchesInSwitch"})
    public static void getAnyNode(Context context){
        String nodeStr = "";
        String idStr = context.queryParam("objectid");
        String typeStr = "";
             typeStr=   context.queryParam("type");

        int id = 0;
        try {
            id = Integer.parseInt(idStr); //object id
            
        }
        catch (ClassCastException ce){
            ce.printStackTrace();
            context.result("BAD NODE ID TYPE");
        }
        finally{
            
        }

        //This switch statement subjects us to the shotgun code problem. Ie adding a new node class should automatically
        //make the ability to query it, but instead has to be updated here. The problem is that there is no good way that
        //I can find to cast an object from a supertype to a subtype in a way that can store the "cast type" as a variable and then cast objects to that type. -Alex
        switch (typeStr) {
            case "default_nodes" -> {
                DefaultNode foundNode = DefaultNode.findById(id); //queries the default_nodes table for an object with the passed in id.
                nodeStr = foundNode.toJson(true);
                break;
            }
            case "class_nodes" -> {
                ClassNode foundNode = ClassNode.findById(id);
                nodeStr = foundNode.toJson(true);
                break;
            }
            case "folder_nodes" -> {
                FolderNode foundNode = FolderNode.findById(id);
                nodeStr = foundNode.toJson(true);
                break;
            }
            case "life_line_nodes" -> {
                LifeLineNode foundNode = LifeLineNode.findById(id);
                nodeStr = foundNode.toJson(true);
                break;
            }
            case "loop_nodes" -> {
                LoopNode foundNode = LoopNode.findById(id);
                nodeStr = foundNode.toJson(true);
                break;
            }
            case "note_nodes" -> {
                NoteNode foundNode = NoteNode.findById(id);
                nodeStr = foundNode.toJson(true);
                break;
            }
            case "oval_nodes" -> {
                OvalNode foundNode = OvalNode.findById(id);
                nodeStr = foundNode.toJson(true);
                break;
            }
            case "square_nodes" -> {
                SquareNode foundNode = SquareNode.findById(id);
                nodeStr = foundNode.toJson(true);
                break;
            }
            case "stick_figure_nodes" -> {
                StickFigureNode foundNode = StickFigureNode.findById(id);
                nodeStr = foundNode.toJson(true);
                break;
            }
            case "text_box_nodes" -> {
                TextBoxNode foundNode = TextBoxNode.findById(id);
                nodeStr = foundNode.toJson(true);
                break;
            }
        }

        context.result(nodeStr); //sends the json back as a response string.
    }

    /**
     * This method attaches to the /getanynode/ get request. It tries to find a node with the id {objectid} and the table name {type}
     * @param context implicitly passed in context
     */
    @SuppressWarnings({"UnnecessaryBreak", "DuplicateBranchesInSwitch"})
    public static void getAnyEdge(Context context) {
        String edgeStr = "";
        String idStr = context.queryParam("objectid");
        String typeStr = "";
        typeStr = context.queryParam("type");
        int id = 0;
        try {
            id = Integer.parseInt(idStr); //object id

        } catch (ClassCastException ce) {
            ce.printStackTrace();
            context.result("BAD NODE ID TYPE");
        }
        switch (typeStr) {
            case "default_edges" -> {
                DefaultEdge foundEdge = DefaultEdge.findById(id); //queries the default_nodes table for an object with the passed in id.
                edgeStr = foundEdge.toJson(true);
                break;
            }
            case "normal_edges" -> {
                NormalEdge foundEdge = NormalEdge.findById(id);
                edgeStr = foundEdge.toJson(true);
                break;
            }
        }
            context.result(edgeStr);
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
                //DefaultNode foundNode = new DefaultNode();
                DefaultNode foundNode = DefaultNode.findById(nodeId); //MESSY, FIX LATER -Alex
                nodeStr = foundNode.toJson(true);

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
     * Given a query param of the form /trycreatenode/?node={"json"} returns an id of an inialized edge or "-1" if bad input.
     * @param context
     */
    public static void tryCreateNode(Context context){
        String errJson = "{\"id\":\"-1\"}";
        String nodeJson = context.queryParam("node");

        try{

            JsonObject jsonObject = new Gson().fromJson(nodeJson, JsonObject.class);
            Set<Map.Entry<String, JsonElement>> testEntrySet = jsonObject.entrySet();
            String tableName = jsonObject.get("type").getAsString();
            NodeFactory nodeFactory = new NodeFactory();
           AbstractNode newNode=  nodeFactory.buildNode(tableName,0,0,0,0);
           newNode.createIt();
           System.out.println(newNode.saveIt());
           //entry.remove("id");
            for (Map.Entry<String, JsonElement> entry : testEntrySet) { //Sets the updateNode's values to be the hydrated node map's values
                //System.out.print("Key = {" + entry.getKey().toString() +"} "+", Value = {" + entry.getValue().toString()+"}");
                newNode.set(entry.getKey().replaceAll("\"", ""), entry.getValue().toString().replaceAll("\"", ""));
            }

            String idOfCreatedNode = newNode.getString("id");

            String jsonSuccess = "{\"id\":\""+idOfCreatedNode+"\"}";
            context.result(jsonSuccess);
        }
        catch (JsonSyntaxException jsonEx){
            jsonEx.printStackTrace();
            context.result(errJson);
        }

    }

    /**
     * Given a query param of the form /trycreateedge/?edge={"json"} returns an id of an initialized edge or "-1" if bad input.
     * @param context
     */
    public static void tryCreateEdge(Context context){
        String edgeJson = context.queryParam("edge");
        String errJson = "{\"id\":\"-1\"}";
        try{

            JsonObject jsonObject = new Gson().fromJson(edgeJson, JsonObject.class);
            Set<Map.Entry<String, JsonElement>> testEntrySet = jsonObject.entrySet();
            String tableName = jsonObject.get("type").getAsString();
            int fromNodeId = jsonObject.get("from_node_id").getAsInt();
            String fromNodeType = jsonObject.get("from_node_type").getAsString();
            int toNodeId = jsonObject.get("to_node_id").getAsInt();
            String toNodeType = jsonObject.get("to_node_type").getAsString();


            EdgeFactory edgeFactory = new EdgeFactory();
            AbstractEdge newEdge=  edgeFactory.buildEdge(tableName,fromNodeId,fromNodeType,toNodeId,toNodeType);
            newEdge.createIt();

            for (Map.Entry<String, JsonElement> entry : testEntrySet) { //Sets the updateNode's values to be the hydrated node map's values
                //System.out.print("Key = {" + entry.getKey().toString() +"} "+", Value = {" + entry.getValue().toString()+"}");
                newEdge.set(entry.getKey().replaceAll("\"", ""), entry.getValue().toString().replaceAll("\"", ""));
            }
            String idOfCreatedEdge = newEdge.getString("id"); //id of initialized edge

            String jsonSuccess = "{\"id\":\""+idOfCreatedEdge+"\"}";
            context.result(jsonSuccess);
        }
        catch (JsonSyntaxException jsonEx){
            jsonEx.printStackTrace();
            context.result(errJson);
        }
        catch(ClassCastException c){
            c.printStackTrace();
            context.result(errJson);
        }
        catch (Exception e){
            e.printStackTrace();
            context.result(errJson);
        }

    }

    /**
     * Given a query param of the form edge = "json", attempts to delete that edge
     * Takes queries in the form /deleteedge/?edge={}
     *
     * @param context
     */
    public static void deleteEdge(Context context) {

        String edgeJson = context.queryParam("edge");
        JsonObject jsonObject = new Gson().fromJson(edgeJson, JsonObject.class);
        String fromId = jsonObject.get("id").getAsString();
        String edgeType = jsonObject.get("id").getAsString();
        AbstractEdge updateEdge = null;

        LazyList<? extends AbstractEdge> dfList = switch (edgeType) {
            case "default_edges" -> DefaultEdge.where("id = ?", fromId);
            case "normal_edges" -> NormalEdge.where("id = ?", fromId);
        };

        try{
            if (dfList.size()==0){
                context.result("NOT FOUND");
            }
            else{
               dfList.get(0).delete();
                context.result("SUCCESS");
            }
        }
        catch (Exception n){
            n.printStackTrace();
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
        String nodeJson = context.queryParam("node"); //query param
        CustomJsonHelper testDeser = new CustomJsonHelper();
       //System.out.println(testDeser.outputObjNoId(nodeJson));

        try {
            //deserializes our gson as a json object rather than a direct object
            JsonObject jsonObject = new Gson().fromJson(nodeJson, JsonObject.class);
            //A set of key value pairs of attributes and attribute values from the json object.
            Set<Map.Entry<String, JsonElement>> testEntrySet = jsonObject.entrySet();

            //Since Activejdbc doesn't like serializing with objects we can cheat and get id directly from the json object.
            String fromId = jsonObject.get("id").getAsString(); //gets the id of the passed in object
            String nodeType = jsonObject.get("type").getAsString();

            //private String[] nodeTableArr = {"default_nodes","class_nodes","folder_nodes","life_line_nodes","loop_nodes","note_nodes","oval_nodes","square_nodes","stick_figure_nodes","text_box_nodes"};

            LazyList<? extends AbstractNode> dfList = switch (nodeType) {
                case "default_nodes" -> DefaultNode.where("id = ?", fromId);
                case "folder_nodes" -> FolderNode.where("id = ?", fromId);
                case "class_nodes" -> ClassNode.where("id = ?", fromId);
                case "life_line_nodes"->LifeLineNode.where("id = ?",fromId);
                case "loop_nodes"-> LoopNode.where("id = ?",fromId);
                case "note_nodes"-> NoteNode.where("id = ?",fromId);
                case "oval_nodes"-> OvalNode.where("id = ?",fromId);
                case "square_nodes"->SquareNode.where("id = ?",fromId);
                case "stick_figure_nodes"->StickFigureNode.where("id = ?",fromId);
                case "text_box_nodes"->TextBoxNode.where("id = ?",fromId);
                default -> DefaultNode.where("id = ?", fromId); //just the default list type.
                //There has to be a better way to specify Class type right?
            };

           System.out.println("Passed in edit request node: " + nodeJson);

            //This node is the node that we want to edit the values of.
            AbstractNode updateNode = null;
            try {
                if(dfList.size()>0) {
                    updateNode = dfList.get(0);
                }

                else{
                    context.result("node not found");
                }
            }
            catch (NullPointerException nullPointerException){
                nullPointerException.printStackTrace();
                context.result("node not found");
            }
           // System.out.println("Database Node Pre Update: " + updateNode.toJson(true));
            // System.out.println("Map: ");
            for (Map.Entry<String, JsonElement> entry : testEntrySet) { //Sets the updateNode's values to be the hydrated node map's values
                //System.out.print("Key = {" + entry.getKey().toString() +"} "+", Value = {" + entry.getValue().toString()+"}");
                updateNode.set(entry.getKey().replaceAll("\"", ""), entry.getValue().toString().replaceAll("\"", ""));
            }

            //String updatedJson = updateNode.toJson(true);
            //AbstractNode outputNode2 = new Gson().fromJson(updatedJson, nodeClass);
           // System.out.println("Database Node Post Update: " + updateNode.toJson(true));
            updateNode.saveIt();
            context.result("SUCCESS");
        }
        catch (JsonSyntaxException e){
            e.printStackTrace();
            context.result("BAD INPUT");
        }
        catch(Exception e){
            e.printStackTrace();
            context.result("GENERIC EXCEPTION");
        }
    }

    /**
     * Given a query param of the form node = "json", attempts to delete that node
     * Takes queries in the form /deletenode/?node={}
     *
     * @param context
     */
    public static void deleteNode(Context context) {
        String nodeJson = context.queryParam("node"); //query param
        JsonObject jsonObject = new Gson().fromJson(nodeJson, JsonObject.class);
        String fromId = jsonObject.get("id").getAsString(); //gets the id of the passed in object
        String nodeType = jsonObject.get("type").getAsString();

        LazyList<? extends AbstractNode> dfList = switch (nodeType) {
            case "default_nodes" -> DefaultNode.where("id = ?", fromId);
            case "folder_nodes" -> FolderNode.where("id = ?", fromId);
            case "class_nodes" -> ClassNode.where("id = ?", fromId);
            case "life_line_nodes"->LifeLineNode.where("id = ?",fromId);
            case "loop_nodes"-> LoopNode.where("id = ?",fromId);
            case "note_nodes"-> NoteNode.where("id = ?",fromId);
            case "oval_nodes"-> OvalNode.where("id = ?",fromId);
            case "square_nodes"->SquareNode.where("id = ?",fromId);
            case "stick_figure_nodes"->StickFigureNode.where("id = ?",fromId);
            case "text_box_nodes"->TextBoxNode.where("id = ?",fromId);
            default -> DefaultNode.where("id = ?", fromId); //just the default list type.
            //There has to be a better way to specify Class type right?
        };

        try {
            if(dfList.size()>0) {
                dfList.get(0).delete();
                context.result("SUCCESS");
            }
            else{
                context.result("NODE NOT FOUND");
            }
        }
        catch (NullPointerException n){
            n.printStackTrace();
            context.result("NODE NOT FOUND");
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

    /**
     * The following are not yet implemented
     */
    public static void createPage(int userId, String pageJson){

    }
    public static void deletePage(int pageId){

    }
    public static void addNodeToPage(int pageId, String nodeJson){

    }
    public static void removeNodeFromPage(int pageId, String edgeJson){
    }

    public static void addEdgeToPage(int pageId, String edgeJson){

    }
    public static void removeEdgeFromPage(int pageId, String nodeJson){

    }

    public static void createUser(String userJson){

    }
    public static void deleteUser(int userId){
    }
    public static void addUserToPage(int userId, int pageId){}

}
