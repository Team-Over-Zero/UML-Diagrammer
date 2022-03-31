package UML.Diagrammer.backend.objects.EdgeFactory;

import UML.Diagrammer.backend.objects.AbstractEdge;

/**
 * Default edge given no parameter
 */
public class DefaultEdge extends AbstractEdge {
    DefaultEdge(){
        super();
        set("type","default_edges");
    }
}
