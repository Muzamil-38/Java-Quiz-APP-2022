package com.example.quizapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class WelcomeActivity extends AppCompatActivity {
    Button btn_start;
    TextView highScoreText;
    private static final int REQUEST_CODE_QUIZ=1;
    public static final String EXTRA_SUBJECT="extraSubject";
    private int highScore ;
    private Spinner spinnerSubject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        init();

        String[] subjectLevels = Question.getAllSubject();
        ArrayAdapter<String> adapterSubject = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,subjectLevels);
        adapterSubject.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSubject.setAdapter(adapterSubject);



        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startQuiz();
            }
        });

    }
    private void startQuiz(){
        String subject = spinnerSubject.getSelectedItem().toString();
        Intent startQuizIntent =new Intent(WelcomeActivity.this,QuestionActivity.class);
        startQuizIntent.putExtra(EXTRA_SUBJECT,subject);
        startActivityForResult(startQuizIntent,REQUEST_CODE_QUIZ);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQUEST_CODE_QUIZ){
            if (resultCode==RESULT_OK){
                int score = data.getIntExtra(QuestionActivity.EXTRA_SCORE,0);
                if(score>highScore){
                    updateHighScore(score);
                }
            }
        }
    }

    private void updateHighScore(int highScoreNew){
        highScore= highScoreNew;
        highScoreText.setText("HighScore: "+highScore);
    }


    public void init(){
        btn_start = findViewById(R.id.btnStartquiz);
        highScoreText= findViewById(R.id.textHighScore);
        spinnerSubject=findViewById(R.id.spinner);
    }
}