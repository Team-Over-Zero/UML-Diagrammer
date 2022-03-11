/**
 * FXMLController.java
 *
 * Controller for FXML that will control the logic for the UI.
 * This class will be set up as an observer(PropertyChangeListener), so when there is a change in the backend
 * the UI can update automatically.
 * @author Show
 */

package UML.Diagrammer.desktop;

import UML.Diagrammer.backend.objects.*;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Objects;


public class FXMLController implements PropertyChangeListener{

    /**
     * Observer object for references
     */
    private static final ObjectRequester objectRequesterObservable = new ObjectRequester();
    private static final Canvas canvasObservable = new Canvas();

    /**
     * Constructor to set this class as the observer for the observable objects
     */
    public FXMLController(){
        objectRequesterObservable.addPropertyChangeListener(this);
        canvasObservable.addPropertyChangeListener(this);
    }

    /**
     * This is the function that is called by ObjectRequester(or any observable) to update the UI
     * newNodeCreation should deal with any of it's subtype of nodes.
     * newNodeCreation will require the UI Object (Shape) because the shape has the data(node) associated with it already.
     * @param event The event that was changed that needs to be reflected in the UI.
     */
    public void propertyChange(PropertyChangeEvent event){
    	switch(event.getPropertyName()) {
    	case "newNodeCreation" -> this.updateUINewNode((Shape) event.getNewValue());
    	case "newEdgeCreation" -> this.updateUINewEdge((AbstractEdge) event.getNewValue());
    	}
    }

    /**
     * Label for the UI to display object changes
     */
    @FXML private Label testLabel;
    @FXML private Label circleObject;
    @FXML private Pane canvasPane;


    /**
     * These functions are what is executed on the press of the UML object button(oval, class etc.).
     * Sends a request to ObjectRequester to make a UML object(Just a node right now).
     * ObjectRequester then turns around after making the object and calls updateUIFunction with said object for UI display
     */
    @FXML private void ovalButtonPressed() {
        objectRequesterObservable.makeOvalRequest();
    }

    @FXML private void classButtonPressed() {
        objectRequesterObservable.makeClassRequest();
    }

    @FXML
    private void edgeButtonPressed() {
        objectRequesterObservable.makeEdgeRequest();
    }
    
    /**
     * This function is called after a new node is created and updates the UI to display the new element.
     * The shape required by the parameters will have a node associated with it, accessible via shape.getUserData().
     * @param newNodeUIRepresentation this is the shape made by the ObjectRequester
     */

    private void updateUINewNode(Shape newNodeUIRepresentation){
        testLabel.setText(newNodeUIRepresentation.getUserData().toString());
        canvasPane.getChildren().add(newNodeUIRepresentation);
    }
    
    /**
     * Just displays the edge's info to the screen via a label for now.
     */
    private void updateUINewEdge(AbstractEdge newEdge){
        testLabel.setText(newEdge.toString());
    }

}