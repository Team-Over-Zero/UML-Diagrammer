package UML.Diagrammer.backend.objects.NodeFactory;

import UML.Diagrammer.backend.objects.AbstractNode;

/**
 * This makes a Oval uml element.
 * This does NOT need a description, but need a name and a OVAL_SVG image.
 */
public class OvalNode extends AbstractNode {
    public OvalNode(int x, int y, int w, int h){
        super("Oval Name","oval_nodes", null, "OVAL_SVG", x, y, w, h);
    }
}