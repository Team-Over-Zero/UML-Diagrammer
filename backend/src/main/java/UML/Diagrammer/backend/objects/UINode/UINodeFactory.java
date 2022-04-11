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

public class UINodeFactory {
    public UINodeFactory(){}

    @SuppressWarnings("unchecked")
    public <genericNode extends UINode> genericNode
    buildNode(String type, int x, int y, int w, int h){
        switch(type){
            case "class_nodes" -> {return (genericNode) new UIClassNode(x, y, w, h);}
            case "text_box_nodes" -> {return (genericNode) new UITextBoxNode(x, y, w, h);}
            case "note_nodes" -> {return (genericNode) new UINoteNode(x, y, w, h);}
            case "folder_nodes" -> {return (genericNode) new UIFolderNode(x, y, w, h);}
            case "square_nodes" -> {return (genericNode) new UISquareNode(x, y, w, h);}
            case "stick_figure_nodes" -> {return (genericNode) new UIStickFigureNode(x, y, w, h);}
            case "oval_nodes" -> {return (genericNode) new UIOvalNode(x, y, w, h);}
            case "life_line_nodes" -> {return (genericNode) new UILifeLineNode(x, y, w, h);}
            case "loop_nodes" -> {return (genericNode) new UILoopNode(x, y, w, h);}
            default -> {return (genericNode) new UIDefaultNode();}
        }
    }

    @SuppressWarnings("unchecked")
    public <genericNode extends UINode> genericNode buildNode(){
        return (genericNode) new UIDefaultNode();
    }


}
