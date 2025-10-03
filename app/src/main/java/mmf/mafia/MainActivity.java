package mmf.mafia;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import mmf.mafia.scenario.ClassicMafia;

public class MainActivity extends AppCompatActivity {
    private EditText numPlayersCount;
    private Button btnStartGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        this.btnStartGame = findViewById(R.id.btnEnterNames);
    }

    public void enterNames(View view) {
        this.numPlayersCount = findViewById(R.id.numPlayersCount);
        int playersCount = Integer.parseInt(numPlayersCount.getText().toString());
        try {
            Shared.classicMafia = new ClassicMafia(playersCount);
            Intent intent = new Intent(MainActivity.this, GamePreparationActivity.class);
            startActivity(intent);
            finish();
        } catch (IllegalArgumentException ex) {
            showMessage("تعداد بازیکنان باید بین " + ClassicMafia.MINIMUM_NUMBER_OF_PLAYERS + " و " + ClassicMafia.MAXIMUM_NUMBER_OF_PLAYERS + " باشد");
        }
    }

    private void showMessage(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}