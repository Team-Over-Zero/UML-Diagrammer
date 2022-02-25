package UML.Diagrammer;

import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

// I probably want this class to be the observer, so that it watches for any changes in the backend.

/**
 * Controller for FXML that will control the logic for the UI.
 * This class will be set up as an observer(PropertyChangeListener), so when there is a change in the backend
 * the UI can update automatically.
 */
public class FXMLController implements PropertyChangeListener{

    public void propertyChange(PropertyChangeEvent event){
        this.updateUIText((String) event.getNewValue());
    }

    /**
     * Button and label for the UI
     */
    @FXML
    private Button testButton; @FXML private Label testLabel;
    /**
     * This the function that is called when the button is pressed. Since we don't have a real main flow yet, this
     * functions as making the observable objects and adding their listeners(this class, the UI.
     */
    @FXML
    private void testButtonAction() {
        ProofOfConceptObservable observable = new ProofOfConceptObservable();
        observable.addPropertyChangeListener(this);

        observable.setText("I'm being Observed!");
    }

    /**
     * Simply Updates the UI when a properChange comes in from a listener
     * @param newText the thing that needs to updated
     */
    private void updateUIText(String newText){
        testLabel.setText(newText);
    }

}
