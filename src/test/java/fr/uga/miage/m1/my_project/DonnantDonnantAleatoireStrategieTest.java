package fr.uga.miage.m1.my_project;
import fr.uga.miage.m1.my_project.Enums.TypeAction;
import fr.uga.miage.m1.my_project.Strategies.DonnantDonnantAleatoireStrategie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DonnantDonnantAleatoireStrategieTest {

    private DonnantDonnantAleatoireStrategie strategie;

    @BeforeEach
    void setUp() {
        strategie = new DonnantDonnantAleatoireStrategie();
    }

    @Test
    void testRandomOrLastAction() {
        List<TypeAction> actions = new ArrayList<>();
        actions.add(TypeAction.COOPERER);
        actions.add(TypeAction.TRAHIR);

        TypeAction result = strategie.getAction(actions, 0);

        // Le résultat doit être soit la dernière action de l'adversaire (TRAHIR), soit une action aléatoire (COOPERER ou TRAHIR)
        assertTrue(result == TypeAction.COOPERER || result == TypeAction.TRAHIR,
                "La stratégie doit retourner soit la dernière action de l'adversaire, soit une action aléatoire.");
    }

    @Test
    void testDefaultCooperateOnEmptyList() {
        List<TypeAction> actions = new ArrayList<>();

        // Appel de la stratégie avec une liste vide
        TypeAction result = strategie.getAction(actions, 0);

        // Vérifie que la stratégie coopère par défaut
        assertTrue(result == TypeAction.COOPERER, "La stratégie doit coopérer par défaut lorsque la liste est vide.");
    }
}
