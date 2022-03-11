package UML.Diagrammer.desktop;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import UML.Diagrammer.backend.objects.AbstractNode;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

/**
 * Class to handle the actual vector tooling in the App.
 * @author Show
 */
public class Canvas {
	
    private final PropertyChangeSupport support;
	double orgSceneX, orgSceneY;
	double orgTranslateX, orgTranslateY;
	Rectangle red_Rectangle;
	
    /**
     * Observer functions, see ObjectRequester for more information. The same implementation is there.
     */
    Canvas(){
        support = new PropertyChangeSupport(this);
    }
    public void addPropertyChangeListener(PropertyChangeListener listener){
        support.addPropertyChangeListener(listener);
    }
    public void removePropertyChangeListener(PropertyChangeListener listener){
        support.removePropertyChangeListener(listener);
    }

	
		
	/**
	 * These two function together allow for dragging of shapes across the canvas
	 */
    EventHandler<MouseEvent> nodeOnMousePressedEventHandler = 
    		new EventHandler<MouseEvent>() {
    	
            @Override
            public void handle(MouseEvent t) {
                orgSceneX = t.getSceneX();
                orgSceneY = t.getSceneY();
                orgTranslateX = ((Rectangle)(t.getSource())).getTranslateX();
                orgTranslateY = ((Rectangle)(t.getSource())).getTranslateY();
            }
     };
        
    EventHandler<MouseEvent> nodeOnMouseDraggedEventHandler = 
        new EventHandler<MouseEvent>() {

    	/**
    	 * With this method we are not "physically" moving the object. We actually have 2 different objects. The shape(javaFX) and the actual node.
    	 * We move the shape across the screen and that in turn updates it's associated node object. 
    	 * This way we have 2 object "linked", one is for front end, the other is for backend.
    	 * The caveat with this method is we must be very careful to update the backed object whenever we change the frontend one, or else we will get desync.
    	 * Use Shape.getUserData() to get the node object that we associated with the UI element
    	 */
        @Override
        public void handle(MouseEvent t) {
        	// This deals with the visual movement on the UI.
            double offsetX = t.getSceneX() - orgSceneX;
            double offsetY = t.getSceneY() - orgSceneY;
            double newTranslateX = orgTranslateX + offsetX;
            double newTranslateY = orgTranslateY + offsetY;
            
        	((Rectangle)(t.getSource())).setTranslateX(newTranslateX);
            ((Rectangle)(t.getSource())).setTranslateY(newTranslateY);
            
            // Sets the new coordinates of the that we moved.
            Object nodeObject = t.getSource();
            if (nodeObject instanceof Rectangle) {
            	AbstractNode node = (AbstractNode) ((Rectangle) nodeObject).getUserData();
            	node.setCoords((int)newTranslateX, (int)newTranslateY); // Updates the object with the new coordinates
            	support.firePropertyChange("classUpdate", null, node);
            	System.out.println("Moving node ID: " + node.getID());
            }
        }
    };
                    
}