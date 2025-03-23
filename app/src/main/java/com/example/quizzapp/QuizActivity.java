package com.example.quizzapp;

import android.database.SQLException;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quizzapp.model.Question;
import com.example.quizzapp.model.QuizRepository;

import java.util.List;

public class QuizActivity extends AppCompatActivity {

    private QuizRepository quizRepository;
    private List<Question> questions;
    private int currentQuestionIndex = 0;
    private int score = 0;
    private long timeLeftInMillis = 30000; // 30 secondes pour chaque question
    private CountDownTimer countDownTimer;

    private TextView tvQuestion, tvScore, tvTimer;
    private Button btnOption1, btnOption2, btnOption3, btnOption4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        // Initialiser les éléments UI
        tvQuestion = findViewById(R.id.tv_question);
        tvScore = findViewById(R.id.tv_score);
        tvTimer = findViewById(R.id.tv_timer);
        btnOption1 = findViewById(R.id.btn_option1);
        btnOption2 = findViewById(R.id.btn_option2);
        btnOption3 = findViewById(R.id.btn_option3);
        btnOption4 = findViewById(R.id.btn_option4);

        // Initialiser le repository et récupérer les questions
        quizRepository = new QuizRepository(this);
        try {
            quizRepository.open(); // Assurez-vous que la base de données est ouverte ici
            if (questions == null || questions.isEmpty()) {
                // Gérer le cas où il n'y a pas de questions (par exemple, afficher un message ou insérer des questions par défaut)
                Log.e("QuizActivity", "Aucune question disponible");
                // Insérer des questions par défaut si nécessaire
                quizRepository.insertDefaultQuestions();
                // Réessayez de récupérer les questions après insertion
                questions = quizRepository.getAllQuestions();
            }

            // Afficher la première question
            displayQuestion(currentQuestionIndex);
            quizRepository.close();
        } catch (SQLException e) {
            e.printStackTrace();
            Toast.makeText(this, "Erreur lors de l'accès à la base de données", Toast.LENGTH_SHORT).show();
            return; // Si une erreur se produit, afficher un message et ne pas démarrer le quiz
        }


        // Afficher la première question
        displayQuestion(currentQuestionIndex);

        // Définir les listeners pour chaque bouton de réponse
        btnOption1.setOnClickListener(v -> checkAnswer(btnOption1.getText().toString()));
        btnOption2.setOnClickListener(v -> checkAnswer(btnOption2.getText().toString()));
        btnOption3.setOnClickListener(v -> checkAnswer(btnOption3.getText().toString()));
        btnOption4.setOnClickListener(v -> checkAnswer(btnOption4.getText().toString()));
    }

    // Afficher la question et les options
    private void displayQuestion(int index) {
        if (index < questions.size()) {
            Question currentQuestion = questions.get(index);
            tvQuestion.setText(currentQuestion.getQuestion());
            btnOption1.setText(currentQuestion.getOption1());
            btnOption2.setText(currentQuestion.getOption2());
            btnOption3.setText(currentQuestion.getOption3());
            btnOption4.setText(currentQuestion.getOption4());

            // Démarrer le timer pour la question actuelle
            startTimer();
        } else {
            // Terminer le quiz si toutes les questions ont été posées
            tvQuestion.setText("Quiz terminé !");
            btnOption1.setVisibility(View.GONE);
            btnOption2.setVisibility(View.GONE);
            btnOption3.setVisibility(View.GONE);
            btnOption4.setVisibility(View.GONE);
            tvTimer.setVisibility(View.GONE);
        }
    }

    // Vérifier la réponse donnée et calculer le score
    private void checkAnswer(String selectedAnswer) {
        // Vérifier si la liste des questions est vide
        if (questions == null || questions.isEmpty()) {
            return; // Ne rien faire si la liste est vide
        }

        Question currentQuestion = questions.get(currentQuestionIndex);

        // Si la réponse est correcte, attribuer un score en fonction du temps restant
        if (selectedAnswer.equals(currentQuestion.getAnswer())) {
            int timeBonus = (int) (timeLeftInMillis / 1000); // Bonus basé sur le temps restant
            score += timeBonus;  // Ajouter le score basé sur le temps
        }

        // Passer à la question suivante
        currentQuestionIndex++;
        displayQuestion(currentQuestionIndex);
        updateScore();
    }

    // Démarrer le timer
    private void startTimer() {
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateTimer();
            }

            @Override
            public void onFinish() {
                // Temps écoulé, passer à la question suivante
                checkAnswer("");  // Appel sans réponse pour passer à la question suivante
            }
        }.start();
    }

    // Mettre à jour l'affichage du timer
    private void updateTimer() {
        int seconds = (int) (timeLeftInMillis / 1000);
        tvTimer.setText("Time: " + seconds + "s");
    }

    // Mettre à jour l'affichage du score
    private void updateScore() {
        tvScore.setText("Score: " + score);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}
