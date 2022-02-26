//Node.java
package UML.Diagrammer.backend.objects;

/**
 * This interface should define what methods every single UML "object" (not including edges) should have.
 * @author Alex Diviney
 */
public interface Node {

//    /**
//     *
//     * @return name of object
//     */
//    String getName();
//
//    /**
//     *
//     * @return unique id of object
//     */
//    int getID(); //ID should be unique object value.

//    /**
//     * Sets the Size of the diagram. (width, height, radius etc.)
//     */
//    void setSize();

    /**
     * Sets the x and y position on the page of the object.
     */
    void setCoords(int x, int y);
    int getID();

}
