package com.example.equiposdefutbol;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        EditText etUrl = findViewById(R.id.etBaseUrl);
        Button btnSave = findViewById(R.id.btnSave);
        Button btnReset = findViewById(R.id.btnReset);

        SharedPreferences prefs = getSharedPreferences("settings", MODE_PRIVATE);
        String defaultUrl = "https://www.vidalibarraquer.net/android/sports/";
        etUrl.setText(prefs.getString("base_url", defaultUrl));

        btnSave.setOnClickListener(v -> {
            String url = etUrl.getText().toString().trim();
            if (!url.isEmpty()) {
                prefs.edit().putString("base_url", url).apply();
                Toast.makeText(this, "URL guardada", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Ingresa una URL válida", Toast.LENGTH_SHORT).show();
            }
        });

        btnReset.setOnClickListener(v -> {
            etUrl.setText(defaultUrl);
            prefs.edit().putString("base_url", defaultUrl).apply();
            Toast.makeText(this, "URL restaurada", Toast.LENGTH_SHORT).show();
        });
    }
}

