package UML.Diagrammer.backend.objects.NodeFactory;

import UML.Diagrammer.backend.objects.AbstractNode;

/**
 * This makes a Loop uml element.
 * This does NOT need a name or a description, but need a name and a LOOP_SVG image.
 */
public class LoopNode extends AbstractNode {
    public LoopNode(int x, int y, int w, int h){
        super("LoopNode Name","loop_nodes", null, "LOOP_SVG", x, y, w, h);
    }
}