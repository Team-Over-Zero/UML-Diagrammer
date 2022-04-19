import static org.junit.jupiter.api.Assertions.*;

import UML.Diagrammer.backend.apis.Database_Client;
import UML.Diagrammer.backend.apis.RequestController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
public class RequestControllerTest {
    private Database_Client db;
    @BeforeEach
    public void startUp(){
       db = new Database_Client();
    }




}
