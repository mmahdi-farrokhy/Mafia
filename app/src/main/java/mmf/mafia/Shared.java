package mmf.mafia;

import mmf.mafia.scenario.ClassicMafia;

public class Shared {
    private Shared() {
        throw new Error("Could not initialize this class");
    }

    public static int PLAYERS_COUNT = 0;
    public static int MAFIAS_COUNT = 0;
    public static int CITIZENS_COUNT = 0;
    public static ClassicMafia classicMafia;
}
