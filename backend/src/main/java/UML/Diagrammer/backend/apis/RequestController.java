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
import org.javalite.activejdbc.associations.NotAssociatedException;
import org.javalite.common.JsonHelper;

public final class RequestController {
    public static final String nullParams = "ERROR: NULL PARAMETERS";
    public static final String genericException = "GENERIC EXCEPTION";
    public static final String successMsg = "SUCCESS";
    public static final String nodeNotFoundErr = "ERROR: NODE NOT FOUND";
    private RequestController() {
    }

    /**
     * This method attaches to the /getanynode/ get request. It tries to find a node with the id {objectid} and the table name {type}
     *
     * @param context implicitly passed in context
     */
    @SuppressWarnings({"UnnecessaryBreak", "DuplicateBranchesInSwitch"})
    public static void getAnyNode(Context context) {
        String nodeStr = "";
        String idStr = context.queryParam("objectid");
        String typeStr = "";
        typeStr = context.queryParam("type");

        int id = 0;
        try {
            id = Integer.parseInt(idStr); //object id

        } catch (ClassCastException ce) {
            ce.printStackTrace();
            context.result("BAD NODE ID TYPE");
        } finally {

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
     *
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
     *
     * @param context implicitly passed in context
     */
    public static void getDefaultNode(Context context) {
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

            } catch (Exception il) {
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
     *
     * @param context
     */
    public static void tryCreateNode(Context context) {
        String nodeJson = context.queryParam("node");
        if(nodeJson!=null) {
            String retJson = createNode(nodeJson);
            context.result(retJson);
        }
        else{
            context.status(499);
            context.result(nullParams);
        }
    }

    /**
     * This does the real grunt work of tryCreateNode.
     * @param nodeJson Passed in node string
     * @return
     */
    private static String createNode(String nodeJson){
        String errJson = "{\"id\":\"-1\"}";
        String finalJson = "";

        try {
            JsonObject jsonObject = new Gson().fromJson(nodeJson, JsonObject.class);
            Set<Map.Entry<String, JsonElement>> testEntrySet = jsonObject.entrySet();
            String tableName = jsonObject.get("type").getAsString();
            NodeFactory nodeFactory = new NodeFactory();
            AbstractNode newNode = nodeFactory.buildNode(tableName, 0, 0, 0, 0);
            newNode.createIt();
            System.out.println(newNode.saveIt());
            //entry.remove("id");
            for (Map.Entry<String, JsonElement> entry : testEntrySet) { //Sets the updateNode's values to be the hydrated node map's values
                //System.out.print("Key = {" + entry.getKey().toString() +"} "+", Value = {" + entry.getValue().toString()+"}");
                newNode.set(entry.getKey().replaceAll("\"", ""), entry.getValue().toString().replaceAll("\"", ""));
            }

            String idOfCreatedNode = newNode.getString("id");

            String jsonSuccess = "{\"id\":\"" + idOfCreatedNode + "\"}";
            finalJson = jsonSuccess;
            //context.result(jsonSuccess);
        } catch (JsonSyntaxException jsonEx) {
            jsonEx.printStackTrace();
            finalJson =errJson;
            //context.result(errJson);
        }
        return finalJson;
    }


    /**
     * Given a query param of the form /trycreateedge/?edge={"json"} returns an id of an initialized edge or "-1" if bad input.
     *
     * @param context
     */
    public static void tryCreateEdge(Context context) {
        String edgeJson = context.queryParam("edge");
        if(edgeJson!=null) {
            createEdge(edgeJson);
        }
        else{
            context.status(499);
            context.result(nullParams);
        }

    }
    private static String createEdge(String edgeJson){
        String finalJson = "";
        String errJson = "{\"id\":\"-1\"}";
        try {

            JsonObject jsonObject = new Gson().fromJson(edgeJson, JsonObject.class);
            Set<Map.Entry<String, JsonElement>> testEntrySet = jsonObject.entrySet();
            String tableName = jsonObject.get("type").getAsString();
            int fromNodeId = jsonObject.get("from_node_id").getAsInt();
            String fromNodeType = jsonObject.get("from_node_type").getAsString();
            int toNodeId = jsonObject.get("to_node_id").getAsInt();
            String toNodeType = jsonObject.get("to_node_type").getAsString();

            EdgeFactory edgeFactory = new EdgeFactory();
            AbstractEdge newEdge = edgeFactory.buildEdge(tableName, fromNodeId, fromNodeType, toNodeId, toNodeType);
            newEdge.createIt();

            for (Map.Entry<String, JsonElement> entry : testEntrySet) { //Sets the updateNode's values to be the hydrated node map's values
                //System.out.print("Key = {" + entry.getKey().toString() +"} "+", Value = {" + entry.getValue().toString()+"}");
                newEdge.set(entry.getKey().replaceAll("\"", ""), entry.getValue().toString().replaceAll("\"", ""));
            }
            String idOfCreatedEdge = newEdge.getString("id"); //id of initialized edge

            String jsonSuccess = "{\"id\":\"" + idOfCreatedEdge + "\"}";
            finalJson = jsonSuccess;
            //context.result(jsonSuccess);
        } catch (JsonSyntaxException jsonEx) {
            jsonEx.printStackTrace();
           // context.result(errJson);
            finalJson = errJson;

        } catch (ClassCastException c) {
            c.printStackTrace();
            //context.result(errJson);
        } catch (Exception e) {
            e.printStackTrace();
            //context.result(errJson);
        }
        return finalJson;
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
            default -> DefaultEdge.where("id = ?", fromId);
        };

        try {
            if (dfList.size() == 0) {
                context.result("NOT FOUND");
            } else {
                dfList.get(0).delete();
                context.result(successMsg);
            }
        } catch (Exception n) {
            n.printStackTrace();
        }
    }


    /**
     * Given a query param of the form node = "json", attempts to update an existing node with all the attributes in the passed in node.
     * Takes queries in the form /updatenode/?node={}
     * <p>
     * Alex Note: This Method currently has a bunch of junk code, but I can't remove it until I figure out how reflection actually works.
     *
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

            System.out.println("Passed in edit request node: " + nodeJson);

            //This node is the node that we want to edit the values of.
            AbstractNode updateNode = null;
            try {
                LazyList<? extends AbstractNode> dfList = nodeListByIdType(fromId, nodeType);
                if (dfList.size() > 0) {
                    updateNode = dfList.get(0);
                } else {
                    context.result(nodeNotFoundErr);
                }
            } catch (NullPointerException nullPointerException) {
                nullPointerException.printStackTrace();
                context.result(nodeNotFoundErr);
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
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            context.result("BAD INPUT");
        } catch (Exception e) {
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


        try {
            LazyList<? extends AbstractNode> dfList = nodeListByIdType(fromId, nodeType);

            if (dfList.size() > 0) {
                dfList.get(0).delete();
                context.result("SUCCESS");
            } else {
                context.result(nodeNotFoundErr);
            }
        } catch (NullPointerException n) {
            n.printStackTrace();
            context.result("NULL POINTER EXCEPTION");
        }
        catch(Exception E){
            context.result(genericException);
        }
    }

    /**
     * DEPRECATED
     *
     * Given a two param object get request with id and table name as parameters, attempts to send back an object.
     * attaches to the /getobject/ get request. unlike the path params {} this takes implicit user defined params.
     * These would be structured in the url like : .../getobject/?objectid=foo&objecttable=bar in this method.
     * <p>
     * Note that this will actually return a list of maps not an object.
     *
     * @param context
     */
    public static void getObjsAsMapWithIdandTable(Context context) {
        Gson gson = new Gson();
        String objectId = context.queryParam("objectid");
        String tableName = context.queryParam("objecttable");
        context.status(405);

        List<Map> resultSet = Base.findAll(" SELECT * FROM " + tableName + " WHERE id = " + objectId + ";");
        try {
            String rezStr = "";

            ListIterator<Map> lm = resultSet.listIterator();
            while (lm.hasNext()) {
                rezStr += lm.next().toString();
            }
            context.result(rezStr);
        } catch (NullPointerException np) {
            np.printStackTrace();
            //System.out.println("No node found");
        }
    }

    /**
     * attaches to the /testpostnode/ post request. looks for the name query parameter and then creates a new node
     * with that parameter and saves it to the database. This is a dev tool, not how we would actually build nodes
     *
     * @param context implicitly passed in context
     */
    public static void createTestDefaultNodeWithPost(Context context){
        NodeFactory nf = new NodeFactory();
        DefaultNode testNode = nf.buildNode();
        testNode.set("name", context.queryParam("name")); //sets the name of the node based on a ? query.
        testNode.saveIt();
        context.result(testNode.getString("name"));
    }


    /**
     * Attaches to the /createpage/ post request. looks for the userId and page query paramters and creates a new page with
     * the passed in attributes of page attached to the user with the passed in id.
     *
     * @param context
     */
    public static void createPage(Context context) {
        String userId = context.queryParam("userid");
        String pageJson = context.queryParam("page");

        User foundUser = User.findById(userId);

        CustomJsonHelper jsonHelper = new CustomJsonHelper();
        Iterator<Map.Entry<String, JsonElement>> jsonIterator = jsonHelper.getIterator(pageJson); //needs exception checking
        Page createdPage = new Page();
        while (jsonIterator.hasNext()) {
            Map.Entry<String, JsonElement> currentRow = jsonIterator.next();
            createdPage.set(currentRow.getKey(), currentRow.getValue().toString()); //sets the attributes of the page to the passed in object attributes
        }

        try {
            foundUser.add(createdPage);
            context.result("SUCCESS");

        } catch (NotAssociatedException notAsso) {
            notAsso.printStackTrace();
            context.result("OBJECTS NOT ASSOCIATED");

        }

    }

    //I need to put a little bit of thought into this before implementing due to cascading deletion issues.
    public static void deletePage(Context context) {
        String userId = context.queryParam("userid");
        String pageJson = context.queryParam("page");
    }

    /**
     * Attaches to the /pagecreatenode/ post request. Given the query params pageid and node where pageid is a string representation of an id
     * and node is a passed in node object, this instantiates that node and adds it to the page associated with pageId.
     * @param context
     */
    public static void createNodeOnPage(Context context) {

        String userId = context.queryParam("pageid");
        String nodeJson = context.queryParam("node");
        int id = -1;
        CustomJsonHelper jsonHelper = new CustomJsonHelper();

        if(userId!=null && nodeJson!=null) {
            try {
                id = Integer.parseInt(userId); //will throw exception if userId is not int-able. Messy, fix later.
                String newJson = jsonHelper.replaceId(id, nodeJson);
                Gson gson = new Gson();
                JsonObject jObj = gson.fromJson(newJson, JsonObject.class);
               //String type = jObj.get("type").toString();

            } catch (Exception e) { //should implement throwing some specific exceptions in CustomJsonHelper.
                e.printStackTrace();
                context.status(500);

                context.result("GENERIC EXCEPTION");
            }
        }
        else{
            context.status(499);
            context.result(nullParams);
        }

    }

    /**
     * Attaches to the /pageremovenode/ post request. Given the query params pageid and node where pageid is a json of an id
     * and node is a passed in node object, this removes that node from a page and deletes it.
     * @param context
     */
    public static void removeNodeFromPage(Context context) {
        String pageJson = context.queryParam("page");
        String nodeJson = context.queryParam("node");
        if(pageJson!=null && nodeJson!=null){
            try{
                CustomJsonHelper jHelper = new CustomJsonHelper();
                String pageId = jHelper.getObjId(pageJson);
                String nodeId= jHelper.getObjId(nodeJson);
                String nodeType = jHelper.getObjType(nodeJson);
                Page queriedPage = Page.findById(jHelper);
                LazyList<? extends AbstractNode> lazyList = nodeListByIdType(nodeId,nodeType);
                queriedPage.remove(lazyList.get(0));
                lazyList.get(0).delete();
                context.result(successMsg);

            } catch (Exception e) {
                e.printStackTrace();
                context.status(500);
                context.result(genericException);
            }
        }

    }


    /**
     * Attaches to the /pagecreateedge/ post request. Given the query params pageid and node where pageid is a json representation of an id
     * and edge is a passed in edge object, this instantiates that edge and adds it to the page associated with pageId.
     * @param context
     */
    public static void createEdgeOnPage(Context context) {
        String userId = context.queryParam("pageid");
        String edgeJson = context.queryParam("edge");

        int id = -1;
        CustomJsonHelper jsonHelper = new CustomJsonHelper();

        if(userId!=null && edgeJson!=null) {
            try {
                id = Integer.parseInt(userId);
                String newJson = jsonHelper.replaceId(id, edgeJson);
            } catch (Exception e) { //should implement throwing some specific exceptions in CustomJsonHelper.
                e.printStackTrace();
                context.status(500);
                context.result("GENERIC EXCEPTION");
            }
        }
        else{
            context.status(499);
            context.result(nullParams);
        }

    }


    /**
     * Attaches to the /pageremoveedge/ post request. Given the query params page and edge where page is a page object.
     * edge is a passed in edge object, this removes that edge and returns a context result regarding the success of the operation.
     * @param context
     */
    public static void removeEdgeFromPage(Context context) {
        String page = context.queryParam("page");
        String edgeJson = context.queryParam("edge"); //this edge should have an id.
        CustomJsonHelper jsonHelper = new CustomJsonHelper();

        if(page!=null && edgeJson!=null){

            try {
                String edgeId = jsonHelper.getObjId(edgeJson);
                String edgeType = jsonHelper.getObjType(edgeJson);
                String pageId = jsonHelper.getObjId(page);
                LazyList<? extends AbstractEdge> lazyList = edgeListByIdType(edgeId, edgeType);
                Page queriedPage = Page.findById(pageId);
                context.result("SUCCESS");
            } catch (Exception e) {
                e.printStackTrace();
                context.result("GENERIC EXCEPTION");
            }
        }
        else{
            context.result(nullParams);
        }
    }

    /**
     * Attachhes to the /createuser/ post request.
     * Given a json string of user attributes, attempts to create a User in the backend.
     * @param context
     */
    public static void createUser(Context context) {
        String userJson = context.queryParam("user");
        //JsonHelper.toMap(); //Note to self. Javalite has a serializer and deserializer.
        if(userJson!=null) {
            try {
                User user = new User(); //Activejdbc user.
                CustomJsonHelper jHelper = new CustomJsonHelper();
                Iterator<Map.Entry<String, JsonElement>> iterator = jHelper.getIterator(userJson);
                while (iterator.hasNext()) {
                    Map.Entry<String, JsonElement> currEntry = iterator.next();
                    user.set(currEntry.getKey(), currEntry.getValue().getAsString());
                }
                user.saveIt();
                context.result(successMsg);
            }
            catch (Exception e){
                e.printStackTrace();
                context.status(500);
                context.result(genericException);
            }
        }
        else{
            context.status(499);
            context.result(nullParams);
        }

    }

    /**
     * Attaches to the /removeuserfrompage/ post request.
     * removes a user from a page. Does not delete the user.
     * @param context
     */
    public static void removeUserFromPage(Context context) {
        String userJson = context.queryParam("user");
        String pageJson = context.queryParam("page");
        try {
            if(userJson!=null && pageJson!=null) {
                CustomJsonHelper jHelper = new CustomJsonHelper();
                String userId = jHelper.getObjId(userJson);
                String pageId = jHelper.getObjId(pageJson);
                User queriedUser = User.findById(userId);
                Page queriedPage = Page.findById(pageId);
                queriedPage.remove(queriedUser);
            }
            else{
                context.status(499);
                context.result(nullParams);
            }
        }
        catch(Exception e){
            e.printStackTrace();
            context.status(500);
            context.result(genericException);
        }

    }

    public static void addUserToPage(Context context) {
        String userJson = context.queryParam("user");
        String pageJson = context.queryParam("page");
        if(userJson!=null && pageJson!=null){
            CustomJsonHelper jHelper = new CustomJsonHelper();
            String userId = jHelper.getObjId(userJson);
            String pageId = jHelper.getObjId(pageJson);
            Page page = Page.findById(pageId);
            User user = User.findById(userId);
            page.add(user);
        }
        else{
            context.result(nullParams);
        }
    }

    /**
     * This method is a helper method. A developer may not need access to this so its private and used by other methods in
     * RequestController currently. Gets a list (of 0 or 1) nodes matching the given id and type.
     * @param nodeId id of the passed in node.
     * @param nodeType type of the passed in node. should be a table name, ie "default_nodes"
     * @return A lazy list of nodes, a lazy list being a special activejdbc construct.
     * @throws Exception
     */
    private static LazyList<? extends AbstractNode> nodeListByIdType(String nodeId, String nodeType) throws Exception{
        LazyList<? extends AbstractNode> dfList = switch (nodeType) {
            case "default_nodes" -> DefaultNode.where("id = ?", nodeId);
            case "folder_nodes" -> FolderNode.where("id = ?", nodeId);
            case "class_nodes" -> ClassNode.where("id = ?", nodeId);
            case "life_line_nodes" -> LifeLineNode.where("id = ?", nodeId);
            case "loop_nodes" -> LoopNode.where("id = ?", nodeId);
            case "note_nodes" -> NoteNode.where("id = ?", nodeId);
            case "oval_nodes" -> OvalNode.where("id = ?", nodeId);
            case "square_nodes" -> SquareNode.where("id = ?", nodeId);
            case "stick_figure_nodes" -> StickFigureNode.where("id = ?", nodeId);
            case "text_box_nodes" -> TextBoxNode.where("id = ?", nodeId);
            default -> DefaultNode.where("id = ?", nodeId); //just the default list type.
            //There has to be a better way to specify Class type right?
        };

        return dfList;
    }

    /**
     * Helper method for getting an edge given an id and type.
     * @param edgeId id of passed in edge
     * @param edgeType type of the passed in edge, should be a table_name, ie "default_edges"
     * @return
     * @throws Exception
     */
    private static LazyList<? extends AbstractEdge> edgeListByIdType(String edgeId, String edgeType) throws Exception{

        LazyList<? extends AbstractEdge> dfList = switch (edgeType) {
            case "default_edges" -> DefaultEdge.where("id = ?", edgeId);
            case "normal_edges" -> NormalEdge.where("id = ?", edgeId);
            default -> DefaultEdge.where("id = ?", edgeId);
        };

        return dfList;

    }

}