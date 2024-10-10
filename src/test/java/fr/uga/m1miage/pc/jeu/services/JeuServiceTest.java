package fr.uga.m1miage.pc.jeu.services;

import fr.uga.m1miage.pc.jeu.enums.StatutJeuEnum;
import fr.uga.m1miage.pc.jeu.models.JeuEntity;
import fr.uga.m1miage.pc.jeu.repository.JeuRepository;
import fr.uga.m1miage.pc.joueur.models.JoueurEntity;
import fr.uga.m1miage.pc.joueur.repository.JoueurRepository;
import fr.uga.m1miage.pc.partie.enums.StatutPartieEnum;
import fr.uga.m1miage.pc.partie.models.PartieEntity;
import fr.uga.m1miage.pc.partie.repository.PartieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class JeuServiceTest {

    @InjectMocks
    private JeuService jeuService;

    @MockBean
    private JoueurRepository joueurRepository;

    @MockBean
    private JeuRepository jeuRepository;

    @MockBean
    private PartieRepository partieRepository;



    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreerJeu() {

        String nomJoueur = "John";
        int nombreParties = 5;
        JeuEntity jeu = JeuEntity.builder()
                .nombreParties(nombreParties)
                .statut(StatutJeuEnum.EN_ATTENTE)
                .build();
        jeuRepository.save(jeu);

        JoueurEntity joueur = JoueurEntity.builder()
                .nomJoueur(nomJoueur)
                .jeu(jeu)
                .build();

        when(jeuRepository.save(any(JeuEntity.class))).thenReturn(jeu);
        when(joueurRepository.save(any(JoueurEntity.class))).thenReturn(joueur);


        JeuEntity resultat = jeuService.creerJeu(nomJoueur, nombreParties);

        assertNotNull(resultat);
        assertEquals(nombreParties, resultat.getNombreParties());
        assertEquals(StatutJeuEnum.EN_ATTENTE, resultat.getStatut());
        verify(jeuRepository, times(2)).save(any(JeuEntity.class));
        verify(joueurRepository, times(1)).save(any(JoueurEntity.class));
    }



    @Test
    void testJoindreJeu() {
        // Arrange
        Long jeuId = 1L;
        String pseudo = "John";

        JeuEntity jeu = JeuEntity.builder()
                .id(jeuId)
                .statut(StatutJeuEnum.EN_ATTENTE)
                .nombreParties(3)
                .build();

        JoueurEntity secondJoueur = JoueurEntity.builder()
                .nomJoueur(pseudo)
                .jeu(jeu)
                .build();

        PartieEntity partie = PartieEntity.builder()
                .jeu(jeu)
                .statut(StatutPartieEnum.EN_COURS)
                .build();

        when(jeuRepository.findById(jeuId)).thenReturn(Optional.of(jeu));
        when(joueurRepository.save(any(JoueurEntity.class))).thenReturn(secondJoueur);
        when(partieRepository.save(any(PartieEntity.class))).thenReturn(partie);
        when(jeuRepository.save(jeu)).thenReturn(jeu);

        JeuEntity result = jeuService.joindreJeu(pseudo, jeuId);


        assertNotNull(result);
        assertEquals(StatutJeuEnum.EN_COURS, result.getStatut());
        verify(joueurRepository, times(1)).save(any(JoueurEntity.class));
        verify(partieRepository, times(1)).save(any(PartieEntity.class));
        verify(jeuRepository, times(1)).save(jeu);
    }


}





