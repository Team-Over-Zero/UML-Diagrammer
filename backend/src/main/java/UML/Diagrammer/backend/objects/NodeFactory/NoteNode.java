package UML.Diagrammer.backend.objects.NodeFactory;

import UML.Diagrammer.backend.objects.AbstractNode;

/**
 * This makes a note uml element.
 * This does NOT need a name but does need a description and a NOTE_SVG image.
 */
public class NoteNode extends AbstractNode {
    NoteNode(int x, int y, int w, int h){
        super(null, "Note Description", "NOTE_SVG", x, y, w, h);
    }
}
