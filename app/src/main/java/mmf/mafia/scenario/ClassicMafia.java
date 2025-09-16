package mmf.mafia.scenario;

import java.util.Set;

public class ClassicMafia {
    private final int MINIMUM_NUMBER_OF_PLAYERS = 5;
    private final int MAXIMUM_NUMBER_OF_PLAYERS = 12;
    private final int playersCount;
    private final int citizensCount;
    private final int mafiasCount;
    private Set<Player> players;

    public ClassicMafia(int playersCount) {
        if (playersCount < MINIMUM_NUMBER_OF_PLAYERS) {
            throw new IllegalArgumentException("Number of players should be greater than " + MINIMUM_NUMBER_OF_PLAYERS);
        }

        if (playersCount > MAXIMUM_NUMBER_OF_PLAYERS) {
            throw new IllegalArgumentException("Number of players should be less than " + MAXIMUM_NUMBER_OF_PLAYERS);
        }


        this.playersCount = playersCount;
        this.mafiasCount = calculateMafiasCount(playersCount);
        this.citizensCount = this.playersCount - this.mafiasCount;
    }

    private int calculateMafiasCount(int playersCount) {
        int mafiasCount;

        if (playersCount < 8) {
            mafiasCount = 2;
        } else if (playersCount < 12) {
            mafiasCount = 3;
        } else {
            mafiasCount = 4;
        }

        return mafiasCount;
    }

    public int getCitizensCount() {
        return citizensCount;
    }

    public int getMafiasCount() {
        return mafiasCount;
    }

    public void startTheGame() {

    }
}
