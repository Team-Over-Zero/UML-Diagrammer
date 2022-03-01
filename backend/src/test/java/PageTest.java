import UML.Diagrammer.backend.objects.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import static org.junit.jupiter.api.Assertions.*;

public class PageTest {
    @Test
    public void testPageAddNode(){
        Page page = new Page();
        DefaultNode node = new DefaultNode();
        page.addNode(node);
        assertNotNull(page);
    }
}
