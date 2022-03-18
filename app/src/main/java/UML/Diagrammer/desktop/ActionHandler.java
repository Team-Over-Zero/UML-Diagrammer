package UML.Diagrammer.desktop;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.security.cert.X509Certificate;

import UML.Diagrammer.backend.objects.AbstractNode;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Popup;

/**
 * Class to handle the most UI event handing like editing text, deleting, moving etc.
 * @author Show
 */
public class ActionHandler {

    double orgSceneX, orgSceneY;
    double orgTranslateX, orgTranslateY;


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
        button.setOnAction(e -> {
            // Should be careful here, since I am completely clearing all labels and such in the UI element.
            uIElement.getChildren().clear();
            uIElement.getChildren().add(new Label(textField.getText()));
            ((AbstractNode)uIElement.getUserData()).set("Name", textField.getText());
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

    public void deleteObject(StackPane uIElement, Pane canvasPane){
        canvasPane.getChildren().remove(uIElement);
        // DATABASE DELETE REQUEST
        // Or if we only save via sending the whole page to the database, then I can traslate the
        // canvas pane to a Page before save.
    }

    public void setFocus(StackPane uIElement, Pane canvasPane) {
        uIElement.requestFocus();
        System.out.println();
    }

}