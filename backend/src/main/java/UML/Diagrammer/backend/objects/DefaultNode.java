package UML.Diagrammer.backend.objects;
import lombok.*;
@Getter @Setter
public class DefaultNode extends AbstractNode{


    public DefaultNode(){
        ID = -1;
        name = "DEFAULT";
        title = "DEFAULT";
        description = "DEFAULT";
        xCoord = 0;
        yCoord = 0;
        width = 1;
        height = 1;
        ID = hashCode();
    }
    public DefaultNode(String nm,String desc,int x,int y,int w,int h){
        name = nm;
        description = desc;
        xCoord = x;
        yCoord = y;
        width = w;
        height = h;
        ID = hashCode();

    }

    public void testFunc(){
        System.out.println("HELLO");
    }



}
