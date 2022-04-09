package UML.Diagrammer.backend.objects;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UIUser {
    int id;

    UIUser(int id){
        this.id = id;
    }

    UIUser(){
        this.id = -1;
    }

    public String getIDAsJson(){
        return "{\"id\":\"" + id + "\"}";
    }
}
