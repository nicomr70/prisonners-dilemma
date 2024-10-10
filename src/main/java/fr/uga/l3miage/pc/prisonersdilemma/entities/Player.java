package fr.uga.l3miage.pc.prisonersdilemma.entities;

import fr.uga.l3miage.pc.prisonersdilemma.services.Strategy;
import fr.uga.l3miage.pc.prisonersdilemma.utils.Decision;

import java.util.UUID;

public class Player {
    private String name;
    private int score = 0;
    private boolean isConnected;
    private Strategy strategy;
    private UUID playerId;
    private Decision actualRoundDecision;

    public Player(String name) {
        this.playerId = UUID.randomUUID();
        this.name = name;
        this.isConnected = true;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean getConnected() {
        return isConnected;
    }

    public void setConnected(boolean connected) {
        isConnected = connected;
    }

    public Strategy getStrategy() {
        return strategy;
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public void setPlayerId(UUID playerId) {
        this.playerId = playerId;
    }

    public Decision getActualRoundDecision() {
        return actualRoundDecision;
    }

    public void setActualRoundDecision(Decision actualRoundDecision) {
        this.actualRoundDecision = actualRoundDecision;
    }

    public Decision strategyAutomatedPlay(Decision opponentLastMove) throws Exception {
        if (!this.isConnected && this.strategy != null) {
            return this.strategy.nextMove(opponentLastMove);
        } else {
            throw new Exception("Manifestement une erreur s'est produite !");
        }
    }

    public void giveUp(Strategy strategyAfterPlayerDeparture) {
        this.strategy = strategyAfterPlayerDeparture;
        this.isConnected = false;
    }

    public void updateScore(int points) {
        this.score =+ points ;
    }
}
