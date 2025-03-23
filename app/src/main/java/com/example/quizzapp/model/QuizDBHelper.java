package com.example.quizzapp.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class QuizDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "quiz_db";
    public static final int DATABASE_VERSION = 2;  // Incrémentez la version

    public static final String TABLE_QUESTIONS = "questions";
    public static final String TABLE_SCORES = "scores";  // Nouvelle table pour stocker les scores
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_QUESTION = "question";
    public static final String COLUMN_OPTION1 = "option1";
    public static final String COLUMN_OPTION2 = "option2";
    public static final String COLUMN_OPTION3 = "option3";
    public static final String COLUMN_OPTION4 = "option4";
    public static final String COLUMN_ANSWER = "answer";

    public static final String COLUMN_SCORE_ID = "id";
    public static final String COLUMN_SCORE = "score";  // Colonne pour stocker le meilleur score

    private static final String CREATE_TABLE_QUESTIONS = "CREATE TABLE " + TABLE_QUESTIONS + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_QUESTION + " TEXT, " +
            COLUMN_OPTION1 + " TEXT, " +
            COLUMN_OPTION2 + " TEXT, " +
            COLUMN_OPTION3 + " TEXT, " +
            COLUMN_OPTION4 + " TEXT, " +
            COLUMN_ANSWER + " TEXT);";

    private static final String CREATE_TABLE_SCORES = "CREATE TABLE " + TABLE_SCORES + " (" +
            COLUMN_SCORE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_SCORE + " INTEGER);"; // Table pour stocker un seul score

    public QuizDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_QUESTIONS);
        db.execSQL(CREATE_TABLE_SCORES);  // Création de la table des scores
        Log.d("QuizDB", "Database and tables created.");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCORES);  // Supprimer la table des scores
        onCreate(db);
    }
}
