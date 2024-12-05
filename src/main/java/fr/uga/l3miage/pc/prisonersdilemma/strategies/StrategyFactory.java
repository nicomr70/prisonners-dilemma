package fr.uga.l3miage.pc.prisonersdilemma.strategies;

import lombok.Setter;

import java.security.SecureRandom;

public class StrategyFactory {
    @Setter
    private static SecureRandom random = new SecureRandom();

    public static Strategy createRandomStrategy() {
        int strategyIndex = random.nextInt(18);

        return switch (strategyIndex) {
            case 0 -> new AlwaysBetray();
            case 1 -> new AlwaysCooperate();
            case 2 -> new NaivePeacemaker(random);
            case 3 -> new Peacemaker(random);
            case 4 -> new PollsterRandomBetray(random);
            case 5 -> new RandomStrategy(random);
            case 6 -> new ResentfulStrategy();
            case 7 -> new TitforTat();
            case 8 -> new TitforTatRandom(random);
            case 9 -> new TitforTwoTats();
            case 10 -> new TitforTwoTatsRandom(random);
            case 11 -> new RepentantPollster(random);
            case 12 -> new Adaptive();
            case 13 -> new GradualStrategy();
            case 14 -> new Pavlov();
            case 15 -> new PavlovRandom(random);
            case 16 -> new SoftResentful();
            case 17 -> new TitForTatSuspicious();
            default -> throw new IllegalStateException("Unexpected value: " + strategyIndex);
        };
    }
}
