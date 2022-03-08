package UML.Diagrammer.backend.objects.EdgeFactory;

import UML.Diagrammer.backend.objects.AbstractEdge;
import UML.Diagrammer.backend.objects.Node;

/**
 * Basic solid edge that is between two nodes
 */
public class NormalEdge extends AbstractEdge {
    NormalEdge(Node n1, Node n2){
        super(n1, n2);
    }
}