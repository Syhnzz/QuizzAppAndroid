package com.example.quizzapp.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class QuizRepository {
    private SQLiteDatabase database;
    private QuizDBHelper dbHelper;

    public QuizRepository(Context context) {
        dbHelper = new QuizDBHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public long insertQuestion(Question question) {
        ContentValues values = new ContentValues();
        values.put(QuizDBHelper.COLUMN_QUESTION, question.getQuestion());
        values.put(QuizDBHelper.COLUMN_OPTION1, question.getOption1());
        values.put(QuizDBHelper.COLUMN_OPTION2, question.getOption2());
        values.put(QuizDBHelper.COLUMN_OPTION3, question.getOption3());
        values.put(QuizDBHelper.COLUMN_OPTION4, question.getOption4());
        values.put(QuizDBHelper.COLUMN_ANSWER, question.getAnswer());

        return database.insert(QuizDBHelper.TABLE_QUESTIONS, null, values);
    }

    public void insertDefaultQuestions() {
        List<Question> defaultQuestions = QuestionData.getSampleQuestions();  // Récupère les questions par défaut

        for (Question question : defaultQuestions) {
            insertQuestion(question);  // Insère chaque question dans la base de données
        }
    }

    public List<Question> getAllQuestions() {
        List<Question> questions = new ArrayList<>();
        Cursor cursor = database.query(QuizDBHelper.TABLE_QUESTIONS, null, null, null, null, null, null);

        // Vérifiez que le curseur n'est pas vide
        if (cursor != null && cursor.moveToFirst()) {
            do {
                Question question = new Question();

                // Récupère les colonnes
                int columnIndexId = cursor.getColumnIndex(QuizDBHelper.COLUMN_ID);
                int columnIndexQuestion = cursor.getColumnIndex(QuizDBHelper.COLUMN_QUESTION);
                int columnIndexOption1 = cursor.getColumnIndex(QuizDBHelper.COLUMN_OPTION1);
                int columnIndexOption2 = cursor.getColumnIndex(QuizDBHelper.COLUMN_OPTION2);
                int columnIndexOption3 = cursor.getColumnIndex(QuizDBHelper.COLUMN_OPTION3);
                int columnIndexOption4 = cursor.getColumnIndex(QuizDBHelper.COLUMN_OPTION4);
                int columnIndexAnswer = cursor.getColumnIndex(QuizDBHelper.COLUMN_ANSWER);

                if (columnIndexId != -1) {
                    question.setId(cursor.getInt(columnIndexId));
                }
                if (columnIndexQuestion != -1) {
                    question.setQuestion(cursor.getString(columnIndexQuestion));
                }
                if (columnIndexOption1 != -1) {
                    question.setOption1(cursor.getString(columnIndexOption1));
                }
                if (columnIndexOption2 != -1) {
                    question.setOption2(cursor.getString(columnIndexOption2));
                }
                if (columnIndexOption3 != -1) {
                    question.setOption3(cursor.getString(columnIndexOption3));
                }
                if (columnIndexOption4 != -1) {
                    question.setOption4(cursor.getString(columnIndexOption4));
                }
                if (columnIndexAnswer != -1) {
                    question.setAnswer(cursor.getString(columnIndexAnswer));
                }

                questions.add(question);

            } while (cursor.moveToNext());
            cursor.close();
        } else {
            cursor.close();
            System.out.println("La base de donnée ne marche pas");
        }
        return questions;
    }


}
