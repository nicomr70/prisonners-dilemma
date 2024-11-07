package fr.uga.m1miage.pc.partie.services;

import fr.uga.m1miage.pc.jeu.enums.StatutJeuEnum;
import fr.uga.m1miage.pc.jeu.models.JeuEntity;
import fr.uga.m1miage.pc.jeu.repository.JeuRepository;
import fr.uga.m1miage.pc.joueur.enums.StrategieEnum;
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
import org.mockito.Spy;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class PartieServiceTest {

    @Spy
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
        Long idJeu = 1L;
        JeuEntity jeu = new JeuEntity();
        JoueurEntity joueur1 = new JoueurEntity();
        JoueurEntity joueur2 = new JoueurEntity();
        joueur1.setAbandon(true); // Ce joueur a abandonné
        joueur2.setAbandon(null); // Ce joueur continue
        jeu.setJoueurs(Arrays.asList(joueur1, joueur2));
        when(jeuRepository.findById(idJeu)).thenReturn(Optional.of(jeu));
        boolean result = partieService.regarderSiJoueurAdverseAAbandonne(idJeu);
        assertTrue(result); // On s'attend à ce que le joueur adverse ait abandonné
    }




    @Test
    void testJoueurCoupQuandJoueurAdverseNAbandonnePas() {
        JeuEntity jeu = JeuEntity.builder().build();
        JoueurEntity joueur = JoueurEntity.builder().build();
        PartieEntity partieEnCours = PartieEntity.builder()
                .partiesJoueur(new ArrayList<>())
                .build();
        jeu.setParties(List.of(partieEnCours));
        joueur.setJeu(jeu);
        when(joueurRepository.findById(joueur.getId())).thenReturn(Optional.of(joueur));
        when(partieRepository.findByJeuIdAndStatut(jeu.getId(), StatutPartieEnum.EN_COURS))
                .thenReturn(partieEnCours);

        doReturn(false).when(partieService).regarderSiJoueurAdverseAAbandonne(jeu.getId());
        partieService.joueurCoup(joueur.getId(), jeu.getId(), CoupEnum.COOPERER);
        verify(partieService, never()).jouerServeurCoup(jeu.getId());
    }


    @Test
    void testRegarderSiJoueurAdverseAAbandonne_JeuNonTrouve() {
        Long idJeu = 1L;
        when(jeuRepository.findById(idJeu)).thenReturn(Optional.empty());
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


    @Test
    void joueurCoup_ShouldThrowException_WhenJoueurDoesNotExist() {

        JeuEntity jeu = JeuEntity
                .builder()
                .id(1L)
                .build();

        JoueurEntity joueur = JoueurEntity
                .builder()
                .jeu(jeu)
                .nomJoueur("Joueur 1")
                .build();

        when(joueurRepository.findById(joueur.getId())).thenReturn(Optional.empty());

        // Act & Assert
        verify(partieJoueurRepository, never()).save(any(PartieJoueurEntity.class));
    }


    @Test
    void testJouerServeurCoup_Succes() {
        JoueurEntity joueur = JoueurEntity
                .builder()
                .abandon(true)
                .strategie(StrategieEnum.DONNANT_DONNANT)
                .build();
        JoueurEntity autreJoueur = JoueurEntity
                .builder()
                .abandon(null)
                .build();

        JeuEntity jeu = JeuEntity
                .builder()
                .joueurs(List.of(joueur,autreJoueur))
                .build();

        PartieEntity partie = PartieEntity
                .builder()
                .jeu(jeu)
                .statut(StatutPartieEnum.EN_COURS)
                .build();
        jeu.setParties(List.of(partie));
        when(jeuRepository.findById(anyLong())).thenReturn(Optional.of(jeu));
        when(partieRepository.findByJeuIdAndStatut(anyLong(), eq(StatutPartieEnum.EN_COURS))).thenReturn(partie);

        // Exécuter la méthode
        assertDoesNotThrow(() -> partieService.jouerServeurCoup(1L));

        // Vérifier que le partieJoueurRepository.save() a été appelé
        verify(partieJoueurRepository, times(1)).save(any(PartieJoueurEntity.class));
    }



    @Test
    void testJouerServeurCoup_AucunJoueurAbandonne() {

        JoueurEntity joueur = JoueurEntity
                .builder()
                .abandon(true)
                .strategie(StrategieEnum.DONNANT_DONNANT)
                .build();
        JoueurEntity autreJoueur = JoueurEntity
                .builder()
                .abandon(null)
                .build();

        JeuEntity jeu = JeuEntity
                .builder()
                .joueurs(List.of(joueur,autreJoueur))
                .build();

        PartieEntity partie = PartieEntity
                .builder()
                .jeu(jeu)
                .statut(StatutPartieEnum.EN_COURS)
                .build();
        jeu.setParties(List.of(partie));
        jeu.setJoueurs(List.of(new JoueurEntity(), new JoueurEntity()));

        when(jeuRepository.findById(anyLong())).thenReturn(Optional.of(jeu));

        Exception exception = assertThrows(IllegalStateException.class, () -> partieService.jouerServeurCoup(1L));
        assertTrue(exception.getMessage().contains("Aucun joueur n'a"));
        verify(partieJoueurRepository, never()).save(any(PartieJoueurEntity.class));
    }



    @Test
    void testRecupererDetailsPartie() {
        PartieEntity partie = PartieEntity
                .builder()
                .ordre(1)
                .build();
        when(partieRepository.findById(partie.getId())).thenReturn(Optional.of(partie));

        PartieEntity partie1 = partieService.recupererDetailsPartie(partie.getId());

        assertNotNull(partie1);
    }

    @Test
    void testCreerNouvellePartie_Success() {
        PartieEntity partie = PartieEntity
                .builder()
                .ordre(1)
                .build();
        JeuEntity jeu = JeuEntity
                .builder()
                .parties(List.of(partie))
                .nombreParties(2)
                .build();
        PartieEntity partie1 = PartieEntity
                .builder()
                .ordre(2)
                .jeu(jeu)
                .build();
        when(partieRepository.save(partie1)).thenReturn(partie1);
        partieService.creerNouvellePartie(jeu,2);
        verify(partieRepository, times(1)).save(any(PartieEntity.class));
    }


    @Test

    void testCreerNouvellePartie_Failure() {
        PartieEntity partie = PartieEntity.builder().ordre(1).build();
        JeuEntity jeu = JeuEntity.builder()
                .parties(List.of(partie))
                .nombreParties(1)
                .build();
        partieService.creerNouvellePartie(jeu, 2);
        verify(partieRepository, never()).save(any(PartieEntity.class)); // Vérifie que save n'a pas été appelé

    }



    @Test
    void testTerminerPartie() {
        PartieEntity partieEnCours = PartieEntity.builder()
                .jeu(new JeuEntity())
                .ordre(1)
                .partiesJoueur(new ArrayList<>()) // Assurez-vous d'initialiser la liste
                .build();
        doNothing().when(partieService).calculerScore(anyList());
        doNothing().when(partieService).creerNouvellePartie(any(JeuEntity.class), anyInt());
        partieService.terminerPartie(partieEnCours);
        verify(partieService).calculerScore(partieEnCours.getPartiesJoueur());
        verify(partieService).creerNouvellePartie(partieEnCours.getJeu(), partieEnCours.getOrdre() + 1);
        assertEquals(StatutPartieEnum.TERMINE, partieEnCours.getStatut());
        verify(partieRepository).save(partieEnCours);
    }




    @Test
    void testCalculerScoreCooperation() {
        PartieJoueurEntity joueur1 = new PartieJoueurEntity();
        joueur1.setCoup(CoupEnum.COOPERER);
        PartieJoueurEntity joueur2 = new PartieJoueurEntity();
        joueur2.setCoup(CoupEnum.COOPERER);
        List<PartieJoueurEntity> partiesJoueurs = List.of(joueur1, joueur2);
        partieService.calculerScore(partiesJoueurs);
        assertEquals(3, joueur1.getScore());
        assertEquals(3, joueur2.getScore());
        verify(partieJoueurRepository).saveAll(partiesJoueurs);
    }


    @Test
    void testCalculerScoreTrahisonJoueur1() {
        PartieJoueurEntity joueur1 = new PartieJoueurEntity();
        joueur1.setCoup(CoupEnum.COOPERER);
        PartieJoueurEntity joueur2 = new PartieJoueurEntity();
        joueur2.setCoup(CoupEnum.TRAHIR);
        List<PartieJoueurEntity> partiesJoueurs = List.of(joueur1, joueur2);
        partieService.calculerScore(partiesJoueurs);
        assertEquals(0, joueur1.getScore());
        assertEquals(5, joueur2.getScore());
        verify(partieJoueurRepository).saveAll(partiesJoueurs);
    }


    @Test
    void testCalculerScoreTrahisonJoueur2() {
        PartieJoueurEntity joueur1 = new PartieJoueurEntity();
        joueur1.setCoup(CoupEnum.TRAHIR);
        PartieJoueurEntity joueur2 = new PartieJoueurEntity();
        joueur2.setCoup(CoupEnum.COOPERER);
        List<PartieJoueurEntity> partiesJoueurs = List.of(joueur1, joueur2);
        partieService.calculerScore(partiesJoueurs);
        assertEquals(5, joueur1.getScore());
        assertEquals(0, joueur2.getScore());
        verify(partieJoueurRepository).saveAll(partiesJoueurs);
    }


    @Test
    void testCalculerScoreTrahisonDesDeux() {
        PartieJoueurEntity joueur1 = new PartieJoueurEntity();
        joueur1.setCoup(CoupEnum.TRAHIR);
        PartieJoueurEntity joueur2 = new PartieJoueurEntity();
        joueur2.setCoup(CoupEnum.TRAHIR);
        List<PartieJoueurEntity> partiesJoueurs = List.of(joueur1, joueur2);
        partieService.calculerScore(partiesJoueurs);
        assertEquals(1, joueur1.getScore());
        assertEquals(1, joueur2.getScore());
        verify(partieJoueurRepository).saveAll(partiesJoueurs);
    }




    // ------------------------------------------------------- //

    @Test
    void testJoueurCoupAdverseAbandonne() {

        CoupEnum coup = CoupEnum.COOPERER;

        JoueurEntity joueur = JoueurEntity
                .builder()
                .strategie(StrategieEnum.DONNANT_DONNANT)
                .build();

        JoueurEntity joueurAdverse = JoueurEntity
                .builder()
                .abandon(true)
                .strategie(StrategieEnum.DONNANT_DONNANT)
                .build();

        PartieEntity partieEnCours = PartieEntity.builder()
                .statut(StatutPartieEnum.EN_COURS)
                .partiesJoueur(new ArrayList<>())
                .build();

        JeuEntity jeu = JeuEntity
                .builder()
                .id(1L)
                .joueurs(List.of(joueur, joueurAdverse))
                .parties(List.of(partieEnCours))
                .build();
        partieEnCours.setJeu(jeu);
        joueur.setJeu(jeu);
        joueurAdverse.setJeu(jeu);

        when(joueurRepository.findById(joueur.getId())).thenReturn(Optional.of(joueur));
        when(partieRepository.findByJeuIdAndStatut(jeu.getId(), StatutPartieEnum.EN_COURS)).thenReturn(partieEnCours);
        when(jeuRepository.findById(jeu.getId())).thenReturn(Optional.of(jeu));
        partieService.joueurCoup(joueur.getId(), jeu.getId(), coup);

        verify(joueurRepository).findById(joueur.getId());
        verify(partieRepository, times(2)).findByJeuIdAndStatut(jeu.getId(), StatutPartieEnum.EN_COURS); // Ajustez ici si nécessaire
    }


    @Test
    void testJoueurCoupDeuxJoueurs() {
        CoupEnum coup = CoupEnum.COOPERER;
        JeuEntity jeu = JeuEntity.builder().build();
        JoueurEntity joueur = JoueurEntity.builder().build();
        JoueurEntity joueurAdverse = JoueurEntity.builder().jeu(jeu).build();
        joueur.setJeu(jeu);
        PartieEntity partieEnCours = PartieEntity
                .builder()
                .jeu(jeu)
                .statut(StatutPartieEnum.EN_COURS)
                .build();

        PartieJoueurEntity partieJoueur = PartieJoueurEntity
                .builder()
                .build();
        partieEnCours.setPartiesJoueur(List.of(partieJoueur));

        jeu.setJoueurs(List.of(joueur,joueurAdverse));
        jeu.setParties(List.of(partieEnCours));

        when(joueurRepository.findById(joueur.getId())).thenReturn(Optional.of(joueur));
        when(partieRepository.findByJeuIdAndStatut(jeu.getId(), StatutPartieEnum.EN_COURS)).thenReturn(partieEnCours);
        when(jeuRepository.findById(jeu.getId())).thenReturn(Optional.of(jeu));

        partieService.joueurCoup(joueur.getId(), jeu.getId(), coup);
        verify(joueurRepository).findById(joueur.getId());
        verify(partieRepository).findByJeuIdAndStatut(jeu.getId(), StatutPartieEnum.EN_COURS);
        verify(jeuRepository).findById(jeu.getId());

    }

    @Test
    void testJouerCoup_TerminerPartieDeuxJoueurs() {

        CoupEnum coup = CoupEnum.COOPERER;
        JoueurEntity joueur = JoueurEntity.builder().build();
        PartieEntity partie = PartieEntity.builder().build();
        JeuEntity jeu = JeuEntity.builder().nombreParties(1).build();

        PartieJoueurEntity partieJoueur1 = PartieJoueurEntity.builder().coup(coup).joueur(joueur).build();
        PartieJoueurEntity partieJoueur2 = PartieJoueurEntity.builder().coup(coup).joueur(JoueurEntity.builder().build()).build();

        partie.setJeu(jeu);
        partie.setPartiesJoueur(List.of(partieJoueur1, partieJoueur2));
        jeu.setJoueurs(List.of(joueur,JoueurEntity.builder().build()));
        jeu.setParties(List.of(partie));
        joueur.setJeu(jeu);

        when(jeuRepository.findById(jeu.getId())).thenReturn(Optional.of(jeu));
        when(joueurRepository.findById(joueur.getId())).thenReturn(Optional.of(joueur));
        when(partieRepository.findByJeuIdAndStatut(jeu.getId(), StatutPartieEnum.EN_COURS)).thenReturn(partie);

        PartieJoueurEntity partieJoueur = partieService.joueurCoup(joueur.getId(), jeu.getId(), coup);

        verify(partieService).terminerPartie(partie);
        assertNotNull(partieJoueur);

    }






}