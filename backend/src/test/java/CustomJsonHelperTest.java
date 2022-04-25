
import UML.Diagrammer.backend.objects.NodeFactory.DefaultNode;
import UML.Diagrammer.backend.objects.NodeFactory.NodeFactory;
import UML.Diagrammer.backend.objects.tools.CustomJsonHelper;
import org.javalite.activejdbc.test.DBSpec;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CustomJsonHelperTest extends DBSpec {
    private CustomJsonHelper help;
    private NodeFactory nf;
    private DefaultNode dn;

    @BeforeEach
    public void setUp(){
        help = new CustomJsonHelper();
        nf = new NodeFactory();
        dn =nf.buildNode();
        dn.set("id",999);


    }

    /**
     * Test outputObjNodId
     */
    @Test
    public void testOutputObjNoId(){
        String s = help.outputObjNoId(dn.toJson(true));
        assertEquals("{\"description\":\"DEFAULT DESCRIPTION\",\"height\":3,\"name\":\"DEFAULT NAME\",\"svg_image\":\"DEFAULT IMAGE\",\"type\":\"defaultnodes\",\"width\":3,\"x_coord\":0,\"y_coord\":0}",help.outputObjNoId(dn.toJson(true)));
    }

    @Test
    public void testReplaceId(){
        String s = help.replaceId(111,dn.toJson(true));
        assertEquals("{\n" +
                "  \"description\":\"DEFAULT DESCRIPTION\",\n" +
                "  \"height\":3,\n" +
                "  \"id\":999,\n" +
                "  \"name\":\"DEFAULT NAME\",\n" +
                "  \"svg_image\":\"DEFAULT IMAGE\",\n" +
                "  \"type\":\"defaultnodes\",\n" +
                "  \"width\":3,\n" +
                "  \"x_coord\":0,\n" +
                "  \"y_coord\":0\n" +
                "}",help.replaceId(111,dn.toJson(true)));

    }

    @Test
    public void testGetObjId(){
        assertEquals("999",help.getObjId(dn.toJson(false)));
    }

    @Test
    public void testGetObjType(){
        assertEquals("defaultnodes",help.getObjType(dn.toJson(false)));
    }

    @Test
    public void testGetIterator(){
        assertNotNull(help.getIterator(dn.toJson(false)));
    }
}
