/**CustomJsonHelper.java
 *
 * This object is a custom json helper that will allow UML-Diagrammer objects to be be somewhat editable.
 * This is generalizable so I guess it is something of a visitor for our UML objects.
 *
 * @Author Alex
 */


package UML.Diagrammer.backend.objects.tools;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class CustomJsonHelper {

    public CustomJsonHelper(){

    }

    /**
     *
     * @param jsonStr Given a json object with or without an id attribute, removes the id attribute altogether and returns it.
     * @return
     */
    public String outputObjNoId(String jsonStr){
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gsonHelper = gsonBuilder.create();
        JsonObject jsonObject = gsonHelper.fromJson(jsonStr, JsonObject.class);
        Set<Map.Entry<String, JsonElement>> objectSet = jsonObject.entrySet();
        Iterator<Map.Entry<String,JsonElement>> setIterator = objectSet.iterator();

        //looks for an ID attribute and removes it if found.
        while(setIterator.hasNext()){
            Map.Entry<String,JsonElement> currentObj = setIterator.next();
           //System.out.println(currentObj.getKey());
            if(currentObj.getKey().equals("id")){
                //System.out.println(currentObj.getValue());
                setIterator.remove();
            }
        }
        JsonObject strippedJson = new JsonObject();
        for (Map.Entry<String,JsonElement> entry: objectSet) {
            strippedJson.add(entry.getKey(),entry.getValue()); //adds a kvp of strings (attributes) and json elements (values) to a json object.
        }
       String retStr =  strippedJson.toString();

        return retStr;
    }

    /**
     *
     * @param  id integer id to set the object id to.
     * @param jsonStr object with or without an id.
     * @return an object with an id attribute equal to the passed in object.
     */
    public String replaceId(int id,String jsonStr){
        String strippedStr = outputObjNoId(jsonStr); //removes id attribute
        Gson gson = new Gson();
        JsonObject jsonObj  = gson.fromJson(strippedStr,JsonObject.class);
        jsonObj.addProperty("id",id); //re adds id attribute and sets it to the passed in id.
        return jsonStr;
    }

    public Iterator<Map.Entry<String,JsonElement>> getIterator(String jsonStr){
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        JsonObject jsonObject = gson.fromJson(jsonStr,JsonObject.class);
        Iterator<Map.Entry<String,JsonElement>> objectIterator = jsonObject.entrySet().iterator();
        return  objectIterator;

    }





}
