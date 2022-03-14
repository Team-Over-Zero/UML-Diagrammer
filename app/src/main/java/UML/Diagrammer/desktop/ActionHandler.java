package UML.Diagrammer.desktop;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ActionHandler {
    private final PropertyChangeSupport support;

    /**
     * Observable stuff. See Object Requester for more information
     */
    ActionHandler(){
        support = new PropertyChangeSupport(this);
    }
    public void addPropertyChangeListener(PropertyChangeListener listener){
        support.addPropertyChangeListener(listener);
    }
    public void removePropertyChangeListener(PropertyChangeListener listener){
        support.removePropertyChangeListener(listener);
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
                            TextField editTextField = new TextField();
                            editTextField.setCursor(Cursor.TEXT);
                            editTextField.setAlignment(Pos.CENTER);
                            editTextField.setMaxHeight(100);
                            editTextField.setMaxWidth(300);
                            editTextField.setLayoutX(UIElement.getLayoutX());
                            editTextField.setLayoutY(UIElement.getLayoutY() - (UIElement.getHeight() / 2) + 30); // Set the textbox just above the UI element
                            //canvasPane.getChildren().add(textField);
                            support.firePropertyChange("doubleMouseClick", null, editTextField);
                        }
                    }
                }
            };


}
