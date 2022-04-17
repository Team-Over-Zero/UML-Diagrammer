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

import UML.Diagrammer.backend.objects.UIEdge.UIEdge;
import UML.Diagrammer.backend.objects.UINode.UIClassNode;
import UML.Diagrammer.backend.objects.UINode.UINode;
import com.google.gson.Gson;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Line;
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
    DatabaseConnection dbConnection = new DatabaseConnection();

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
            UINode node = (UINode) ((StackPane) nodeObject).getUserData();
            node.setX((int)newTranslateX);
            node.setY((int)newTranslateY); // Updates the object with the new coordinates
        }
    }

    /**
     * Finished the object movement. Send a request to see if any edges need to be updated.
     * @param t the mouse action(mouse release in this case)
     */
    public void releaseObject(MouseEvent t){
        StackPane nodeUIObject = (StackPane) t.getSource();
        support.firePropertyChange("finishedDragUpdateEdges", null, nodeUIObject);
        UINode associatedNode = (UINode) nodeUIObject.getUserData();
        //ARE NODES UNIQUE EVEN ACROSS PAGES? If so, then calling dbconnection.updateNode(Node) would be okay,
        // because it doesn't need a page reference. If it is, then I need to have objectRequest be the middle man and
        // take the node from action handler get the current page and send it to the dbConnection.
        // ASSUMING NODES ARE VERY UNIQUE.
        updateNode(associatedNode);
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
                editNamePopUp(uIElement, (int)e.getScreenX(), (int)e.getSceneY());
            }
        }
    }

    /**
     * Creates a textbook for the user to input data and edit a node.
     * @param uIElement The stack pane associated with the object
     */
    public void makePopUpEditTextBox(StackPane uIElement, int x, int y, int type) {
        if (uIElement == null){ uIElement = currentFocusedUIElement;}

        UINode node = (UINode) uIElement.getUserData();

        Popup popUp = new Popup();
        popUp.setHeight(100);
        popUp.setWidth(100);
        popUp.setX(x - (uIElement.getWidth() / 2));
        popUp.setY(y);
        Label label; TextField textField; int elIndex;
        if (type == 0) {
            label = new Label("Enter a new name");
            textField = new TextField(node.getName());
            elIndex = findString(uIElement, String.valueOf(node.getName()));
        }
        else{
            label = new Label("Enter a new description");
            textField = new TextField(node.getDesc());
            elIndex = findString(uIElement, String.valueOf(node.getDesc()));
        }
        //TextField textField = new TextField(node.getName());
        textField.setPrefWidth(200);
        textField.setPrefHeight(50);

        Button button = new Button("Confirm");
        StackPane finalUIElement = uIElement;
        button.setOnAction(e -> {
            //int elIndex = findString(finalUIElement, String.valueOf(node.getName()));
            Label textEl = (Label) finalUIElement.getChildren().get(elIndex);
            textEl.setText(textField.getText());
            UINode associatedNode = (UINode) finalUIElement.getUserData();
            if(type == 0) { associatedNode.setName(textField.getText()); }
            else{ associatedNode.setDesc(textField.getText()); }
            updateNode(associatedNode);
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

    public void editNamePopUp(StackPane uIElement, int x, int y){
        makePopUpEditTextBox(uIElement, x, y, 0);
    }

    public void editDescPopUp(StackPane uIElement, int x, int y){
        makePopUpEditTextBox(uIElement, x, y, 1);
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
                Label curText = (Label) curItem;
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
        MenuItem editItem = new MenuItem("Edit name");
        MenuItem deleteItem = new MenuItem("Delete");

        editItem.setOnAction(e -> {
            editNamePopUp(uIElement, x, y);
        });
        deleteItem.setOnAction(e -> {
            deleteObject(uIElement, canvasPane);
        });
        UINode associatedNode = (UINode) uIElement.getUserData();
        System.out.println("UINODE is type: "+associatedNode.getType());
        if (associatedNode.getType().equals("classnodes")){
            System.out.println("IS UICLASSNODE           ~");
            MenuItem editDesc = new MenuItem("Edit Description");
            menu.getItems().add(editDesc);
            editDesc.setOnAction(e -> {
                editDescPopUp(uIElement, x, y);
            });
        }

        menu.getItems().addAll(editItem, deleteItem);
        menu.setX(x); menu.setY(y);
        menu.show(App.primaryStage);
    }

    /**
     * Right click to delete edges. This is seperate from the nodes due to how the UI is set up, it could use the same
     * function with a bit of reformatting though.
     * @param lineElement What line to delete
     * @param canvasPane What parent does the line belong to
     * @param x x coord of the mouse
     * @param y y coord of the mouse
     */
    public void makeEdgeContextMenu(Line lineElement, Pane canvasPane, int x, int y){
        ContextMenu menu = new ContextMenu();
        menu.setAutoHide(true);
        MenuItem deleteItem = new MenuItem("Delete");

        deleteItem.setOnAction(e-> {
            canvasPane.getChildren().remove(lineElement);
            UIEdge associatedEdge = (UIEdge) lineElement.getUserData();
            deleteEdge(associatedEdge);
        });
        menu.getItems().addAll(deleteItem);
        menu.setX(x); menu.setY(y);
        menu.show(App.primaryStage);
    }

    /**
     * Removes the node Element from the UI(The main pane)
     * @param uIElement The UI piece that we would like to remove
     * @param canvasPane the main pane of the UI
     */
    public void deleteObject(StackPane uIElement, Pane canvasPane){
        if (uIElement == null ){ // for deleting current focused node. E.G button press rather than context menu.
            uIElement = currentFocusedUIElement;
        }

        UINode connectedNode = (UINode) uIElement.getUserData();
        int nodeToRemoveId = connectedNode.getId();
        canvasPane.getChildren().remove(uIElement);

        ArrayList<Line> linesToRemove = new ArrayList<>(); // A list of all the lines to be deleted
        // Checks if the node had any nodes associated with it so those lines can also be deleted.
        for (Object obj: canvasPane.getChildren()) {
            if (obj instanceof Line curLine) {
                UIEdge curUIEdge = (UIEdge) curLine.getUserData();
                int curLineConnectedNode1 = curUIEdge.getN1().getId();
                int curLineConnectedNode2 = curUIEdge.getN2().getId();
                if (curLineConnectedNode1 == nodeToRemoveId || curLineConnectedNode2 == nodeToRemoveId){
                    linesToRemove.add(curLine);
                }
            }
        }
        canvasPane.getChildren().removeAll(linesToRemove);

        // Deletes the node from the db
        UINode nodeToRemove = (UINode)uIElement.getUserData();
        deleteNode(nodeToRemove);

        //Iterates through all the edges to delete them from the db.
        for (Line curLine : linesToRemove){
            UIEdge curEdge = (UIEdge) curLine.getUserData();
            deleteEdge(curEdge);
        }
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

            FXMLController.objectRequesterObservable.makeEdgeRequest(n0,n1, null);
            selectedNodesForEdgeCreation.clear();
            support.firePropertyChange("clearLineCreationActions", null, null);
        }

    }

    public void clearFocusedElement(){
        currentFocusedUIElement.setStyle("-fx-border-color: ");
        currentFocusedUIElement = null;
    }

    /**
     * Simple function to update a node to the db. This makes it easy to see where I am calling the db to update a node.
     * @param node The node that you'd like to update
     */
    private void updateNode(UINode node){
        dbConnection.updateNode(node);
    }

    private void deleteNode(UINode node){
        FXMLController.objectRequesterObservable.deleteNodeFromPage(node);
    }

    private void deleteEdge(UIEdge edge){
        FXMLController.objectRequesterObservable.deleteEdgeFromPage(edge);
    }
}