package UML.Diagrammer.desktop;

import UML.Diagrammer.backend.objects.DefaultNode;
import javafx.fxml.*;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Objects;

// I probably want this class to be the observer, so that it watches for any changes in the backend.

/**
 * Controller for FXML that will control the logic for the UI.
 * This class will be set up as an observer(PropertyChangeListener), so when there is a change in the backend
 * the UI can update automatically.
 */
public class FXMLController implements PropertyChangeListener{

    /**
     * Observer object for references
     */
    private static final ObjectRequester observable = new ObjectRequester();

    /**
     * Constructor to set this class as the observer for the observable objects
     */
    public FXMLController(){
        observable.addPropertyChangeListener(this);
    }

    /**
     * This is the function that is called by ObjectRequester to update the UI
     * @param event The event that was changed that needs to be reflected in the UI.
     */
    public void propertyChange(PropertyChangeEvent event){
        if (Objects.equals(event.getPropertyName(), "newCircleCreation")){
            this.updateUINewCircle((DefaultNode) event.getNewValue());
        }
        else if(Objects.equals(event.getPropertyName(), "newSquareCreation")){
            this.updateUINewSquare((DefaultNode) event.getNewValue());
        }
    }

    /**
     * Label for the UI to display object changes
     */
    @FXML private Label testLabel;


    /**
     * These functions are what is executed on the press of the UML object button(Circle, square etc.).
     * Sends a request to ObjectRequester to make a UML object(Just a node right now).
     * ObjectRequester then turns around after making the object and calls updateUIFunction with said object for UI display
     */
    @FXML private void circleButtonPressed() {
        observable.makeCircleRequest();
    }

    @FXML private void squareButtonPressed() {
        observable.makeSquareRequest();
    }

    @FXML
    private void lineButtonPressed() {
        testLabel.setText("Can't make a line yet, sorry ¯\\_(ツ)_/¯");
    }

    /**
     * These 2 function are the exact same right now, in the future it may be necessary for creation of different UML object.
     * There is probably a way, so I can use 1 function to make any node object, gotta think about it.
     * @param newNode The newly create Node object that needs to be displayed
     */
    private void updateUINewCircle(DefaultNode newNode){
        testLabel.setText("New node made: "+ "\n"+
                "at: ("+ (int) newNode.getXCoord()+ ", "+ (int)newNode.getYCoord()+ ")" + "\n"+
                "Name: "+ newNode.getName()+ "\n"+
                "Title: "+ newNode.getTitle()+ "\n"+
                "Description: "+ newNode.getDescription()+ "\n"+
                "ID: "+ (int) newNode.getID()+ "\n"+
                "width/height: ("+ (int) newNode.getWidth()+ ", "+ (int) newNode.getHeight()+ ")");
    }

    private void updateUINewSquare(DefaultNode newNode){
        testLabel.setText("New node made: "+ "\n"+
                "at: ("+ (int) newNode.getXCoord()+ ", "+ (int)newNode.getYCoord()+ ")" + "\n"+
                "Name: "+ newNode.getName()+ "\n"+
                "Title: "+ newNode.getTitle()+ "\n"+
                "Description: "+ newNode.getDescription()+ "\n"+
                "ID: "+ (int) newNode.getID()+ "\n"+
                "width/height: ("+ (int) newNode.getWidth()+ ", "+ (int) newNode.getHeight()+ ")");
    }

}