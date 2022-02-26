package UML.Diagrammer;


import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * This is just a class to show test/show how the observer patter will work
 * This class is the observable and FXMLController is the observer
 * In the real program the observable will probably be the graph class/objects.
 * @author Show P
 */
public class ProofOfConceptObservable {

    private String currentText;
    private PropertyChangeSupport support;

    /**
     * Constructor, needs to make the PropertyChangeSupport object for to notify listeners
     */
    ProofOfConceptObservable(){
        support = new PropertyChangeSupport(this);
    }

    /**
     * Adds a listener to notify when a change occurs
     * @param listener who want's to know when this object changes(Probably javaFX UI)
     */
    public void addPropertyChangeListener(PropertyChangeListener listener){
        support.addPropertyChangeListener(listener);
    }

    /**
     * Removes a listener so they no longer update on a change
     * @param listener the listener you'd like to remove
     */
    public void removePropertyChangeListener(PropertyChangeListener listener){
        support.removePropertyChangeListener(listener);
    }

    /**
     * Proof of concept, this changes the object's properties and tells its listeners.
     * @param newText The value to be changed.
     */
    public void setText(String newText){
        // This tells the listener to update. firePropertyChange params are (observed property, old value, new value)
        support.firePropertyChange("textUpdate", this.currentText, newText);
    }

}
