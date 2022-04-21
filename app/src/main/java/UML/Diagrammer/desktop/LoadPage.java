package UML.Diagrammer.desktop;

import UML.Diagrammer.backend.objects.UIEdge.UIEdge;
import UML.Diagrammer.backend.objects.UIEdge.UIEdgeFactory;
import UML.Diagrammer.backend.objects.UIEdge.UINormalEdge;
import UML.Diagrammer.backend.objects.UINode.*;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;

public class LoadPage {

    ObjectRequester oR = FXMLController.objectRequesterObservable;
    UINodeFactory nodeFactory = new UINodeFactory();
    UIEdgeFactory edgeFactory = new UIEdgeFactory();

    /**
     * Loads the page by takeing a list of nodes and edges and adds them to the pane with their given information
     * @param nodes The set of nodes we are loading in
     * @param edge The set of edges we are loading in
     * @param pane The parent pane that everything loads into
     */
    public void loadPage(ArrayList<UINode> nodes, ArrayList<UIEdge> edge, Pane pane){
        for (UINode curNode: nodes) {
            loadNode(curNode);
        }

        for (UIEdge curEdge: edge){
            loadEdge(curEdge, pane);
        }
    }

    /**
     * Calls the appropriate make method based on the node type.
     * Uses the given nodes attributes as a reference to create it in the specified area with loaded names.
     * @param node The node you are loading in.
     */
    public void loadNode(UINode node){
        switch (node.getType()){
            case "ovalnodes" -> oR.makeOvalRequest(node.getX_coord(), node.getY_coord(), node.getName(), (UIOvalNode) node);
            case "classnodes" -> oR.makeClassRequest(node.getX_coord(), node.getY_coord(), node.getName(), node.getDescription(), (UIClassNode) node);
            case "foldernodes" -> oR.makeFolderRequest(node.getX_coord(), node.getY_coord(), node.getName(), (UIFolderNode) node);
            case "lifelinenodes" -> oR.makeLifeLineRequest(node.getX_coord(), node.getY_coord(), node.getName(), (UILifeLineNode) node);
            case "loopnodes" -> oR.makeLoopRequest(node.getX_coord(), node.getY_coord(), node.getName(),(UILoopNode) node);
            case "notenodes" -> oR.makeNoteRequest(node.getX_coord(), node.getY_coord(), node.getName(), (UINoteNode) node);
            case "stickfigurenodes" -> oR.makeStickFigureRequest(node.getX_coord(), node.getY_coord(), node.getName(), (UIStickFigureNode) node);
            case "textboxnodes" -> oR.makeTextBoxRequest(node.getX_coord(), node.getY_coord(), node.getName(), (UITextBoxNode) node);
            case "squarenodes" -> oR.makeSquareRequest(node.getX_coord(), node.getY_coord(), node.getName(), (UISquareNode) node);
        }
    }

    /**
     * Loads the edge into the pane given a pane and an edge. The edge finds it's connected components via looking at
     * all the nodes and finds a matching ID.
     * @param edge The edge we are trying to create
     * @param pane The parent pane where we are displaying th edge.
     */
    public void loadEdge(UIEdge edge, Pane pane){
        int n1Id = edge.getN1().getId();
        int n2Id = edge.getN2().getId();
        ArrayList<StackPane> matchingNodes = new ArrayList<>();
        for (Node curNode: pane.getChildren()) {
            if (curNode instanceof StackPane curPane){
                UINode curUINode = (UINode) curPane.getUserData();
                int nodeId = curUINode.getId();
                if (nodeId == n1Id){
                    matchingNodes.add(curPane);
                }
                else if (nodeId == n2Id){
                    matchingNodes.add(curPane);
                }
            }
            if (matchingNodes.size() == 2){break;} // Leave loop early if both nodes have been found
        }
        System.out.println(matchingNodes.get(0).getWidth());
        oR.makeEdgeRequest(matchingNodes.get(0), matchingNodes.get(1), edge);
    }

    /**
     * Test function to make sure that the loading works. Will remove in the future.
     */
    public void loadNodesTest(Pane pane){
        UIOvalNode ovalTestNode = nodeFactory.buildNode("ovalnodes", 150, 600, 300, 150);
        UIFolderNode folderTestNode = nodeFactory.buildNode("foldernodes", 500, 150, 300, 150);
        UISquareNode squareTestNode = nodeFactory.buildNode("squarenodes", 5, 5, 300, 300);
        ovalTestNode.setName("New name is taken");
        folderTestNode.setName("I folder, I loaded");
        ArrayList<UINode> testArray = new ArrayList<UINode>();
        testArray.add(ovalTestNode);
        testArray.add(folderTestNode);
        testArray.add(squareTestNode);

        UINormalEdge testEdge = edgeFactory.buildEdge("normaledges", ovalTestNode, folderTestNode);
        ArrayList<UIEdge> testEdgeArray = new ArrayList<>();
        testEdgeArray.add(testEdge);

        loadPage(testArray, testEdgeArray, pane);
    }
}
