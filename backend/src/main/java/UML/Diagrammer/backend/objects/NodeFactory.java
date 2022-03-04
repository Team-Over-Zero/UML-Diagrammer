package UML.Diagrammer.backend.objects;

/**
 * This file will have many classes that are the various subtypes of nodes described by AbstractNode.
 * All will be slightly different because they all need different images associated with them.
 * And doing this way makes it so the default name/description(if needed) is different before the user edits it.
 * E.G The user makes a new Class, and it's default text says "Class Name" and "Class Description"
 * Feel free to message me if this class is incomprehensible
 * @author Show
 */
/**
 * This makes a Class uml element.
 * This will need a name, description, and a TEXT_BOX_SVG image.
 */
 class ClassNode extends AbstractNode{
     ClassNode(int x, int y, int w, int h){
        super("Class Name", "Class Description", "CLASS_SVG", x, y, w, h);
    }
    @Override public int getID() {return super.getID();}
    @Override public String getDescription() {return super.getDescription();}
    @Override public String getSVG() {return super.getSVGImage();}
}

/**
 * This makes a TextBox uml element.
 * This will NOT need a name, but does need a description, and a TEXT_BOX_SVG image.
 */
class TextBoxNode extends AbstractNode{
    TextBoxNode(int x, int y, int w, int h){
        super(null, "Text Box Description", "TEXT_BOX_SVG", x, y, w, h);
    }
    @Override public int getID() {return super.getID();}
    @Override public String getDescription() {return super.getDescription();}
    @Override public String getSVG() {return super.getSVGImage();}
}

/**
 * This makes a note uml element.
 * This does NOT need a name but does need a description and a NOTE_SVG image.
 */
class NoteNode extends AbstractNode{
    NoteNode(int x, int y, int w, int h){
        super(null, "Note Description", "NOTE_SVG", x, y, w, h);
    }
    @Override public int getID() {return super.getID();}
    @Override public String getDescription() {return super.getDescription();}
    @Override public String getSVG() {return super.getSVGImage();}
}

/**
 * This makes a folder uml element.
 * This does NOT need a description, but need a name and a FOLDER_SVG image.
 */
class FolderNode extends AbstractNode{
     FolderNode(int x, int y, int w, int h){
         super("Folder Name", null, "FOLDER_SVG", x, y, w, h);
     }
    @Override public int getID() {return super.getID();}
    @Override public String getDescription() {return super.getDescription();}
    @Override public String getSVG() {return super.getSVGImage();}
}

/**
 * This makes a square uml element.
 * This does NOT need a description, but need a name and a SQUARE_SVG image.
 */
class SquareNode extends  AbstractNode{
    SquareNode(int x, int y, int w, int h){
        super("Square Name", null, "SQUARE_SVG", x, y, w, h);
    }
    @Override public int getID() {return super.getID();}
    @Override public String getDescription() {return super.getDescription();}
    @Override public String getSVG() {return super.getSVGImage();}
}

/**
 * This makes a Stick Figure uml element.
 * This does NOT need a description, but need a name and a STICK_FIGURE_SVG image.
 */
class StickFigureNode extends AbstractNode{
    StickFigureNode(int x, int y, int w, int h){
        super("Stick Figure Name", null, "STICK_FIGURE_SVG", x, y, w, h);
    }
    @Override public int getID() {return super.getID();}
    @Override public String getDescription() {return super.getDescription();}
    @Override public String getSVG() {return super.getSVGImage();}
}

/**
 * This makes a Oval uml element.
 * This does NOT need a description, but need a name and a OVAL_SVG image.
 */
class OvalNode extends AbstractNode{
    OvalNode(int x, int y, int w, int h){
        super("Oval Name", null, "OVAL_SVG", x, y, w, h);
    }
    @Override public int getID() {return super.getID();}
    @Override public String getDescription() {return super.getDescription();}
    @Override public String getSVG() {return super.getSVGImage();}
}

/**
 * This makes a LifeLine uml element.
 * This does NOT need a name or a description, but need a name and a LIFE_LINE_SVG image.
 */
class LifeLineNode extends AbstractNode{
    LifeLineNode(int x, int y, int w, int h){
        super(null, null, "LIFE_LINE_SVG", x, y, w, h);
    }

    @Override public int getID() {return super.getID();}
    @Override public String getDescription() {return super.getDescription();}
    @Override public String getSVG() {return super.getSVGImage();}
}

/**
 * This makes a Loop uml element.
 * This does NOT need a name or a description, but need a name and a LOOP_SVG image.
 */
class LoopNode extends AbstractNode{
    LoopNode(int x, int y, int w, int h){
        super(null, null, "LOOP_SVG", x, y, w, h);
    }

    @Override public int getID() {return super.getID();}
    @Override public String getDescription() {return super.getDescription();}
    @Override public String getSVG() {return super.getSVGImage();}
}

/**
 * Default constructor if you don't need/want to specify nodes for some reason.
 */
class DefaultNode extends AbstractNode{
    DefaultNode(){
        super();
    }

    @Override public int getID() {return super.getID();}
    @Override public String getDescription() {return super.getDescription();}
    @Override public String getSVG() {return super.getSVGImage();}
}

/**
 * Here is the actual nodeFactory that will be used by the UI to request node objects.
 * The UI simply needs to have a NodeFactory object and then call
 * nodeFactory.buildNode(Sting of wanted node,xCoordinate, yCoordinate, width, height).
 * Image is an enum for your specific node type
 * @param SVGImage what type of object you want in a string and all caps. E.G: "CLASS", "LOOP", "TEXTBOX", "STICKFIGURE"
 */
public class NodeFactory{
    public NodeFactory(){}
    public AbstractNode buildNode(String SVGImage, int x, int y, int w, int h){
        AbstractNode node = null;

        switch(SVGImage){
            case "CLASS" -> node = new ClassNode(x, y, w, h);
            case "TEXTBOX" -> node = new TextBoxNode(x, y, w, h);
            case "NOTE" -> node = new NoteNode(x, y, w, h);
            case "FOLDER" -> node = new FolderNode(x, y, w, h);
            case "SQUARE" -> node = new SquareNode(x, y, w, h);
            case "STICKFIGURE" -> node = new StickFigureNode(x, y, w, h);
            case "OVAL" -> node = new OvalNode(x, y, w, h);
            case "LIFELINE" -> node = new LifeLineNode(x, y, w, h);
            case "LOOP" -> node = new LoopNode(x, y, w, h);
            default -> node = new DefaultNode();
        }
        return node;
    }

    /**
     * Default constructor if no parameters are given
     */
    public AbstractNode buildNode(){
        return new DefaultNode();
    }
}