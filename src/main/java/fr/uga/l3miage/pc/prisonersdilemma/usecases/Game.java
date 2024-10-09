package fr.uga.l3miage.pc.prisonersdilemma.usecases;

import fr.uga.l3miage.pc.prisonersdilemma.entities.Player;
import fr.uga.l3miage.pc.prisonersdilemma.services.Round;
import fr.uga.l3miage.pc.prisonersdilemma.utils.*;
import fr.uga.l3miage.pc.prisonersdilemma.services.GameService;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;
import java.util.concurrent.CountDownLatch;

public class Game {
    private final int totalRounds;
    private GameService gameService;
    private int playedRound = 0;
    private boolean availableToJoin = true;
    private final UUID gameId;
    private Round activeRound;
    private final Player thePlayer1;
    private Player thePlayer2;


    public Game(int rounds, String player1Name) {
        this.gameId = UUID.randomUUID();
        this.totalRounds = rounds;
        this.thePlayer1 = new Player(player1Name);
    }
    public ApiResponse<Game> joinGame(String player2Name) {
        if (!this.availableToJoin) {
            return new ApiResponse<>(200, "Cette partie est déjà complète", null);
        }
        this.thePlayer2 = new Player(player2Name);
        this.gameService = new GameService(this.thePlayer1, this.thePlayer2);
        this.availableToJoin = false;
        return new ApiResponse<>(200, "OK", this);
    }

    private void start() {

        //TODO : add /*|| this.player2 == this.player1*/ when we will be able to distinction
        this.gameService.secondPlayerHaveJoinTheGame();

        for (playedRound = 0; playedRound < totalRounds; playedRound++) {

            try {
                activeRound = new Round();
                activeRound.waitForChoices();  // Attend que les deux joueurs aient fait leurs choix
            } catch (InterruptedException e) {
                //TODO : gérer ceci en faisant un continue (saut) après avoir ajouté +1 a totalRounds ou arreter la partie
                e.printStackTrace();
                System.out.println(e.getMessage());
                System.out.println("Crash of synchronisation process, Bye Bye");
                return;
            }

            RoundReward score = ScoringSystem.calculateScore(thePlayer1.getActualRoundDecision(), thePlayer2.getActualRoundDecision());

            thePlayer1.updateScore(score.getPlayer1Reward());
            thePlayer2.updateScore(score.getPlayer2Reward());

            System.out.println("Round " + (playedRound + 1) + ":");
            System.out.println(thePlayer1.getName() + " chose " + thePlayer1.getActualRoundDecision() + " and scored " + score.getPlayer1Reward() + " points.");
            System.out.println(thePlayer2.getName() + " chose " + thePlayer2.getActualRoundDecision() + " and scored " + score.getPlayer2Reward() + " points.\n");

            //attendre que chacun aie lu le résultat : on fait un endpoint qu'on consulte toute les 3 secondes et qui donne les information par rapport au tour en cours comme round
        }

        try {
            activeRound.waitForRoundResultConsultation();
        } catch (InterruptedException e) {
            //TODO : gérer ceci en faisant un continue (saut) après avoir ajouté +1 a totalRounds ou arreter la partie
            e.printStackTrace();
            System.out.println(e.getMessage());
            System.out.println("Crash of synchronisation process, Bye Bye");
            return;
        }
        this.resetPlayersDecisionForNextRound();
        //displayResults();
        cleanMySelfOfTheGlobalMap();
        //désallouer les ressources ?
    }

    public ApiResponse<Game> playGame(UUID playerId, String decision) {
        try {
            GameService.decisionIsValid(decision);
        } catch (Exception e) {
            return new ApiResponse<>(404, "Specified decision not found", this);
        }
        if (gameService.verifyPlayer(playerId, thePlayer1)) {
            thePlayer1.setActualRoundDecision(Decision.valueOf(decision));
            try {
                activeRound.countAPlayerChoice();
            } catch (InterruptedException e) {
                //TODO : gérer ceci en faisant un continue (saut) après avoir ajouté +1 a totalRounds ou arreter la partie
                e.printStackTrace();
                System.out.println(e.getMessage());
                System.out.println("Crash of synchronisation update process, Bye Bye");
                return new ApiResponse<>(500, "Crash of synchronisation update process", this);
            }

        } else if (gameService.verifyPlayer(playerId, thePlayer2)) {
            thePlayer2.setActualRoundDecision(Decision.valueOf(decision));
            try {
                activeRound.countAPlayerChoice();
            } catch (InterruptedException e) {
                //TODO : gérer ceci en faisant un continue (saut) après avoir ajouté +1 a totalRounds ou arreter la partie
                e.printStackTrace();
                System.out.println(e.getMessage());
                System.out.println("Crash of synchronisation update process, Bye Bye");
                return new ApiResponse<>(500, "Crash of synchronisation update process", this);
            }
        } else {
            return new ApiResponse<>(404, "The specified player hasn't been found", this);
        }
        return new ApiResponse<>(200, "OK", this);
    }

    public ApiResponse<Game> getGameState(UUID playerId) {
        try {
            gameService.userExistAndActiveInGame(playerId, thePlayer1, thePlayer2);
        } catch (Exception e) {
            return new ApiResponse<>(404, e.getMessage(), this);
        }
        return new ApiResponse<>(200, "OK", this);
    }

    private void resetPlayersDecisionForNextRound() {
        thePlayer1.setActualRoundDecision(null);
        thePlayer2.setActualRoundDecision(null);
    }

    private void cleanMySelfOfTheGlobalMap() {
        GlobalGameMap gameMap = GlobalGameMap.getInstance();
        gameMap.removeElement(this.gameId);
    }

    public ApiResponse<String> displayResults() {
        //The # will serve to split the strig on the user client to display them one by one
        String resultsText = "Game over!" + "\n" +
                thePlayer1.getName() + " final score: " + thePlayer1.getScore() + "\n" +
                thePlayer2.getName() + " final score: " + thePlayer2.getScore();

        if (thePlayer1.getScore() > thePlayer2.getScore()) {
            resultsText = "\n" + thePlayer1.getName() + " wins!";
        } else if (thePlayer2.getScore() > thePlayer1.getScore()) {
            resultsText = "\n" + thePlayer2.getName() + " wins!";
        } else {
            resultsText = "\n" + "It's a tie!";
        }
        return new ApiResponse<>(200, "OK", resultsText);
    }

    public UUID getGameId() {
        return gameId;
    }

    public void endGame() {
        System.out.println(this.thePlayer1.getName() + " had end the party!, Bye Bye");
        displayResults();
        cleanMySelfOfTheGlobalMap();
        //désallouer les ressources ?
    }
}
