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

import java.util.Random;

@Getter @Setter
public abstract class UINode {
    public String name;
    public String type;
    public String desc;
    public String SVGImage;
    public int x;
    public int y;
    public int w;
    public int h;
    public int id;
    Random ran = new Random();

    public UINode(String name, String type, String desc, String SVGImage, int x, int y, int w, int h){
        id = ran.nextInt(1000); // random num until I get db connection up
        this.name = name;
        this.type = type;
        this.desc = desc;
        this.SVGImage = SVGImage;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public UINode(){
        this.id = ran.nextInt(1000);
        this.name = "DEFAULT NAME";
        this.type = "DEFAULT TYPE";
        this.desc = "DEFAULT DESCRIPTION";
        this.SVGImage = "DEFAULT SVG";
        this.x = 0;
        this.y = 0;
        this.w = 3;
        this.h = 3;
    }

    public String toString() {
        return "Made a UINode with:" + "\n" +
                "name: "+name+ "\n"+
                "type: "+type+ "\n"+
                "desc: " + desc + "\n" +
                "SVG " + SVGImage + "\n" +
                "(x, y): " + "(" + x + ", " + y + ")" + "\n" +
                "(Width, Height): " + "(" + w + ", " + h + ")";
    }

    public String getIDAsJson(){
        return "{\"id\":\"" + id + "\"}";
    }
}
