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

import UML.Diagrammer.backend.objects.UIEdge.UIEdge;
import UML.Diagrammer.backend.objects.UINode.UINode;
import UML.Diagrammer.backend.objects.UIPage;
import UML.Diagrammer.backend.objects.UIUser;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Line;
import javafx.stage.FileChooser;
import javafx.stage.Popup;
import lombok.Getter;
import lombok.Setter;
import org.apache.batik.transcoder.TranscoderException;
import org.checkerframework.checker.guieffect.qual.UI;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.util.Map;


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
     * Function is called when the board is launched via FXMLLoginController to set up the current user and page
     * in the ObjectRequester, so the requester knows what to reference for db - client connection.
     * @param user The user who logged in
     * @param page The page they are going to use(first page in db or a brand new one if no page is made yet.)
     */
    public static void setUpUserPage(UIUser user, UIPage page){
        objectRequesterObservable.setCurrentUser(user);
        objectRequesterObservable.setCurrentPage(page);
    }

    /**
     * This is the function that is called by ObjectRequester(or any observable) to update the UI
     * newNodeCreation should deal with any of its subtype of nodes.
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
     * Various ui elements for the UI to display object changes
     */
    @FXML private Label ActionLabel;
    @FXML public Label noElementSelectedErrorLabel;
    @FXML public Label currentPageLabel;
    @FXML public Pane canvasPane;
    @FXML public MenuButton loadMenuButton;
    @FXML public Button dummyLoadButton;
    @FXML public static Label saveFailedError;

    /**
     * These functions are what is executed on the press of the UML object button(oval, class etc.).
     * Sends a request to ObjectRequester to make a UML object(Just a node right now).
     * ObjectRequester then turns around after making the object and calls updateUIFunction with said object for UI display
     */
    @FXML private void ovalButtonPressed() {
        objectRequesterObservable.makeOvalRequest(-1, -1, null, null);}
    @FXML private void classButtonPressed() {
        objectRequesterObservable.makeClassRequest(-1, -1, null, null, null);}
    @FXML private void folderButtonPressed() {
        objectRequesterObservable.makeFolderRequest(-1, -1, null, null);}
    @FXML private void lifeLineButtonPressed() {
        objectRequesterObservable.makeLifeLineRequest(-1, -1, null, null);}
    @FXML private void loopButtonPressed() {
        objectRequesterObservable.makeLoopRequest(-1, -1, null, null);}
    @FXML private void noteButtonPressed() {
        objectRequesterObservable.makeNoteRequest(-1, -1, null, null);}
    @FXML private void stickFigureButtonPressed() {
        objectRequesterObservable.makeStickFigureRequest(-1, -1, null, null);}
    @FXML private void TextBoxButtonPressed() {
        objectRequesterObservable.makeTextBoxRequest(-1, -1, null, null);}
    @FXML private void SquareButtonPressed() {
        objectRequesterObservable.makeSquareRequest(-1, -1, null, null);}

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
        action.editNamePopUp(null, (int)App.primaryStage.getX(), (int)App.primaryStage.getY());
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
     * Added the given line to the Pane to display it to the user.
     */
    private void updateUINewEdge(Line newLine){
        canvasPane.getChildren().add(newLine);
        newLine.setOnContextMenuRequested(e -> // Right click for deletion on line
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
        //fxObject.setOnMouseDragReleased(nodeOnMouseReleased);
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
        UINode movedElementNode = (UINode) movedElement.getUserData();
        int movedElementId = movedElementNode.getId();
        for (Object obj: canvasPane.getChildren()) {
            if (obj instanceof Line curLine) {
                UIEdge curEdge = (UIEdge) curLine.getUserData();
                UINode n1 = (UINode) curEdge.getN1();
                UINode n2 = (UINode) curEdge.getN2();
                int n1Id = n1.getId();
                int n2Id = n2.getId();
                if(n1Id == movedElementId){
                    curLine.setStartX(movedElement.getTranslateX() + movedElement.getWidth() / 2);
                    curLine.setStartY(movedElement.getTranslateY() + movedElement.getHeight() / 2);
                }
                else if(n2Id == movedElementId){
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
     * On press of the "load" button it populates it with the current pages that the user has.
     * On click of an item it fetches that page from the db and loads it into the UI.
     * On the UI startup/login there is a button that just says "Load", this populates the menubutton items on press
     * and disappears the normal menuButton can be shown. This is because the FXMLController needs to be created
     * before the button can be populated, but the button can't be populated until something is clicked because I can't
     * send parameters to the javafx initalize() function without significant refactoring.
     * Very janky but it works for the mvp.
     */
    @FXML private void populateLoadButtons(){
        Map<Integer, String> pages = objectRequesterObservable.getUserPages();
        for (Map.Entry<Integer, String> curPage : pages.entrySet()){
            if (!containsPage(curPage.getValue())) {
                MenuItem newItem = new MenuItem(curPage.getValue());
                newItem.setOnAction(a -> {
                    UIPage page = new UIPage(curPage.getKey(), curPage.getValue());
                    canvasPane.getChildren().clear();
                    objectRequesterObservable.loadPagesFromDB(canvasPane, page);
                    objectRequesterObservable.setCurrentPage(page);
                    currentPageLabel.setText(page.getName());
                });
                loadMenuButton.getItems().add(newItem);
            }
        }
        dummyLoadButton.setVisible(false);
    }

    /**
     * Just a quick function to check if the item we are adding to the list is already present
     * @param value The name of the item you are adding
     * @return If that item already exists.
     */
    public Boolean containsPage(String value){
        for (MenuItem curItem: loadMenuButton.getItems()) {
            if(value.equals(curItem.getText())){
                return true;
            }
        }
        return false;
    }

    /**
     * Makes a popup for the user to name the new page they are creating.
     * It then creates the page in the db, and clears the scene for the new page.
     */
    @FXML private void makeNewPageMenuItemPressed(){
        Popup popUp = new Popup();
        popUp.setHeight(100);
        popUp.setWidth(100);
        popUp.setX((int)App.primaryStage.getX());
        popUp.setY((int)App.primaryStage.getY());
        Label label = new Label("Enter a new name");
        TextField textField = new TextField("New Page");
        textField.setPrefWidth(200);
        textField.setPrefHeight(50);
        Button button = new Button("Confirm");
        button.setOnAction(e -> {
            if (textField.getText().equals("") || textField.getText().matches(".*\\d.*")) {
                label.setText("Page name can't be empty or include numbers");
                label.setWrapText(true);
            }
            else{
                UIPage newPage = objectRequesterObservable.createNewPage(textField.getText());
                canvasPane.getChildren().clear();
                objectRequesterObservable.setCurrentPage(newPage);
                currentPageLabel.setText(newPage.getName());
                popUp.hide();
                populateLoadButtons();
            }
        });

        StackPane sp = new StackPane();
        StackPane.setAlignment(textField, Pos.CENTER);
        StackPane.setAlignment(label, Pos.TOP_CENTER);
        StackPane.setAlignment(button, Pos.BOTTOM_RIGHT);

        sp.getChildren().addAll(textField, label, button);
        popUp.getContent().add(sp);
        popUp.show(App.primaryStage);
        button.setDefaultButton(true);
        popUp.setAutoHide(true);
    }

    /**
     * When the user hits the log out button it sends them back to the log in page
     * and clears the canvas and current user/page.
     * @throws IOException
     */
    @FXML private void logOutButtonPressed() throws IOException {
        canvasPane.getChildren().removeAll();
        objectRequesterObservable.setCurrentPage(null);
        objectRequesterObservable.setCurrentUser(null);
        Parent login = FXMLLoader.load(getClass().getResource("/UserLogIn.fxml"));
        App.primaryStage.setScene(new Scene(login, 600, 400));
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