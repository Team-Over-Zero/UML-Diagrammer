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

package UML.Diagrammer.backend.objects.UIEdge;

import UML.Diagrammer.backend.objects.UINode.UINode;
import UML.Diagrammer.backend.objects.UINode.UINodeFactory;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class UIEdge {
    UINode n1;
    UINode n2;
    int id;

    public <genericNode extends UINode>UIEdge(genericNode n1, genericNode n2) {
        this.id = -1;
        this.n1 = n1;
        this.n2 = n2;
    }

    public UIEdge(){
        this.id = -1;
        UINodeFactory NFac = new UINodeFactory();
        UINode n1 = NFac.buildNode();
        UINode n2 = NFac.buildNode();
        this.n1 = n1;
        this.n2 = n2;
    }

    public String toString(){
        return "Edge has attributes:" + "\n" +
                "ID: " + this.id + "\n" +
                "Node 1 ID: " + n1.getId() + "\n" +
                "Node 2 ID: " + n2.getId() + "\n" +
                "Node 1 Type: "+ n1.getType() + "\n" +
                "Node 2 Type: "+ n2.getType();
    }

}
