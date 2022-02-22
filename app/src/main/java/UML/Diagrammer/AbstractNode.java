//AbstractNode.java
package UML.Diagrammer;
import lombok.*;
/**
 * This class should illustrate a basic Node object.
 */
public abstract class AbstractNode implements Node{
    int id;
    String name;
    String title;
    String description;
    int xCoord;
    int yCoord;
    int width;
    int height;

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
