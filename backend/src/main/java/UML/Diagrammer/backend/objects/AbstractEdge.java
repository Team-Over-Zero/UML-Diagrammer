/**
 * AbstractEdge.java
 *
 * Since an Edge object has a many to one relationship with a page (one page can have many edges)
 * and since edge has a 1 to 2 relationship with nodes, we have to architect it in a special way.
 * Since nodes can be subtyped, we cannot reference any node table directly, and instead require
 * the tableName and id of
 * @author Alex
 */

package UML.Diagrammer.backend.objects;
import UML.Diagrammer.backend.objects.NodeFactory.DefaultNode;
import UML.Diagrammer.backend.objects.NodeFactory.NodeFactory;
import lombok.Getter;
import lombok.Setter;
import org.javalite.activejdbc.Model;

import java.util.List;

@Getter @Setter
public abstract class AbstractEdge extends Model {


    /**
     * Default constructor, creates two defaultNodes, and sets the attributes of the edge based on the type and ids of the
     * edge. Note that this instantiates the nodes it is dependent on.
     */
    public AbstractEdge(){
        set("type","abstract_edges");
        NodeFactory NFac = new NodeFactory();
        DefaultNode n1 = NFac.buildNode();
        DefaultNode n2 = NFac.buildNode();
        int fN = Integer.parseInt( n1.getId().toString());
        int tN = Integer.parseInt( n2.getId().toString());
        //n1.get("type")
        String fT = n1.get("type").toString();
        String tT = n2.get("type").toString();
        setNodes(fN,fT,tN,tT);

    }

    /**
     * This method is a bit deceptive. Since we can't actually store instances of nodes inside edge, we
     * mimick it by storing ids of nodes and types of nodes to allow for the retrieval of nodes.
     * @param n1 From Node object
     * @param n2 To Node object
     */
    public AbstractEdge(AbstractNode n1, AbstractNode n2){
        set("type","abstract_edges");
        int fromNodeId =  Integer.parseInt(n1.getId().toString());
        int toNodeId =  Integer.parseInt(n2.getId().toString());
        String fromNodeType = n1.getString("type");
        String toNodeType = n2.getString("type");
        setNodes(fromNodeId,fromNodeType,toNodeId,toNodeType);
    }


    /**
     * Wrapper method that unpacks node objects and sends the data to the other setNodes method.
     * @param n1 AbstractNode fromNode
     * @param n2 AbstractNode toNode
     */
    public void setNodes(AbstractNode n1, AbstractNode n2){
        int fromNodeId =  Integer.parseInt(n1.getId().toString());
        int toNodeId =  Integer.parseInt(n2.getId().toString());
        String fromNodeType = n1.getString("type");
        String toNodeType = n2.getString("type");
        setNodes(fromNodeId,fromNodeType,toNodeId,toNodeType);
    }
    /**
     * Takes passed in attributes and saves them to the database.
     * @param fromId The id of Node 1
     * @param fromType The object type (table_name) of Node 1
     * @param toId The id of Node 2
     * @param toType The object type (table_name) of Node 2
     */
    public void setNodes(int fromId, String fromType, int toId,String toType){
        set("from_node_id",fromId);
        set("to_node_id",toId);
        set("from_node_type",fromType);
        set("to_node_type",toType);
        saveIt();

    }

    @Override
    public String toString(){
        return "Edge has attributes:" + "\n" +
                "ID: " + this.getId() + "\n" +
                "Node 1 ID: " + get("from_node_id") + "\n" +
                "Node 2 ID: " +get("to_node_id") + "\n" +
                "Node 1 Type: "+get("from_node_type")+ "\n" +
                "Node 2 Type: "+ get("to_node_type");
    }

//    public AbstractNode getFromNode(){
//
//        String n1Table = get("from_node_type").toString();
//        String n1Id = get("from_node_id").toString();
//        String queryStr = "SELECT * FROM "+n1Table + " WHERE id = "+n1Id;
//        System.out.println(queryStr);
//        List<AbstractNode> rules = findBySQL(queryStr);
//        for (AbstractNode x: rules
//             ) {
//           return x;
//        }
//        return null;
//    }
//
//    public AbstractNode getToNode() {
//        String n2Table = get("to_node_type").toString();
//        String n2Id = get("to_node_id").toString();
//        String queryStr = "SELECT * FROM "+n2Table + "WHERE id = '"+n2Id+"'";
//        System.out.println(queryStr);
//        List<AbstractNode> rules = findBySQL(queryStr);
//        for (AbstractNode x: rules
//        ) {
//            return x;
//        }
//        return null;
//    }

        /**
         * Equals deals with different ordering of nodes(Since order doesn't matter in an edge). ALEX NOTE: Deprecating temporarily
         */
//    @Override
//    public boolean equals(Object o){
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        AbstractEdge that = (AbstractEdge) o;
//        return
//        n1 == that.n1 && n2 == that.n2 ||
//        n1 == that.n2 && n2 == that.n1;
//    }
}
