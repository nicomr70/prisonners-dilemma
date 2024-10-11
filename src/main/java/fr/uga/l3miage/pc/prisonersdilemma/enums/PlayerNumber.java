package fr.uga.l3miage.pc.prisonersdilemma.enums;

public enum PlayerNumber {
    PLAYER_ONE(0),
    PLAYER_TWO(1);

    private final int index;

    PlayerNumber(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

}
