import static org.junit.jupiter.api.Assertions.*;

import UML.Diagrammer.backend.apis.RequestController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
public class RequestControllerTest {
    private RequestController mockedController;
    @BeforeEach
    public void startUp(){
        RequestController mockedController = Mockito.mock(RequestController.class);
    }


}
