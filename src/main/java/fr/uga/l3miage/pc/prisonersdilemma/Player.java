package fr.uga.l3miage.pc.prisonersdilemma;

import fr.uga.l3miage.pc.prisonersdilemma.enums.PlayerNumber;
import lombok.Getter;

public class Player {
    @Getter
    private PlayerNumber playerNumber;
    @Getter
    private String username;

    public Player(String username, PlayerNumber playerNumber){
        this.username = username;
        this.playerNumber = playerNumber;
    }

}
