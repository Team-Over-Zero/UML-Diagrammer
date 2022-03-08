package UML.Diagrammer.backend.objects.NodeFactory;
import UML.Diagrammer.backend.objects.*;
/**
 * This makes a Class uml element.
 * This will need a name, description, and a TEXT_BOX_SVG image.
 */
 public class ClassNode extends AbstractNode{
     ClassNode(int x, int y, int w, int h){
        super("Class Name", "Class Description", "CLASS_SVG", x, y, w, h);
     }
     public void testFunc(){
         System.out.println("subclassing stuffs is working alright");
     }
}