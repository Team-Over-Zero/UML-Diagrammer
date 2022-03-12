package UML.Diagrammer.backend.objects.EdgeFactory;

import UML.Diagrammer.backend.objects.AbstractEdge;
import UML.Diagrammer.backend.objects.AbstractNode;
import UML.Diagrammer.backend.objects.Node;

/**
 * Basic solid edge that is between two nodes
 */
public class NormalEdge extends AbstractEdge {
    NormalEdge(AbstractNode n1, AbstractNode n2){
        super(n1, n2);
    }
}