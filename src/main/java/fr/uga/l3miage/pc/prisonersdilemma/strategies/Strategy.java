package fr.uga.l3miage.pc.prisonersdilemma.strategies;
import fr.uga.l3miage.pc.prisonersdilemma.game.Game;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Action;

public interface Strategy {

    public Action play(Game game);
}
