package mmf.mafia;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class GamePreparationActivity extends AppCompatActivity {
    private TextView txtPlayersCount;
    private TextView txtMafiasCount;
    private TextView txtCitizensCount;
    private TextView txtPlayerName;

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
    }

    public void addPlayer(View view) {
        try {
            txtPlayerName = findViewById(R.id.txtPlayerName);
            String playerName = txtPlayerName.getText().toString();
            Shared.classicMafia.addPlayer(playerName);
        } catch (Exception e) {
            showMessage("این بازیکن قبلا به بازی اضافه شده است!");
        }
    }

    private void showMessage(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}
