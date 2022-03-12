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
     * This is the function that is called by ObjectRequester to update the UI
     * @param event The event that was changed that needs to be reflected in the UI.
     */
    public void propertyChange(PropertyChangeEvent event){
    	switch(event.getPropertyName()) {
    	case "newOvalCreation" -> this.updateUINewOval((AbstractNode) event.getNewValue());
    	case "newClassCreation" -> this.updateUINewClass((Shape) event.getNewValue());
    	case "newEdgeCreation" -> this.updateUINewEdge((AbstractEdge) event.getNewValue());
    	case "classUpdate" -> {this.updateUIClassChange((String) event.getNewValue());System.out.print("working \n");}
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
     * These 2 function are the exact same right now, in the future it may be necessary for creation of different UML object.
     * There is probably a way, so I can use 1 function to make any node object, gotta think about it.
     * @param newNode The newly create Node object that needs to be displayed
     */
    private void updateUINewOval(AbstractNode newNode){
        testLabel.setText(newNode.toString());
    }

    private void updateUINewClass(Shape newNodeUIRepresentation){
        testLabel.setText(newNodeUIRepresentation.getUserData().toString());
        canvasPane.getChildren().add(newNodeUIRepresentation);
    }
    
    private void updateUIClassChange(String node) {
        System.out.print(node);
        testLabel.setText(node);
    }

    /**
     * Just makes an edge object and displays it's info.
     */
    private void updateUINewEdge(AbstractEdge newEdge){
    	//canvasPane.getChildren().add(newEdge);
        testLabel.setText(newEdge.toString());
    }

}