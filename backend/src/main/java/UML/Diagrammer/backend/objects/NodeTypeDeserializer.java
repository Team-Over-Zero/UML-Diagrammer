/**NodeTypeDeserializer.java
 *
 * Ripped straight from: https://www.baeldung.com/gson-list
 *
 * @author Alex
 */



package UML.Diagrammer.backend.objects;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class NodeTypeDeserializer implements JsonDeserializer<AbstractNode> {

        private String nodeTypeElementName;
        private Gson gson;
        private Map<String, Class<? extends AbstractNode>> nodeTypeRegistry;

        public NodeTypeDeserializer(String nodeTypeElementName) {
            this.nodeTypeElementName = nodeTypeElementName;
            this.gson = new Gson();
            this.nodeTypeRegistry = new HashMap<>();
        }

        public void registerSubtype(String nodeTypeName, Class<? extends AbstractNode> nodeType) {
            nodeTypeRegistry.put(nodeTypeName, nodeType);
        }

        public AbstractNode deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
            JsonObject nodeObject = json.getAsJsonObject();
            JsonElement nodeTypeElement = nodeObject.get(nodeTypeElementName);

            Class<? extends AbstractNode> nodeType = nodeTypeRegistry.get(nodeTypeElement.getAsString());
            return gson.fromJson(nodeObject, nodeType);
        }
    }

