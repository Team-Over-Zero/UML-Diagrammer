/**
 * ObjectRequester.java
 * Class to create objects using the backend.
 * The FXMLController will use these function to request object creation using the backend.
 * This class is set up as an observable, so it can call support.firePropertyChange to update the UI.
 * @author Show P
 */

package UML.Diagrammer.desktop;

import UML.Diagrammer.backend.objects.AbstractNode;
import UML.Diagrammer.backend.objects.EdgeFactory.DefaultEdge;
import UML.Diagrammer.backend.objects.EdgeFactory.EdgeFactory;
import UML.Diagrammer.backend.objects.NodeFactory.ClassNode;
import UML.Diagrammer.backend.objects.NodeFactory.NodeFactory;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.commons.io.FileUtils;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import UML.Diagrammer.backend.objects.NodeFactory.*;


public class ObjectRequester {

    /**
     * Global observable object to call updates to the UI.
     */
    private final PropertyChangeSupport support;
    private static final NodeFactory nodeFactory = new NodeFactory();
    private static final EdgeFactory edgeFactory = new EdgeFactory();
    //private static final Canvas canvas = new Canvas();
    private static final BufferedImageTranscoder transcoder = new BufferedImageTranscoder();


    /**
     * Constructor, needs to make the PropertyChangeSupport object for to notify listeners
     */
    ObjectRequester(){
        support = new PropertyChangeSupport(this);
    }

    /**
     * Adds a listener to notify when a change occurs
     * @param listener who wants to know when this object changes(Probably javaFX UI)
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
     * A WIP for the SVG converter, seriously got frustrated trying to implement this, so I started working on other thing.
     * Will come back to complete later.
     */
    private ByteArrayOutputStream convertSVG(String fileLoc) throws IOException, TranscoderException {
        try {
            File svgFile = new File(fileLoc);
            System.out.println(svgFile.exists());
            byte[] streamBytes = FileUtils.readFileToByteArray(svgFile);

            BufferedImageTranscoder transcoder = new BufferedImageTranscoder();
            TranscoderInput input = new TranscoderInput(new ByteArrayInputStream(streamBytes));
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            TranscoderOutput output = new TranscoderOutput(outStream);

            System.out.println("OUT/IN NULL??? "+"\nout; "+output+"\nin; "+input);

            transcoder.transcode(input, output);
            System.out.println("NEW OUT: "+output);
            //outStream.flush();
            return outStream;
        }
        catch (Exception e){e.printStackTrace();}
        return null;
    }

    /**
     * Creates a class object along with a rectangle and ties them together.
     * Then returns these objects to the UI via the support.firePropertyChange call.
     * All makeXRequest function should look really similar to this function, the difference being the image and node type.
     */
    public void makeOvalRequest() throws TranscoderException, IOException {
        OvalNode newNode = nodeFactory.buildNode("OVAL", 3, 3, 3, 3);
        Image image = new Image("/Images/Oval_UseCase.png");/*ByteArrayOutputStream stuff = convertSVG("src/main/resources/Images/DefaultImage.svg");*/
        StackPane newUIShape = UIShapeRequest(image,newNode);
        support.firePropertyChange("newNodeCreation", null, newUIShape);
    }

    /**
     * Creates a class object along with a rectangle and ties them together. 
     * Then returns these objects to the UI via the support.firePropertyChange call.
     */
    public void makeClassRequest(){
    	ClassNode newNode = nodeFactory.buildNode("CLASS", 3, 3, 3, 3);
        Image image = new Image("/Images/Class.png"); // Will be just newNode.getSVG() when we get the object back up and running.(And be a svg too)
    	StackPane newUIShape = UIShapeRequest(image,newNode);
        support.firePropertyChange("newNodeCreation", null, newUIShape);
    }

    public void makeFolderRequest(){
        ClassNode newNode = nodeFactory.buildNode("CLASS", 3, 3, 3, 3);
        Image image = new Image("/Images/Folder.png"); // Will be just newNode.getSVG() when we get the object back up and running.(And be a svg too)
        StackPane newUIShape = UIShapeRequest(image,newNode);
        support.firePropertyChange("newNodeCreation", null, newUIShape);
    }

    public void makeLifeLineRequest(){
        ClassNode newNode = nodeFactory.buildNode("CLASS", 3, 3, 3, 3);
        Image image = new Image("/Images/LifeLine.png"); // Will be just newNode.getSVG() when we get the object back up and running.(And be a svg too)
        StackPane newUIShape = UIShapeRequest(image,newNode);
        support.firePropertyChange("newNodeCreation", null, newUIShape);
    }

    public void makeLoopRequest(){
        ClassNode newNode = nodeFactory.buildNode("CLASS", 3, 3, 3, 3);
        Image image = new Image("/Images/Loop.png"); // Will be just newNode.getSVG() when we get the object back up and running.(And be a svg too)
        StackPane newUIShape = UIShapeRequest(image,newNode);
        support.firePropertyChange("newNodeCreation", null, newUIShape);
    }

    public void makeNoteRequest(){
        ClassNode newNode = nodeFactory.buildNode("CLASS", 3, 3, 3, 3);
        Image image = new Image("/Images/Note.png"); // Will be just newNode.getSVG() when we get the object back up and running.(And be a svg too)
        StackPane newUIShape = UIShapeRequest(image,newNode);
        support.firePropertyChange("newNodeCreation", null, newUIShape);
    }

    public void makeStickFigureRequest(){
        ClassNode newNode = nodeFactory.buildNode("CLASS", 3, 3, 3, 3);
        Image image = new Image("/Images/StickFigure.png"); // Will be just newNode.getSVG() when we get the object back up and running.(And be a svg too)
        StackPane newUIShape = UIShapeRequest(image,newNode);
        support.firePropertyChange("newNodeCreation", null, newUIShape);
    }

    public void makeTextBoxRequest(){
        ClassNode newNode = nodeFactory.buildNode("CLASS", 3, 3, 3, 3);
        Image image = new Image("/Images/TextBox_Square_Interface.png"); // Will be just newNode.getSVG() when we get the object back up and running.(And be a svg too)
        StackPane newUIShape = UIShapeRequest(image,newNode);
        support.firePropertyChange("newNodeCreation", null, newUIShape);
    }

    public void makeSquareRequest(){
        ClassNode newNode = nodeFactory.buildNode("CLASS", 3, 3, 3, 3);
        Image image = new Image("/Images/TextBox_Square_Interface.png"); // Will be just newNode.getSVG() when we get the object back up and running.(And be a svg too)
        StackPane newUIShape = UIShapeRequest(image,newNode);
        support.firePropertyChange("newNodeCreation", null, newUIShape);
    }

    /**
     * Creates a default edge for now and displays and lets the controller know.
     */
    public void makeEdgeRequest(){ // Edge required ***************
        DefaultEdge newEdge = edgeFactory.buildEdge();
        support.firePropertyChange("newEdgeCreation", null, newEdge);
    }
    
    /**
     * Sets up the mouse actions for dragging and updating the associated node.
     * This should be called after any new creation of a node.
     * setUserData makes it so we can reference the data object from the UI object via UIElement.getUserData()
     * This effectively ties together the UI object Rectangle and the given AbstractNode data object.
     * @param fxObject The UI element that is displayed to the screen.
     * @param node The actual backend object that is apart of the data.
     */
    public void setMouseActions(StackPane fxObject, AbstractNode node) {
    	fxObject.setUserData(node);
    	support.firePropertyChange("setMouseActions", null, fxObject);
    }
    
    /**
     * Creates a new shape for the UI and sets up it's mouse actions
     * This function ties together a UI Object (Rectangle) and node object (AbstractNode). 
     * @param image the image that you would like to associate with the new UI element. (Will be a UML element)
     * @param node The node that connects to the data side of things
     * @return the shape you requested with it's data node linked and actions set up.
     */
    public StackPane UIShapeRequest(Image image, AbstractNode node) {
    	//SVGImage img = SVGLoader.load("/Images/DefaultNode.svg");
    	//svgContainer.load("/Images/DefaultNode.svg");

        BackgroundImage BImage = new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        Background background = new Background(BImage);
        StackPane stack = new StackPane();
        stack.setPrefWidth(image.getWidth());
        stack.setPrefHeight(image.getHeight());
        stack.setBackground(background);
        stack.setCursor(Cursor.HAND);
        Text text = new Text( (String) node.get("Name"));
        //If I want to make the data object I should make it here and insert the stuff
        stack.getChildren().addAll(text);
        setMouseActions(stack, node);
        return stack;
    }

}
