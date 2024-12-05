package fr.uga.l3miage.pc.prisonersdilemma.StrategiesTest;

import fr.uga.l3miage.pc.prisonersdilemma.strategies.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.SecureRandom;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
public class StrategyFactoryTest {
    private SecureRandom mockRandom;

    @BeforeEach
    void setUp(){
        mockRandom = Mockito.mock(SecureRandom.class);
        StrategyFactory.setRandom(mockRandom);
    }
    @Test
    void testReturnStrategy1(){
        when(mockRandom.nextInt(18)).thenReturn(0);
        Strategy strategy = StrategyFactory.createRandomStrategy();
        assertTrue(strategy instanceof AlwaysBetray, "Factory should return an instance of AlwaysBetray.");
    }


    @Test
    public void testCreateRandomStrategy_Adaptive() {
        when(mockRandom.nextInt(18)).thenReturn(12); // Simuler le choix Adaptive

        Strategy strategy = StrategyFactory.createRandomStrategy();
        assertInstanceOf(Adaptive.class, strategy, "Factory should return an instance of Adaptive.");
    }

    @Test
    public void testCreateRandomStrategy_PavlovRandom() {
        when(mockRandom.nextInt(18)).thenReturn(15); // Simuler le choix PavlovRandom

        Strategy strategy = StrategyFactory.createRandomStrategy();
        assertInstanceOf(PavlovRandom.class, strategy, "Factory should return an instance of PavlovRandom.");
    }

    @Test
    public void testAllStrategyIndices() {
        // Test tous les indices possibles
        for (int i = 0; i < 18; i++) {
            when(mockRandom.nextInt(18)).thenReturn(i);

            Strategy strategy = StrategyFactory.createRandomStrategy();

            switch (i) {
                case 0 -> assertInstanceOf(AlwaysBetray.class, strategy, "Expected AlwaysBetray.");
                case 1 -> assertInstanceOf(AlwaysCooperate.class, strategy, "Expected AlwaysCooperate.");
                case 2 -> assertInstanceOf(NaivePeacemaker.class, strategy, "Expected NaivePeacemaker.");
                case 3 -> assertInstanceOf(Peacemaker.class, strategy, "Expected Peacemaker.");
                case 4 -> assertInstanceOf(PollsterRandomBetray.class, strategy, "Expected PollsterRandomBetray.");
                case 5 -> assertInstanceOf(RandomStrategy.class, strategy, "Expected RandomStrategy.");
                case 6 -> assertInstanceOf(ResentfulStrategy.class, strategy, "Expected ResentfulStrategy.");
                case 7 -> assertInstanceOf(TitforTat.class, strategy, "Expected TitforTat.");
                case 8 -> assertInstanceOf(TitforTatRandom.class, strategy, "Expected TitforTatRandom.");
                case 9 -> assertInstanceOf(TitforTwoTats.class, strategy, "Expected TitforTwoTats.");
                case 10 -> assertInstanceOf(TitforTwoTatsRandom.class, strategy, "Expected TitforTwoTatsRandom.");
                case 11 -> assertInstanceOf(RepentantPollster.class, strategy, "Expected RepentantPollster.");
                case 12 -> assertInstanceOf(Adaptive.class, strategy, "Expected Adaptive.");
                case 13 -> assertInstanceOf(GradualStrategy.class, strategy, "Expected GradualStrategy.");
                case 14 -> assertInstanceOf(Pavlov.class, strategy, "Expected Pavlov.");
                case 15 -> assertInstanceOf(PavlovRandom.class, strategy, "Expected PavlovRandom.");
                case 16 -> assertInstanceOf(SoftResentful.class, strategy, "Expected SoftResentful.");
                case 17 -> assertInstanceOf(TitForTatSuspicious.class, strategy, "Expected TitForTatSuspicious.");
                default -> throw new IllegalStateException("Unexpected index: " + i);
            }
        }
    }
}
