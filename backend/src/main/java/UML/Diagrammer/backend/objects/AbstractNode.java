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
public abstract class AbstractNode extends Model {



    /**
     * Constructor for the NodeFactory class so this class can put together things from it's subclasses
     *
     * @author Show
     */
    public AbstractNode(String name, String type,String desc, String SVGImage, int x, int y, int w, int h) {
        set("name", name);
        set("type",type);
        set("description", desc);
        set("svg_image", SVGImage);
        set("x_coord", x);
        set("y_coord", y);
        set("width", w);
        set("height", h);
        //saveIt();
    }

    /**
     * A default constructor with no parameters
     *
     * @author Show
     */
    public AbstractNode() {
        set("name", "DEFAULT NAME");
        set("type","abstract_nodes");
        set("description", "DEFAULT DESCRIPTION");
        set("svg_image", "DEFAULT IMAGE");
        set("x_coord", 0);
        set("y_coord", 0);
        set("width", 3);
        set("height", 3);
        //saveIt();
    }

    /**
     * Sets the width and height of the node
     *
     * @param w width of node
     * @param h height of node
     */
    public void setSize(int w, int h) {
        set("width", w);
        set("height", h);


    }

    /**
     * Sets the x y coordinates of the node
     *
     * @param x X coordinate
     * @param y Y coordinate
     */
    public void setCoords(int x, int y) {
        set("x_coord", x);
        set("y_coord", y);
    }

    /**
     * Just a quick toString function
     * @author Show, refactored by Alex
     */
//    @Override
    public String toString() {
        String table = getTableName();
        String name = get("name").toString();
        String id = getId().toString();
        String description = get("description").toString();
        String xCoord = get("x_coord").toString();
        String yCoord = get("y_coord").toString();
        String width = get("width").toString();
        String height = get("height").toString();

        return "Made a node with:" + "\n" +
                "Table: "+table+ "\n"+
                "ID: "+id+ "\n"+
                "Name: " + name + "\n" +
                "Description: " + description + "\n" +
                "(x, y): " + "(" + xCoord + ", " + yCoord + ")" + "\n" +
                "(Width, Height): " + "(" + width + ", " + height + ")";
    }


   // @Override
    /**
     * There is inconsistency between getId() returning java Integers and Big Integers so I forced normal ints.
     * @Author Alex
     */
    public Integer getId(){

        return getInteger("id");
    }


    /**
     * This just adds an alias for saveIt so that we can move saveIt calls out of constructors.
     */
    public void createIt(){
        saveIt();
    }

//
//    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        AbstractNode that = (AbstractNode) o;
//        return get("x_coord") == that.xCoord && yCoord == that.yCoord && name.equals(that.name)  && description.equals(that.description);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(name, description, xCoord, yCoord);
//    }


}

