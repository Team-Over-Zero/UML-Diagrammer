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
package UML.Diagrammer.backend.objects.UINode;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class UINode {
    public String name;
    public String type;
    public String description;
    public String svg_image;
    public int x_coord;
    public int y_coord;
    public int width;
    public int height;
    public int id;

    public UINode(String name, String type, String description, String SVGImage, int x, int y_coord, int w, int h){
        id = -1;
        this.name = name;
        this.type = type;
        this.description = description;
        this.svg_image = SVGImage;
        this.x_coord = x;
        this.y_coord = y_coord;
        this.width = w;
        this.height = h;
    }

    public UINode(){
        this.id = -1;
        this.name = "DEFAULT NAME";
        this.type = "DEFAULT TYPE";
        this.description = "DEFAULT DESCRIPTION";
        this.svg_image = "DEFAULT SVG";
        this.x_coord = 0;
        this.y_coord = 0;
        this.width = 3;
        this.height = 3;
    }

    public String toString() {
        return "Made a UINode with:" + "\n" +
                "name: "+name+ "\n"+
                "type: "+type+ "\n"+
                "desc: " + description + "\n" +
                "SVG " + svg_image + "\n" +
                "(x, y): " + "(" + x_coord + ", " + y_coord + ")" + "\n" +
                "(Width, Height): " + "(" + width + ", " + height + ")";
    }

    public String getIDAsJson(){
        return "{\"id\":\"" + id + "\"}";
    }

    public String getNodeAsJSon(){
        return "{\"description\":\"" + description + "\"," +
                "\"height\":\"" + height + "\"," +
                "\"id\":\"" + id + "\"," +
                "\"name\":\"" + name + "\"," +
                "\"svg_image\":\"" + svg_image + "\"," +
                "\"type\":\"" + type + "\"," +
                "\"width\":\"" + width + "\"," +
                "\"x_coord\":\"" + x_coord + "\"," +
                "\"y_coord\":\"" + y_coord + "\"" +
                "}";
    }
}
