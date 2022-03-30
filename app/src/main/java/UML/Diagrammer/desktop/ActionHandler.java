package UML.Diagrammer.desktop;

import UML.Diagrammer.backend.objects.AbstractNode;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Popup;

/**
 * Class to handle the most UI event handing like editing text, deleting, moving etc.
 * @author Show
 */
public class ActionHandler {

    double orgSceneX, orgSceneY;
    double orgTranslateX, orgTranslateY;
    StackPane currentFocusedUIElement = null;


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
     * Creates a textbook for the user to input data and edit a node.
     * @param uIElement The stack pane associated with the object
     */
    public void makePopUpEditTextBox(StackPane uIElement, int x, int y) {
        if (uIElement == null){ uIElement = currentFocusedUIElement;}
        Popup popUp = new Popup();
        popUp.setHeight(100);    // Might want to scale this to the size of the node in the future
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
        canvasPane.getChildren().remove(uIElement);
        // DATABASE DELETE REQUEST
        // Or if we only save via sending the whole page to the database, then I can translate the
        // canvas pane to a Page before save.
    }

    /**
     * Overridden, if we use delete via the button rather than a right click.
     * Deletes the currently focused UI element
     * @param canvasPane The main pane of the UI
     */
    public void deleteObject(Pane canvasPane){
        canvasPane.getChildren().remove(currentFocusedUIElement);
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
        System.out.println();
    }

}