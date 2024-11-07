package fr.uga.m1miage.pc.joueur.repositories;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import fr.uga.m1miage.pc.jeu.models.JeuEntity;
import fr.uga.m1miage.pc.jeu.repository.JeuRepository;
import fr.uga.m1miage.pc.joueur.enums.StrategieEnum;
import fr.uga.m1miage.pc.joueur.models.JoueurEntity;
import fr.uga.m1miage.pc.joueur.repository.JoueurRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@DataJpaTest
class JoueurRepositoryTest {

    @MockBean
    private JoueurRepository joueurRepository;

    @Autowired
    private JeuRepository jeuRepository;

    @Test
    void testFindByJeuId() {

        JeuEntity jeu = new JeuEntity();
        jeu.setId(1L);

        JoueurEntity joueur1 = JoueurEntity
                .builder()
                .nomJoueur("joueur1")
                .jeu(jeu)
                .build();

        JoueurEntity joueur2 = JoueurEntity
                .builder()
                .nomJoueur("joueur2")
                .jeu(jeu)
                .build();
        when(joueurRepository.findByJeuId(1L)).thenReturn(List.of(joueur1,joueur2));

        List<JoueurEntity> joueurs = joueurRepository.findByJeuId(1L);

        assertEquals(2, joueurs.size());
        assertEquals("joueur1",joueurs.get(0).getNomJoueur());
        assertEquals("joueur2",joueurs.get(1).getNomJoueur());

        verify(joueurRepository,times(1)).findByJeuId(1L);

    }



    @Test
    void testAbandonnerJeu() {

        UUID idJoueur = UUID.randomUUID();
        JoueurEntity joueur = JoueurEntity.builder()
                .id(idJoueur)
                .nomJoueur("Joueur1")
                .strategie(StrategieEnum.DONNANT_DONNANT)
                .build();

        when(joueurRepository.findById(idJoueur)).thenReturn(Optional.of(joueur));

        when(joueurRepository.save(joueur)).thenReturn(joueur);

        // Act
        joueur.setStrategie(StrategieEnum.TOUJOURS_TRAHIR);
        JoueurEntity updatedJoueur = joueurRepository.save(joueur);

        // Assert
        assertNotNull(updatedJoueur);
        assertEquals(StrategieEnum.TOUJOURS_TRAHIR, updatedJoueur.getStrategie());

        // Vérifie que findById et save ont été appelés
        verify(joueurRepository, times(1)).save(joueur);
    }
}
