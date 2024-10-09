package fr.uga.l3miage.pc.prisonersdilemma.services;

import fr.uga.l3miage.pc.prisonersdilemma.utils.Decision;

public interface Strategy {
    Decision nextMove();
}

