package com.example.quizapp;

import android.provider.BaseColumns;

public final class QuizContract {

    private QuizContract(){}

    public static class QuestionsTable implements BaseColumns {
        public static final String TABLE_NAME = "quiz_questions";
        public static final String Column_Question = "questions";
        public static final String Column_Option1 = "option1";
        public static final String Column_Option2 = "option2";
        public static final String Column_Option3 = "option3";
        public static final String Column_Answer_NR = "answer_nr";
        public static final String Column_SUBJECT = "subject";
    }
}