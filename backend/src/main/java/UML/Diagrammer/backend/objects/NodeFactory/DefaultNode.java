package UML.Diagrammer.backend.objects.NodeFactory;

import UML.Diagrammer.backend.objects.AbstractNode;

/**
 * Default constructor if you don't need/want to specify nodes for some reason.
 */
public class DefaultNode extends AbstractNode {
    public DefaultNode(){
        super();
        set("type","default_nodes");
        //saveIt();

    }


}