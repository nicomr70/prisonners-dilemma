package fr.uga.l3miage.pc.prisonersdilemma.entities;

import java.util.UUID;

public class GameCreationDTO {

    private int rounds;
    private UUID gameId;
    private UUID playerId;
    private String playerName;
    private String playerDecision;

    public int getRounds() {
        return rounds;
    }

    public void setRounds(int rounds) {
        this.rounds = rounds;
    }

    public UUID getGameId() {
        return gameId;
    }

    public void setGameId(UUID gameId) {
        this.gameId = gameId;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public void setPlayerId(UUID playerId) {
        this.playerId = playerId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerDecision() {
        return playerDecision;
    }

    public void setPlayerDecision(String playerDecision) {
        this.playerDecision = playerDecision;
    }
}
