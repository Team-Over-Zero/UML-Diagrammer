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

import UML.Diagrammer.backend.objects.AbstractNode;
import UML.Diagrammer.backend.objects.EdgeFactory.EdgeFactory;
import UML.Diagrammer.backend.objects.NodeFactory.*;
import javafx.scene.Cursor;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Line;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Text;
import org.apache.batik.transcoder.TranscoderException;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class ObjectRequester {

    /**
     * Global observable object to call updates to the UI.
     */
    private final PropertyChangeSupport support;
    private static final NodeFactory nodeFactory = new NodeFactory();
    private static final EdgeFactory edgeFactory = new EdgeFactory();


    /**
     * Constructor, needs to make the PropertyChangeSupport object for to notify listeners
     */
    ObjectRequester(){
        support = new PropertyChangeSupport(this);
    }

    /**
     * Adds a listener to notify when a change occurs
     * @param listener who wants to know when this object changes(Probably javaFX UI)
     */
    public void addPropertyChangeListener(PropertyChangeListener listener){
        support.addPropertyChangeListener(listener);
    }
    /**
     * Removes a listener so they no longer update on a change
     * @param listener the listener you'd like to remove
     */
    public void removePropertyChangeListener(PropertyChangeListener listener){
        support.removePropertyChangeListener(listener);
    }

    /**
     * Creates a class object along with a rectangle and ties them together.
     * Then returns these objects to the UI via the support.firePropertyChange call.
     * All makeXRequest function should look really similar to this function, the difference being the image and node type.
     */
    public void makeOvalRequest() throws TranscoderException, IOException {
        OvalNode newNode = nodeFactory.buildNode("oval_nodes", 3, 3, 3, 3);
        String image = "/Oval_UseCase.svg";
        StackPane newUIShape = UIShapeRequest(image,newNode);
        support.firePropertyChange("newNodeCreation", null, newUIShape);
    }

    /**
     * Creates a class object along with a stackpane and ties them together.
     * Then returns these objects to the UI via the support.firePropertyChange call.
     */
    public void makeClassRequest(){
    	ClassNode newNode = nodeFactory.buildNode("class_nodes", 3, 3, 3, 3);
        String image = "Class.svg";
    	StackPane newUIShape = UIShapeRequest(image,newNode);
        support.firePropertyChange("newNodeCreation", null, newUIShape);
    }

    public void makeFolderRequest(){
        FolderNode newNode = nodeFactory.buildNode("folder_nodes", 3, 3, 3, 3);
        String image = "/Folder.svg";
        StackPane newUIShape = UIShapeRequest(image,newNode);
        support.firePropertyChange("newNodeCreation", null, newUIShape);
    }

    public void makeLifeLineRequest(){
        LifeLineNode newNode = nodeFactory.buildNode("life_line_nodes", 3, 3, 3, 3);
        String image = "/LifeLine.svg";
        StackPane newUIShape = UIShapeRequest(image,newNode);
        support.firePropertyChange("newNodeCreation", null, newUIShape);
    }

    public void makeLoopRequest(){
        LoopNode newNode = nodeFactory.buildNode("loop_nodes", 3, 3, 3, 3);
        String image = "/Loop.svg";
        StackPane newUIShape = UIShapeRequest(image,newNode);
        support.firePropertyChange("newNodeCreation", null, newUIShape);
    }

    public void makeNoteRequest(){
        NoteNode newNode = nodeFactory.buildNode("note_nodes", 3, 3, 3, 3);
        String image = "/Note.svg";
        StackPane newUIShape = UIShapeRequest(image,newNode);
        support.firePropertyChange("newNodeCreation", null, newUIShape);
    }

    public void makeStickFigureRequest(){
        StickFigureNode newNode = nodeFactory.buildNode("stick_figure_nodes", 3, 3, 3, 3);
        String image = "/StickFigure.svg";
        StackPane newUIShape = UIShapeRequest(image,newNode);
        support.firePropertyChange("newNodeCreation", null, newUIShape);
    }

    public void makeTextBoxRequest(){
        TextBoxNode newNode = nodeFactory.buildNode("text_box_nodes", 3, 3, 3, 3);
        String image = "/TextBox_Square_Interface.svg";
        StackPane newUIShape = UIShapeRequest(image,newNode);
        support.firePropertyChange("newNodeCreation", null, newUIShape);
    }

    public void makeSquareRequest(){
        SquareNode newNode = nodeFactory.buildNode("square_nodes", 3, 3, 3, 3);
        String image = "/TextBox_Square_Interface.svg";
        StackPane newUIShape = UIShapeRequest(image,newNode);
        support.firePropertyChange("newNodeCreation", null, newUIShape);
    }

    /**
     * Creates creates an edge for the data and a line for the UI. Then notifies the FXMLController to update.
     */
    public void makeEdgeRequest(StackPane n0, StackPane n1){
        //DefaultEdge newEdge = edgeFactory.buildEdge();
        Line newLine = UIEdgeRequest(n0,n1);
        support.firePropertyChange("newEdgeCreation", null, newLine);
    }

    /**
     * When we make an edge. The edge will know about it's associated nodes. But the nodes won't know about the edge.
     * So when we move an node, we need to make a call to tell the edge that we moved and the edge can update.
     * This is okay on the data side since all the data that is needed is the nodes that are collected. All other info
     * can be inferred from the UI/UI objects of said nodes.
     * @param n0 first node
     * @param n1 second node
     * @return the created UI object line.
     */
    public Line UIEdgeRequest(StackPane n0, StackPane n1){
       // edgeFactory.buildEdge((AbstractNode) n0.getUserData(), (AbstractNode) n1.getUserData());
        Line line = new Line();
        line.setStartX(n0.getTranslateX()+ (n0.getWidth()/2));
        line.setStartY(n0.getTranslateY() + (n0.getHeight()/2));
        line.setEndX(n1.getTranslateX() + (n1.getWidth()/2));
        line.setEndY(n1.getTranslateY() + (n1.getHeight()/2));
        StackPane[] UINodes = {n0, n1};
        line.setUserData(UINodes);
        return line; // I NEED TO MAKE AN UPDATE EVERYTIME i MOVE A NODE OR DELETE IT.
    }
    
    /**
     * Sets up the mouse actions for dragging and updating the associated node.
     * This should be called after any new creation of a node.
     * setUserData makes it so we can reference the data object from the UI object via UIElement.getUserData()
     * This effectively ties together the UI object Rectangle and the given AbstractNode data object.
     * @param fxObject The UI element that is displayed to the screen.
     * @param node The actual backend object that is apart of the data.
     */
    public void setMouseActions(StackPane fxObject, AbstractNode node) {
    	fxObject.setUserData(node);
    	support.firePropertyChange("setMouseActions", null, fxObject);
    }
    
    /**
     * Creates a new shape for the UI and sets up it's mouse actions
     * This function ties together a UI Object (StackPane) and node object (AbstractNode).
     * @param image the image that you would like to associate with the new UI element.
     * @param node The node that connects to the data side of things
     * @return the shape you requested with its data node linked and actions set up.
     */
    public StackPane UIShapeRequest(String image, AbstractNode node) {

        try {
            SVGPath path = new SVGPath();

            Path filePath = Paths.get("src/main/resources/Images/"+image);
            Path absPath = filePath.toAbsolutePath();

            String svgString = Files.readString(absPath);

            path.setContent(svgString);

            StackPane stack = new StackPane(path);

            stack.setPrefWidth(300);
            stack.setPrefHeight(300);
            stack.setCursor(Cursor.HAND);

            Text text = new Text((String) node.get("Name"));

            stack.getChildren().addAll(text);
            setMouseActions(stack, node);
            return stack;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;

    }
}