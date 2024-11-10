package fr.uga.l3miage.pc.prisonersdilemma.services;

import fr.uga.l3miage.pc.prisonersdilemma.Game;
import org.springframework.web.socket.TextMessage;
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

    public String  createGame(WebSocketSession session, String payload) throws IOException {
        int maxTurns;

        if(!payload.startsWith("CREATE_GAME")) {
            throw new IOException("payload does not start with CREATE_GAME for create game request");
        }

        try{
            maxTurns = getMaxTurns(payload);
        } catch (Exception e) {
            throw new IOException("maxTurns not found in payload or not an integer");
        }

        Game game = new Game(maxTurns);
        String gameId = game.getId();

        addPlayerToGame(session, gameId);

        sendGameIdToPlayer(session, gameId);

        return gameId;
    }

    private int getMaxTurns(String payload){
        String maxTurnsInStringFormat = getMaxTurnsFromPayload(payload);
        return maxTurnsFromString(maxTurnsInStringFormat);
    }

    private int maxTurnsFromString(String maxTurnsStringFormat) {
        return Integer.parseInt(maxTurnsStringFormat);
    }

    private String getMaxTurnsFromPayload(String payload) {
        return payload.split(":")[1];
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

    private static void sendGameIdToPlayer(WebSocketSession session, String gameId) throws IOException {
        session.sendMessage(new TextMessage("GAME_ID:" + gameId));
    }
}
