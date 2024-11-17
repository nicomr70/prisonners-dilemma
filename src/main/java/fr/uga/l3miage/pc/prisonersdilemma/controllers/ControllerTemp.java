package fr.uga.l3miage.pc.prisonersdilemma.controllers;

import fr.uga.l3miage.pc.prisonersdilemma.entities.GameCreationDTO;
import fr.uga.l3miage.pc.prisonersdilemma.utils.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ControllerTemp {

    private static final Logger log = LoggerFactory.getLogger(ControllerTemp.class);

    // Création de partie
    @MessageMapping("/api/game/server/new")
    @SendTo("/api/game/clients/new")
    public ApiResponse<GameCreationDTO> createGame(/*@Payload*/ GameCreationDTO gameCreationDTO) {
        log.info("Message de création de partie reçu : " + gameCreationDTO);
        // Logique pour créer la partie
        //GameCreationDTO createdGame = new GameCreationDTO(gameCreationDTO.getRounds(), gameCreationDTO.getPlayerName());
        return new ApiResponse<>(200, "Jeu créé avec succès", "createdGame", null);
    }
}
