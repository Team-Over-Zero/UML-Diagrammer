package UML.Diagrammer.backend.objects.NodeFactory;
import UML.Diagrammer.backend.objects.AbstractNode;
import org.javalite.activejdbc.Model;
//import activejdbc.*;


/*NODE/EDGE CREATION NOTE:
 * Since the factories are currently being made to implement the Model for database stuff, they currently break the build on creation.
 * Until we have this fixed I have commeted out all object creations and linking. This is denoted by the Node creation ********* comments
 * for easy decommeting when we get this fixed. Most UI stuff should still work largely the same, just be sure not to make any node/edge objects for now.*/

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
    @SuppressWarnings("unchecked")
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
    @SuppressWarnings("unchecked")
	public <genericNode extends AbstractNode> genericNode buildNode(){
        return (genericNode) new DefaultNode();
    }

}