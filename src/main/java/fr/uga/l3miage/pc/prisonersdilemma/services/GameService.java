package fr.uga.l3miage.pc.prisonersdilemma.services;

import fr.uga.l3miage.pc.prisonersdilemma.Game;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class GameService {

    private static GameService instance = null ;

    private final Map<String, Set<WebSocketSession>> currentGames = new ConcurrentHashMap<>();

    private GameService() {}

    public static GameService getInstance() {
        if(instance == null) {
            GameService.instance= new GameService();
        }
        return instance;
    }

    public static Map<String, Set<WebSocketSession>> getCurrentGames() throws IOException {
        return GameService.getInstance().currentGames;
    }

    public String createGame(WebSocketSession session) {
        Game game = new Game(5);
        String gameId = game.getId();
        addPlayerToGame(session, gameId);
        return gameId;
    }

    public boolean joinGame(WebSocketSession session, String gameId) {

        if(isGameFull(gameId) || !doesGameExists(gameId)) {
            return false;
        }

        addPlayerToGame(session, gameId);
        return true;
    }

    private boolean isGameFull(String gameId) {
        Set<WebSocketSession> game = currentGames.get(gameId);
        return game.size() >= 2;
    }

    private boolean doesGameExists(String gameId) {
        return currentGames.get(gameId) != null;
    }

    public Set<WebSocketSession> getPlayers(String gameId) {
        return currentGames.getOrDefault(gameId, Set.of());
    }

    public void removePlayer(WebSocketSession session) {
        currentGames.values().forEach(game -> game.remove(session));
    }

    private void addPlayerToGame(WebSocketSession session, String gameId) {
        currentGames.putIfAbsent(gameId, ConcurrentHashMap.newKeySet());
        currentGames.get(gameId).add(session);
    }
}
