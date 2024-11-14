package fr.uga.l3miage.pc.prisonersdilemma.game.states;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Action;
import fr.uga.l3miage.pc.prisonersdilemma.enums.PlayerNumber;
import fr.uga.l3miage.pc.prisonersdilemma.game.Game;

public abstract class State {
    Game game;

    State(Game game) {
        this.game = game;
    }

    public abstract void getNextState();
    public abstract void play(Action action, PlayerNumber playerNumber);

}
