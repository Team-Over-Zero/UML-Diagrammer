/**
 * AbstractNode.java
 *
 * This class should illustrate a basic Node object.
 * This class will be the superclass of the various subclasses found in NodeFactory.
 * @author Alex
 */

package UML.Diagrammer.backend.objects;
import lombok.*;
import java.util.Objects;
import org.javalite.activejdbc.Model;

@Getter @Setter
public abstract class AbstractNode extends Model implements Node{

    //protected int id;
    protected String name;
    protected String title;
    protected String description;
    protected String svgImage;
    protected int xCoord;
    protected int yCoord;
    protected int width;
    protected int height;

    /**
     * Constructor for the NodeFactory class so this class can put together things from it's subclasses
     * @author Show
     */
    public AbstractNode(String name, String desc, String SVGImage, int x, int y, int w, int h) {
        this.name = name;
        // I didn't add a "Title" attribute here, not sure if we want that.
        this.description = desc;
        this.xCoord = x;
        this.yCoord = y;
        this.width = w;
        this.height = h;
        this.svgImage = SVGImage;
        //int intExact = (int) (System.currentTimeMillis() % Integer.MAX_VALUE);
    }

    /**
     * A default constructor with no parameters
     * @author Show
     */
    public AbstractNode(){
        this.name = "DEFAULT NAME";
        this.description = "DEFAULT DESCRIPTION";
        this.xCoord = 0;
        this.yCoord = 0;
        this.width = 3;
        this.height = 3;
        this.svgImage = "DEFAULT IMAGE LOCATION";
       // int intExact = (int) (System.currentTimeMillis() % Integer.MAX_VALUE);
       // id = -1;
    }

    /**
     * Sets the width and height of the node
     * @param w width of node
     * @param h height of node
     */
    public void setSize(int w, int h){
        width = w;
        height = h;
    }

    /**
     * Sets the x y coordinates of the node
     * @param x X coordinate
     * @param y Y coordinate
     */
    public void setCoords(int x, int y){
        xCoord = x;
        yCoord = y;
    }

    /**
     * Just a quick toString function
     * @author Show
     */
    @Override
    public String toString() {
        return "Made a node with:" + "\n" +
                "Name: " + name + "\n" +
                "Description: " + description + "\n" +
                "(x, y): " + "(" + xCoord + ", " + yCoord + ")" + "\n" +
                "(Width, Height): " + "(" + width + ", " + height + ")";
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
