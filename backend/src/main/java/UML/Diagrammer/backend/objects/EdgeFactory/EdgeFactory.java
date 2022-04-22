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
package UML.Diagrammer.backend.objects.EdgeFactory;
import UML.Diagrammer.backend.objects.AbstractEdge;
import UML.Diagrammer.backend.objects.AbstractNode;

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
    buildEdge(String tableName,int fromNodeId,String fromNodeType,int toNodeId,String toNodeType){

        if(tableName.equals("normaledges")) {
            return (genericEdge) new NormalEdge(fromNodeId,fromNodeType,toNodeId,toNodeType);
        }

        else return (genericEdge) new DefaultEdge(fromNodeId,fromNodeType,toNodeId,toNodeType);
    }

    @SuppressWarnings("unchecked")
	public <genericEdge extends AbstractEdge> genericEdge buildEdge(){
        return (genericEdge) new DefaultEdge();
    }
}