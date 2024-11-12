package fr.uga.m1miage.pc.joueur.strategies;

import fr.uga.m1miage.pc.joueur.models.JoueurEntity;
import fr.uga.m1miage.pc.partie.enums.CoupEnum;
import fr.uga.m1miage.pc.partie.enums.StatutPartieEnum;
import fr.uga.m1miage.pc.partie.models.PartieEntity;
import fr.uga.m1miage.pc.partie.models.PartieJoueurEntity;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.TestConfiguration;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestConfiguration
@ExtendWith(MockitoExtension.class)
public class DonnantDonnantTest {


    private DonnantDonnant strategie;
    private List<PartieEntity> parties;

    @BeforeEach
    public void setUp() {
        strategie = new DonnantDonnant();
        parties = new ArrayList<>();
    }

    @Test
    public void testPartiePrecedenteAvecCoupAdverse() {
        // Cas où la partie précédente a un joueur adverse qui a joué un coup
        PartieEntity partiePrecedente = new PartieEntity();
        partiePrecedente.setOrdre(1);

        PartieJoueurEntity partieJoueurAdverse = new PartieJoueurEntity();
        JoueurEntity joueurAdverse = new JoueurEntity();
        joueurAdverse.setAbandon(null);
        partieJoueurAdverse.setJoueur(joueurAdverse);
        partieJoueurAdverse.setCoup(CoupEnum.TRAHIR);
        partiePrecedente.setPartiesJoueur(List.of(partieJoueurAdverse));

        PartieEntity partieEnCours = new PartieEntity();
        partieEnCours.setStatut(StatutPartieEnum.EN_COURS);
        partieEnCours.setOrdre(2);

        parties.add(partiePrecedente);
        parties.add(partieEnCours);

        // Vérifie que la stratégie reproduit le coup adverse
        CoupEnum coup = strategie.getCoup(parties);
        assertEquals(CoupEnum.TRAHIR, coup, "La stratégie Donnant-Donnant doit reproduire le coup adverse.");
    }

}