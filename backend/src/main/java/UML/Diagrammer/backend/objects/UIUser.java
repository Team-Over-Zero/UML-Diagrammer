package UML.Diagrammer.backend.objects;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UIUser {
    int id;
    String name;

    public UIUser(int id, String name){
        this.id = id;
        this.name = name;
    }

    public UIUser(){
        this.id = -1;
        this.name = "Default Name";
    }

    public String getIDAsJson(){
        return "{\"id\":\"" + id + "\"}";
    }
}
