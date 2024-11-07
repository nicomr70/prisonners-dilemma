package fr.uga.m1miage.pc.joueur.services;



import fr.uga.m1miage.pc.jeu.enums.StatutJeuEnum;
import fr.uga.m1miage.pc.jeu.models.JeuEntity;
import fr.uga.m1miage.pc.jeu.repository.JeuRepository;
import fr.uga.m1miage.pc.jeu.response.JeuConnexionResponseDTO;
import fr.uga.m1miage.pc.joueur.enums.StrategieEnum;
import fr.uga.m1miage.pc.joueur.models.JoueurEntity;
import fr.uga.m1miage.pc.joueur.repository.JoueurRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class JoueurServiceTest {


    @InjectMocks
    private JoueurService joueurService;

    @Mock
    private JoueurRepository joueurRepository;


    @Mock
    private JeuRepository jeuRepository;


    @Test
    void testAbandonnerJeu() {
        UUID joueurId = UUID.randomUUID();
        StrategieEnum strategie = StrategieEnum.TOUJOURS_COOPERER;

        JoueurEntity joueur = JoueurEntity.builder()
                .id(joueurId)
                .nomJoueur("John")
                .build();

        when(joueurRepository.findById(joueurId)).thenReturn(Optional.of(joueur));
        when(joueurRepository.save(any(JoueurEntity.class))).thenReturn(joueur);

        JoueurEntity result;
        result = joueurService.abandonnerJeu(joueurId, strategie);

        assertNotNull(result);
        assertEquals(strategie, result.getStrategie());
        verify(joueurRepository, times(1)).findById(joueurId);
        verify(joueurRepository, times(1)).save(joueur);
    }



    @Test
    void abandonnerJeu_ShouldReturnNull_WhenJoueurDoesNotExist() {

        JoueurEntity joueur = JoueurEntity.builder()
                .nomJoueur("Abdraman")
                .strategie(null)
                .build();
        when(joueurRepository.findById(joueur.getId())).thenReturn(Optional.empty());
        StrategieEnum strategie = StrategieEnum.DONNANT_DONNANT;
        JoueurEntity result = joueurService.abandonnerJeu(joueur.getId(), strategie);
        assertNull(result);
        verify(joueurRepository, never()).save(any(JoueurEntity.class));
    }



}



