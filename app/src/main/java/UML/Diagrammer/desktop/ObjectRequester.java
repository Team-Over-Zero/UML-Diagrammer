/**
 * ObjectRequester.java
 * Class to create objects using the backend.
 * The FXMLController will use these function to request object creation using the backend.
 * This class is set up as an observable, so it can call support.firePropertyChange to update the UI.
 * @author Show P
 */

package UML.Diagrammer.desktop;


import java.awt.Shape;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import UML.Diagrammer.backend.objects.*;
import UML.Diagrammer.backend.objects.EdgeFactory.*;
import UML.Diagrammer.backend.objects.NodeFactory.*;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;


public class ObjectRequester {

    /**
     * Global observable object to call updates to the UI.
     */
    private final PropertyChangeSupport support;
    private static final NodeFactory nodeFactory = new NodeFactory();
    private static final EdgeFactory edgeFactory = new EdgeFactory();
    private static final Canvas canvas = new Canvas();

    /**
     * Constructor, needs to make the PropertyChangeSupport object for to notify listeners
     */
    ObjectRequester(){
        support = new PropertyChangeSupport(this);
    }

    /**
     * Adds a listener to notify when a change occurs
     * @param listener who want's to know when this object changes(Probably javaFX UI)
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
    public void makeOvalRequest(){
        OvalNode newNode = nodeFactory.buildNode("OVAL", 3, 3, 3, 3);
        Image image = new Image("/Images/DefaultImage.png");
    	Rectangle newUIShape = UIShapeRequest(image, (AbstractNode) newNode);
        support.firePropertyChange("newNodeCreation", null, newUIShape);
    }

    /**
     * Creates a class object along with a rectangle and ties them together. 
     * Then returns these objects to the UI via the support.firePropertyChange call.
     */
    public void makeClassRequest(){
    	ClassNode newNode = nodeFactory.buildNode("CLASS", 3, 3, 3, 3);
        Image image = new Image("/Images/PngTestClass.png");
    	Rectangle newUIShape = UIShapeRequest(image, (AbstractNode) newNode);
        support.firePropertyChange("newNodeCreation", null, newUIShape);
    }

    /**
     * Creates a default edge for now and displays and lets the controller know.
     */
    public void makeEdgeRequest(){
        DefaultEdge newEdge = edgeFactory.buildEdge();
        support.firePropertyChange("newEdgeCreation", null, newEdge);
    }
    
    /**
     * Sets up the mouse actions for dragging and updating the associated node.
     * This should be called after any new creation of a node.
     * setUserData makes it so we can reference the data object from the UI object via UIElement.getUserData()
     * This effectively ties together the UI object Rectangle and the given AbstractNode data object.
     * @param fxObject The UI element that is displayed to the screen.
     * @param node The actual backend object that is apart of the data.
     */
    public void setMouseActions(Rectangle fxObject, AbstractNode node) {
    	fxObject.setUserData(node);
    	fxObject.setOnMousePressed(canvas.nodeOnMousePressedEventHandler);
    	fxObject.setOnMouseDragged(canvas.nodeOnMouseDraggedEventHandler);
    }
    
    /**
     * Creates a new shape for the UI and sets up it's mouse actions
     * This function ties together a UI Object (Rectangle) and node object (AbstractNode). 
     * @param image the image that you would like to associate with the new UI element. (Will be a UML element)
     * @param node The node that connects to the data side of things
     * @return the shape you requested with it's data node linked and actions set up.
     */
    public Rectangle UIShapeRequest(Image image, AbstractNode node) {
    	Rectangle rectangle = new Rectangle(200.0f, 100.0f, Color.RED);
    	rectangle.setFill(new ImagePattern(image));
    	rectangle.setX(200);
    	rectangle.setY(200);
    	rectangle.setCursor(Cursor.HAND);
    	setMouseActions(rectangle, node);
        return rectangle;
    }

}
