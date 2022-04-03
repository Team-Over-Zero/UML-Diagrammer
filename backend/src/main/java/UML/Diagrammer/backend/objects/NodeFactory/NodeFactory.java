/*Copyright 2022 Team OverZero
<p>
Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
documentation files (the "Software"), to deal in the Software without restriction, including without limitation
the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and
to permit persons to whom the Software is furnished to do so, subject to the following conditions:
<p>
The above copyright notice and this permission notice shall be included in all copies or substantial portions of
the Software.
<p>
THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.*/
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
 * TextBoxNode myNode = factory.buildNode("TEXTBOX", x, y, w, h)
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