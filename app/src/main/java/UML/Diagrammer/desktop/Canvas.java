package UML.Diagrammer.desktop;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;

/**
 * Class to handle the actual vector tooling in the App.
 * @author Show
 */
public class Canvas {
	
	double orgSceneX, orgSceneY;
	double orgTranslateX, orgTranslateY;
	Circle red_circle;

	/**
	 * These two function together allow for dragging of shapes across the canvas
	 */
    EventHandler<MouseEvent> circleOnMousePressedEventHandler = 
    		new EventHandler<MouseEvent>() {
    	
            @Override
            public void handle(MouseEvent t) {
                orgSceneX = t.getSceneX();
                orgSceneY = t.getSceneY();
                orgTranslateX = ((Circle)(t.getSource())).getTranslateX();
                orgTranslateY = ((Circle)(t.getSource())).getTranslateY();
            }
        };
        
        EventHandler<MouseEvent> circleOnMouseDraggedEventHandler = 
            new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent t) {
                double offsetX = t.getSceneX() - orgSceneX;
                double offsetY = t.getSceneY() - orgSceneY;
                double newTranslateX = orgTranslateX + offsetX;
                double newTranslateY = orgTranslateY + offsetY;
                
                ((Circle)(t.getSource())).setTranslateX(newTranslateX);
                ((Circle)(t.getSource())).setTranslateY(newTranslateY);
            }
        };
        
}