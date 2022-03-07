package UML.Diagrammer.backend.objects;
/**
 * This is similar to NodeFactory just much fewer subclasses for now.
 * You can only really make 1 type of edge(normalEdge) and there is a default one for no parameters given.
 * This is formatted exactly the same as NodeFactory for consistency and to make it extendable in the future.
 * @author Show
 */

/**
 * Basic solid edge that is between two nodes
 */
class normalEdge extends AbstractEdge{
    normalEdge(Node n1, Node n2){
        super(n1, n2);
    }

    @Override public int getID() {return super.getID();}
    public Node getN1() {return super.getN1();}
    public Node getN2() {return super.getN2();}
}

/**
 * Default edge given no parameter
 */
class defaultEdge extends AbstractEdge{
    defaultEdge(){
        super();
    }

    @Override public int getID() {return super.getID();}
    public Node getN1() {return super.getN1();}
    public Node getN2() {return super.getN2();}
}

/**
 * Actual factory to make edges
 */
public class EdgeFactory {
    public EdgeFactory(){}

    public AbstractEdge buildEdge(Node n1, Node n2){
        return new normalEdge(n1, n2);
    }

    public AbstractEdge buildEdge(){
        return new defaultEdge();
    }
}