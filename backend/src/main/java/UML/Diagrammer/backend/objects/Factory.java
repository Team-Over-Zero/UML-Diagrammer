package UML.Diagrammer.backend.objects;

import java.lang.reflect.Type;

public class Factory {

    public Factory(){}

    public Node createSmallNode(Type nodeType,String name, int xCoord, int yCoord){
        if(nodeType.equals(DefaultNode.class)){
            int width = 2;
            int height = 2;
            String description = "Small Node of name: "+name + " Generated By Factory. Width is: "+width +" and height is: "+ height;
            return new DefaultNode(name,description,xCoord,yCoord,2,2);
        }

       return new DefaultNode();
    }
}
