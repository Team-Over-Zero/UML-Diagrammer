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
import UML.Diagrammer.backend.objects.UINode.*;
import UML.Diagrammer.backend.objects.UIUser;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
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
            newNode = nodeFactory.buildNode("oval_nodes", 3, 3, 3, 3);
        }
        String image = "Oval_UseCase.svg";
        StackPane newUIShape = UIBasicRequest(newNode, image, 300, 150, Pos.CENTER, x, y, text);
        if (x == -1){/*database save call*/}
        support.firePropertyChange("newNodeCreation", null, newUIShape);
    }

    // Currently my test button for testing db connection.
    public void makeClassRequest(int x, int y, String name, String desc, UIClassNode newNode) {
        try {
            newNode = nodeFactory.buildNode("class_nodes", 3, 3, 3, 3);
            //AbstractNode savedNode = saveNodeToDB(newNode);
            StackPane newUIShape = UIClassRequest(newNode, x, y, name, desc);
            newNode.toJson();
            if (x == -1){saveNewNodeToDB(newNode);}
            support.firePropertyChange("newNodeCreation", null, newUIShape);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void makeFolderRequest(int x, int y, String text, UIFolderNode newNode) {
        if (x == -1) {
            newNode = nodeFactory.buildNode("folder_nodes", 3, 3, 3, 3);
        }
        String image = "Folder.svg";
        StackPane newUIShape = UIBasicRequest(newNode, image, 300, 150, Pos.CENTER, x, y, text);
        if (x == -1){/*database save call*/}
        support.firePropertyChange("newNodeCreation", null, newUIShape);
    }

    //unique
    public void makeLifeLineRequest(int x, int y, String text, UILifeLineNode newNode) {
        if (x == -1) {
            newNode = nodeFactory.buildNode("life_line_nodes", 3, 3, 3, 3);
        }
        String image = "/LifeLine.svg";
        StackPane newUIShape = UIBasicRequest(newNode, image, 80, 430, Pos.TOP_CENTER, x, y, text);
        if (x == -1){/*database save call*/}
        support.firePropertyChange("newNodeCreation", null, newUIShape);
    }

    //unique
    public void makeLoopRequest(int x, int y, String text, UILoopNode newNode) {
        if (x == -1) {
            newNode = nodeFactory.buildNode("loop_nodes", 3, 3, 3, 3);
        }
        StackPane newUIShape = UILoopRequest(newNode, x, y, text);
        if (x == -1){/*database save call*/}
        support.firePropertyChange("newNodeCreation", null, newUIShape);
    }

    public void makeNoteRequest(int x, int y, String text, UINoteNode newNode) {
        if (x == -1) {
            newNode = nodeFactory.buildNode("note_nodes", 3, 3, 3, 3);
        }
        String image = "/Note.svg";
        StackPane newUIShape = UIBasicRequest(newNode, image, 200, 150, Pos.TOP_LEFT, x, y, text);
        if (x == -1){/*database save call*/}
        support.firePropertyChange("newNodeCreation", null, newUIShape);
    }

    public void makeStickFigureRequest(int x, int y, String text, UIStickFigureNode newNode) {
        if (x == -1) {
            newNode = nodeFactory.buildNode("stick_figure_nodes", 3, 3, 3, 3);
        }
        String image = "/StickFigure.svg";
        StackPane newUIShape = UIBasicRequest(newNode, image, 220, 150, Pos.BOTTOM_CENTER, x, y, text);
        if (x == -1){/*database save call*/}
        support.firePropertyChange("newNodeCreation", null, newUIShape);
    }

    public void makeTextBoxRequest(int x, int y, String text, UITextBoxNode newNode) {
        if (x == -1) {
            newNode = nodeFactory.buildNode("text_box_nodes", 3, 3, 3, 3);
        }
        String image = "TextBox_Square_Interface.svg";
        StackPane newUIShape = UIBasicRequest(newNode, image, 300, 150, Pos.TOP_LEFT, x, y, text);
        if (x == -1){/*database save call*/}
        support.firePropertyChange("newNodeCreation", null, newUIShape);
    }

    public void makeSquareRequest(int x, int y, String text, UISquareNode newNode) {
        if (x == -1) {
            newNode = nodeFactory.buildNode("square_nodes", 3, 3, 3, 3);
        }
        String image = "TextBox_Square_Interface.svg";
        StackPane newUIShape = UIBasicRequest(newNode, image, 300, 150, Pos.CENTER, x, y, text);
        if (x == -1){/*database save call*/}
        support.firePropertyChange("newNodeCreation", null, newUIShape);
    }

    /**
     * Creates creates an edge for the data and a line for the UI. Then notifies the FXMLController to update.
     */
    public void makeEdgeRequest(StackPane n0, StackPane n1) {
        UIDefaultEdge newEdge = edgeFactory.buildEdge();
        Line newLine = UIEdgeRequest(n0, n1);
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

    public Line UIEdgeRequest(StackPane n0, StackPane n1) {
        //edgeFactory.buildEdge((AbstractNode) n0.getUserData(), (AbstractNode) n1.getUserData());
        Line line = new Line();
        line.setStartX(n0.getTranslateX() + (n0.getWidth() / 2));
        line.setStartY(n0.getTranslateY() + (n0.getHeight() / 2));
        line.setEndX(n1.getTranslateX() + (n1.getWidth() / 2));
        line.setEndY(n1.getTranslateY() + (n1.getHeight() / 2));
        line.setStrokeWidth(3);
        StackPane[] UINodes = {n0, n1};
        line.setUserData(UINodes);
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
        stack.setPrefWidth(300);
        stack.setPrefHeight(150);

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
     * @param w width of your pane
     * @param h height of your pane
     * @param namePos the position you'd like the name to appear.
     * @param x x coordinate, if loadin a node. -1 if a new node
     * @param y y coordinate, if loading a node.
     * @param text the loaded name of the node.
     * @return UI StackPane
     */
    public StackPane UIBasicRequest(UINode node, String image, int w, int h, Pos namePos, int x, int y, String text) {
        StackPane stack = makeStackPane(node, image);
        stack.setPrefWidth(w);
        stack.setPrefHeight(h);

        Label name = new Label((String) node.getName());
        name.setWrapText(true);
        stack.setPrefHeight(h);
        stack.setPrefHeight(w);
        if (x != -1){       // For loading in a new node
            stack.setTranslateX(x);
            stack.setTranslateX(y);
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
        StackPane stack = UIBasicRequest(node, "Loop.svg", 300, 150, Pos.TOP_CENTER, x, y, name);
        Label loopLabel = new Label("Loop");
        stack.getChildren().add(loopLabel);
        StackPane.setAlignment(loopLabel, Pos.TOP_LEFT);
        return stack;
    }

    /**
     * Creates a new user and gets their ID from the database
     * @param name name of the user
     */
    private UIUser createNewUser(String name){
        try {
            UIUser newUser = new UIUser(-1, name);
            String dbUserString = HTTPClient.usercreaterequest(newUser.getIDAsJson());
            newUser.setId(Integer.valueOf(dbUserString));
            //newUser.setId(dbUserString.matches("\\d+")); // regex gets on the integers that were returned(ID)
            return newUser;
        }
        catch (Exception e){e.printStackTrace();}
        return null;
    }

    /*private UIPage createNewPage(UIUser user, ){
    }*/

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

    public void loadPage(ArrayList<UINode> nodes/*, UIEdge[] edge*/){
        for (UINode curNode: nodes) {
            loadNode(curNode);
        }
        /*for (UIEdge curEdge: edge){
            loadEdge(curEdge);
        }*/
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

    private void loadeEdge(UIEdge edge, Pane canvasPane){}

    /**
     * Test function to make sure that the loading works. Will remove in the future.
     */
    private void loadNodesTest(){
        UIOvalNode ovalTestNode = nodeFactory.buildNode("oval_nodes", 150, 600, 3, 3);
        UIFolderNode folderTestNode = nodeFactory.buildNode("folder_nodes", 500, 150, 3, 3);
        ovalTestNode.setName("NAME WORKS!!!");
        folderTestNode.setName("I FOLDER I WORK");
        ArrayList<UINode> testArray = new ArrayList<UINode>();
        testArray.add(ovalTestNode);
        testArray.add(folderTestNode);
        loadPage(testArray);
    }
}