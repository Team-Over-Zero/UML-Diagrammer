//AbstractNode.java
package UML.Diagrammer.backend.objects;
import lombok.*;

import java.util.Objects;

/**
 * This class should illustrate a basic Node object.
 */

@Getter @Setter
public abstract class AbstractNode implements Node{

   protected int ID;
   protected String name;
    protected String title;
    protected String description;
    protected int xCoord;
    protected int yCoord;
    protected int width;
    protected int height;


    public void setSize(int w, int h){
        width = w;
        height = h;
    }
    public void setCoords(int x, int y){
        xCoord = x;
        yCoord = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractNode that = (AbstractNode) o;
        return xCoord == that.xCoord && yCoord == that.yCoord && name.equals(that.name) && title.equals(that.title) && description.equals(that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, title, description, xCoord, yCoord);
    }
}
