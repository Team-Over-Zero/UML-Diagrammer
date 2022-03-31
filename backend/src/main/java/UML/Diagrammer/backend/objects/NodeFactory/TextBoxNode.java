package UML.Diagrammer.backend.objects.NodeFactory;

import UML.Diagrammer.backend.objects.AbstractNode;

/**
 * This makes a TextBox uml element.
 * This will NOT need a name, but does need a description, and a TEXT_BOX_SVG image.
 */
public class TextBoxNode extends AbstractNode {
    TextBoxNode(int x, int y, int w, int h){
        super("Textbox Name","square_nodes", "Text Box Description", "TEXT_BOX_SVG", x, y, w, h);
    }
}