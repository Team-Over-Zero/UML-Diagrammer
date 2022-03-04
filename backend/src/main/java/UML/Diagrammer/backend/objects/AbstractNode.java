//AbstractNode.java
package UML.Diagrammer.backend.objects;
import lombok.*;

import java.util.Objects;

/**
 * This class should illustrate a basic Node object.
 * This class will be the superclass of the various subclasses found in NodeFactory.
 */

@Getter @Setter
public abstract class AbstractNode implements Node{

    protected int ID;
    protected String name;
    protected String title;
    protected String description;
    protected String SVGImage;
    protected int xCoord;
    protected int yCoord;
    protected int width;
    protected int height;

    /**
     * Constructor for the NodeFactory class so this class can put together things from it's subclasses
     * @author Show
     */
    AbstractNode(String name, String desc, String SVGImage, int x, int y, int w, int h) {
        this.name = name;
        // I didn't add a "Title" attribute here, not sure if we want that.
        this.description = desc;
        this.xCoord = x;
        this.yCoord = y;
        this.width = w;
        this.height = h;
        this.SVGImage = SVGImage;
        int intExact = (int) (System.currentTimeMillis() % Integer.MAX_VALUE);
        ID = hashCode()+ intExact;
    }

    /**
     * A default constructor with no parameters
     * @author Show
     */
    AbstractNode(){
        this.name = "DEFAULT NAME";
        this.description = "DEFAULT DESCRIPTION";
        this.xCoord = 0;
        this.yCoord = 0;
        this.width = 3;
        this.height = 3;
        this.SVGImage = "DEFAULT IMAGE LOCATION";
        int intExact = (int) (System.currentTimeMillis() % Integer.MAX_VALUE);
        ID = hashCode()+ intExact;
    }
    public void setSize(int w, int h){
        width = w;
        height = h;
    }
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
