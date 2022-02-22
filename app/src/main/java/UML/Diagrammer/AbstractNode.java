//AbstractNode.java
package UML.Diagrammer;
import lombok.*;
/**
 * This class should illustrate a basic Node object.
 */
@Getter @Setter
public abstract class AbstractNode implements Node{

   private int id;
   private String name;
    private String title;
    private String description;
    private int xCoord;
    private int yCoord;
    private int width;
    private int height;

    AbstractNode(){
        id = -1;
        name = "DEFAULT";
        title = "DEFAULT";
        description = "DEFAULT";
        xCoord = 0;
        yCoord = 0;
        width = 1;
        height = 1;
    }

    public void setSize(int w, int h){
        width = w;
        height = h;
    }
    public void setCoords(int x, int y){
        xCoord = x;
        yCoord = y;
    }

}
