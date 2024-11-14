package fr.uga.l3miage.pc.prisonersdilemma.controllers;

import fr.uga.l3miage.pc.prisonersdilemma.services.GameService;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

public class GameController {
    private static GameController instance = null;
    private GameService gameService = GameService.getInstance();

    private GameController(){}

    public static GameController getInstance(){
        if(instance == null){
            instance = new GameController();
        }
        return instance;
    }

    public void createGame(WebSocketSession session, String payload) throws IOException {
        gameService.createGame(session, payload);
    }

    public void joinGame(WebSocketSession session, String payload) throws IOException {
        gameService.joinGame(session, payload);
    }

    public void leaveGames(WebSocketSession session) {
        gameService.leaveGames(session);
    }

    public void action(WebSocketSession session, String payload) {
        gameService.action(session, payload);
    }
}
