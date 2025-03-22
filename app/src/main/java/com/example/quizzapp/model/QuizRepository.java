package com.example.quizzapp.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

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

    public List<Question> getAllQuestions() {
        List<Question> questions = new ArrayList<>();
        Cursor cursor = database.query(QuizDBHelper.TABLE_QUESTIONS, null, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Question question = new Question();
                question.setId(cursor.getInt(cursor.getColumnIndex(QuizDBHelper.COLUMN_ID)));
                question.setQuestion(cursor.getString(cursor.getColumnIndex(QuizDBHelper.COLUMN_QUESTION)));
                question.setOption1(cursor.getString(cursor.getColumnIndex(QuizDBHelper.COLUMN_OPTION1)));
                question.setOption2(cursor.getString(cursor.getColumnIndex(QuizDBHelper.COLUMN_OPTION2)));
                question.setOption3(cursor.getString(cursor.getColumnIndex(QuizDBHelper.COLUMN_OPTION3)));
                question.setOption4(cursor.getString(cursor.getColumnIndex(QuizDBHelper.COLUMN_OPTION4)));
                question.setAnswer(cursor.getString(cursor.getColumnIndex(QuizDBHelper.COLUMN_ANSWER)));
                questions.add(question);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return questions;
    }
}
