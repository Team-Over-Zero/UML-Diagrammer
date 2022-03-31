package UML.Diagrammer.backend.objects.NodeFactory;

import UML.Diagrammer.backend.objects.AbstractNode;

/**
 * This makes a folder uml element.
 * This does NOT need a description, but need a name and a FOLDER_SVG image.
 */
public class FolderNode extends AbstractNode {
    FolderNode(int x, int y, int w, int h){
        super("Folder Name","folder_nodes",null, "FOLDER_SVG", x, y, w, h);
    }
}