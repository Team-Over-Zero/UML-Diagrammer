import UML.Diagrammer.backend.objects.UIUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UIUserTest {
    private UIUser user;

    @BeforeEach
    public void setUp(){
        user = new UIUser(99,"Test");
    }

    @Test
    public void testGetID(){
        assertEquals(99,user.getId());
    }

    @Test
    public void testGetName(){
        assertEquals("Test",user.getName());

    }

    @Test
    public void testLombok(){
        user.setId(0);
        user.setName("no");
        assertEquals(0,user.getId());
        assertEquals("no",user.getName());
    }

    @Test
    public void testGetIDAsJson(){
        String s = user.getIDAsJson();
        assertEquals("{\"id\":\"" + "99"+ "\"}",s);
    }

    @Test
    public void testDefaultValues(){
        UIUser u = new UIUser();
        assertEquals(-1,u.getId());
        assertEquals("Default Name",u.getName());
    }



}
