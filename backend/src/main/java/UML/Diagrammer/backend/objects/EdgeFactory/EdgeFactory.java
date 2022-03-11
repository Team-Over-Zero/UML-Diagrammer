package UML.Diagrammer.backend.objects.EdgeFactory;
import UML.Diagrammer.backend.objects.AbstractEdge;
import UML.Diagrammer.backend.objects.AbstractNode;
import UML.Diagrammer.backend.objects.Node;

/**
 * This is similar to NodeFactory just much fewer subclasses for now.
 * You can only really make 1 type of edge(normalEdge) and there is a default one for no parameters given.
 * This is formatted exactly the same as NodeFactory for consistency and to make it extendable in the future.
 * @author Show
 */
public class EdgeFactory {
    public EdgeFactory(){}

    @SuppressWarnings("unchecked")
	public <genericEdge extends AbstractEdge> genericEdge
    buildEdge(AbstractNode n1, AbstractNode n2){
        return (genericEdge) new NormalEdge(n1, n2);
    }

    @SuppressWarnings("unchecked")
	public <genericEdge extends AbstractEdge> genericEdge buildEdge(){
        return (genericEdge) new DefaultEdge();
    }
}