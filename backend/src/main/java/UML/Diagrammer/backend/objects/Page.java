//Page.java
package UML.Diagrammer.backend.objects;
import java.util.*;

/**
 * This class should act as a web "page" that holds nodes and edges. Front ends
 * should be able to get all information that they need from this.
 */
public class Page implements Graph {
    private HashMap nodeDict; //keys are node ids, values are node objects.
    private HashMap edgeDict; //keys are edge ids, values are edge objects.
    public Page(){
        nodeDict = new HashMap();
        edgeDict = new HashMap();
    }

    public void addNode(Node n1){
        nodeDict.put(n1.getID(),n1);
    }
    public void removeNode(Node n1){
        nodeDict.remove(n1.getID());

        //IMPLEMENT: for edge in edge dictionary, if key or value contains n1, remove.

    }

    public void addEdge(Edge e1){
        edgeDict.put(e1.getID(),e1);
    }
    public void removeEdge(Edge e1){
        edgeDict.remove(e1.getID());
    }

}
