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
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import lombok.Getter;
import org.apache.batik.transcoder.TranscoderException;
import org.javalite.activejdbc.Base;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;


public class FXMLController implements PropertyChangeListener{

    /**
     * Observer object for references
     */
    @Getter private final ObjectRequester objectRequesterObservable = new ObjectRequester();
    @Getter private final Canvas canvasObservable = new Canvas();

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
        System.out.println("PROP CHANGE CALLED");
    	switch(event.getPropertyName()) {
    	case "newNodeCreation" -> this.updateUINewNode((StackPane) event.getNewValue());
    	case "newEdgeCreation" -> this.updateUINewEdge((AbstractEdge) event.getNewValue());
    	case "doubleMouseClick" -> this.setMouseClicked((StackPane) event.getNewValue());
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
    @FXML private void ovalButtonPressed() throws TranscoderException, IOException {
        openConnection();
        objectRequesterObservable.makeOvalRequest();
        closeConnection();
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
        //testLabel.setText(newNodeUIRepresentation.getUserData().toString()); // Node creation ********************************
        canvasPane.getChildren().add(newNodeUIRepresentation);
    }
    
    /**
     * Just displays the edge's info to the screen via a label for now.
     */
    private void updateUINewEdge(AbstractEdge newEdge){
        //testLabel.setText(newEdge.toString()); // Edge creation ********************************
    }

    /**
     * Double click event handler for editing text on a node.
     * Might break this up for specific types of nodes since some will only need name, or name + desc etc.
     * This is in the FXMLController right now due to a strange error in the Canvas not working well with being an observable.
     * Will look into this for future releases
     */
    EventHandler<MouseEvent> nodeOnMouseClickedEventHandler =
            new EventHandler<>() {
                public void handle(MouseEvent t) {
                    if (t.getButton().equals(MouseButton.PRIMARY)) {
                        if (t.getClickCount() == 2) {
                            StackPane UIElement = (StackPane) t.getSource();
                            TextField textField = makeNodeTextBox(UIElement);
                            canvasPane.getChildren().add(textField);
                        }
                    }
                }
            };

    /**
     * Sets the action for a double click on an object to edit it.
     * @param fxObject the StackPane associated with the object.
     */
    private void setMouseClicked(StackPane fxObject){
        fxObject.setOnMouseClicked(nodeOnMouseClickedEventHandler);
    }

    /**
     * Creates a textbox for the user to input data and edit a node.
     * @param UIElement The stack pane associated with the object
     * @return A new text field at a position above the node.
     */
    public TextField makeNodeTextBox(StackPane UIElement){
        TextField editTextField = new TextField();
        editTextField.setCursor(Cursor.TEXT);
        editTextField.setAlignment(Pos.CENTER);
        editTextField.setMaxHeight(100);
        editTextField.setMaxWidth(300);
        editTextField.setLayoutX(UIElement.getLayoutX());
        editTextField.setLayoutY(UIElement.getLayoutY() - (UIElement.getHeight() / 2) + 30); // Set the textbox just above the UI element
        return editTextField;
    }

    public void openConnection(){
        String databaseURL = "jdbc:mysql://ls-a9db0e6496e5430883b43e690a26b7676cf9d7af.cuirr4jp1g1o.us-west-2.rds.amazonaws.com/test?useSSL=false";
        String databaseUser = "root";
        String databasePassword = "TeamOverZero";

        Base.open("com.mysql.cj.jdbc.Driver", databaseURL, databaseUser, databasePassword);
    }


    public void closeConnection(){
        Base.close();
    }

}