/** EdgeTypeDeserializer.java
 *
 * Ripped straight from: https://www.baeldung.com/gson-list
 *
 * DEPRECATED. USING A DIFFERENT DESERIALIZATION STANDARD
 *
 * @author Alex
 */


package UML.Diagrammer.backend.objects.tools;

import UML.Diagrammer.backend.objects.AbstractEdge;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class EdgeTypeDeserializer implements JsonDeserializer<AbstractEdge> {

        private String edgeTypeElementName;
        private Gson gson;
        private Map<String, Class<? extends AbstractEdge>> edgeTypeRegistry;

        public EdgeTypeDeserializer(String edgeTypeElementName) {
            this.edgeTypeElementName = edgeTypeElementName;
            this.gson = new Gson();
            this.edgeTypeRegistry = new HashMap<>();
        }

        public void registerSubtype(String edgeTypeName, Class<? extends AbstractEdge> edgeType) {
            edgeTypeRegistry.put(edgeTypeName, edgeType);
        }

        public AbstractEdge deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
            JsonObject edgeObject = json.getAsJsonObject();
            JsonElement edgeTypeElement = edgeObject.get(edgeTypeElementName);

            Class<? extends AbstractEdge> edgeType = edgeTypeRegistry.get(edgeTypeElement.getAsString());
            return gson.fromJson(edgeObject, edgeType);
        }
    }

