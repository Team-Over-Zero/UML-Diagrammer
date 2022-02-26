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
