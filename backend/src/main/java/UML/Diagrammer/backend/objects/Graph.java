/**
 * Graph.java
 *
 * Since a UML diagram can be described as a graph, concrete implementations can implement the below methods.
 * Just like a graph is made of nodes and edges, a UML diagram will be made up of nodes, edges, and some additional implementation
 * level details.
 * @author Alex
 */

package UML.Diagrammer.backend.objects;

public interface Graph {

    /**
     *
     * @param n1 Node to add.
     */
    void addNode(Node n1);

    /**
     *
     * @param n1 Node to remove. Should also remove attached edges from graph
     */
    void removeNode(Node n1);

    /**
     *
     * @param e1 Edge to add.
     */
    void addEdge(Edge e1);

    /**
     *
     * @param e1 Edge to remove.
     */
    void removeEdge(Edge e1);



}
