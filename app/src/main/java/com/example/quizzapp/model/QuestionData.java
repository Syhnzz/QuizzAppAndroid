package com.example.quizzapp.model;

import java.util.ArrayList;
import java.util.List;

public class QuestionData {

    // Méthode pour obtenir une liste de questions prédéfinies
    public static List<Question> getSampleQuestions() {
        List<Question> questions = new ArrayList<>();

        // Ajout des questions avec leurs options et la réponse correcte
        questions.add(new Question("Quelle est la capitale de la France ?", "Paris", "Londres", "Berlin", "Rome", "Paris"));
        questions.add(new Question("Qui a écrit 'Les Misérables' ?", "Victor Hugo", "Emile Zola", "Marcel Proust", "Albert Camus", "Victor Hugo"));
        questions.add(new Question("Quel est le plus grand océan du monde ?", "Atlantique", "Indien", "Arctique", "Pacifique", "Pacifique"));
        questions.add(new Question("Quel est l'élément chimique dont le symbole est O ?", "Oxygène", "Or", "Ozone", "Osmium", "Oxygène"));
        questions.add(new Question("Qui a peint la Joconde ?", "Vincent van Gogh", "Pablo Picasso", "Claude Monet", "Léonard de Vinci", "Léonard de Vinci"));

        return questions;
    }
}
