package fr.uga.m1miage.pc.partie.services;

import fr.uga.m1miage.pc.jeu.enums.StatutJeuEnum;
import fr.uga.m1miage.pc.jeu.models.JeuEntity;
import fr.uga.m1miage.pc.jeu.repository.JeuRepository;
import fr.uga.m1miage.pc.joueur.models.JoueurEntity;
import fr.uga.m1miage.pc.joueur.repository.JoueurRepository;
import fr.uga.m1miage.pc.partie.enums.CoupEnum;
import fr.uga.m1miage.pc.partie.enums.StatutPartieEnum;
import fr.uga.m1miage.pc.partie.models.PartieEntity;
import fr.uga.m1miage.pc.partie.models.PartieJoueurEntity;
import fr.uga.m1miage.pc.partie.repository.PartieJoueurRepository;
import fr.uga.m1miage.pc.partie.repository.PartieRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class PartieServiceTest {

    @InjectMocks
    private PartieService partieService;

    @Mock
    private JeuRepository jeuRepository;

    @Mock
    private JoueurRepository joueurRepository;

    @Mock
    private PartieRepository partieRepository;

    @Mock
    private PartieJoueurRepository partieJoueurRepository;

    @Test
    void testJoueurCoup() {
        UUID joueurId = UUID.randomUUID();
        Long jeuId = 1L;
        CoupEnum coup = CoupEnum.COOPERER;


        JeuEntity jeu = JeuEntity
                .builder()
                .id(jeuId)
                .build();
        jeuRepository.save(jeu);


        JoueurEntity joueur = JoueurEntity
                .builder()
                .id(joueurId)
                .jeu(jeu)
                .nomJoueur("Joueur 1")
                .build();

        joueurRepository.save(joueur);


        PartieJoueurEntity partieJoueur = PartieJoueurEntity
                .builder()
                .joueur(joueur)
                .build();
        partieJoueurRepository.save(partieJoueur);

        List<PartieJoueurEntity> partieJoueurEntities = new ArrayList<>();
        partieJoueurEntities.add(partieJoueur);

        PartieEntity partieEnCours = PartieEntity
                .builder()
                .statut(StatutPartieEnum.EN_COURS)
                .partiesJoueur(partieJoueurEntities)
                .build();

        List<PartieEntity> parties = new ArrayList<>();
        parties.add(partieEnCours);

        List<JoueurEntity> joueurs = new ArrayList<>();
        joueurs.add(joueur);
        jeu.setJoueurs(joueurs);
        jeu.setParties(parties);



        when(joueurRepository.findById(joueurId)).thenReturn(Optional.of(joueur));
        when(partieRepository.findByJeuIdAndStatut(jeuId, StatutPartieEnum.EN_COURS)).thenReturn(partieEnCours);
        when(jeuRepository.findById(jeuId)).thenReturn(Optional.of(jeu));

        PartieJoueurEntity result = partieService.joueurCoup(joueurId, jeuId, coup);
        assertNotNull(result);
    }








    @Test
    void testTerminerJeu() {
        Long idJeu = 1L;
        JeuEntity jeu = new JeuEntity();
        jeu.setId(idJeu);
        jeu.setStatut(StatutJeuEnum.EN_COURS);

        when(jeuRepository.findById(idJeu)).thenReturn(Optional.of(jeu));

        partieService.terminerJeu(jeu);

        assertEquals(StatutJeuEnum.TERMINE, jeu.getStatut());
        verify(jeuRepository, times(1)).save(jeu);
    }



    @Test
     void testRegarderSiJoueurAdverseAAbandonne_True() {
        // Arrange
        Long idJeu = 1L;
        JeuEntity jeu = new JeuEntity();
        JoueurEntity joueur1 = new JoueurEntity();
        JoueurEntity joueur2 = new JoueurEntity();
        joueur1.setAbandon(true); // Ce joueur a abandonné
        joueur2.setAbandon(null); // Ce joueur continue

        jeu.setJoueurs(Arrays.asList(joueur1, joueur2));

        when(jeuRepository.findById(idJeu)).thenReturn(Optional.of(jeu));

        // Act
        boolean result = partieService.regarderSiJoueurAdverseAAbandonne(idJeu);

        // Assert
        assertTrue(result); // On s'attend à ce que le joueur adverse ait abandonné
    }



    @Test
    void testRegarderSiJoueurAdverseAAbandonne_False() {
        // Arrange
        Long idJeu = 1L;
        JeuEntity jeu = new JeuEntity();
        JoueurEntity joueur1 = new JoueurEntity();
        JoueurEntity joueur2 = new JoueurEntity();
        joueur1.setAbandon(null); // Aucun joueur n'a abandonné
        joueur2.setAbandon(null);

        jeu.setJoueurs(Arrays.asList(joueur1, joueur2));

        when(jeuRepository.findById(idJeu)).thenReturn(Optional.of(jeu));

        // Act
        boolean result = partieService.regarderSiJoueurAdverseAAbandonne(idJeu);

        // Assert
        assertFalse(result); // Aucun joueur n'a abandonné
    }


    @Test
    void testRegarderSiJoueurAdverseAAbandonne_JeuNonTrouve() {
        // Arrange
        Long idJeu = 1L;

        when(jeuRepository.findById(idJeu)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            partieService.regarderSiJoueurAdverseAAbandonne(idJeu);
        });
    }



    @Test
    void testCalculerScore_CoopereVsCoopere() {
        PartieJoueurEntity partieJoueur1 = new PartieJoueurEntity();
        partieJoueur1.setCoup(CoupEnum.COOPERER);

        PartieJoueurEntity partieJoueur2 = new PartieJoueurEntity();
        partieJoueur2.setCoup(CoupEnum.COOPERER);

        partieService.calculerScore(List.of(partieJoueur1, partieJoueur2));

        assertEquals(3, partieJoueur1.getScore());
        assertEquals(3, partieJoueur2.getScore());
    }

    @Test
    void testCalculerScore_CoopereVsTrahir() {
        PartieJoueurEntity partieJoueur1 = new PartieJoueurEntity();
        partieJoueur1.setCoup(CoupEnum.COOPERER);

        PartieJoueurEntity partieJoueur2 = new PartieJoueurEntity();
        partieJoueur2.setCoup(CoupEnum.TRAHIR);

        partieService.calculerScore(List.of(partieJoueur1, partieJoueur2));

        assertEquals(0, partieJoueur1.getScore());
        assertEquals(5, partieJoueur2.getScore());
    }

    @Test
    void testCalculerScore_TrahirVsTrahir() {
        PartieJoueurEntity partieJoueur1 = new PartieJoueurEntity();
        partieJoueur1.setCoup(CoupEnum.TRAHIR);

        PartieJoueurEntity partieJoueur2 = new PartieJoueurEntity();
        partieJoueur2.setCoup(CoupEnum.TRAHIR);

        partieService.calculerScore(List.of(partieJoueur1, partieJoueur2));

        assertEquals(1, partieJoueur1.getScore());
        assertEquals(1, partieJoueur2.getScore());
    }


    @Test
    void testJouerServeurCoup_NoJoueurAbandonne() {
        Long idJeu = 1L;
        JeuEntity jeu = JeuEntity
                .builder()
                .build();
        JoueurEntity joueurAdverse = JoueurEntity
                .builder().build();
        joueurAdverse.setAbandon(null);  // Player did not abandon
        jeu.setJoueurs(List.of(joueurAdverse));

        when(jeuRepository.findById(idJeu)).thenReturn(Optional.of(jeu));

        assertThrows(IllegalStateException.class, () -> {
            partieService.jouerServeurCoup(idJeu);
        });
    }


}