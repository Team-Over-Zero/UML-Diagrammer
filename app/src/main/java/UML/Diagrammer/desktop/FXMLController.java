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
 * FXMLController.java
 *
 * Controller for FXML that will control the logic for the UI.
 * This class will be set up as an observer(PropertyChangeListener), so when there is a change in the backend
 * the UI can update automatically.
 * @author Show
 */

package UML.Diagrammer.desktop;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Line;
import javafx.stage.FileChooser;
import lombok.Getter;
import org.apache.batik.transcoder.TranscoderException;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;


public class FXMLController extends App implements PropertyChangeListener{

    /**
     * Observer object for references
     */
    @Getter public static final ObjectRequester objectRequesterObservable = new ObjectRequester();
    public static final ActionHandler action = new ActionHandler();

    /**
     * Constructor to set this class as the observer for the observable objects
     */
    public FXMLController(){
        objectRequesterObservable.addPropertyChangeListener(this);
        action.addPropertyChangeListener(this);
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
    	case "newEdgeCreation" -> this.updateUINewEdge((Line) event.getNewValue());
    	case "setMouseActions" -> this.setMouseActions((StackPane) event.getNewValue());
    	case "clearLineCreationActions" -> this.clearLineCreationActions();
    	case "finishedDragUpdateEdges" -> this.updateEdgesAfterDrag((StackPane) event.getNewValue());
        }
    }

    /**
     * Label for the UI to display object changes
     */
    @FXML private Label ActionLabel;
    @FXML public Label noElementSelectedErrorLabel;
    @FXML public Pane canvasPane;

    /**
     * These functions are what is executed on the press of the UML object button(oval, class etc.).
     * Sends a request to ObjectRequester to make a UML object(Just a node right now).
     * ObjectRequester then turns around after making the object and calls updateUIFunction with said object for UI display
     */
    @FXML private void ovalButtonPressed() throws TranscoderException, IOException {
        objectRequesterObservable.makeOvalRequest();}
    @FXML private void classButtonPressed() {
        objectRequesterObservable.makeClassRequest();}
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
     * Have this jump to ActionHandler, AH will return the two stackpanes I need to create the line.
     * Then it calls ObjectRequester with those stackpanes and makes the edge, both UI and data.
     * Need to update the movement method in AH to updates edges as well(If there is one.)
     */
    @FXML private void edgeButtonPressed() {
        ActionLabel.setText("Select 2 items to draw a line between them");
        ActionLabel.setVisible(true);
        action.connectNodes(canvasPane);
    }

    @FXML private void deleteButtonPressed(){
        action.deleteObject(null, canvasPane);
    }
    @FXML private void editButtonPressed(){
        action.makePopUpEditTextBox(null, (int)App.primaryStage.getX(), (int)App.primaryStage.getY());
    }

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
    private void updateUINewEdge(Line newLine){
        canvasPane.getChildren().add(newLine);
        newLine.setOnContextMenuRequested(e ->
                action.makeEdgeContextMenu(newLine, canvasPane, (int)e.getScreenX(), (int)e.getScreenY()));
    }

    /**
     * Sets the action for a double click on an object to edit it.
     * @param fxObject the StackPane associated with the object.
     */
    private void setMouseActions(StackPane fxObject) {
        fxObject.setOnMouseClicked(clickNodeEventHandler);
        fxObject.setOnMousePressed(nodeOnMouseGrabEventHandler);
        fxObject.setOnMouseDragged(nodeOnMouseDragEventHandler);
        fxObject.setOnMouseReleased(nodeOnMouseReleased);
        fxObject.setOnContextMenuRequested(e -> // Right click
                action.makeContextMenu(fxObject, canvasPane, (int)e.getScreenX(), (int)e.getScreenY()));
    }

    /**
     * Changes the mouse action from creating a line back to the default action.
     */
    private void clearLineCreationActions(){
        for (Node curElement : canvasPane.getChildren()) {
            if (curElement instanceof StackPane curStackPane){
                curStackPane.setOnMouseClicked(clickNodeEventHandler);
                ActionLabel.setVisible(false);
            }
        }
    }

    /**
     * After an node is dragged, it checks every edge and updates it if it's end/starting node matches the line.
     * There is no need to update anything on the backend, since the only data for an edge is its nodes.
     * @param movedElement The UI Element that was moved
     */
    private void updateEdgesAfterDrag(StackPane movedElement){
        for (Object obj: canvasPane.getChildren()) {
            if (obj instanceof Line curLine) {
                StackPane[] curLineConnectedNodes = (StackPane[]) curLine.getUserData();
                if(curLineConnectedNodes[0] == movedElement){
                    curLine.setStartX(movedElement.getTranslateX() + movedElement.getWidth() / 2);
                    curLine.setStartY(movedElement.getTranslateY() + movedElement.getHeight() / 2);
                }
                else if(curLineConnectedNodes[1] == movedElement){
                    curLine.setEndX(movedElement.getTranslateX() + movedElement.getWidth() / 2);
                    curLine.setEndY(movedElement.getTranslateY() + movedElement.getHeight() / 2);
                }
            }
        }
    }

    /**
     * Takes a screenshot of the current scene when you press the png export button
     */
    @FXML
    private void exportToPNG() {
        action.clearFocusedElement();
        FileChooser fileChooser = new FileChooser();

        //Set extension filter
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("png files (*.png)", "*.png"));

        //Prompt user to select a file
        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            try {
                //Pad the capture area
                WritableImage writableImage = new WritableImage((int) App.primaryStage.getWidth() + 20,
                        (int) App.primaryStage.getHeight() - 120);
                canvasPane.snapshot(null, writableImage);
                RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
                //Write the snapshot to the chosen file
                ImageIO.write(renderedImage, "png", file);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Double click event handler for editing text on a node.
     * Might break this up for specific types of nodes since some will only need name, or name + desc etc.
\     */
    EventHandler<MouseEvent> clickNodeEventHandler =
            action::clickObject;

    /**
     * These two function together allow for dragging of shapes across the canvas
     */
    EventHandler<MouseEvent> nodeOnMouseGrabEventHandler =
            action::grabObject;
    EventHandler<MouseEvent> nodeOnMouseDragEventHandler =
            action::moveObject;

    EventHandler<MouseEvent> nodeOnMouseReleased =
            action::releaseObject;
}