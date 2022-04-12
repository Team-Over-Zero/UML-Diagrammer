package UML.Diagrammer.backend.objects;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UIPage {
    int id;
    String name;

    public UIPage(int id, String name){
        this.id = id;
        this.name = name;
    }
    public UIPage(){
        this.id = -1;
        this.name = "DEFAULT NAME";
    }

    public String getPageIDAsJSon(){
        return "{\"pageid\":\"" + id + "\"}";
    }
}
