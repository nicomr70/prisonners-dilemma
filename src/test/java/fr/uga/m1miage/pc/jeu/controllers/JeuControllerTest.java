package fr.uga.m1miage.pc.jeu.controllers;
import fr.uga.m1miage.pc.jeu.enums.StatutJeuEnum;
import fr.uga.m1miage.pc.jeu.models.JeuEntity;
import fr.uga.m1miage.pc.jeu.requests.JeuConnexionRequestDTO;
import fr.uga.m1miage.pc.jeu.requests.JeuCreationRequestDTO;
import fr.uga.m1miage.pc.jeu.response.JeuConnexionResponseDTO;
import fr.uga.m1miage.pc.jeu.response.JeuCreationResponseDTO;
import fr.uga.m1miage.pc.jeu.response.JeuDTO;
import fr.uga.m1miage.pc.jeu.services.JeuService;
import fr.uga.m1miage.pc.joueur.models.JoueurEntity;
import fr.uga.m1miage.pc.joueur.responses.JoueurDTO;
import fr.uga.m1miage.pc.partie.enums.StatutPartieEnum;
import fr.uga.m1miage.pc.partie.models.PartieEntity;
import fr.uga.m1miage.pc.partie.responses.PartieDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@AutoConfigureTestDatabase
@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect")
class JeuControllerTest {

    @Mock
    private JeuService jeuService;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @InjectMocks
    private JeuController jeuController;


    @BeforeEach
    void setUp() {
        JeuEntity jeu = new JeuEntity();
        jeu.setId(1L);
        jeu.setStatut(StatutJeuEnum.EN_COURS);
        jeu.setNombreParties(3);

        // Configuration du mock pour simuler la réponse du service
        when(jeuService.creerJeu("Abdraman", 3)).thenReturn(jeu);
    }



    @Test
    void testCreerJeu() {
        // Arrange : Création des objets nécessaires pour le test
        JeuCreationRequestDTO requestDTO = JeuCreationRequestDTO.builder()
                .pseudoJoueur("Player1")
                .nombreParties(3)
                .build();

        // Simulation du comportement du service
        JeuEntity jeuEntity = JeuEntity.builder()
                .id(1L)
                .statut(StatutJeuEnum.EN_ATTENTE)
                .nombreParties(3)
                .build();

        when(jeuService.creerJeu("Player1", 3)).thenReturn(jeuEntity); // Mock le comportement

        JeuCreationResponseDTO responseDTO = JeuCreationResponseDTO.builder()
                .id(1L)
                .statut(StatutJeuEnum.EN_ATTENTE)
                .nombreParties(3)
                .build();

        // Act : Appel du endpoint
        ResponseEntity<JeuCreationResponseDTO> response = jeuController.creerJeu(requestDTO);

        // Assert : Vérification des résultats
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(responseDTO, response.getBody());
    }


    @Test
    void testJoindreJeu() {
        // Given
        JeuEntity jeuEntity = JeuEntity.builder()
                .id(57657L)
                .statut(StatutJeuEnum.EN_COURS)
                .nombreParties(3)
                .build();


        JeuConnexionRequestDTO jeuConnexionRequestDTO = JeuConnexionRequestDTO.builder()
                .codeJeu(57657L)
                .pseudoJoueur("Gabriel")
                .build();

        // When
        when(jeuService.joindreJeu("Gabriel", 57657L)).thenReturn(jeuEntity);

        JeuConnexionResponseDTO jeuConnexionResponseDTO = JeuConnexionResponseDTO.builder()
                .id(57657L)
                .statut(StatutJeuEnum.EN_COURS)
                .build();

        ResponseEntity<JeuConnexionResponseDTO> response = jeuController.joindreJeu(jeuConnexionRequestDTO);

        // Then
        assert(response != null);
        assert(response.getBody() != null);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(jeuConnexionResponseDTO, response.getBody());
    }

    @Test
    void testRecupererDetailsJeu() {
        JoueurEntity joueur = JoueurEntity.
                builder()
                .nomJoueur("Gabriel")
                .build();
        PartieEntity partie = PartieEntity
                .builder()
                .statut(StatutPartieEnum.EN_COURS)
                .ordre(1)
                .build();
        JeuEntity jeu = JeuEntity.builder()
                .id(57657L)
                .statut(StatutJeuEnum.EN_ATTENTE)
                .joueurs(List.of(joueur))
                .parties(List.of(partie))
                .nombreParties(1)
                .build();
        PartieDTO partieDTO = PartieDTO
                .builder()
                .id(partie.getId())
                .statut(partie.getStatut())
                .ordre(partie.getOrdre())
                .build();
        JoueurDTO joueurDTO = JoueurDTO
                .builder()
                .nom("Gabriel")
                .build();

        JeuDTO jeuDTO = JeuDTO
                .builder()
                .id(jeu.getId())
                .nombreParties(1)
                .statut(jeu.getStatut())
                .joueurs(List.of(joueurDTO))
                .parties(List.of(partieDTO))
                .build();

        when(jeuService.recupererJeu(jeu.getId())).thenReturn(jeu);
        ResponseEntity<JeuDTO> res = jeuController.recupererDetailsJeu(jeu.getId());
        assertEquals(HttpStatus.OK, res.getStatusCode());
        assertThat(res.getBody().getId()).isEqualTo(jeuDTO.getId());
        assertThat(res.getBody().getNombreParties()).isEqualTo(jeuDTO.getNombreParties());
        assertThat(res.getBody().getStatut()).isEqualTo(jeuDTO.getStatut());
        assertThat(res.getBody().getParties().get(0).getId()).isEqualTo(jeuDTO.getParties().get(0).getId());
        assertThat(res.getBody().getJoueurs().get(0).getId()).isEqualTo(jeuDTO.getJoueurs().get(0).getId());
        assertThat(res.getBody().getJoueurs().get(0).getNom()).isEqualTo(jeuDTO.getJoueurs().get(0).getNom());
    }




}

