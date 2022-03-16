package UML.Diagrammer.desktop;
//Depreciated, property fire linstener doensn't work when called directly from the UI to here.
// ALl these function are in FXMLController now, I want to refactor before final build to get these into their own class.
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.security.cert.X509Certificate;

import UML.Diagrammer.backend.objects.AbstractNode;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;

/**
 * Class to handle the actual vector tooling in the App.
 * @author Show
 */
/*public class Canvas {

    private final PropertyChangeSupport support;
	double orgSceneX, orgSceneY;
	double orgTranslateX, orgTranslateY;*/

    /**
     * Observer functions, see ObjectRequester for more information. The same implementation is there.
     */
    /*Canvas(){
        support = new PropertyChangeSupport(this);
    }
    public void addPropertyChangeListener(PropertyChangeListener listener){
        support.addPropertyChangeListener(listener);
    }
    public void removePropertyChangeListener(PropertyChangeListener listener){
        support.removePropertyChangeListener(listener);
    }


}*/