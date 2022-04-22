package UML.Diagrammer.backend.objects;

import com.google.gson.Gson;
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

    //returns something like: {"id":"1","name":"brandNewUser","password":"brandnewpassword"}
    public String getFullUserString(String pw){
        return "{\"id\":\"" + id + "\"" +
                ",\"name\":\"" + name + "\"" +
                ",\"password\":\"" + pw + "\"}";
    }
}
