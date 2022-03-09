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
import javafx.scene.shape.Circle;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Objects;


public class FXMLController implements PropertyChangeListener{

    /**
     * Observer object for references
     */
    private static final ObjectRequester observable = new ObjectRequester();

    /**
     * Constructor to set this class as the observer for the observable objects
     */
    public FXMLController(){
        observable.addPropertyChangeListener(this);
    }

    /**
     * This is the function that is called by ObjectRequester to update the UI
     * @param event The event that was changed that needs to be reflected in the UI.
     */
    public void propertyChange(PropertyChangeEvent event){
    	switch(event.getPropertyName()) {
    	case "newCircleCreation" -> this.updateUINewCircle((AbstractNode) event.getNewValue());
    	case "newSquareCreation" -> this.updateUINewClass((AbstractNode) event.getNewValue());
    	case "newEdgeCreation" -> this.updateUINewEdge((Circle) event.getNewValue());
    	}
    }

    /**
     * Label for the UI to display object changes
     */
    @FXML private Label testLabel;
    @FXML private Label circleObject;
    @FXML private Pane canvasPane;


    /**
     * These functions are what is executed on the press of the UML object button(Circle, square etc.).
     * Sends a request to ObjectRequester to make a UML object(Just a node right now).
     * ObjectRequester then turns around after making the object and calls updateUIFunction with said object for UI display
     */
    @FXML private void ovalButtonPressed() {
        observable.makeCircleRequest();
    }

    @FXML private void classButtonPressed() {
        observable.makeClassRequest();
    }

    @FXML
    private void edgeButtonPressed() {
        observable.makeEdgeRequest();
    }
    

    /**
     * These 2 function are the exact same right now, in the future it may be necessary for creation of different UML object.
     * There is probably a way, so I can use 1 function to make any node object, gotta think about it.
     * @param newNode The newly create Node object that needs to be displayed
     */
    private void updateUINewCircle(AbstractNode newNode){
        testLabel.setText(newNode.toString());
    }

    private void updateUINewClass(AbstractNode newNode){
        testLabel.setText(newNode.toString());
    }

    /**
     * Currently being used as a test for moving objects around the canvas.
     * @param newEdge The object that is created (just a circle for now)
     */
    private void updateUINewEdge(Circle newEdge){
    	canvasPane.getChildren().add(newEdge);
        testLabel.setText(newEdge.toString());
    }

}