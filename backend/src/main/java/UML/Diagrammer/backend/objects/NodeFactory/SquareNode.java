package UML.Diagrammer.backend.objects.NodeFactory;

import UML.Diagrammer.backend.objects.AbstractNode;

/**
 * This makes a square uml element.
 * This does NOT need a description, but need a name and a SQUARE_SVG image.
 */
public class SquareNode extends AbstractNode {
    public SquareNode(int x, int y, int w, int h){
        super("Square Name","square_nodes", null, "SQUARE_SVG", x, y, w, h);
    }
}
