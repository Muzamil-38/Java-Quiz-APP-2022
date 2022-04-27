package com.example.quizapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.quizapp.QuizContract.*;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class QuizDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "MyAwesomeQuiz.db";
    private static final int DATABASE_VERSION = 1;
    private SQLiteDatabase db;

    public QuizDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;
        final String SQL_CREATE_QUESTIONS_TABLE="CREATE TABLE "+QuestionsTable.TABLE_NAME+" ( "+
                QuestionsTable._ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                QuestionsTable.Column_Question+" TEXT, "+
                QuestionsTable.Column_Option1+" TEXT, "+
                QuestionsTable.Column_Option2+" TEXT, "+
                QuestionsTable.Column_Option3+" TEXT, "+
                QuestionsTable.Column_Answer_NR+" INTEGER,"+
                QuestionsTable.Column_SUBJECT + " TEXT"+
                ")";
        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);

        fillQuestionsTable();

    }

    private void fillQuestionsTable() {
        Question q1 = new Question("Maths: A is Correct ","A","B","C",1,Question.SUBJECT_MATHS);
        addQuestion(q1);
        Question q2 = new Question("History: B is Correct ","A","B","C",2,Question.SUBJECT_HISTORY);
        addQuestion(q2);
        Question q3 = new Question("Maths: C is Correct ","A","B","C",3,Question.SUBJECT_MATHS);
        addQuestion(q3);
        Question q4 = new Question("History: A is Correct Again ","A","B","C",1,Question.SUBJECT_HISTORY);
        addQuestion(q4);
    }
    private void addQuestion(Question question){
        ContentValues contentValues=new ContentValues();
        contentValues.put(QuestionsTable.Column_Question,question.getQuestion());
        contentValues.put(QuestionsTable.Column_Option1,question.getOption1());
        contentValues.put(QuestionsTable.Column_Option2,question.getOption2());
        contentValues.put(QuestionsTable.Column_Option3,question.getOption3());
        contentValues.put(QuestionsTable.Column_Answer_NR,question.getAnsNumber());
        contentValues.put(QuestionsTable.Column_SUBJECT,question.getSubject());

        db.insert(QuestionsTable.TABLE_NAME,null,contentValues);
    }
    public List<Question> getAllQuestion(){
        List<Question> questionList = new ArrayList<>();
        db=getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM "+QuestionsTable.TABLE_NAME,null);
        if (c.moveToFirst()){
            do {
                Question question = new Question();
                question.setQuestion(c.getString(c.getColumnIndex(QuestionsTable.Column_Question)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionsTable.Column_Option1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionsTable.Column_Option2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionsTable.Column_Option3)));
                question.setAnsNumber(c.getInt(c.getColumnIndex(QuestionsTable.Column_Answer_NR)));
                question.setSubject(c.getString(c.getColumnIndex(QuestionsTable.Column_SUBJECT)));
                questionList.add(question);
            }while (c.moveToNext());
        }
        c.close();
        return questionList;
    }

    public List<Question> getQuestion(String subject){
        List<Question> questionList = new ArrayList<>();
        db=getReadableDatabase();
        String[] selectArg = new String[]{subject};
        Cursor c = db.rawQuery("Select * From "+QuestionsTable.TABLE_NAME+
                " Where "+QuestionsTable.Column_SUBJECT+" =?",selectArg);
        if (c.moveToFirst()){
            do {
                Question question = new Question();
                question.setQuestion(c.getString(c.getColumnIndex(QuestionsTable.Column_Question)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionsTable.Column_Option1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionsTable.Column_Option2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionsTable.Column_Option3)));
                question.setAnsNumber(c.getInt(c.getColumnIndex(QuestionsTable.Column_Answer_NR)));
                question.setSubject(c.getString(c.getColumnIndex(QuestionsTable.Column_SUBJECT)));
                questionList.add(question);
            }while (c.moveToNext());
        }
        c.close();
        return questionList;
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+QuestionsTable.TABLE_NAME);
        onCreate(db);
    }
}
