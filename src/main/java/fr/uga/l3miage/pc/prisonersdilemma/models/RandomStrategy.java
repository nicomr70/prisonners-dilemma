package fr.uga.l3miage.pc.prisonersdilemma.models;

import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

public class RandomStrategy extends Strategy{
    @Override
    boolean play(List<Tour> history, int opponentPlayerNumber) {
        int k= new SecureRandom().nextInt(2)+1;
        return k==1;
    }
}
