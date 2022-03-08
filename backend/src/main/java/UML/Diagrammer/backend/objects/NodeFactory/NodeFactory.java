
/**
 * NodeFactory.java
 *
 * This file will have many classes that are the various subtypes of nodes described by AbstractNode.
 * All will be slightly different because they all need different images associated with them.
 * And doing this way makes it so the default name/description(if needed) is different before the user edits it.
 * E.G The user makes a new Class, and it's default text says "Class Name" and "Class Description"
 * Feel free to message me if this class is incomprehensible
 * @author Show
 */



/**
 * Here is the actual nodeFactory that will be used by the UI to request node objects.
 * The UI simply needs to have a NodeFactory object and then call
 * nodeFactory.buildNode(Sting of wanted node,xCoordinate, yCoordinate, width, height).
 * Image is an enum for your specific node type
 * @param SVGImage what type of object you want in a string and all caps. E.G: "CLASS", "LOOP", "TEXTBOX", "STICKFIGURE"
 */
package UML.Diagrammer.backend.objects.NodeFactory;
import UML.Diagrammer.backend.objects.AbstractNode;

/**
 * NodeFactory that any class will need if they want to create various node objects.
 * Syntax for creating a node object could be for example:
 * NodeFactory factory = new NodeFactory();
 * TextBoxNode myNode = factory.buildNoe("TEXTBOX", x, y, w, h)
 *
 * This factory returns a genericNode so that it can return any subclass of AbstractNode.
 */
public class NodeFactory{
    public NodeFactory(){}

    /**
     * Main function to be used by the factory for creating nodes, delegates tasks to subclasses.
     * @param SVGImage The type of node you want, CLASS, TEXTBOX, NOTE, FOLDER, SQUARE, STICKFIGURE, OVAL, LIFELINE, and LOOP
     * @return A subtype of an AbstractNode, if the SVG text doesn't match one above (or empty params) returns a DefaultNode
     */
    public <genericNode extends AbstractNode> genericNode
    buildNode(String SVGImage, int x, int y, int w, int h){

        switch(SVGImage){
            case "CLASS" -> {return (genericNode) new ClassNode(x, y, w, h);}
            case "TEXTBOX" -> {return (genericNode) new TextBoxNode(x, y, w, h);}
            case "NOTE" -> {return (genericNode) new NoteNode(x, y, w, h);}
            case "FOLDER" -> {return (genericNode) new FolderNode(x, y, w, h);}
            case "SQUARE" -> {return (genericNode) new SquareNode(x, y, w, h);}
            case "STICKFIGURE" -> {return (genericNode) new StickFigureNode(x, y, w, h);}
            case "OVAL" -> {return (genericNode) new OvalNode(x, y, w, h);}
            case "LIFELINE" -> {return (genericNode) new LifeLineNode(x, y, w, h);}
            case "LOOP" -> {return (genericNode) new LoopNode(x, y, w, h);}
            default -> {return (genericNode) new DefaultNode();}
        }
    }

    /**
     * Default constructor if no parameters are given
     */
    public <genericNode extends AbstractNode> genericNode buildNode(){
        return (genericNode) new DefaultNode();
    }

}