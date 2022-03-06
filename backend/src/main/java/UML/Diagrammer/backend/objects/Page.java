/**
 * Page.java
 *
 * This class should act as a web "page" that holds nodes and edges. Front ends
 * should be able to get all information that they need from this.
 * @author Alex
 *
 */
package UML.Diagrammer.backend.objects;
import lombok.*;
import java.util.*;

    @Getter
    @Setter
public class Page implements Graph {
    private HashMap nodeDict; //keys are node ids, values are node objects.
    private HashMap edgeDict; //keys are edge ids, values are edge objects.
    private String pageName;

        /**
         * Default Page with pageName set to DEFAULT
         */
    public Page(){
        nodeDict = new HashMap();
        edgeDict = new HashMap();
        pageName = "DEFAULT";
    }

        /**
         *
         * @param nD Hashmap of Nodes
         * @param eD Hashmap of Edges
         * @param pN Name of the Page (No spaces)
         */
    public Page(HashMap nD,HashMap eD, String pN){
        nodeDict = nD;
        edgeDict = eD;
        pageName = pN;
    }

        /**
         *
         * @param n1 Node to add.
         */
    public void addNode(Node n1){
        nodeDict.put(n1.getID(),n1);
    }

        /**
         *
         * @param n1 Node to remove. Should also remove attached edges from graph
         */
    public void removeNode(Node n1){
        nodeDict.remove(n1.getID());
        //IMPLEMENT: for edge in edge dictionary, if key or value contains n1, remove.
    }

        /**
         *
         * @param e1 Edge to add.
         */
    public void addEdge(Edge e1){
        edgeDict.put(e1.getID(),e1);
    }


        /**
         *
         * @param e1 Edge to remove.
         */
    public void removeEdge(Edge e1){
        edgeDict.remove(e1.getID());
    }
    //needed this to test something

        /**
         *
         * @return Size of nodeDict
         */
    public int getNodeDictSize(){
        return nodeDict.size();
    }

        /**
         *
         * @return Size of edgeDict
         */
    public int getEdgeDictSize(){
        return edgeDict.size();
    }

}
