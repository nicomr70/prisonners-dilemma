package fr.uga.l3miage.pc.prisonersdilemma.game.states;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Action;
import fr.uga.l3miage.pc.prisonersdilemma.enums.PlayerNumber;
import fr.uga.l3miage.pc.prisonersdilemma.game.Game;

public class CompletedState extends State {

    public CompletedState(Game game) {
        super(game);
    }

    @Override
    public void play(Action action, PlayerNumber playerNumber ) {
        throw new IllegalStateException("Game is completed");
    }

}
