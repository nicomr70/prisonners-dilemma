package fr.uga.l3miage.pc.prisonersdilemma.services.Commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

public interface ActionGame {
    void execute(WebSocketSession session, ObjectMapper objectMapper, TextMessage message) throws IOException;
}
