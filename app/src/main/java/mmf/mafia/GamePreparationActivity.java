package mmf.mafia;

import static mmf.mafia.Shared.classicMafia;

import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;

import mmf.mafia.scenario.Player;
import mmf.mafia.scenario.exceptions.DuplicatedPlayerException;
import mmf.mafia.scenario.exceptions.FullDeckException;

public class GamePreparationActivity extends AppCompatActivity {
    private TextView txtPlayersCount;
    private TextView txtMafiasCount;
    private TextView txtCitizensCount;
    private TextView txtPlayerName;
    private Button btnStartGame;
    private SoundPool soundPool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_game_preparation);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        txtPlayersCount = findViewById(R.id.txtPlayersCount);
        txtPlayersCount.setText("تعداد  بازیکنان:  " + Shared.PLAYERS_COUNT);

        txtMafiasCount = findViewById(R.id.txtMafiasCount);
        txtMafiasCount.setText("تعداد مافیا:  " + Shared.MAFIAS_COUNT);

        txtCitizensCount = findViewById(R.id.txtCitizensCount);
        txtCitizensCount.setText("تعداد شهروند: " + Shared.CITIZENS_COUNT);

        btnStartGame = findViewById(R.id.btnStartGame);
        btnStartGame.setEnabled(false);

        this.soundPool = initSoundPool();
    }

    private SoundPool initSoundPool() {
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        return new SoundPool.Builder()
                .setMaxStreams(1)
                .setAudioAttributes(audioAttributes)
                .build();
    }

    public void addPlayer(View view) {
        txtPlayerName = findViewById(R.id.txtPlayerName);
        String playerName = txtPlayerName.getText().toString();

        try {
            classicMafia.addPlayer(playerName);
            if (classicMafia.allPlayersAreAdded()) {
                btnStartGame.setEnabled(true);
            }
        } catch (DuplicatedPlayerException e) {
            showMessage(playerName + " قبلا اضافه شده است!");
        } catch (FullDeckException e) {
            showMessage("همه بازیکنان اضافه شده اند.");
        }
    }

    private void showMessage(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    public void startGame(View view) {
        // assign the roles
        classicMafia.assignRoles();
        classicMafia.setSpecialRoles();

        // all players sleep
        allPlayersSleep();
        sleep(1000);

        // Mafias wake up
        mafiasWakeUp();
        sleep(1000);

        // Don shows his like
        donShowsLike();
        sleep(1000);

        // Mafias consult 60 seconds
        mafiaConsultFor60Seconds();

        // Mafias sleep
        mafiasSleep();

        // All players wake up
        allPlayersWakeUp();

        // if number of remaining mafias is greater than or equal to citizens, mafia wins
        if (classicMafia.aliveMafiasCount() > classicMafia.aliveCitizensCount()) {
            mafiaWinsTheGame();
        }

        if (classicMafia.getDeckSize() > 5 && classicMafia.aliveMafiasCount() == classicMafia.aliveCitizensCount()) {
            mafiaWinsTheGame();
        }

        // All players talk for 90 seconds, with a 60 seconds challenge
        int deckSize = classicMafia.getDeckSize();
        Random random = new Random();
        int firstSeatPlayerIndex = random.nextInt(deckSize) + 1;
        String firstSeatPlayerName = classicMafia.getPlayers().get(firstSeatPlayerIndex).getName();
        List<Player> deck = classicMafia.sortDeck(firstSeatPlayerName);
        for (Player player : deck) {
            player.talk(TALK_TURN_TIME);
        }

        // take vote
        Scanner scanner = new Scanner(System.in);
        for (Player player : deck) {
            String input = scanner.nextLine();
            int votes = Integer.parseInt(input);
            player.setFirstVote(votes);

            if (votes >= requiredNumberOfVotes()) {
                player.defense();
            }
        }

        // If there are any players in defense, they talk for 90 seconds
        List<Player> defendants = players.stream().filter(Player::shouldDefend).collect(Collectors.toList());
        for (Player defendant : defendants) {
            wait(DEFENSE_TIME);
        }

        // Revote
        for (Player defendant : defendants) {
            String input = scanner.nextLine();
            int secondVote = Integer.parseInt(input);
            defendant.setSecondVotes(secondVote);
        }

        if (defendants.size() == 1) {
            if (defendants.get(0).getSecondVotes() > requiredNumberOfVotes()) {
                defendants.get(0).exit();
            }
        } else {
            int maxVote = defendants.stream()
                    .mapToInt(Player::getSecondVotes)
                    .max()
                    .getAsInt();
            List<Player> candidates = defendants.stream()
                    .filter(defendant -> defendant.getSecondVotes() == maxVote)
                    .collect(Collectors.toList());

            if (candidates.size() == 1) {
                candidates.get(0).exit();
            } else {
                Player exitingPlayer = drawDeathLottery(candidates);
                exitingPlayer.die();
            }
        }

        // if there is no mafia left in the game, city wins
        if (mafias.isEmpty()) {
            cityWinsTheGame();
        }

        // if number of remaining mafias is greater than or equal to citizens, mafia wins
        if (mafias.size() > citizens.size() || mafias.size() == citizens.size()) {
            mafiaWinsTheGame();
        }
    }

    private void mafiaWinsTheGame() {
        int soundId = soundPool.load(this, R.raw.mafia_won_the_game, 1);
        soundPool.play(soundId, 1, 1, 0, 0, 1);
    }

    private void allPlayersWakeUp() {
        int soundId = soundPool.load(this, R.raw.all_players_wakeup, 1);
        soundPool.play(soundId, 1, 1, 0, 0, 1);
    }

    private void mafiasSleep() {
        int soundId = soundPool.load(this, R.raw.mafia_team_sleep, 1);
        soundPool.play(soundId, 1, 1, 0, 0, 1);
    }

    private void mafiaConsultFor60Seconds() {
        int soundId = soundPool.load(this, R.raw.mafia_team_has_60_seconds_to_consult, 1);
        soundPool.play(soundId, 1, 1, 0, 0, 1);
        sleep(60);
    }

    private static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void donShowsLike() {
        int soundId = soundPool.load(this, R.raw.don_shows_his_like, 1);
        soundPool.play(soundId, 1, 1, 0, 0, 1);
    }

    private void mafiasWakeUp() {
        int soundId = soundPool.load(this, R.raw.mafia_team_wakeup, 1);
        soundPool.play(soundId, 1, 1, 0, 0, 1);
    }

    private void allPlayersSleep() {
        int soundId = soundPool.load(this, R.raw.all_players_sleep, 1);
        soundPool.play(soundId, 1, 1, 0, 0, 1);
    }
}
