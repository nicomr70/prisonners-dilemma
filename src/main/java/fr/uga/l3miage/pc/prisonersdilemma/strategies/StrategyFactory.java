package fr.uga.l3miage.pc.prisonersdilemma.strategies;

import java.security.SecureRandom;

public class StrategyFactory {
    private static final SecureRandom random = new SecureRandom();

    public static Strategy createRandomStrategy() {
        int strategyIndex = random.nextInt(12); // Nombre de stratégies disponibles

        switch (strategyIndex) {
            case 0:
                return new AlwaysBetray();
            case 1:
                return new AlwaysCooperate();
            case 2:
                return new NaivePeacemaker(random);
            case 3:
                return new Peacemaker(random);
            case 4:
                return new PollsterRandomBetray(random);
            case 5:
                return new RandomStrategy(random);
            case 6:
                return new ResentfulStrategy();
            case 7:
                return new TitforTat();
            case 8:
                return new TitforTatRandom(random);
            case 9:
                return new TitforTwoTats();
            case 10:
                return new TitforTwoTatsRandom(random);
            case 11:
                return new RepentantPollster(random);
            default:
                throw new IllegalStateException("Unexpected value: " + strategyIndex);
        }
    }
}
