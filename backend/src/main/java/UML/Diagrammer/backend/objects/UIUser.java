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
        Gson gson = new Gson();
        String jsonStr = gson.toJson(this);
        //return jsonStr;
        return "{\"id\":\"" + id + "\"}";
    }
}
