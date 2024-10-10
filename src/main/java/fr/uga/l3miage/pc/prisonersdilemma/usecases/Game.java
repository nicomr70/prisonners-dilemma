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

        for (playedRound = 1; playedRound <= totalRounds; playedRound++) {

            RoundReward score;
            try {
                activeRound = new Round();

                activeRound.waitForChoices();  // Attend que les deux joueurs aient fait leurs choix

                score = ScoringSystem.calculateScore(thePlayer1.getActualRoundDecision(), thePlayer2.getActualRoundDecision());

                thePlayer1.updateScore(score.getPlayer1Reward());
                thePlayer2.updateScore(score.getPlayer2Reward());

                activeRound.waitForRoundResultConsultation();

            } catch (InterruptedException e) {
                //TODO : gérer ceci en faisant un continue (saut) après avoir aretiré +1 a playedRound ou arreter la partie
                e.printStackTrace();
                System.out.println(e.getMessage());
                System.out.println("Crash of synchronisation process, Bye Bye");
                return;
            }

            String messageForTheRound = "Round " + (playedRound) + ":\n" +
                    thePlayer1.getName() + " chose " + thePlayer1.getActualRoundDecision() + " and scored " + score.getPlayer1Reward() + " points.\n" +
                    thePlayer2.getName() + " chose " + thePlayer2.getActualRoundDecision() + " and scored " + score.getPlayer2Reward() + " points.\n";

            activeRound.setResulDataCompilation(messageForTheRound);

            //attendre que chacun aie lu le résultat : on fait un endpoint qu'on consulte toute les 3 secondes et qui donne les information par rapport au tour en cours comme round
        }

        try {
            activeRound.initTheGameResultConsultation();
            activeRound.waitForTheGameResultConsultation();
        } catch (InterruptedException e) {
            //TODO
            e.printStackTrace();
            System.out.println(e.getMessage());
            System.out.println("Crash of result synchronisation process, Bye Bye");
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

        if (!activeRound.isReadyForPlayersChoices())
            return new ApiResponse<>(503, "Round player choice listen Unavailable", this);

        if (gameService.verifyPlayer(playerId, thePlayer1)) {
            thePlayer1.setActualRoundDecision(Decision.valueOf(decision));
            try {
                activeRound.countAPlayerChoice();
            } catch (InterruptedException e) {
                //TODO : gérer ceci en faisant un continue (saut) après avoir aretiré +1 a playedRound ou arreter la partie
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
                //TODO : gérer ceci en faisant un continue (saut) après avoir retiré +1 a playedRound ou arreter la partie
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
            if (!activeRound.isReadyForRoundResultConsultation()) {
                return new ApiResponse<>(503, "Round result still Unavailable", this);
            }
            activeRound.countAPlayerChoice();
        } catch (Exception e) {
            return new ApiResponse<>(404, e.getMessage(), this);
        }
        return new ApiResponse<>(200, activeRound.getResulDataCompilation(), this);
    }

    public ApiResponse<Game> giveUpGame(UUID playerId) {
        //TODO trouver le joueur, et remplacer ses tours de passage par une strategie
        //TODO vérifier si le joueur a déjà joué pour ce tout avant d'abandonner et agir en
        // conséquence selon le cas

        //TODO juste après avoir reçu la décision d'un joueur pour le tout actuel on véifie
        // que l'autre joeur est connecté ou a déjà donné sa décision, sinon on va déclencher le mouvement suivant la
        // strategie qu'il a choisi en partant
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

        if (!activeRound.isReadyForResultConsultation())
            return new ApiResponse<>(503, "The game final results aren't available yet", "");

        //The # will serve to split the strig on the user client to display them one by one

        String resultsText;

        resultsText = "Game over!" + "\n" +
                thePlayer1.getName() + " final score: " + thePlayer1.getScore() + "\n" +
                thePlayer2.getName() + " final score: " + thePlayer2.getScore();

        if (thePlayer1.getScore() > thePlayer2.getScore()) {
            resultsText = resultsText + "\n" + thePlayer1.getName() + " wins!";
        } else if (thePlayer2.getScore() > thePlayer1.getScore()) {
            resultsText = resultsText + "\n" + thePlayer2.getName() + " wins!";
        } else {
            resultsText = resultsText + "\n" + "It's a tie!";
        }
        return new ApiResponse<>(200, "OK", resultsText);
    }

    public UUID getGameId() {
        return gameId;
    }

    public boolean isAvailableToJoin() {
        return availableToJoin;
    }

    public void endGame() {
        System.out.println(this.thePlayer1.getName() + " had end the party!, Bye Bye");
        displayResults();
        cleanMySelfOfTheGlobalMap();
        //désallouer les ressources ?
    }
}
