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
import UML.Diagrammer.backend.objects.NodeFactory.*;
import javafx.scene.Cursor;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.SVGPath;
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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class ObjectRequester {

    /**
     * Global observable object to call updates to the UI.
     */
    private final PropertyChangeSupport support;
    private static final NodeFactory nodeFactory = new NodeFactory();
    private static final EdgeFactory edgeFactory = new EdgeFactory();
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
     * A WIP for the SVG converter. Currently Does not work.
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
        String image = "/Oval_UseCase.svg";
        StackPane newUIShape = UIShapeRequest(image,newNode);
        support.firePropertyChange("newNodeCreation", null, newUIShape);
    }

    /**
     * Creates a class object along with a stackpane and ties them together.
     * Then returns these objects to the UI via the support.firePropertyChange call.
     */
    public void makeClassRequest(){
    	ClassNode newNode = nodeFactory.buildNode("CLASS", 3, 3, 3, 3);
        String image = "Class.svg";
    	StackPane newUIShape = UIShapeRequest(image,newNode);
        support.firePropertyChange("newNodeCreation", null, newUIShape);
    }

    public void makeFolderRequest(){
        FolderNode newNode = nodeFactory.buildNode("FOLDER", 3, 3, 3, 3);
        String image = "/Folder.svg";
        StackPane newUIShape = UIShapeRequest(image,newNode);
        support.firePropertyChange("newNodeCreation", null, newUIShape);
    }

    public void makeLifeLineRequest(){
        LifeLineNode newNode = nodeFactory.buildNode("LIFELINE", 3, 3, 3, 3);
        String image = "/LifeLine.svg";
        StackPane newUIShape = UIShapeRequest(image,newNode);
        support.firePropertyChange("newNodeCreation", null, newUIShape);
    }

    public void makeLoopRequest(){
        LoopNode newNode = nodeFactory.buildNode("LOOP", 3, 3, 3, 3);
        String image = "/Loop.svg";
        StackPane newUIShape = UIShapeRequest(image,newNode);
        support.firePropertyChange("newNodeCreation", null, newUIShape);
    }

    public void makeNoteRequest(){
        NoteNode newNode = nodeFactory.buildNode("NOTE", 3, 3, 3, 3);
        String image = "/Note.svg";
        StackPane newUIShape = UIShapeRequest(image,newNode);
        support.firePropertyChange("newNodeCreation", null, newUIShape);
    }

    public void makeStickFigureRequest(){
        StickFigureNode newNode = nodeFactory.buildNode("STICKFIGURE", 3, 3, 3, 3);
        String image = "/StickFigure.svg";
        StackPane newUIShape = UIShapeRequest(image,newNode);
        support.firePropertyChange("newNodeCreation", null, newUIShape);
    }

    public void makeTextBoxRequest(){
        TextBoxNode newNode = nodeFactory.buildNode("TEXTBOX", 3, 3, 3, 3);
        String image = "/TextBox_Square_Interface.svg";
        StackPane newUIShape = UIShapeRequest(image,newNode);
        support.firePropertyChange("newNodeCreation", null, newUIShape);
    }

    public void makeSquareRequest(){
        SquareNode newNode = nodeFactory.buildNode("SQUARE", 3, 3, 3, 3);
        String image = "/TextBox_Square_Interface.svg";
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
     * This function ties together a UI Object (StackPane) and node object (AbstractNode).
     * @param image the image that you would like to associate with the new UI element.
     * @param node The node that connects to the data side of things
     * @return the shape you requested with its data node linked and actions set up.
     */
    public StackPane UIShapeRequest(String image, AbstractNode node) {

        try {
            SVGPath path = new SVGPath();

            Path filePath = Paths.get("src/main/resources/Images/"+image);
            Path absPath = filePath.toAbsolutePath();

            String svgString = Files.readString(absPath);

            path.setContent(svgString);

            StackPane stack = new StackPane(path);

            stack.setPrefWidth(300);
            stack.setPrefHeight(300);
            stack.setCursor(Cursor.HAND);

            Text text = new Text((String) node.get("Name"));

            stack.getChildren().addAll(text);
            setMouseActions(stack, node);
            return stack;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;

    }
}