package UML.Diagrammer.backend.objects.NodeFactory;

import UML.Diagrammer.backend.objects.AbstractNode;

/**
 * This makes a LifeLine uml element.
 * This does NOT need a name or a description, but need a name and a LIFE_LINE_SVG image.
 */
public class LifeLineNode extends AbstractNode {
    LifeLineNode(int x, int y, int w, int h){
        super(null, null, "LIFE_LINE_SVG", x, y, w, h);
    }
}