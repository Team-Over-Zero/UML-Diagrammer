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
 * Page.java
 *
 * This class should act as a web "page" that holds nodes and edges. Front ends
 * should be able to get all information that they need from this.
 *
 *
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
//        private HashMap<String,Class> classLookup = new HashMap(20);
        private ArrayList<Class> nodeClassLookupList = new ArrayList(20);
        private ArrayList<Class> edgeClassLookupList = new ArrayList(10);

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
         * A better way would be to use a dictionary and map table names to lazy lists as a map. Can
         * only do this if I figure out how to iterate through a dictionary in Java.
         * @author Alex
         * @return
         */
    public ArrayList<LazyList> getNodes(){

        ArrayList totalList = new ArrayList();

        for (Class c:nodeClassLookupList) {
            List<? extends AbstractNode> tempNodes;
            totalList.add(tempNodes =  getAll(c));
        }

        return totalList;

    }
        public ArrayList<LazyList> getEdges(){

            ArrayList totalList = new ArrayList();

            for (Class c:edgeClassLookupList) {
                List<? extends AbstractNode> tempEdges;
                totalList.add(tempEdges =  getAll(c));
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
//            classLookup.put("class_node", ClassNode.class);
//            classLookup.put("default_node", DefaultNode.class);
//            classLookup.put("folder_node", FolderNode.class);
//            classLookup.put("life_line_node", LifeLineNode.class);
//            classLookup.put("loop_node", LoopNode.class);
//            classLookup.put("note_node", NoteNode.class);
//            classLookup.put("oval_node", OvalNode.class);
//            classLookup.put("square_node", SquareNode.class);
//            classLookup.put("stick_figure_node", StickFigureNode.class);
//            classLookup.put("text_box_node", TextBoxNode.class);
//
//            //edge classes
//            classLookup.put("default_edge", DefaultEdge.class);
//            classLookup.put("normal_edge", NormalEdge.class);
//
//

            //This adds list of node class names to our lookup list.
            nodeClassLookupList.add(DefaultNode.class);
            nodeClassLookupList.add(ClassNode.class);
            nodeClassLookupList.add(FolderNode.class);
            nodeClassLookupList.add(LifeLineNode.class);
            nodeClassLookupList.add(LoopNode.class);
            nodeClassLookupList.add(NoteNode.class);
            nodeClassLookupList.add(OvalNode.class);
            nodeClassLookupList.add(SquareNode.class);
            nodeClassLookupList.add(StickFigureNode.class);
            nodeClassLookupList.add(TextBoxNode.class);

            //This adds list of edge class names to our lookup list.

            edgeClassLookupList.add(DefaultEdge.class);
            edgeClassLookupList.add(NormalEdge.class);

        }

        @Override
        /**
         * There is inconsistency between getId() returning java Integers and Big Integers so I forced normal ints.
         * @Author Alex
         */
        public Integer getId(){

            return getInteger("id");
        }

}

