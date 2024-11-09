package fr.uga.l3miage.pc.prisonersdilemma;


import fr.uga.l3miage.pc.prisonersdilemma.handlers.MyWebSocketHandler;
import fr.uga.l3miage.pc.prisonersdilemma.services.GameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.socket.WebSocketSession;

@SpringBootTest
public class WebSocketCreateGameEndpointTest {

    private MyWebSocketHandler myWebSocketHandler;
    private WebSocketSession mockSession;

    @BeforeEach
    void setUp() {
        myWebSocketHandler = new MyWebSocketHandler();
        mockSession = Mockito.mock(WebSocketSession.class);
    }

    @Test
    public void PayloadShouldStartWith_CREATE_GAME() throws Exception {
        myWebSocketHandler.handleGameCreation();
    }
}
