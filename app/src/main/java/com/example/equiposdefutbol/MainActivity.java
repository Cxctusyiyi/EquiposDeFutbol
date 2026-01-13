package com.example.equiposdefutbol;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private int[] leagues = {R.drawable.ic_laliga, R.drawable.ic_premier, R.drawable.ic_seriea, R.drawable.ic_ligue1, R.drawable.ic_bundesliga};
    private String[] leagueNames = {"laliga", "premier", "seriea", "ligue1", "bundesliga"};
    private int[] imageIds = {R.id.imgLiga1, R.id.imgLiga2, R.id.imgLiga3, R.id.imgLiga4, R.id.imgLiga5};

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

        for (int i = 0; i < imageIds.length; i++) {
            AppCompatImageView imageView = findViewById(imageIds[i]);
            imageView.setImageResource(leagues[i]);
            final int index = i;
            imageView.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, TeamsActivity.class);
                intent.putExtra("LEAGUE", leagueNames[index]);
                startActivity(intent);
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}