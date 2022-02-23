//Edge.java
package UML.Diagrammer;

/**
 * This is an interface for UML edge "objects". Edge objects should point to two nodes, and have a type.
 */
public interface Edge {

    /**
     * Should set the specific nodes that this edge is attached to.
     */
    void setNodes(Node n1,Node n2);
    int getID();
}
