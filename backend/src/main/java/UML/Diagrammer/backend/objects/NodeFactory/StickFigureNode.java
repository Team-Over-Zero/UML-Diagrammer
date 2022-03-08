package UML.Diagrammer.backend.objects.NodeFactory;

import UML.Diagrammer.backend.objects.AbstractNode;

/**
 * This makes a Stick Figure uml element.
 * This does NOT need a description, but need a name and a STICK_FIGURE_SVG image.
 */
public class StickFigureNode extends AbstractNode {
    StickFigureNode(int x, int y, int w, int h){
        super("Stick Figure Name", null, "STICK_FIGURE_SVG", x, y, w, h);
    }
}