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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;


public class FXMLController extends App implements PropertyChangeListener{

    /**
     * Observer object for references
     */
    @Getter private final ObjectRequester objectRequesterObservable = new ObjectRequester();
    //@Getter private final Canvas canvasObservable = new Canvas();

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
    @FXML private Pane canvasPane;


    /**
     * These functions are what is executed on the press of the UML object button(oval, class etc.).
     * Sends a request to ObjectRequester to make a UML object(Just a node right now).
     * ObjectRequester then turns around after making the object and calls updateUIFunction with said object for UI display
     */
    @FXML private void ovalButtonPressed() throws TranscoderException, IOException {
        //database.openConnection();
        objectRequesterObservable.makeOvalRequest();
        //database.closeConnection();
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
        fxObject.setOnMouseClicked(doubleClickNodeEventHandler);
        fxObject.setOnMousePressed(nodeOnMouseGrabEventHandler);
        fxObject.setOnMouseDragged(nodeOnMouseDragEventHandler);
    }

    /* Even handling for clicking on objects. Want to extract this all out into it's own class but the observer is
    * not happy with it, look into it for further releases.*/
    /**
     * Double click event handler for editing text on a node.
     * Might break this up for specific types of nodes since some will only need name, or name + desc etc.
\     */
    EventHandler<MouseEvent> doubleClickNodeEventHandler =
            t -> {
                if (t.getButton().equals(MouseButton.PRIMARY)) {
                    if (t.getClickCount() == 2) {
                        StackPane UIElement = (StackPane) t.getSource();
                        makePopUpEditTextBox(UIElement);
                    }
                }
            };


    /**
     * Creates a textbox for the user to input data and edit a node.
     *
     * @param UIElement The stack pane associated with the object
     * @return A new text field at a position above the node.
     */
    public void makePopUpEditTextBox(StackPane UIElement) {
        Popup popUp = new Popup();
        popUp.setHeight(500);    // Might want to scale this to the size of the node in the future
        popUp.setWidth(500);
        popUp.setX(960);
        popUp.setY(540);


        Label label = new Label("Enter a new name");
        //label.setAlignment(Pos.TOP_CENTER);

        TextField textField = new TextField();
        textField.setPrefWidth(200);
        textField.setPrefHeight(50);
        //textField.setAlignment(Pos.CENTER);

        Button button = new Button("Confirm");
        //button.setAlignment(Pos.BOTTOM_RIGHT);
        button.setOnAction(event -> {
            // Should be careful here, since I am completely clearing all labels and such in the UI element.
            UIElement.getChildren().clear();
            UIElement.getChildren().add(new Label(textField.getText()));
            ((AbstractNode)UIElement.getUserData()).set("Name", textField.getText());
            popUp.hide();
        });

        StackPane stackInPopUp = new StackPane();
        StackPane.setAlignment(textField, Pos.CENTER);
        StackPane.setAlignment(label, Pos.TOP_CENTER);
        StackPane.setAlignment(button, Pos.BOTTOM_RIGHT);

        stackInPopUp.getChildren().addAll(textField, label, button);


        popUp.getContent().add(stackInPopUp);

        popUp.show(App.primaryStage);
        popUp.setAutoHide(true);
    }

    /**
     * What happens when the user hits enter after entering a new text for the UML object.
     * Not implemented yet.
     */
    EventHandler<ActionEvent> confirmButtonIsPressedToEditText = event -> {
        Object nodeObject = event.getSource();
        AbstractNode associatedNode = (AbstractNode) ((StackPane) nodeObject).getUserData();

    };


    double orgSceneX, orgSceneY;
    double orgTranslateX, orgTranslateY;

    /**
     * These two function together allow for dragging of shapes across the canvas
     */
    EventHandler<MouseEvent> nodeOnMouseGrabEventHandler =
            new EventHandler<>() {

                @Override
                public void handle(MouseEvent t) {
                    orgSceneX = t.getSceneX();
                    orgSceneY = t.getSceneY();
                    orgTranslateX = ((StackPane)(t.getSource())).getTranslateX();
                    orgTranslateY = ((StackPane)(t.getSource())).getTranslateY();
                }
            };

    EventHandler<MouseEvent> nodeOnMouseDragEventHandler =
            new EventHandler<>() {

                /**
                 * With this method we are not "physically" moving the object. We actually have 2 different objects. The shape(javaFX) and the actual node.
                 * We move the shape across the screen and that in turn updates it's associated node object.
                 * This way we have 2 object "linked", one is for front end, the other is for backend.
                 * The caveat with this method is we must be very careful to update the backed object whenever we change the frontend one, or else we will get desync.
                 * Use Shape.getUserData() to get the node object that we associated with the UI element
                 */
                public void handle(MouseEvent t) {
                    // This deals with the visual movement on the UI.
                    double offsetX = t.getSceneX() - orgSceneX;
                    double offsetY = t.getSceneY() - orgSceneY;
                    double newTranslateX = orgTranslateX + offsetX;
                    double newTranslateY = orgTranslateY + offsetY;

                    ((StackPane)(t.getSource())).setTranslateX(newTranslateX);
                    ((StackPane)(t.getSource())).setTranslateY(newTranslateY);

                    // Sets the new coordinates of the that we moved.
                    Object nodeObject = t.getSource();
                    if (nodeObject instanceof StackPane) {
                        AbstractNode node = (AbstractNode) ((StackPane) nodeObject).getUserData();
                        node.set("x_coord", (int)newTranslateX);
                        node.set("y_coord", (int)newTranslateY); // Updates the object with the new coordinates
                    }
                }
            };
}