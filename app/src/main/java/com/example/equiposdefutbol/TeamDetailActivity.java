package com.example.equiposdefutbol;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class TeamDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_detail);

        String league = getIntent().getStringExtra("LEAGUE");
        String teamCode = getIntent().getStringExtra("TEAM_CODE");

        if (league == null) league = "";
        if (teamCode == null) teamCode = "";

        loadTeamDetail(league, teamCode);
    }

    private void loadTeamDetail(String league, String teamCode) {
        if (!NetworkUtils.isNetworkAvailable(this)) {
            Toast.makeText(this, "No hay conexión a internet", Toast.LENGTH_SHORT).show();
            return;
        }

        new Thread(() -> {
            try {
                String baseUrl = getSharedPreferences("settings", MODE_PRIVATE)
                    .getString("base_url", "https://www.vidalibarraquer.net/android/sports/");
                String url = baseUrl + league + "/" + teamCode.toLowerCase() + ".json";

                String jsonString = NetworkUtils.downloadJson(url);
                JsonObject jsonObject = JsonParser.parseString(jsonString).getAsJsonObject();

                int titles = jsonObject.has("titulos") ? jsonObject.get("titulos").getAsInt() : 0;
                String stadium = jsonObject.has("estadio") ? jsonObject.get("estadio").getAsString() : "N/A";
                String shield = jsonObject.has("escudo") ? jsonObject.get("escudo").getAsString() : "";

                runOnUiThread(() -> {
                    ((TextView) findViewById(R.id.tvTeamName)).setText(teamCode);
                    ((TextView) findViewById(R.id.tvTitles)).setText("Títulos: " + titles);
                    ((TextView) findViewById(R.id.tvStadium)).setText("Estadio: " + stadium);

                    if (shield != null && !shield.isEmpty()) {
                        Glide.with(TeamDetailActivity.this)
                            .load(shield)
                            .into((ImageView) findViewById(R.id.imgShield));
                    }
                });
            } catch (Exception e) {
                runOnUiThread(() ->
                    Toast.makeText(TeamDetailActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                );
            }
        }).start();
    }
}

