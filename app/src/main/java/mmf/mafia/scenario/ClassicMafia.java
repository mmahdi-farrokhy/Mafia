package mmf.mafia.scenario;

import java.util.Set;

public class ClassicMafia {
    public static final int TALK_TURN_TIME = 90;
    private static final int DEFENSE_TIME = 90;
    private final int MINIMUM_NUMBER_OF_PLAYERS = 5;
    private final int MAXIMUM_NUMBER_OF_PLAYERS = 12;
    private final int playersCount;
    private final int citizensCount;
    private final int mafiasCount;
    private final List<Player> players = new ArrayList<>();
    private List<Player> mafias = new ArrayList<>();
    private List<Player> citizens = new ArrayList<>();
    private Player don;
    private Player detective;
    private Player doctor;
    private final int MAFIA_CONSULT_TIME = 60000;


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

    public void addPlayer(Player player) {
        if (players.size() < playersCount) {
            players.add(player);
        } else {
            throw new IllegalArgumentException("All " + playersCount + " players are already added to the deck");
        }
    }

    public void startTheGame() {
        if (players.size() != playersCount) {
            throw new RuntimeException("Not all players are added to the deck");
        }

        // assign the roles
        assignRoles(players);
        mafias = players.stream().filter(Player::isMafia).toList();
        citizens = players.stream().filter(Player::isCitizen).toList();
        don = mafias.stream().filter(Player::isDon).findFirst().get();
        detective = citizens.stream().filter(Player::isDetective).findFirst().get();
        doctor = citizens.stream().filter(Player::isDoctor).findFirst().get();

        // all players sleep
        allPlayersSleep();

        // Mafias wake up
        mafiasWakeUp();

        // Don shows his like
        don.showsLike();

        // Mafias consult 60 seconds
        wait(MAFIA_CONSULT_TIME);

        // Mafias sleep
        mafiasSleep();

        // All players wake up
        allPlayersWakeUp();

        // All players talk for 90 seconds, with a 60 seconds challenge
        String firstSeatPlayerName = "x";
        List<Player> deck = sortDeck(firstSeatPlayerName);
        for (Player player : deck) {
            player.talk(TALK_TURN_TIME);
        }

        // take vote
        Scanner scanner = new Scanner(System.in);
        for (Player player : deck) {
            String input = scanner.nextLine();
            int votes = Integer.parseInt(input);

            if (votes >= requiredNumberOfVotes()) {
                player.defense();
            }
        }

        // If there are any players in defense, they talk for 90 seconds
        List<Player> defendants = players.stream().filter(Player::shouldDefend).toList();
        for (Player defendant : defendants) {
            wait(DEFENSE_TIME);
        }

        // Revote
        List<Integer> secondVotes = new ArrayList<>();
        for (Player defendant : defendants) {
            String input = scanner.nextLine();
            int secondVote = Integer.parseInt(input);
            secondVotes.add(secondVote);
        }

        if (defendants.size() == 1) {
            if (secondVotes.get(0) > requiredNumberOfVotes()) {
                defendants.get(0).exit();
            }
        } else {
            int maxSecondVote = secondVotes.stream().max(Integer::compareTo).get();
            secondVotes.indexOf()
            defendants.stream().max(player -> player.secondVotes())
        }
    }

    private int requiredNumberOfVotes() {
        if (players.size() > 10) {
            return 6;
        } else if (players.size() > 8) {
            return 5;
        } else if (players.size() > 6) {
            return 4;
        } else {
            return 3;
        }
    }

    private List<Player> sortDeck(String firstSeatPlayerName) {
        if (players.stream().noneMatch(player -> player.getName().equals(firstSeatPlayerName))) {
            throw new IllegalArgumentException("First player " + firstSeatPlayerName + " does not exist in the deck");
        }

        Player firstSetPlayer = players
                .stream()
                .filter(player -> player.getName().equals(firstSeatPlayerName))
                .findFirst()
                .get();

        int firstSeatPlayerIndexInDeck = players.indexOf(firstSetPlayer);
        List<Player> deck = new ArrayList<>();
        deck.addAll(players.subList(firstSeatPlayerIndexInDeck, players.size()));
        deck.addAll(players.subList(0, firstSeatPlayerIndexInDeck));
        return deck;
    }

    private void mafiasSleep() {
        System.out.println("Mafias must sleep");
    }

    private static void wait(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void mafiasWakeUp() {
        System.out.println("Wake up mafias");
    }

    private void allPlayersSleep() {
        System.out.println("All players must sleep");
    }

    private void allPlayersWakeUp() {
        System.out.println("All players must wake up");
    }

    private void assignRoles(List<Player> players) {
        int numberOfAssignedRoles = 0;
        Collections.shuffle(players);

        players.get(0).assignRole(new Don());
        numberOfAssignedRoles++;

        for (int i = 1; i < mafiasCount; i++) {
            players.get(i).assignRole(new SimpleMafia());
            numberOfAssignedRoles++;
        }

        players.get(numberOfAssignedRoles).assignRole(new Detective());
        numberOfAssignedRoles++;
        players.get(numberOfAssignedRoles).assignRole(new Doctor());
        numberOfAssignedRoles++;

        for (int i = numberOfAssignedRoles; i < playersCount; i++) {
            players.get(i).assignRole(new SimpleCitizen());
            numberOfAssignedRoles++;
        }
    }
}
