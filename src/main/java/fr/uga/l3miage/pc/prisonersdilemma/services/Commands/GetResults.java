package fr.uga.l3miage.pc.prisonersdilemma.services.Commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.uga.l3miage.pc.prisonersdilemma.controllers.GameController;
import fr.uga.l3miage.pc.prisonersdilemma.entities.GameCreationDTO;
import fr.uga.l3miage.pc.prisonersdilemma.utils.ApiResponse;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

public class GetResults implements ActionGame {
    @Override
    public void execute(WebSocketSession session, ObjectMapper objectMapper, TextMessage message) throws IOException {
        ApiResponse<GameCreationDTO> apiGetResultsResponse = objectMapper.readValue(message.getPayload(), ApiResponse.class);

        GameController.getResults(apiGetResultsResponse.getData().getGameId(), apiGetResultsResponse.getData().getPlayerId());
    }
}
