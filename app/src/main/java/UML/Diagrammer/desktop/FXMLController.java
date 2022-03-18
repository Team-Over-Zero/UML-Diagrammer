/**
 * FXMLController.java
 *
 * Controller for FXML that will control the logic for the UI.
 * This class will be set up as an observer(PropertyChangeListener), so when there is a change in the backend
 * the UI can update automatically.
 * @author Show
 */

package UML.Diagrammer.desktop;

import UML.Diagrammer.backend.objects.AbstractEdge;
import UML.Diagrammer.backend.objects.AbstractNode;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Popup;
import lombok.Getter;
import org.apache.batik.transcoder.TranscoderException;
import org.javalite.activejdbc.Base;
import org.w3c.dom.Text;
import org.w3c.dom.events.UIEvent;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;


public class FXMLController extends App implements PropertyChangeListener{

    /**
     * Observer object for references
     */
    @Getter private final ObjectRequester objectRequesterObservable = new ObjectRequester();
    private static ActionHandler action = new ActionHandler();

    /**
     * Constructor to set this class as the observer for the observable objects
     */
    public FXMLController(){
        objectRequesterObservable.addPropertyChangeListener(this);
        //canvasObservable.addPropertyChangeListener(this);
    }

    /**
     * This is the function that is called by ObjectRequester(or any observable) to update the UI
     * newNodeCreation should deal with any of it's subtype of nodes.
     * newNodeCreation will require the UI Object (Shape) because the shape has the data(node) associated with it already.
     * @param event The event that was changed that needs to be reflected in the UI.
     */
    public void propertyChange(PropertyChangeEvent event){
    	switch(event.getPropertyName()) {
    	case "newNodeCreation" -> this.updateUINewNode((StackPane) event.getNewValue());
    	case "newEdgeCreation" -> this.updateUINewEdge((AbstractEdge) event.getNewValue());
    	case "setMouseActions" -> this.setMouseActions((StackPane) event.getNewValue());
    	}
    }

    /**
     * Label for the UI to display object changes
     */
    @FXML private Label testLabel;
    @FXML private Label circleObject;
    @FXML public Pane canvasPane;


    /**
     * These functions are what is executed on the press of the UML object button(oval, class etc.).
     * Sends a request to ObjectRequester to make a UML object(Just a node right now).
     * ObjectRequester then turns around after making the object and calls updateUIFunction with said object for UI display
     */
    @FXML private void ovalButtonPressed() throws TranscoderException, IOException {
        objectRequesterObservable.makeOvalRequest();
    }
    @FXML private void classButtonPressed() {
        objectRequesterObservable.makeClassRequest();}
    @FXML private void edgeButtonPressed() {
        objectRequesterObservable.makeEdgeRequest();}
    @FXML private void folderButtonPressed() {
        objectRequesterObservable.makeFolderRequest();}
    @FXML private void lifeLineButtonPressed() {
        objectRequesterObservable.makeLifeLineRequest();}
    @FXML private void loopButtonPressed() {
        objectRequesterObservable.makeLoopRequest();}
    @FXML private void noteButtonPressed() {
        objectRequesterObservable.makeNoteRequest();}
    @FXML private void stickFigureButtonPressed() {
        objectRequesterObservable.makeStickFigureRequest();}
    @FXML private void TextBoxButtonPressed() {
        objectRequesterObservable.makeTextBoxRequest();}
    @FXML private void SquareButtonPressed() {
        objectRequesterObservable.makeSquareRequest();}

    /**
     * This function is called after a new node is created and updates the UI to display the new element.
     * The shape required by the parameters will have a node associated with it, accessible via shape.getUserData().
     * @param newNodeUIRepresentation this is the shape made by the ObjectRequester
     */
    private void updateUINewNode(StackPane newNodeUIRepresentation){
        canvasPane.getChildren().add(newNodeUIRepresentation);
    }
    
    /**
     * Just displays the edge's info to the screen via a label for now.
     */
    private void updateUINewEdge(AbstractEdge newEdge){
        testLabel.setText(newEdge.toString());
    }

    /**
     * Sets the action for a double click on an object to edit it.
     *
     * @param fxObject the StackPane associated with the object.
     */
    private void setMouseActions(StackPane fxObject) {
        fxObject.setOnMouseClicked(clickNodeEventHandler);
        fxObject.setOnMousePressed(nodeOnMouseGrabEventHandler);
        fxObject.setOnMouseDragged(nodeOnMouseDragEventHandler);
        fxObject.setOnContextMenuRequested(e ->
                action.makeContextMenu(fxObject, (int)e.getSceneX(), (int)e.getSceneY()));
    }

    /**
     * Double click event handler for editing text on a node.
     * Might break this up for specific types of nodes since some will only need name, or name + desc etc.
\     */
    EventHandler<MouseEvent> clickNodeEventHandler =
            t -> { // Double click
                StackPane uIElement = (StackPane) t.getSource();
                if (t.getButton().equals(MouseButton.PRIMARY)) {
                    if (t.getClickCount() == 2) {
                        action.makePopUpEditTextBox(uIElement, (int)t.getScreenX(), (int)t.getSceneY());
                    }
                } // Right click
                else if (t.getButton().equals(MouseButton.SECONDARY)) {
                    action.makeContextMenu(uIElement, (int)t.getScreenX(), (int)t.getSceneY());
                    System.out.println("right clicked");
                }
            };

    /**
     * These two function together allow for dragging of shapes across the canvas
     */
    EventHandler<MouseEvent> nodeOnMouseGrabEventHandler =
            t -> action.grabObject(t);

    EventHandler<MouseEvent> nodeOnMouseDragEventHandler =
            t -> action.moveObject(t);
}