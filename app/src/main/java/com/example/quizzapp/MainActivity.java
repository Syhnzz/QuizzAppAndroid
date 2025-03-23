package com.example.quizzapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);  // Affiche le layout de l'activité

        // Initialiser les boutons
        Button startQuizButton = findViewById(R.id.btn_start_quiz);
        Button settingsButton = findViewById(R.id.btn_settings);
        Button quitButton = findViewById(R.id.btn_quit);

        // Gérer le clic sur le bouton "Démarrer le Quiz"
        startQuizButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startQuiz();
            }
        });

        // Gérer le clic sur le bouton "Paramètres"
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSettings();
            }
        });

        // Gérer le clic sur le bouton "Quitter"
        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();  // Quitter l'application
            }
        });
    }

    // Démarrer l'activité du quiz
    private void startQuiz() {
        Intent intent = new Intent(MainActivity.this, QuizActivity.class);
        startActivity(intent);
    }

    // Ouvrir l'activité des paramètres
    private void openSettings() {
        Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
        startActivity(intent);
    }
}
