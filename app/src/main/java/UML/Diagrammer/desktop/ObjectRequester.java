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
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Line;
import javafx.scene.shape.SVGPath;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;


public class ObjectRequester {

    /**
     * Global observable object to call updates to the UI.
     */
    private final PropertyChangeSupport support;
    //private static final NodeFactory nodeFactory = new NodeFactory();
    private static final UINodeFactory nodeFactory = new UINodeFactory();
    private static final UIEdgeFactory edgeFactory = new UIEdgeFactory();
    HTTP_Client HTTPClient = new HTTP_Client();

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
        if (x == -1){
            newNode = nodeFactory.buildNode("oval_nodes", 3, 3, 300, 150);
        }
        String image = "Oval_UseCase.svg";
        StackPane newUIShape = UIBasicRequest(newNode, image, Pos.CENTER, x, y, text);
        if (x == -1){/*database save call*/}
        support.firePropertyChange("newNodeCreation", null, newUIShape);
    }

    public void makeClassRequest(int x, int y, String name, String desc, UIClassNode newNode) {
        if(x == -1) {
            newNode = nodeFactory.buildNode("class_nodes", 3, 3, 300, 150);
        }
        StackPane newUIShape = UIClassRequest(newNode, x, y, name, desc);
        if (x == -1){/*saveNewNodeToDB(newNode);*/}
        support.firePropertyChange("newNodeCreation", null, newUIShape);
    }

    public void makeFolderRequest(int x, int y, String text, UIFolderNode newNode) {
        if (x == -1) {
            newNode = nodeFactory.buildNode("folder_nodes", 3, 3, 300, 150);
        }
        String image = "Folder.svg";
        StackPane newUIShape = UIBasicRequest(newNode, image, Pos.CENTER, x, y, text);
        if (x == -1){/*database save call*/}
        support.firePropertyChange("newNodeCreation", null, newUIShape);
    }

    //unique
    public void makeLifeLineRequest(int x, int y, String text, UILifeLineNode newNode) {
        if (x == -1) {
            newNode = nodeFactory.buildNode("life_line_nodes", 3, 3, 30, 500);
        }
        String image = "/LifeLine.svg";
        StackPane newUIShape = UIBasicRequest(newNode, image, Pos.TOP_CENTER, x, y, text);
        newUIShape.setPrefHeight(430);
        newUIShape.setPrefWidth(80);
        if (x == -1){/*database save call*/}
        support.firePropertyChange("newNodeCreation", null, newUIShape);
    }

    //unique
    public void makeLoopRequest(int x, int y, String text, UILoopNode newNode) {
        if (x == -1) {
            newNode = nodeFactory.buildNode("loop_nodes", 3, 3, 750, 450);
        }
        StackPane newUIShape = UILoopRequest(newNode, x, y, text);
        if (x == -1){/*database save call*/}
        support.firePropertyChange("newNodeCreation", null, newUIShape);
    }

    public void makeNoteRequest(int x, int y, String text, UINoteNode newNode) {
        if (x == -1) {
            newNode = nodeFactory.buildNode("note_nodes", 3, 3, 200, 200);
        }
        String image = "/Note.svg";
        StackPane newUIShape = UIBasicRequest(newNode, image, Pos.TOP_LEFT, x, y, text);
        if (x == -1){/*database save call*/}
        support.firePropertyChange("newNodeCreation", null, newUIShape);
    }

    public void makeStickFigureRequest(int x, int y, String text, UIStickFigureNode newNode) {
        if (x == -1) {
            newNode = nodeFactory.buildNode("stick_figure_nodes", 3, 3, 100, 200);
        }
        String image = "/StickFigure.svg";
        StackPane newUIShape = UIBasicRequest(newNode, image, Pos.BOTTOM_CENTER, x, y, text);
        if (x == -1){/*database save call*/}
        support.firePropertyChange("newNodeCreation", null, newUIShape);
    }

    public void makeTextBoxRequest(int x, int y, String text, UITextBoxNode newNode) {
        if (x == -1) {
            newNode = nodeFactory.buildNode("text_box_nodes", 3, 3, 300, 150);
        }
        String image = "TextBox_Square_Interface.svg";
        StackPane newUIShape = UIBasicRequest(newNode, image, Pos.TOP_LEFT, x, y, text);
        if (x == -1){/*database save call*/}
        support.firePropertyChange("newNodeCreation", null, newUIShape);
    }

    public void makeSquareRequest(int x, int y, String text, UISquareNode newNode) {
        if (x == -1) {
            newNode = nodeFactory.buildNode("square_nodes", 3, 3, 300, 150);
        }
        String image = "TextBox_Square_Interface.svg";
        StackPane newUIShape = UIBasicRequest(newNode, image, Pos.CENTER, x, y, text);
        if (x == -1){/*database save call*/}
        support.firePropertyChange("newNodeCreation", null, newUIShape);
    }

    /**
     * Creates creates an edge for the data and a line for the UI. Then notifies the FXMLController to update.
     */
    public void makeEdgeRequest(StackPane n0, StackPane n1, UIEdge newEdge) {
        if (newEdge == null) { // Check for loading
            newEdge = edgeFactory.buildEdge("normal_edges", (UINode) n0.getUserData(), (UINode) n1.getUserData());
        }
        Line newLine = UIEdgeRequest(n0, n1, newEdge);
        support.firePropertyChange("newEdgeCreation", null, newLine);
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
        line.setStartX(node0.getX() + (node0.getW() / 2));
        line.setStartY(node0.getY() + (node0.getH() / 2));
        line.setEndX(node1.getX() + (node1.getW() / 2));
        line.setEndY(node1.getY() + (node1.getH() / 2));
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
        stack.setPrefWidth(node.getW());
        stack.setPrefHeight(node.getH());

        Label name = new Label((String) node.getName());
        Label desc = new Label((String) node.getDesc());
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
        stack.setPrefHeight(node.getH());
        stack.setPrefWidth(node.getW());
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

    public void testDBConnections(){
        UIUser newUser = createNewUser("myNewUser");
        createNewPage(newUser, "newPage");
    }

    // Putting either of these strings in postman work properly
    // http://127.0.0.1:8888/createuser/?user={"id":"-1","name":"newName"}
    // http://127.0.0.1:8888/createpage/?page={"name":"TESTPAGE"}&userid={"id":"1"}
    /**
     * Creates a new user and gets their ID from the database
     * @param name name of the user
     */
    private UIUser createNewUser(String name){
        try {
            UIUser newUser = new UIUser(-1, name);
            String dbUserString = HTTPClient.usercreaterequest(newUser.getIDAsJson());
            dbUserString = dbUserString.replaceAll("\\D", ""); // Strips everything but the id int
            newUser.setId(Integer.valueOf(dbUserString));
            return newUser;
        }
        catch (Exception e){e.printStackTrace();}
        return null;
    }

    private UIPage createNewPage(UIUser userId, String pageName){
    try{
        UIPage newPage = new UIPage(-1, pageName);
        String dbPageString = HTTPClient.pageCreateRequest(newPage.getPageNameAsJSon(), userId.getIDAsJson());
        dbPageString = dbPageString.replaceAll("\\D", ""); // Strips everything but the id int
        newPage.setId(Integer.valueOf(dbPageString));
        System.out.println("newPageId is now: "+newPage.getId());
        return newPage;
    }
    catch (Exception e){e.printStackTrace();}
    return null;
    }

    /**
     * WIP of testing database connection
     * @param node
     */
    private void saveNewNodeToDB(UINode node) {
        try {
            //System.out.println("about to gson");
            //Gson gson = new Gson();

            //UIUser newUser = createNewUser("testUser");
            UIUser newUser = new UIUser(-1, "user0");
            //UIPage newPage = new UIPage(-1, "page0");

            //System.out.println("usercreaterrequest was given: " + newUser.getIDAsJson());

            //String pgStr = HTTPClient.usercreaterequest(newUser.getIDAsJson());

            //System.out.println("returned string is: " + pgStr);
            //String newID = HTTPClient.sendAddNodeToPage(node.getIDAsJson(), newPage.getPageIDAsJSon());
            //System.out.println(newID);


            //String jsonString = gson.toJson(node, UINode.class);
            //System.out.println(jsonString);
            //String dbString = HTTPClient.sendNodeCreateRequest(jsonString);
            //System.out.println("dbString: "+dbString);

            //JsonObject dbObject = gson.fromJson(dbString, JsonObject.class);

            //Set<Map.Entry<String, JsonElement>> testEntrySet = jsonObject.entrySet();
            //String returnedID = dbObject.get("id").getAsString();
            //node.setId(Integer.valueOf(returnedID));
        }
        catch (Exception e){e.printStackTrace();System.out.println("FAILED TO SAVE");}
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

        for (UIEdge curEdge: edge){
            loadEdge(curEdge, pane);
        }
    }

    /**
     * Calls the appropriate make method based on the node type.
     * Uses the given nodes attributes as a reference to create it in the specified area with loaded names.
     * @param node The node you are loading in.
     */
    private void loadNode(UINode node){
        switch (node.getType()){
            case "oval_nodes" -> makeOvalRequest(node.getX(), node.getY(), node.getName(), (UIOvalNode) node);
            case "class_nodes" -> makeClassRequest(node.getX(), node.getY(), node.getName(), node.getDesc(), (UIClassNode) node);
            case "folder_nodes" -> makeFolderRequest(node.getX(), node.getY(), node.getName(), (UIFolderNode) node);
            case "life_line_nodes" -> makeLifeLineRequest(node.getX(), node.getY(), node.getName(), (UILifeLineNode) node);
            case "loop_nodes" -> makeLoopRequest(node.getX(), node.getY(), node.getName(),(UILoopNode) node);
            case "note_nodes" -> makeNoteRequest(node.getX(), node.getY(), node.getName(), (UINoteNode) node);
            case "stick_figure_nodes" -> makeStickFigureRequest(node.getX(), node.getY(), node.getName(), (UIStickFigureNode) node);
            case "text_box_nodes" -> makeTextBoxRequest(node.getX(), node.getY(), node.getName(), (UITextBoxNode) node);
            case "square_nodes" -> makeSquareRequest(node.getX(), node.getY(), node.getName(), (UISquareNode) node);
        }
    }

    /**
     * Loads the edge into the pane given a pane and an edge. The edge finds it's connected components via looking at
     * all the nodes and finds a matching ID.
     * @param edge The edge we are trying to create
     * @param pane The parent pane where we are displaying th edge.
     */
    private void loadEdge(UIEdge edge, Pane pane){
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
        System.out.println(matchingNodes.get(0).getWidth());
        makeEdgeRequest(matchingNodes.get(0), matchingNodes.get(1), edge);
    }

    /**
     * Test function to make sure that the loading works. Will remove in the future.
     */
    public void loadNodesTest(Pane pane){
        UIOvalNode ovalTestNode = nodeFactory.buildNode("oval_nodes", 150, 600, 300, 150);
        UIFolderNode folderTestNode = nodeFactory.buildNode("folder_nodes", 500, 150, 300, 150);
        UISquareNode squareTestNode = nodeFactory.buildNode("square_nodes", 5, 5, 300, 300);
        ovalTestNode.setName("New name is taken");
        folderTestNode.setName("I folder, I loaded");
        ArrayList<UINode> testArray = new ArrayList<UINode>();
        testArray.add(ovalTestNode);
        testArray.add(folderTestNode);
        testArray.add(squareTestNode);

        UINormalEdge testEdge = edgeFactory.buildEdge("normal_edges", ovalTestNode, folderTestNode);
        ArrayList<UIEdge> testEdgeArray = new ArrayList<>();
        testEdgeArray.add(testEdge);

        loadPage(testArray, testEdgeArray, pane);
    }
}