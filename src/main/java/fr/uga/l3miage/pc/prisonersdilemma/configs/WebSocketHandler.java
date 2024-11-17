package fr.uga.l3miage.pc.prisonersdilemma.configs;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.uga.l3miage.pc.prisonersdilemma.services.Commands.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;

import static fr.uga.l3miage.pc.prisonersdilemma.utils.Type.*;

public class WebSocketHandler extends TextWebSocketHandler {

    private static final Logger log = LoggerFactory.getLogger(WebSocketHandler.class);

    private final ObjectMapper objectMapper = new ObjectMapper();
    // Ensemble des sessions de WebSocket
    //private static final Set<WebSocketSession> sessions = ConcurrentHashMap.newKeySet();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        //sessions.add(session);
        System.out.println("Client connect� : " + session.getId());

        // Envoyer un message de bienvenue au nouveau client
        try {
            session.sendMessage(new TextMessage("Bienvenue ! Vous �tes connect� en tant que client " + session.getId()));
        } catch (IOException e) {
            System.err.println("Erreur lors de l'envoi du message de bienvenue : " + e.getMessage());
        }

        // Notifier les autres clients de la nouvelle connexion
        //broadcastMessage("Nouveau client connect� : " + session.getId(), session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        //sessions.remove(session);
        log.info("Client d�connect� : " + session.getId());

        // Notifier les autres clients de la d�connexion
        //broadcastMessage("Client d�connect� : " + session.getId(), session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        // Message re�u du client
        log.info("Message re�u de " + session.getId() + " : " + message.getPayload());

        JsonNode jsonMessage = objectMapper.readTree(message.getPayload());
        String messageType = jsonMessage.get("type").asText();

        // Obtenir l'action � ex�cuter en fonction du type de message
        ActionGame actInGame = this.getCommand(messageType, session);

        if (actInGame != null) {
            // Ex�cuter la commande
            actInGame.execute(session, objectMapper, message);
        } else {
            // En cas de type inconnu, envoyer un message d'erreur
            //TODO changer le format de la r�ponse en ApiResponse
            session.sendMessage(new TextMessage("Type de message inconnu"));
        }


        // Diffuser le message re�u � tous les clients connect�s
        //broadcastMessage("Message de " + session.getId() + " : " + message.getPayload(), null);
    }

    // M�thode pour diffuser un message � tous les clients, sauf � l'exp�diteur (si pr�cis�)
    /*private void broadcastMessage(String message, WebSocketSession excludeSession) {
        for (WebSocketSession clientSession : sessions) {
            if (clientSession.isOpen() && (excludeSession == null || !clientSession.equals(excludeSession))) {
                try {
                    clientSession.sendMessage(new TextMessage(message));
                } catch (IOException e) {
                    System.err.println("Erreur lors de la diffusion du message : " + e.getMessage());
                }
            }
        }
    }*/


    public static ActionGame getCommand(String messageType, WebSocketSession session) {

        switch (messageType) {
            case createGame:
                return new CreateGame();
            case joinGame:
                return new JoinGame();
            case playGame:
                return new PlayGame();
            case getGameState:
                return new GetGameState();
            case getResults:
                return new GetResults();
            case giveUpGame:
                return new GiveUpGame();
            // Ajouter d'autres types de messages selon les besoins
            default:
                log.error("Type de message inconnus");
                return null;
        }
    }

}