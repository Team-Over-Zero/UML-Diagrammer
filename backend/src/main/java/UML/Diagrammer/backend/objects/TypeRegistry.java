/** TypeRegistry.java
 *
 * This class is a singleton that is designed to make it easy to map tables to Class names of java objects. Since
 * we need these lists in more than one place I decided it might just be best to make a data class that stores them.
 */


package UML.Diagrammer.backend.objects;
import UML.Diagrammer.backend.objects.EdgeFactory.DefaultEdge;
import UML.Diagrammer.backend.objects.EdgeFactory.NormalEdge;
import UML.Diagrammer.backend.objects.NodeFactory.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter @Setter 
public class TypeRegistry {

    private static TypeRegistry instance = null;
    private ArrayList<String> tableNodeList;
    private ArrayList<String> tableEdgeList;
    private ArrayList<Class> nodeClassList;
    private ArrayList<Class> edgeClassList;
    private HashMap<String,Class> nodeClassMap;
    private HashMap<String,Class> edgeClassMap;
    private HashMap<String,Runnable> nodeTableMap;
    private String[] nodeTableArr = {"default_nodes","class_nodes","folder_nodes","life_line_nodes","loop_nodes","note_nodes","oval_nodes","square_nodes","stick_figure_nodes","text_box_nodes"};


    private TypeRegistry(){
        tableNodeList = new ArrayList<>(20);
        tableEdgeList = new ArrayList<>(10);
        nodeClassList = new ArrayList<>();
        edgeClassList = new ArrayList<>();
        nodeClassMap  = new HashMap<>();
        edgeClassMap  = new HashMap<>();

        //this adds our list of node tables
        tableNodeList.add("default_nodes");
        tableNodeList.add("class_nodes");
        tableNodeList.add("folder_nodes");
        tableNodeList.add("life_line_nodes");
        tableNodeList.add("loop_nodes");
        tableNodeList.add("note_nodes");
        tableNodeList.add("oval_nodes");
        tableNodeList.add("square_nodes");
        tableNodeList.add("stick_figure_nodes");
        tableNodeList.add("text_box_nodes");

        //this adds our list of edge tables
        tableEdgeList.add("default_edges");
        tableEdgeList.add("normal_edges");


        //nodeClassList
        //This adds list of node class names to our lookup list.
        nodeClassList.add(DefaultNode.class);
        nodeClassList.add(ClassNode.class);
        nodeClassList.add(FolderNode.class);
        nodeClassList.add(LifeLineNode.class);
        nodeClassList.add(LoopNode.class);
        nodeClassList.add(NoteNode.class);
        nodeClassList.add(OvalNode.class);
        nodeClassList.add(SquareNode.class);
        nodeClassList.add(StickFigureNode.class);
        nodeClassList.add(TextBoxNode.class);



        //This adds list of edge class names to our lookup list.
        edgeClassList.add(DefaultEdge.class);
        edgeClassList.add(NormalEdge.class);


        //this makes an node map from our node class and name lists
        for(int i = 0;i<nodeClassList.size();i++) {
            nodeClassMap.put(tableNodeList.get(i),nodeClassList.get(i));
        }
        //this makes an edge map from our edge class and name lists
        for(int i = 0;i<edgeClassList.size();i++) {
            nodeClassMap.put(tableEdgeList.get(i),edgeClassList.get(i));
        }


    }

    public static TypeRegistry getInstance() {
        if(instance==null){
            instance = new TypeRegistry();
        }
        return instance;
    }





}
