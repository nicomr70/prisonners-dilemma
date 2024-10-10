package fr.uga.l3miage.pc.prisonersdilemma.controllers;

import fr.uga.l3miage.pc.prisonersdilemma.services.GameService;
import fr.uga.l3miage.pc.prisonersdilemma.usecases.Game;
import fr.uga.l3miage.pc.prisonersdilemma.utils.ApiResponse;
import fr.uga.l3miage.pc.prisonersdilemma.utils.GlobalGameMap;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/game")
public class GameController {

    // Endpoint pour créer une nouvelle partie
    @PostMapping("/new")
    public ResponseEntity<ApiResponse<Game>> createGame(@RequestParam int rounds, @RequestParam String player1Name ) {
        try {
            Game game = new Game(rounds, player1Name);
            GlobalGameMap gameMap = GlobalGameMap.getInstance();
            gameMap.putElement(game.getGameId(), game);
            ApiResponse<Game> response = new ApiResponse<>(200, "OK",game);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // En cas d'erreur, retourner un statut 500 (Internal Server Error)
            ApiResponse<Game> response = new ApiResponse<>(500, "Internal Server Error",null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(response);
        }
        //Player 1 doit récupérer son UUID
    }

    @PostMapping("/{gameId}/join")
    public ResponseEntity<ApiResponse<Game>> joinGame(@PathVariable UUID gameId, @RequestParam String player2Name ) {
        try {
            return ResponseEntity.ok(this.findTheRightGame(gameId).joinGame(player2Name));
        } catch (Exception e) {
            // En cas d'erreur, retourner un statut 500 (Internal Server Error)
            ApiResponse<Game> response = new ApiResponse<>(500, "Internal Server Error",null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(response);
        }
        //Player 2 doit récupérer son UUID
    }

    // Endpoint pour envoyer la décision des joueurs
    @PostMapping("/{gameId}/play")
    public ResponseEntity<ApiResponse<Game>> playGame(@PathVariable UUID gameId, @RequestParam UUID playerId, @RequestParam String decision ) {
        try {
            return ResponseEntity.ok(this.findTheRightGame(gameId).playGame(playerId, decision));
        } catch (Exception e) {
            // En cas d'erreur, retourner un statut 500 (Internal Server Error)
            ApiResponse<Game> response = new ApiResponse<>(500, "Internal Server Error",null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(response);
        }
    }

    // Endpoint pour envoyer la décision des joueurs
    @PostMapping("/{gameId}/state")
    public ResponseEntity<ApiResponse<Game>> getGameState(@PathVariable UUID gameId, @RequestParam UUID playerId ) {
        try {
            return ResponseEntity.ok(this.findTheRightGame(gameId).getGameState(playerId));
        } catch (Exception e) {
            // En cas d'erreur, retourner un statut 500 (Internal Server Error)
            ApiResponse<Game> response = new ApiResponse<>(500, "Internal Server Error",null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(response);
        }

    }

    // Endpoint pour obtenir les résultats de la partie
    @GetMapping("/{gameId}/results")
    public ResponseEntity<ApiResponse<String>> getResults(@PathVariable UUID gameId, @RequestParam UUID playerId) {
        try {
            return ResponseEntity.ok(this.findTheRightGame(gameId).displayResults());
        } catch (Exception e) {
            // En cas d'erreur, retourner un statut 500 (Internal Server Error)
            ApiResponse<String> response = new ApiResponse<>(500, "Internal Server Error",null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(response);
        }
    }

    // Endpoint pour envoyer la décision des joueurs
    @PostMapping("/{gameId}/giveup")
    public ResponseEntity<ApiResponse<Game>> giveUpGame(@PathVariable UUID gameId, @RequestParam UUID playerId, @RequestParam String decision ) {
        try {
            return ResponseEntity.ok(this.findTheRightGame(gameId).giveUpGame(playerId));
        } catch (Exception e) {
            // En cas d'erreur, retourner un statut 500 (Internal Server Error)
            ApiResponse<Game> response = new ApiResponse<>(500, "Internal Server Error",null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(response);
        }
    }

    @GetMapping("/gamelist")
    public ResponseEntity<ApiResponse<List<UUID>>> getGamesList() {
        try {
            GlobalGameMap gameMap = GlobalGameMap.getInstance();

            List<UUID> availableGames = new ArrayList<>();

            for (Map.Entry<UUID, Game> entry : gameMap.getMap().entrySet()) {
                if (entry.getValue().isAvailableToJoin()) {
                    availableGames.add(entry.getKey());
                }
            }
            ApiResponse<List<UUID>> response = new ApiResponse<>(200, "OK", availableGames);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // En cas d'erreur, retourner un statut 500 (Internal Server Error)
            ApiResponse<List<UUID>> response = new ApiResponse<>(500, "Internal Server Error", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(response);
        }
    }

    private Game findTheRightGame(UUID gameId) {
        GlobalGameMap gameMap = GlobalGameMap.getInstance();
        return gameMap.getElement(gameId);
    }
}
