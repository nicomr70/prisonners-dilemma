package fr.uga.l3miage.pc.prisonersdilemma.services;

import fr.uga.l3miage.pc.prisonersdilemma.utils.Decision;

public interface Strategy {

     Decision nextMove(Decision precMove);

}


/*public class Strategies {

    private Random random = new Random();




    // Stratégie 3: Donnant pour deux donnants et aléatoire
    public Decision titForTwoTatsRandom(Decision lastOpponentMove, Decision lastButOneOpponentMove) {
        if (random.nextDouble() < 0.1) {
            return randomMove();
        }
        return (lastOpponentMove == lastButOneOpponentMove) ? lastOpponentMove : Decision.COOPERATE;
    }

    // Stratégie 4: Donnant pour deux donnants
    public Decision titForTwoTats(Decision lastOpponentMove, Decision lastButOneOpponentMove) {
        return (lastOpponentMove == lastButOneOpponentMove) ? lastOpponentMove : Decision.COOPERATE;
    }

    // Stratégie 5: Sondeur naïf
    public Decision naiveProber(Decision lastOpponentMove) {
        return random.nextDouble() < 0.1 ? Decision.DEFECT : lastOpponentMove;
    }

    // Stratégie 6: Sondeur repentant
    public Decision remorsefulProber(Decision lastOpponentMove, Decision myLastMove) {
        if (random.nextDouble() < 0.1) {
            return Decision.DEFECT;
        }
        if (lastOpponentMove == Decision.DEFECT && myLastMove == Decision.DEFECT) {
            return Decision.COOPERATE;
        }
        return lastOpponentMove;
    }

    // Stratégie 7: Pacificateur naïf
    public Decision naivePeacemaker(Decision lastOpponentMove) {
        return random.nextDouble() < 0.1 ? Decision.COOPERATE : lastOpponentMove;
    }

    // Stratégie 8: Vrai pacificateur
    public Decision truePeacemaker(Decision lastOpponentMove, Decision lastButOneOpponentMove) {
        if (lastOpponentMove == Decision.DEFECT && lastButOneOpponentMove == Decision.DEFECT) {
            return random.nextDouble() < 0.1 ? Decision.COOPERATE : Decision.DEFECT;
        }
        return Decision.COOPERATE;
    }

    // Stratégie 9: Aléatoire - Trahir ou coopérer avec une probabilité de 50%
    public Decision randomStrategy() {
        return randomMove();
    }


    // Stratégie 12: Rancunier - Coopère jusqu'à la première trahison, puis toujours trahir
    public Decision grimTrigger(Decision lastOpponentMove, boolean opponentHasBetrayedBefore) {
        if (opponentHasBetrayedBefore || lastOpponentMove == Decision.DEFECT) {
            return Decision.DEFECT;
        }
        return Decision.COOPERATE;
    }

    // Stratégie 13: Pavlov - Si 5 ou 3 points obtenus au tour précédent, répéter le dernier choix
    public Decision pavlov(int lastScore, Decision myLastMove) {
        if (lastScore == 3 || lastScore == 5) {
            return myLastMove;
        }
        return myLastMove == Decision.COOPERATE ? Decision.DEFECT : Decision.COOPERATE;
    }

    // Stratégie 14: Pavlov / Aléatoire
    public Decision pavlovRandom(int lastScore, Decision myLastMove) {
        if (random.nextDouble() < 0.1) {
            return randomMove();
        }
        return pavlov(lastScore, myLastMove);
    }

    // Stratégie 15: Adaptatif - Choisir la stratégie donnant le meilleur score
    public Decision adaptive(Decision[] myMoves, int[] scores) {
        int cooperateScore = 0;
        int defectScore = 0;

        for (int i = 0; i < myMoves.length; i++) {
            if (myMoves[i] == Decision.COOPERATE) {
                cooperateScore += scores[i];
            } else {
                defectScore += scores[i];
            }
        }

        return (cooperateScore > defectScore) ? Decision.COOPERATE : Decision.DEFECT;
    }

    // Helper method to return a random move
    private Decision randomMove() {
        return random.nextBoolean() ? Decision.COOPERATE : Decision.DEFECT;
    }

    // Enumeration for the decisions
    public enum Decision {
        COOPERATE, DEFECT
    }
}*/
