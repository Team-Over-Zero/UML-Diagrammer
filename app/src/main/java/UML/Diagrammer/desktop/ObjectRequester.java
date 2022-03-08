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
     * These create the object right here for now.
     * In the future we will want to have a factory in the backend, so we don't make new objects in the UI itself.
     * New node creation so the oldValue will be null
     */
    public void makeCircleRequest(){
        OvalNode newNode = nodeFactory.buildNode("OVAL", 0, 7, 7, 3);
        //DefaultNode newNode = new DefaultNode();
        //newNode.setName("UML-Object-2");
        //newNode.setDescription("circle");
        //newNode.setTitle("object");
        support.firePropertyChange("newCircleCreation", null, newNode);
    }

    /**
     * Exact same as makCircleRequest() for now.
     */
    public void makeClassRequest(){
        ClassNode newNode = nodeFactory.buildNode("CLASS", 2, 4, 2, 1);
        //newNode.setName("UML-Object-1");
        //newNode.setDescription("square");
        //newNode.setTitle("class");
        support.firePropertyChange("newSquareCreation", null, newNode);
    }

    public void makeEdgeRequest(){
        DefaultEdge newEdge = edgeFactory.buildEdge();
        support.firePropertyChange("newEdgeCreation", null, newEdge);
    }

}
