/**
 * AbstractEdge.java
 *
 * Similar to AbstractNode
 * Illustrates a basic Edge object.
 * This class is how the factory will make edges.
 * Able to be expanded in the future if we want to add different line and head types.
 * @author Alex
 */

package UML.Diagrammer.backend.objects;
import UML.Diagrammer.backend.objects.NodeFactory.NodeFactory;
import lombok.Getter;
import lombok.Setter;
import org.javalite.activejdbc.Model;

@Getter @Setter
public abstract class AbstractEdge extends Model implements Edge {

    protected int ID;
    protected Node n1;
    protected Node n2;

    public AbstractEdge(Node n1, Node n2){
            this.n1 = n1;
            this.n2 = n2;
            int intExact = (int) (System.currentTimeMillis() % Integer.MAX_VALUE);
            ID = hashCode() + intExact;
    }

    /**
     * Default constructor, just makes 2 new default nodes and adds an edge between them.
     */
    public AbstractEdge(){
        NodeFactory NFac = new NodeFactory();
        this.n1 = NFac.buildNode();
        this.n2 = NFac.buildNode();
        int intExact = (int) (System.currentTimeMillis() % Integer.MAX_VALUE);
        ID = hashCode() + intExact;
    }

    public void setNodes(Node n1, Node n2){
        this.n1 = n1;
        this.n2 = n2;
    }

    @Override
    public String toString(){
        return "Edge has attributes:" + "\n" +
                "ID: " + ID + "\n" +
                "Node 1: " + n1.getID() + "\n" +
                "Node 2: " + n2.getID();
    }

    /**
     * Equals deals with different ordering of nodes(Since order doesn't matter in an edge)
     */
    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractEdge that = (AbstractEdge) o;
        return
        n1 == that.n1 && n2 == that.n2 ||
        n1 == that.n2 && n2 == that.n1;
    }
}
