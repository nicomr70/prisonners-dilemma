package fr.uga.m1miage.pc.partie.controllers;

import fr.uga.m1miage.pc.jeu.enums.StatutJeuEnum;
import fr.uga.m1miage.pc.jeu.models.JeuEntity;
import fr.uga.m1miage.pc.joueur.models.JoueurEntity;
import fr.uga.m1miage.pc.joueur.repository.JoueurRepository;
import fr.uga.m1miage.pc.joueur.responses.JoueurDTO;
import fr.uga.m1miage.pc.partie.enums.CoupEnum;
import fr.uga.m1miage.pc.partie.enums.StatutPartieEnum;
import fr.uga.m1miage.pc.partie.models.PartieEntity;
import fr.uga.m1miage.pc.partie.models.PartieJoueurEntity;
import fr.uga.m1miage.pc.partie.requests.CoupRequest;
import fr.uga.m1miage.pc.partie.responses.PartieDTO;
import fr.uga.m1miage.pc.partie.responses.PartieDetailsDTO;
import fr.uga.m1miage.pc.partie.responses.PartieJoueurDTO;
import fr.uga.m1miage.pc.partie.services.PartieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestConfiguration
class PartieControllerTest {


    private PartieService partieService;

    @Mock
    private PartieController partieController;

    @Mock
    private JoueurRepository joueurRepository;


    @BeforeEach
    void setup() {
        partieService = Mockito.mock(PartieService.class);
        partieController = new PartieController(partieService);
    }

    @Test
    void jouerCoupTest() {
        JeuEntity jeu = JeuEntity.builder()
                .id(1L)
                .statut(StatutJeuEnum.EN_ATTENTE)
                .nombreParties(3)
                .build();
        CoupRequest coupRequest = CoupRequest
                .builder()
                .coup(CoupEnum.COOPERER)
                .build();
        JoueurEntity joueur = JoueurEntity
                .builder()
                .id(UUID.fromString("d5ebcdc8-9797-463c-a7bc-9a7159c0d44d"))
                .nomJoueur("Abdraman")
                .jeu(jeu)
                .build();
        PartieJoueurEntity partieJoueur = PartieJoueurEntity
                .builder()
                .joueur(joueur)
                .score(1)
                .build();
        when(partieService.joueurCoup(joueur.getId(),jeu.getId(),coupRequest.getCoup())).thenReturn(partieJoueur);
        ResponseEntity<PartieJoueurDTO> response = partieController.jouerCoup(jeu.getId(), joueur.getId(),coupRequest);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getScore()).isEqualTo(1);
    }




    @Test
    void testRecupererDetailsPartie() {
        JoueurEntity joueur = JoueurEntity
                .builder()
                .nomJoueur("Abdraman")
                .build();
        PartieEntity partie = PartieEntity
                .builder()
                .ordre(1)
                .statut(StatutPartieEnum.EN_COURS)
                .build();
        PartieDTO partieDTO = PartieDTO
                .builder()
                .statut(partie.getStatut())
                .ordre(partie.getOrdre())
                .id(partie.getId())
                .build();
        JoueurDTO joueurDTO = JoueurDTO
                .builder()
                .id(joueur.getId())
                .nom(joueur.getNomJoueur())
                .build();
        PartieJoueurEntity partieJoueur1 = PartieJoueurEntity
                .builder()
                .partie(partie)
                .score(2)
                .joueur(joueur)
                .coup(CoupEnum.COOPERER)
                .build();

        PartieJoueurDTO partieJoueur = PartieJoueurDTO
                .builder()
                .score(partieJoueur1.getScore())
                .partie(partieDTO)
                .joueur(joueurDTO)
                .coup(String.valueOf(partieJoueur1.getCoup()))
                .build();


        PartieDetailsDTO partieDetailsDTO = PartieDetailsDTO
                .builder()
                .id(partie.getId())
                .statut(StatutPartieEnum.EN_COURS)
                .ordre(1)
                .build();
        partie.setPartiesJoueur(List.of(partieJoueur1));
        partieDetailsDTO.setPartiesJoueurs(List.of(partieJoueur));
        joueur.setPartieJoueurs(List.of(partieJoueur1));
        when(partieService.recupererDetailsPartie(partie.getId())).thenReturn(partie);
        ResponseEntity<PartieDetailsDTO> response = partieController.recupererDetailsPartie(partie.getId());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getId()).isEqualTo(partieDetailsDTO.getId());
        assertThat(response.getBody().getOrdre()).isEqualTo(partieDetailsDTO.getOrdre());
        assertThat(response.getBody().getStatut()).isEqualTo(partieDetailsDTO.getStatut());
        assertThat(response.getBody().getPartiesJoueurs().get(0).getPartie().getId()).isEqualTo(partieDetailsDTO.getPartiesJoueurs().get(0).getPartie().getId());
        assertThat(response.getBody().getPartiesJoueurs().get(0).getPartie().getStatut()).isEqualTo(partieDetailsDTO.getPartiesJoueurs().get(0).getPartie().getStatut());
        assertThat(response.getBody().getPartiesJoueurs().get(0).getPartie().getOrdre()).isEqualTo(partieDetailsDTO.getPartiesJoueurs().get(0).getPartie().getOrdre());
        assertThat(response.getBody().getPartiesJoueurs().get(0).getCoup()).isEqualTo(partieDetailsDTO.getPartiesJoueurs().get(0).getCoup());
        assertThat(response.getBody().getPartiesJoueurs().get(0).getJoueur().getId()).isEqualTo(partieDetailsDTO.getPartiesJoueurs().get(0).getJoueur().getId());
        assertThat(response.getBody().getPartiesJoueurs().get(0).getPartie().getId()).isEqualTo(partie.getPartiesJoueur().get(0).getId());
        assertThat(response.getBody().getPartiesJoueurs().get(0).getPartie().getId()).isEqualTo(joueur.getPartieJoueurs().get(0).getPartie().getId());
    }




}
