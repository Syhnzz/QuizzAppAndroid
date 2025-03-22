package com.example.quizzapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.quizzapp.model.QuizRepository;
import com.example.quizzapp.model.Question;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private QuizRepository quizRepository;
    private TextView questionText;
    private Button option1Button, option2Button, option3Button, option4Button;
    private List<Question> questions;
    private int currentQuestionIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        quizRepository = new QuizRepository(this);
        quizRepository.open();
        questions = quizRepository.getAllQuestions();

        questionText = findViewById(R.id.questionText);
        option1Button = findViewById(R.id.option1Button);
        option2Button = findViewById(R.id.option2Button);
        option3Button = findViewById(R.id.option3Button);
        option4Button = findViewById(R.id.option4Button);

        loadQuestion();

        option1Button.setOnClickListener(v -> checkAnswer(option1Button.getText().toString()));
        option2Button.setOnClickListener(v -> checkAnswer(option2Button.getText().toString()));
        option3Button.setOnClickListener(v -> checkAnswer(option3Button.getText().toString()));
        option4Button.setOnClickListener(v -> checkAnswer(option4Button.getText().toString()));
    }

    private void loadQuestion() {
        if (currentQuestionIndex < questions.size()) {
            Question question = questions.get(currentQuestionIndex);
            questionText.setText(question.getQuestion());
            option1Button.setText(question.getOption1());
            option2Button.setText(question.getOption2());
            option3Button.setText(question.getOption3());
            option4Button.setText(question.getOption4());
        }
    }

    private void checkAnswer(String selectedOption) {
        Question currentQuestion = questions.get(currentQuestionIndex);
        if (selectedOption.equals(currentQuestion.getAnswer())) {
            // Réponse correcte
        } else {
            // Réponse incorrecte
        }
        currentQuestionIndex++;
        loadQuestion();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        quizRepository.close();
    }
}
