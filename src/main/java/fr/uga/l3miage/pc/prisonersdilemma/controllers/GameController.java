package fr.uga.l3miage.pc.prisonersdilemma.controllers;

import fr.uga.l3miage.pc.prisonersdilemma.usecases.Game;
import fr.uga.l3miage.pc.prisonersdilemma.utils.ApiResponse;
import fr.uga.l3miage.pc.prisonersdilemma.utils.GlobalGameMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static fr.uga.l3miage.pc.prisonersdilemma.utils.Type.*;

public class GameController {

    private static final Logger log = LoggerFactory.getLogger(GameController.class);

    //@Autowired
    //private MappingJackson2MessageConverter messageConverter;

    // Endpoint pour créer une nouvelle partie
    
    public static ApiResponse<Game> createGame(int rounds, String player1Name, WebSocketSession player1Session) {
        try {
            log.info("connection successfully established " + player1Name);
            Game game = new Game(rounds, player1Name, player1Session);
            GlobalGameMap gameMap = GlobalGameMap.getInstance();
            gameMap.putElement(game.getGameId(), game);
            ApiResponse<Game> response = new ApiResponse<>(200, "OK", joinGame ,game);
            GameController.sendToClient(player1Session, response);
            return response;
        } catch (Exception e) {
            // En cas d'erreur, retourner un statut 500 (Internal Server Error)
            ApiResponse<Game> response = new ApiResponse<>(500, "Internal Server Error", createGame,null);
            GameController.sendToClient(player1Session, response);
            return response;
        }
        //Player 1 doit récupérer son UUID
    }

    
    public static ApiResponse<Game> joinGame( UUID gameId,  String player2Name, WebSocketSession player2Session ) {
        try {
            ApiResponse<Game> response = findTheRightGame(gameId).joinGame(player2Name, player2Session);
            GameController.sendToClient(player2Session, response);
            return response;
        } catch (Exception e) {
            // En cas d'erreur, retourner un statut 500 (Internal Server Error)
            ApiResponse<Game> response = new ApiResponse<>(500, "Internal Server Error", joinGame,null);
            GameController.sendToClient(player2Session, response);
            return response;
        }
        //Player 2 doit récupérer son UUID
    }

    // Endpoint pour envoyer la décision des joueurs
    
    public static ApiResponse<Game> playGame( UUID gameId,  UUID playerId,  String decision, WebSocketSession playerSession ) {
        try {
            ApiResponse<Game> response = findTheRightGame(gameId).playGame(playerId, decision);
            GameController.sendToClient(playerSession, response);
            return response;
        } catch (Exception e) {
            // En cas d'erreur, retourner un statut 500 (Internal Server Error)
            ApiResponse<Game> response = new ApiResponse<>(500, "Internal Server Error", getGamesList,null);
            GameController.sendToClient(playerSession, response);
            return response;
        }
    }

    // Endpoint pour envoyer la décision des joueurs
    
    @SendTo("/notify")
    public static ApiResponse<Game> getGameState( UUID gameId,  UUID playerId, WebSocketSession playerSession ) {
        try {
            ApiResponse<Game> response = findTheRightGame(gameId).getGameState(playerId);
            GameController.sendToClient(playerSession, response);
            return response;
        } catch (Exception e) {
            // En cas d'erreur, retourner un statut 500 (Internal Server Error)
            ApiResponse<Game> response = new ApiResponse<>(500, "Internal Server Error", getGameState,null);
            GameController.sendToClient(playerSession, response);
            return response;
        }

    }

    // Endpoint pour obtenir les résultats de la partie
    
    public static ApiResponse<String> getResults( UUID gameId,  UUID playerId) {
        try {
            //TODO
            return findTheRightGame(gameId).displayResults();
        } catch (Exception e) {
            // En cas d'erreur, retourner un statut 500 (Internal Server Error)
            ApiResponse<String> response = new ApiResponse<>(500, "Internal Server Error", getResults, null);
            return response;
        }
    }

    // Endpoint pour envoyer la décision des joueurs
    
    //@PostMapping("/{gameId}/giveup")
    public static ApiResponse<Game> giveUpGame( UUID gameId,  UUID playerId, String strategyToApply, WebSocketSession playerSession) {
        try {
            ApiResponse<Game> response = findTheRightGame(gameId).giveUpGame(playerId, strategyToApply);
            GameController.sendToClient(playerSession, response);
            return response;
        } catch (Exception e) {
            // En cas d'erreur, retourner un statut 500 (Internal Server Error)
            ApiResponse<Game> response = new ApiResponse<>(500, "Internal Server Error", giveUpGame, null);
            GameController.sendToClient(playerSession, response);
            return response;
        }
    }

    
    //@GetMapping("/gamelist")
    public static ApiResponse<List<UUID>> getGamesList() {
        try {
            GlobalGameMap gameMap = GlobalGameMap.getInstance();

            List<UUID> availableGames = new ArrayList<>();

            for (Map.Entry<UUID, Game> entry : gameMap.getMap().entrySet()) {
                if (entry.getValue().isAvailableToJoin()) {
                    availableGames.add(entry.getKey());
                }
            }
            ApiResponse<List<UUID>> response = new ApiResponse<>(200, "OK", getGamesList, availableGames);
            return response;
        } catch (Exception e) {
            // En cas d'erreur, retourner un statut 500 (Internal Server Error)
            ApiResponse<List<UUID>> response = new ApiResponse<>(500, "Internal Server Error", getGamesList, null);
            return response;
        }
    }
    
    //@PostMapping("/{gameId}/endgame")
    public ApiResponse<Void> endGame( UUID gameId,  UUID playerId) {
        return null;
    }

    public static void sendToClient(WebSocketSession clientSession, Object message) {
        if (clientSession.isOpen()) {
            try {
                clientSession.sendMessage(new TextMessage(message.toString()));
            } catch (IOException e) {
                log.error("Erreur lors de la diffusion du message : " + e.getMessage());
                //TODO gestion des messages non envoyés
            }
        }
    }

    private static Game findTheRightGame(UUID gameId) {
        GlobalGameMap gameMap = GlobalGameMap.getInstance();
        return gameMap.getElement(gameId);
    }
}
