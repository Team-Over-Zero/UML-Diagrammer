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

