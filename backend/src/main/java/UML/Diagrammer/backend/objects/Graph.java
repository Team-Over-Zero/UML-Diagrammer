/*Copyright 2022 Team OverZero
<p>
Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
documentation files (the "Software"), to deal in the Software without restriction, including without limitation
the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and
to permit persons to whom the Software is furnished to do so, subject to the following conditions:
<p>
The above copyright notice and this permission notice shall be included in all copies or substantial portions of
the Software.
<p>
THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.*/
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
    void addNode(AbstractNode n1);

    /**
     *
     * @param n1 Node to remove. Should also remove attached edges from graph
     */
    void removeNode(AbstractNode n1);

    /**
     *
     * @param e1 Edge to add.
     */
    void addEdge(AbstractEdge e1);

    /**
     *
     * @param e1 Edge to remove.
     */
    void removeEdge(AbstractEdge e1);



}
