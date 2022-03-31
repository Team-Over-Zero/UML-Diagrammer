/**
 * Page.java
 *
 * This class should act as a web "page" that holds nodes and edges. Front ends
 * should be able to get all information that they need from this.
 * @author Alex
 *
 */
package UML.Diagrammer.backend.objects;
import UML.Diagrammer.backend.objects.NodeFactory.*;
import UML.Diagrammer.backend.objects.EdgeFactory.*;

import lombok.*;
import org.javalite.activejdbc.LazyList;
import org.javalite.activejdbc.Model;

import java.util.*;

    @Getter
    @Setter
public class Page extends Model {
   // private HashMap nodeDict; //keys are node ids, values are node objects.
    //private HashMap edgeDict; //keys are edge ids, values are edge objects.
        private HashMap<String,Class> classLookup = new HashMap(20);
        private ArrayList<Class> nodeClassLookupList = new ArrayList(20);

        /**
         * Default Page with pageName set to DEFAULT
         */
    public Page(){
        set("name","default name");
        initClassMap();
        saveIt();
    }

        /**
         *
         * @param name Name of the page

         */
    public Page(String name){
        set("name",name);
        initClassMap();
        saveIt();
    }

        /**
         * A pretty naive and lazy way to get a list of all the nodes on a page.
         * A better way would be to use a dictionary and map table names to lazy lists as a map. Refactor.
         * @author Alex
         * @return
         */
    public ArrayList<LazyList> getNodes(){



        //manually get every node table list and return a big list?
        LazyList<DefaultNode> defaultNodes =  getAll(DefaultNode.class);
        LazyList<ClassNode> classNodes = getAll(ClassNode.class);
        ArrayList totalList = new ArrayList();

        for(int i = 0;i<nodeClassLookupList.size();i++){
            totalList.add(getAll(nodeClassLookupList.get(i))); //queries every node table
        }


        return totalList;

    }

        /**
         * THis helps us map our table names to our object classes.
         * Necessary because getAll() methods require class names.
         */
        public void initClassMap(){
            //associates "types" with classes. This is stupid but allows us to Hydrate classes easier.
            //node classes
            classLookup.put("class_node", ClassNode.class);
            classLookup.put("default_node", DefaultNode.class);
            classLookup.put("folder_node", FolderNode.class);
            classLookup.put("life_line_node", LifeLineNode.class);
            classLookup.put("loop_node", LoopNode.class);
            classLookup.put("note_node", NoteNode.class);
            classLookup.put("oval_node", OvalNode.class);
            classLookup.put("square_node", SquareNode.class);
            classLookup.put("stick_figure_node", StickFigureNode.class);
            classLookup.put("text_box_node", TextBoxNode.class);

            //edge classes
            classLookup.put("default_edge", DefaultEdge.class);
            classLookup.put("normal_edge", NormalEdge.class);


            nodeClassLookupList.add(ClassNode.class);
            nodeClassLookupList.add(DefaultNode.class);
            nodeClassLookupList.add(FolderNode.class);
            nodeClassLookupList.add(LifeLineNode.class);
            nodeClassLookupList.add(LoopNode.class);
            nodeClassLookupList.add(NoteNode.class);
            nodeClassLookupList.add(OvalNode.class);
            nodeClassLookupList.add(SquareNode.class);
            nodeClassLookupList.add(StickFigureNode.class);
            nodeClassLookupList.add(TextBoxNode.class);


        }


}

