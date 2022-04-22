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
/**
 * ObjectRequester.java
 * Class to create objects using the backend.
 * The FXMLController will use these function to request object creation using the backend.
 * This class is set up as an observable, so it can call support.firePropertyChange to update the UI.
 * @author Show P
 */

package UML.Diagrammer.desktop;

import UML.Diagrammer.backend.apis.HTTP_Client;
import UML.Diagrammer.backend.objects.UIEdge.UIDefaultEdge;
import UML.Diagrammer.backend.objects.UIEdge.UIEdge;
import UML.Diagrammer.backend.objects.UIEdge.UIEdgeFactory;
import UML.Diagrammer.backend.objects.UIEdge.UINormalEdge;
import UML.Diagrammer.backend.objects.UINode.*;
import UML.Diagrammer.backend.objects.UIPage;
import UML.Diagrammer.backend.objects.UIUser;
import UML.Diagrammer.backend.objects.tools.NodeTypeDeserializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sun.javafx.collections.NonIterableChange;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Line;
import javafx.scene.shape.SVGPath;
import org.checkerframework.checker.guieffect.qual.UI;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ObjectRequester {

    /**
     * Global observable object to call updates to the UI.
     */
    private final PropertyChangeSupport support;
    private static final UINodeFactory nodeFactory = new UINodeFactory();
    private static final UIEdgeFactory edgeFactory = new UIEdgeFactory();
    public UIUser currentUser;
    public UIPage currentPage;
    public static DatabaseConnection dbConnection = new DatabaseConnection();

    /**
     * Constructor, needs to make the PropertyChangeSupport object for to notify listeners
     */
    ObjectRequester() {
        support = new PropertyChangeSupport(this);
    }

    /**
     * Adds a listener to notify when a change occurs
     *
     * @param listener who wants to know when this object changes(Probably javaFX UI)
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    /**
     * Removes a listener so they no longer update on a change
     *
     * @param listener the listener you'd like to remove
     */
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }

    public void setCurrentUser(UIUser user){
        currentUser = user;
        support.firePropertyChange("userChanged", null, currentUser);
    }
    public void setCurrentPage(UIPage page){
        currentPage = page;
        support.firePropertyChange("pageChanged", null, currentPage);
    }

    /**
     * Creates a class object along with a rectangle and ties them together.
     * Then returns these objects to the UI via the support.firePropertyChange call.
     * All makeXRequest function should look really similar to this function, the difference being the image and node type.
     * @param x if a node is being loaded, we need it's position and will set it accordingly.
     *          Otherwise x is -1 to represent new node. Set in it's default position
     * @param y the y coordinate of the object, -1 if it's a new object(default position)
     * @param text the name that is given to the object, default if not loading.
     */
    public void makeOvalRequest(int x, int y, String text, UIOvalNode newNode) {
        int success = 1;
        if (x == -1){ // Not loading a node, got to save it to the db
            newNode = nodeFactory.buildNode("ovalnodes", 3, 3, 300, 150);
            success = dbConnection.saveNewNodeToDB(newNode ,currentPage);
        }
        if(success == 1) {
            String image = "Oval_UseCase.svg";
            StackPane newUIShape = UIBasicRequest(newNode, image, Pos.CENTER, x, y, text);
            support.firePropertyChange("newNodeCreation", null, newUIShape);
        }
    }

    public void makeClassRequest(int x, int y, String name, String desc, UIClassNode newNode) {
        int success = 1;
        if(x == -1) {
            newNode = nodeFactory.buildNode("classnodes", 3, 3, 300, 150);
            success = dbConnection.saveNewNodeToDB(newNode ,currentPage);
        }
        if (success == 1) {
            StackPane newUIShape = UIClassRequest(newNode, x, y, name, desc);
            support.firePropertyChange("newNodeCreation", null, newUIShape);
        }
    }

    public void makeFolderRequest(int x, int y, String text, UIFolderNode newNode) {
        int success = 1;
        if (x == -1) {//int success = 1; success = if (success == 1) {
            newNode = nodeFactory.buildNode("foldernodes", 3, 3, 300, 150);
            success = dbConnection.saveNewNodeToDB(newNode ,currentPage);
        }
        if (success == 1) {
            String image = "Folder.svg";
            StackPane newUIShape = UIBasicRequest(newNode, image, Pos.CENTER, x, y, text);
            support.firePropertyChange("newNodeCreation", null, newUIShape);
        }
    }

    //unique
    public void makeLifeLineRequest(int x, int y, String text, UILifeLineNode newNode) {
        int success = 1;
        if (x == -1) {
            newNode = nodeFactory.buildNode("lifelinenodes", 3, 3, 30, 500);
            success = dbConnection.saveNewNodeToDB(newNode ,currentPage);
        }
        if (success == 1) {
            String image = "/LifeLine.svg";
            StackPane newUIShape = UIBasicRequest(newNode, image, Pos.TOP_CENTER, x, y, text);
            newUIShape.setPrefHeight(430);
            newUIShape.setPrefWidth(80);
            support.firePropertyChange("newNodeCreation", null, newUIShape);
        }
    }

    //unique
    public void makeLoopRequest(int x, int y, String text, UILoopNode newNode) {
        int success = 1;
        if (x == -1) {
            newNode = nodeFactory.buildNode("loopnodes", 3, 3, 750, 450);
            success = dbConnection.saveNewNodeToDB(newNode ,currentPage);
        }
        if (success == 1) {
            StackPane newUIShape = UILoopRequest(newNode, x, y, text);
            support.firePropertyChange("newNodeCreation", null, newUIShape);
        }
    }

    public void makeNoteRequest(int x, int y, String text, UINoteNode newNode) {
        int success = 1;
        if (x == -1) {
            newNode = nodeFactory.buildNode("notenodes", 3, 3, 200, 200);
            success = dbConnection.saveNewNodeToDB(newNode ,currentPage);
        }
        if (success == 1) {
            String image = "/Note.svg";
            StackPane newUIShape = UIBasicRequest(newNode, image, Pos.TOP_LEFT, x, y, text);
            support.firePropertyChange("newNodeCreation", null, newUIShape);
        }
    }

    public void makeStickFigureRequest(int x, int y, String text, UIStickFigureNode newNode) {
        int success = 1;
        if (x == -1) {
            newNode = nodeFactory.buildNode("stickfigurenodes", 3, 3, 100, 200);
            success = dbConnection.saveNewNodeToDB(newNode ,currentPage);
        }
        if (success == 1) {
            String image = "/StickFigure.svg";
            StackPane newUIShape = UIBasicRequest(newNode, image, Pos.BOTTOM_CENTER, x, y, text);
            support.firePropertyChange("newNodeCreation", null, newUIShape);
        }
    }

    public void makeTextBoxRequest(int x, int y, String text, UITextBoxNode newNode) {
        int success = 1;
        if (x == -1) {
            newNode = nodeFactory.buildNode("textboxnodes", 3, 3, 300, 150);
            success = dbConnection.saveNewNodeToDB(newNode ,currentPage);
        }
        if(success == 1) {
            String image = "TextBox_Square_Interface.svg";
            StackPane newUIShape = UIBasicRequest(newNode, image, Pos.TOP_LEFT, x, y, text);
            support.firePropertyChange("newNodeCreation", null, newUIShape);
        }
    }

    public void makeSquareRequest(int x, int y, String text, UISquareNode newNode) {
        int success = 1;
        if (x == -1) {
            newNode = nodeFactory.buildNode("squarenodes", 3, 3, 300, 150);
            success = dbConnection.saveNewNodeToDB(newNode, currentPage);
        }
        if (success == 1){
        String image = "TextBox_Square_Interface.svg";
        StackPane newUIShape = UIBasicRequest(newNode, image, Pos.CENTER, x, y, text);
        support.firePropertyChange("newNodeCreation", null, newUIShape);
        }
    }

    /**
     * Creates creates an edge for the data and a line for the UI. Then notifies the FXMLController to update.
     */
    public void makeEdgeRequest(StackPane n0, StackPane n1, UIEdge newEdge) {
        int success = 1;
        if (newEdge == null) { // Check for loading
            newEdge = edgeFactory.buildEdge("normaledges", (UINode) n0.getUserData(), (UINode) n1.getUserData());
            success = dbConnection.saveNewEdgeToDB(newEdge ,currentPage);
        }
        if (success == 1) {
            Line newLine = UIEdgeRequest(n0, n1, newEdge);
            support.firePropertyChange("newEdgeCreation", null, newLine);
        }
    }

    /**
     * When we make an edge. The edge will know about it's associated nodes. But the nodes won't know about the edge.
     * So when we move an node, we need to make a call to tell the edge that we moved and the edge can update.
     * This is okay on the data side since all the data that is needed is the nodes that are collected. All other info
     * can be inferred from the UI/UI objects of said nodes.
     *
     * @param n0 first node
     * @param n1 second node
     * @return the created UI object line.
     */

    public Line UIEdgeRequest(StackPane n0, StackPane n1, UIEdge edge) {
        //edgeFactory.buildEdge((AbstractNode) n0.getUserData(), (AbstractNode) n1.getUserData());
        Line line = new Line();
        UINode node0 = (UINode) n0.getUserData();
        UINode node1 = (UINode) n1.getUserData();
        line.setStartX(node0.getX_coord() + (node0.getWidth() / 2));
        line.setStartY(node0.getY_coord() + (node0.getHeight() / 2));
        line.setEndX(node1.getX_coord() + (node1.getWidth() / 2));
        line.setEndY(node1.getY_coord() + (node1.getHeight() / 2));
        line.setStrokeWidth(3);
        line.setUserData(edge);
        return line;
    }

    /**
     * Sets up the mouse actions for dragging and updating the associated node.
     * This should be called after any new creation of a node.
     * setUserData makes it so we can reference the data object from the UI object via UIElement.getUserData()
     * This effectively ties together the UI object Rectangle and the given UINode data object.
     *
     * @param fxObject The UI element that is displayed to the screen.
     * @param node     The actual backend object that is apart of the data.
     */
    public void setMouseActions(StackPane fxObject, UINode node) {
        fxObject.setUserData(node);
        support.firePropertyChange("setMouseActions", null, fxObject);
    }

    /**
     * A general way to create a stack pane, since each UI element is unique we need specific function for various
     * text elements, locations, and shapes.
     * Creates a new shape for the UI and sets up it's mouse actions
     * his function ties together a UI Object (StackPane) and node object (UINode).
     * @param node The associated data node
     * @param image the shape you'd like to use.
     * @return The stackpane that was created.
     */
    private StackPane makeStackPane(UINode node, String image){
        try {
            SVGPath path = new SVGPath();
            Path filePath = Paths.get("src/main/resources/Images/" + image);
            Path absPath = filePath.toAbsolutePath();
            String svgString = Files.readString(absPath);
            path.setContent(svgString);
            StackPane stack = new StackPane(path);
            stack.setCursor(Cursor.HAND);
            setMouseActions(stack, node);
            return stack;
        }
        catch (Exception e){e.printStackTrace();}
        return null;
    }

    /**
     * Tailored request for the class since it has a name/desc in specific position
     * @param node the associated data node.
     * @return UI StackPane
     */
    public StackPane UIClassRequest(UINode node, int x, int y, String givenName, String givenDesc) {
        StackPane stack = makeStackPane(node, "Class.svg");
        stack.setPrefWidth(node.getWidth());
        stack.setPrefHeight(node.getHeight());

        Label name = new Label((String) node.getName());
        Label desc = new Label((String) node.getDescription());
        if (x != -1){       // For loading in a new node
            stack.setTranslateX(x);
            stack.setTranslateY(y);
            name.setText(givenName);
            desc.setText(givenDesc);
        }
        name.setWrapText(true); desc.setWrapText(true);
        stack.getChildren().addAll(name, desc);
        StackPane.setAlignment(name, Pos.TOP_LEFT);
        StackPane.setAlignment(desc, Pos.CENTER_LEFT);
        return stack;
    }

    /**
     * A general request for most UI objects since most object just need a name in the center of the element.
     * @param node associated data node.
     * @param image the specified background image
     * @param namePos the position you'd like the name to appear.
     * @param x x coordinate, if loadin a node. -1 if a new node
     * @param y y coordinate, if loading a node.
     * @param text the loaded name of the node.
     * @return UI StackPane
     */
    public StackPane UIBasicRequest(UINode node, String image, Pos namePos, int x, int y, String text) {
        StackPane stack = makeStackPane(node, image);
        Label name = new Label(node.getName());
        name.setWrapText(true);
        stack.setPrefHeight(node.getHeight());
        stack.setPrefWidth(node.getWidth());
        if (x != -1){       // For loading in a new node
            stack.setTranslateX(x);
            stack.setTranslateY(y);
            name.setText(text);
        }
        stack.getChildren().addAll(name);
        StackPane.setAlignment(name, namePos);
        return stack;
    }

    /**
     * Talored stackpane request since we need a loop label on the top left.
     * @param node the associated data node.
     * @return UI StackPane
     */
    public StackPane UILoopRequest(UINode node, int x, int y, String name){
        StackPane stack = UIBasicRequest(node, "Loop.svg", Pos.TOP_CENTER, x, y, name);
        Label loopLabel = new Label("Loop");
        stack.getChildren().add(loopLabel);
        StackPane.setAlignment(loopLabel, Pos.TOP_LEFT);
        return stack;
    }

    /**
     * Creates a new page and adds the current user to it.
     * @param pageName The name of the page that the user specified.
     */
    public UIPage createNewPage(String pageName){
        UIPage newPage = dbConnection.createNewPage(currentUser, pageName);
        dbConnection.addUserToPage(currentUser, newPage);
        currentPage = newPage;
        return newPage;
    }

    /**
     * Unfortunately, this function acts as a middle man between action handler and DatabaseConnection due to this removal
     * needing the current page. The current page is only found in object requster. This is bad form, but it will have
     * to do for now.
     * @param node The node you'd like to delete from the db.
     */
    public void deleteNodeFromPage(UINode node){
        dbConnection.removeNodeFromPage(node, currentPage);
    }

    /**
     * Again, a middleman function for the same reason as deleteNodeFromPage.
     * @param edge The edge you'd like to delete from the db.
     */
    public void deleteEdgeFromPage(UIEdge edge){
        dbConnection.removeEdgeFromPage(edge, currentPage);
    }

    /**
     * Gets the list of pages from the db and parses the returned string into a map of id/name pairs of pages.
     * @return
     */
    public Map<Integer, String> getUserPages(){
        try {
            String dbString = dbConnection.getUserPages(currentUser);
            System.out.println("RAW DB PAGES: " + dbString);
            // Matches the string of "\\\"STRING, this gets the name string with some "\\\" before it.
            Pattern pattern = Pattern.compile("\\\"\\\\+\"\\w+");
            Matcher matcher = pattern.matcher(dbString);

            //Actually strip the string and add it's content to a list
            ArrayList<String> nameNotStripped = new ArrayList<>();
            while (matcher.find()) {
                nameNotStripped.add(matcher.group());
            }

            // We now have a list of name string with "\\\" before it, so we just need to strip that off.
            Pattern namePattern = Pattern.compile("\\w+");
            Matcher strippedStringToMatch = namePattern.matcher(nameNotStripped.toString());

            ArrayList<String> normalNameList = new ArrayList<>();
            while (strippedStringToMatch.find()){ // Strip it and add the name's to a list.
                normalNameList.add(strippedStringToMatch.group());
            }

            // Same thing but for id, much simpler regex
            Pattern iDPattern = Pattern.compile("[0-9]+");
            Matcher iDMatcher = iDPattern.matcher(dbString);

            //Actually strip the string and add it's content to a list
            ArrayList<String> idStripped = new ArrayList<>();
            while (iDMatcher.find()) {
                idStripped.add(iDMatcher.group());
            }

            // Add the id/name pairs into a map
            Map<Integer, String> map = new HashMap<>();
            for (int i = 0; i < normalNameList.size(); i++){
                map.put(Integer.valueOf(idStripped.get(i)), normalNameList.get(i));
            }

            return map;
        }
        catch (Exception e){e.printStackTrace();}
        return null;
    }

    /**
     * Given a page and the current pane, load the nodes/edges from the db.
     * @param currentPane The current pane that all the elemnts reside
     * @param page The id of the page you are loading from the db.
     */
    public void loadPagesFromDB(Pane currentPane, UIPage page){
        String retObject = dbConnection.loadPageElements(page.getPageIdAsJSon());

        Gson gson  = new Gson();
        String[][] stringArray = gson.fromJson(retObject, String[][].class);
        ArrayList<UIEdge> hydratedEdgeList = new ArrayList<>();
        if(stringArray[0].length != 0) { // Error checking if edges/nodes are empty
            ArrayList<UINode> hydratedNodeList = hydrateNodes(stringArray[0]);

            if(stringArray[1].length !=0) { // Empty edge checking
                for(String curEdgeString : stringArray[1]){
                    UIEdge hydratedEdge = hydrateEdges(stripEdge(curEdgeString), hydratedNodeList);
                    hydratedEdgeList.add(hydratedEdge);
                }
            }
            loadPage(hydratedNodeList, hydratedEdgeList, currentPane);
        }
    }

    /**
     * Given a json string of nodes create real UINode objects that can be used to display on the UI.
     * @param nodes The string list of json nodes
     * @return An array list of hydrated nodes
     */
    public ArrayList<UINode> hydrateNodes(String[] nodes){
        NodeTypeDeserializer customNodeDeserializer = new NodeTypeDeserializer("type");

        customNodeDeserializer.registerSubtype( "defaultnodes",UIDefaultNode.class);
        customNodeDeserializer.registerSubtype( "classnodes", UIClassNode.class);
        customNodeDeserializer.registerSubtype( "foldernodes", UIFolderNode.class);
        customNodeDeserializer.registerSubtype( "lifelinenodes", UILifeLineNode.class);
        customNodeDeserializer.registerSubtype( "loopnodes", UILoopNode.class);
        customNodeDeserializer.registerSubtype( "notenodes", UINoteNode.class);
        customNodeDeserializer.registerSubtype( "ovalnodes", UIOvalNode.class);
        customNodeDeserializer.registerSubtype( "squarenodes", UISquareNode.class);
        customNodeDeserializer.registerSubtype( "stickfigurenodes", UIStickFigureNode.class);
        customNodeDeserializer.registerSubtype( "textboxnodes", UITextBoxNode.class);

        Gson gBuilder = new GsonBuilder()
                .registerTypeAdapter(UINode.class,customNodeDeserializer)
                .create();

        ArrayList<UINode> UINodeList = new ArrayList<>();

        for (String curJsonNode : nodes){
            System.out.println(curJsonNode.toString());
            UINodeList.add(gBuilder.fromJson(curJsonNode, new TypeToken<UINode>(){}.getType()));
        }
        return UINodeList;
    }

    /**
     * Not a great function due to needing the lists be in a particular order. And no duplicate ids.
     * Gets the edge information that you'd like to hydrate and create a node with the list of nodes that are given to it.
     * @param edgeIds The list of integers that make up the IDs, [fromNodeId, edgeId, toNodeId]
     * @param nodes The list of hydrated nodes that you need to associate lines with.
     * @return The new hydrated edge.
     */
    public UIEdge hydrateEdges(ArrayList<Integer> edgeIds, ArrayList<UINode> nodes){
        ArrayList<UINode> matchedNodes = new ArrayList<>();
        for (UINode curNode : nodes){
            if (curNode.getId() == edgeIds.get(0)) {
                matchedNodes.add(curNode);
            }
        }
        for (UINode curNode : nodes) {
            if (curNode.getId() == edgeIds.get(2)) {
                matchedNodes.add(curNode);
            }
        }
        UIEdge hydratedEdge = new UINormalEdge(matchedNodes.get(0), matchedNodes.get(1));
        hydratedEdge.setId(edgeIds.get(1));
        return hydratedEdge;
    }

    /**
     * Various regex operations to get a list of integers that only have the from node id, edge id, and to node id
     * @param jsonEdgeString The json string from the server that is a edge.
     * @return A arrayList<Integer> in this order [from_node_id, edge_id, to_node_id]
     */
    public ArrayList<Integer> stripEdge(String jsonEdgeString){
        //Strips everything but the numbers, and it's identifier proceeding it.
        Pattern pattern = Pattern.compile("[a-z_]+\":[0-9]+");
        Matcher matcher = pattern.matcher(jsonEdgeString);
        ArrayList<String> idNameAndInt = new ArrayList<>();
        while (matcher.find()) {
            idNameAndInt.add(matcher.group());
        }

        // Gets rid of the page_id number since we don't need it, along with the rest of the character we don't want
        String strippedString = idNameAndInt.toString().replaceAll("((page_id\":[0-9]+)|[a-z_\":])+", "");
        System.out.println(strippedString);

        //Adds the numbers to a list for individual retrieval, list is [from_node_id, edge_id, to_node_id]
        Pattern p = Pattern.compile("[0-9]+");
        Matcher m = p.matcher(strippedString);
        ArrayList<Integer> fromIdTo = new ArrayList<>();
        while (m.find()) {
            fromIdTo.add(Integer.valueOf(m.group()));
        }
        return fromIdTo;
    }



    /**
     * Loads the page by takeing a list of nodes and edges and adds them to the pane with their given information
     * @param nodes The set of nodes we are loading in
     * @param edge The set of edges we are loading in
     * @param pane The parent pane that everything loads into
     */
    public void loadPage(ArrayList<UINode> nodes, ArrayList<UIEdge> edge, Pane pane){
        for (UINode curNode: nodes) {
            loadNode(curNode);
        }

        if (edge.size() > 0) {
            System.out.println("edge size is > 0");
            for (UIEdge curEdge : edge) {
                loadEdge(curEdge, pane);
            }
        }
    }

    /**
     * Calls the appropriate make method based on the node type.
     * Uses the given nodes attributes as a reference to create it in the specified area with loaded names.
     * @param node The node you are loading in.
     */
    public void loadNode(UINode node){
        switch (node.getType()){
            case "ovalnodes" -> makeOvalRequest(node.getX_coord(), node.getY_coord(), node.getName(), (UIOvalNode) node);
            case "classnodes" -> makeClassRequest(node.getX_coord(), node.getY_coord(), node.getName(), node.getDescription(), (UIClassNode) node);
            case "foldernodes" -> makeFolderRequest(node.getX_coord(), node.getY_coord(), node.getName(), (UIFolderNode) node);
            case "lifelinenodes" -> makeLifeLineRequest(node.getX_coord(), node.getY_coord(), node.getName(), (UILifeLineNode) node);
            case "loopnodes" -> makeLoopRequest(node.getX_coord(), node.getY_coord(), node.getName(),(UILoopNode) node);
            case "notenodes" -> makeNoteRequest(node.getX_coord(), node.getY_coord(), node.getName(), (UINoteNode) node);
            case "stickfigurenodes" -> makeStickFigureRequest(node.getX_coord(), node.getY_coord(), node.getName(), (UIStickFigureNode) node);
            case "textboxnodes" -> makeTextBoxRequest(node.getX_coord(), node.getY_coord(), node.getName(), (UITextBoxNode) node);
            case "squarenodes" -> makeSquareRequest(node.getX_coord(), node.getY_coord(), node.getName(), (UISquareNode) node);
        }
    }

    /**
     * Loads the edge into the pane given a pane and an edge. The edge finds it's connected components via looking at
     * all the nodes and finds a matching ID.
     * @param edge The edge we are trying to create
     * @param pane The parent pane where we are displaying th edge.
     */
    public void loadEdge(UIEdge edge, Pane pane){
        int n1Id = edge.getN1().getId();
        int n2Id = edge.getN2().getId();
        ArrayList<StackPane> matchingNodes = new ArrayList<>();
        for (Node curNode: pane.getChildren()) {
            if (curNode instanceof StackPane curPane){
                UINode curUINode = (UINode) curPane.getUserData();
                int nodeId = curUINode.getId();
                if (nodeId == n1Id){
                    matchingNodes.add(curPane);
                }
                else if (nodeId == n2Id){
                    matchingNodes.add(curPane);
                }
            }
            if (matchingNodes.size() == 2){break;} // Leave loop early if both nodes have been found
        }
        makeEdgeRequest(matchingNodes.get(0), matchingNodes.get(1), edge);
    }
}