/**
 * ObjectRequester.java
 * Class to create objects using the backend.
 * The FXMLController will use these function to request object creation using the backend.
 * This class is set up as an observable, so it can call support.firePropertyChange to update the UI.
 * @author Show P
 */

package UML.Diagrammer.desktop;


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
     * Calls the backend to make the object.
     * New node creation so the oldValue will be null
     */
    public void makeOvalRequest(){
        OvalNode newNode = nodeFactory.buildNode("OVAL", 0, 7, 7, 3);
        support.firePropertyChange("newOvalCreation", null, newNode);
    }

    /**
     * A wip of how we might create the UI object and backend object, then associate them to each other.
     */
    public void makeClassRequest(){
    	ClassNode newNode = nodeFactory.buildNode("CLASS", 3, 3, 3, 3);
    	Rectangle redRectange = new Rectangle(200.0f, 100.0f, Color.RED);
    	Image image = new Image("/Images/PngTestClass.png");
    	redRectange.setFill(new ImagePattern(image));
    	redRectange.setX(200);
    	redRectange.setY(200);
    	redRectange.setCursor(Cursor.HAND);
    	setMouseActions(redRectange, newNode);
    	
        //DefaultEdge newEdge = edgeFactory.buildEdge();
        support.firePropertyChange("newClassCreation", null, redRectange);
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
     * @param fxObject The UI element that is displayed to the screen.
     * @param node The actual backend object that is apart of the data.
     */
    public void setMouseActions(Rectangle fxObject, ClassNode node) {
    	fxObject.setUserData(node);
    	fxObject.setOnMousePressed(canvas.circleOnMousePressedEventHandler);
    	fxObject.setOnMouseDragged(canvas.circleOnMouseDraggedEventHandler);
    }

}
