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
package UML.Diagrammer.desktop;

import UML.Diagrammer.backend.objects.AbstractNode;
import UML.Diagrammer.backend.objects.EdgeFactory.NormalEdge;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Popup;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to handle the most UI event handing like editing text, deleting, moving etc.
 * @author Show
 */
public class ActionHandler {

    double orgSceneX, orgSceneY;
    double orgTranslateX, orgTranslateY;
    StackPane currentFocusedUIElement = null;
    public static List<StackPane> selectedNodesForEdgeCreation = new ArrayList<>();

    /**
     * Observer boilerplate, see Object requester for more information
     */
    private final PropertyChangeSupport support;
    ActionHandler(){
        support = new PropertyChangeSupport(this);
    }
    public void addPropertyChangeListener(PropertyChangeListener listener){
        support.addPropertyChangeListener(listener);}
    public void removePropertyChangeListener(PropertyChangeListener listener){
        support.removePropertyChangeListener(listener);}


    /**
     * Grabs the object that you selected with your mouse.
     * This along with moveObject allows for dragging on the screen.
     * @param t
     */
    public void grabObject(MouseEvent t){
        orgSceneX = t.getSceneX();
        orgSceneY = t.getSceneY();
        orgTranslateX = ((StackPane)(t.getSource())).getTranslateX();
        orgTranslateY = ((StackPane)(t.getSource())).getTranslateY();

    }

    /**
     * With this method we are not "physically" moving the object. We actually have 2 different objects. The shape(javaFX) and the actual node.
     * We move the shape across the screen and that in turn updates it's associated node object.
     * This way we have 2 object "linked", one is for front end, the other is for backend.
     * The caveat with this method is we must be very careful to update the backed object whenever we change the frontend one, or else we will get desync.
     * Use Shape.getUserData() to get the node object that we associated with the UI element
     */
    public void moveObject(MouseEvent t){
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

    /**
     * Finished the object movement. Send a request to see if any edges need to be updated.
     * @param t the mouse action(mouse release in this case)
     */
    public void releaseObject(MouseEvent t){
        StackPane nodeUIObject = (StackPane) t.getSource();
        support.firePropertyChange("finishedDragUpdateEdges", null, nodeUIObject);
    }


    /**
     * Sets action for when a item on the UI is clicked or double-clicked.
     * Sets focus for a click and context menu for double click.
     * @param e The mouse event(a primary mouse click in this case.)
     */
    public void clickObject(MouseEvent e){
        StackPane uIElement = (StackPane) e.getSource();
        if (e.getButton().equals(MouseButton.PRIMARY)) {
            if (e.getClickCount() == 1) {
                setFocus(uIElement);
            }

            else if (e.getClickCount() == 2) {
                makePopUpEditTextBox(uIElement, (int)e.getScreenX(), (int)e.getSceneY());
            }
        }
    }

    /**
     * Creates a textbook for the user to input data and edit a node.
     * @param uIElement The stack pane associated with the object
     */
    public void makePopUpEditTextBox(StackPane uIElement, int x, int y) {
        if (uIElement == null){ uIElement = currentFocusedUIElement;}
        Popup popUp = new Popup();
        popUp.setHeight(100);
        popUp.setWidth(100);
        popUp.setX(x - (uIElement.getWidth() / 2));
        popUp.setY(y);

        Label label = new Label("Enter a new name");

        TextField textField = new TextField();
        textField.setPrefWidth(200);
        textField.setPrefHeight(50);

        Button button = new Button("Confirm");
        StackPane finalUIElement = uIElement;
        button.setOnAction(e -> {

            Object nodeObj = finalUIElement.getUserData();
            AbstractNode node = (AbstractNode) nodeObj;

            int elIndex = findString(finalUIElement, String.valueOf(node.get("Name")));
            finalUIElement.getChildren().remove(elIndex); // Removes index 1, the name label.

            finalUIElement.getChildren().add(new Text(textField.getText()));
            ((AbstractNode) finalUIElement.getUserData()).set("Name", textField.getText());
            popUp.hide();
        });

        StackPane stackInPopUp = new StackPane();
        StackPane.setAlignment(textField, Pos.CENTER);
        StackPane.setAlignment(label, Pos.TOP_CENTER);
        StackPane.setAlignment(button, Pos.BOTTOM_RIGHT);

        stackInPopUp.getChildren().addAll(textField, label, button);
        popUp.getContent().add(stackInPopUp);

        popUp.show(App.primaryStage);
        button.setDefaultButton(true); // Lets you press enter to confirm
        popUp.setAutoHide(true);
    }

    /**
     * Gets the string value from the stack Pane and returns it's index for removal/editing.
     * @param UIElement The StackPane that you'd like to search in
     * @param elementToFind The string value you want to find
     * @return The index of the string that matches. -1 if no match.
     */
    public int findString(StackPane UIElement, String elementToFind){
        for (Object curItem: UIElement.getChildren()) {
            try {
                Text curText = (Text) curItem;
                if (curText.getText().equals(elementToFind)) {
                    return UIElement.getChildren().indexOf(curItem);
                }
            }
            catch (Exception e){}
        }
        return -1;
    }

    /**
     * Creates a contextMenu on a right click of a UI element with options like delete and edit.
     * @param uIElement The StackPane UI element that we are editing
     * @param canvasPane The main pane that all the elements reside.
     * @param x coordinate of the mouse
     * @param y coordinate of the mouse
     */
    public void makeContextMenu(StackPane uIElement, Pane canvasPane, int x, int y){
        ContextMenu menu = new ContextMenu();
        menu.setAutoHide(true);
        MenuItem editItem = new MenuItem("Edit");
        MenuItem deleteItem = new MenuItem("Delete");

        editItem.setOnAction(e -> {
            makePopUpEditTextBox(uIElement, x, y);
        });
        deleteItem.setOnAction(e -> {
            deleteObject(uIElement, canvasPane);
        });

        menu.getItems().addAll(editItem, deleteItem);
        menu.setX(x); menu.setY(y);
        menu.show(App.primaryStage);
    }

    /**
     * Removes the UI Element from the UI(The main pane)
     * @param uIElement The UI piece that we would like to remove
     * @param canvasPane the main pane of the UI
     */
    public void deleteObject(StackPane uIElement, Pane canvasPane){
        if (uIElement == null ){ // for deleting current focused node. E.G button press rather than context menu.
            uIElement = currentFocusedUIElement;
        }
        canvasPane.getChildren().remove(uIElement);

        ArrayList<Line> linesToRemove = new ArrayList<>(); // A list of all the lines to be deleted
        // Checks if the node had any nodes associated with it so those lines can also be deleted.
        for (Object obj: canvasPane.getChildren()) {
            if (obj instanceof Line curLine) {
                StackPane[] curLineConnectedNodes = (StackPane[]) curLine.getUserData();
                if (curLineConnectedNodes[0] == uIElement || curLineConnectedNodes[1] == uIElement){
                    linesToRemove.add(curLine);
                }
            }
        }
        canvasPane.getChildren().removeAll(linesToRemove);
        // DATABASE NODE AND EDGE DELETE REQUEST
    }

    /**
     * Makes a blue border around the currently selected object and sets as the currentFocusedUIElement for editing
     * with buttons.
     * @param uIElement
     */
    public void setFocus(StackPane uIElement) {
        if (currentFocusedUIElement != null) { // Take the border off of the current selected object
            currentFocusedUIElement.setStyle("-fx-border-color: ");
        }
        currentFocusedUIElement = uIElement;
        currentFocusedUIElement.setStyle("-fx-border-color: blue");
        uIElement.requestFocus();
    }

    /**
     * Sets up the action for clicking on nodes so that the lines knows where to draw.
     * @param parentPane The parent pane where all the nodes/edges lie.
     */
    public void connectNodes(Pane parentPane){
        for (Node curElement : parentPane.getChildren()) {
            if(curElement instanceof StackPane curStackPane){
                curStackPane.setOnMouseClicked(setOnMouseClickedForEdgeCreation);
            }
        }
    }

    /**
     * Setting the action on a node clicked
     */
    EventHandler<MouseEvent> setOnMouseClickedForEdgeCreation =
            this::selectNodesToConnect;

    /**
     * We have a global array that is filled with up to 2 nodes. When the array reaches 2 we draw a line between the two
     * nodes and clear the array. We also disable further clicking for creating lines.
     * @param e Mouse action event
     */
    private void selectNodesToConnect(MouseEvent e){
        if (selectedNodesForEdgeCreation.size() == 1 && // Error checking to connect an edge to itself
                selectedNodesForEdgeCreation.get(0) == e.getSource()){
            return;
        }

        selectedNodesForEdgeCreation.add((StackPane)e.getSource());

        if(selectedNodesForEdgeCreation.size() == 2){
            StackPane n0 = selectedNodesForEdgeCreation.get(0);
            StackPane n1 = selectedNodesForEdgeCreation.get(1);

            FXMLController.objectRequesterObservable.makeEdgeRequest(n0,n1);
            selectedNodesForEdgeCreation.clear();
            support.firePropertyChange("clearLineCreationActions", null, null);
        }

    }

}