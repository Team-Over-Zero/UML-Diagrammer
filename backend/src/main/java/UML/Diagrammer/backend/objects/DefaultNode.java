package UML.Diagrammer.backend.objects;
import lombok.*;

import java.time.Clock;

@Getter @Setter
public class DefaultNode extends AbstractNode{


    public DefaultNode(){
        Clock clock = Clock.systemDefaultZone();
        long milliSeconds = clock.millis();
        ID = -1;
        name = "DEFAULT";
        title = "DEFAULT";
        description = "DEFAULT";
        xCoord = 0;
        yCoord = 0;
        width = 1;
        height = 1;
        int intExact = Math.toIntExact(milliSeconds);
        ID = hashCode()+ intExact;
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
