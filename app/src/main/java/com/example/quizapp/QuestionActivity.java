package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.security.PublicKey;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class QuestionActivity extends AppCompatActivity {
    private TextView txtQuestion,txtViewScore,txtQuestionCount,txtViewCountDown;
    private RadioGroup rbGroup;
    private RadioButton rb1,rb2,rb3;
    private Button btnConfirmNext;
    private List<Question> questionList;
    private int questionCounter;
    private int questionCountTotal;
    private Question currentQuestion;
    private int score;
    private boolean answered;
    private long bacKPressedTime;


    private ColorStateList textColorDefaultCd;
    private CountDownTimer countDownTimer;
    private long timeLeftInMillis;

    public static final String EXTRA_SCORE = "extraScore";
    private static final long COUNTDOWN_IN_MILLIS = 30000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        init();


        textColorDefaultCd=txtViewCountDown.getTextColors();

        Intent intent = getIntent();
        String subject = intent.getStringExtra(WelcomeActivity.EXTRA_SUBJECT);


        QuizDBHelper dbHelper= new QuizDBHelper(this);
        questionList=dbHelper.getQuestion(subject);
        questionCountTotal= questionList.size();
        Collections.shuffle(questionList);
        showNextQuestion();
        btnConfirmNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!answered){
                    if (rb1.isChecked()||rb2.isChecked()||rb3.isChecked()){
                        checkAnswer();
                    }else {
                        Toast.makeText(QuestionActivity.this, "Plz Select An Answer", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    showNextQuestion();
                }
            }
        });
    }

    private void showNextQuestion() {
        rbGroup.clearCheck();

        if (questionCounter<questionCountTotal){
            currentQuestion=questionList.get(questionCounter);

            txtQuestion.setText(currentQuestion.getQuestion());
            rb1.setText(currentQuestion.getOption1());
            rb2.setText(currentQuestion.getOption2());
            rb3.setText(currentQuestion.getOption3());
            questionCounter++;
            txtQuestionCount.setText("Question: "+questionCounter+"/"+questionCountTotal);
            answered=false;
            btnConfirmNext.setText("Confirm");

            timeLeftInMillis = COUNTDOWN_IN_MILLIS;
            startCountDown();

        }else {
            finishQuiz();
        }

    }

    private void startCountDown() {
        countDownTimer = new CountDownTimer(timeLeftInMillis,1000){

            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis=millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                timeLeftInMillis=0;
                updateCountDownText();
                checkAnswer();
            }
        }.start();
    }

    private void updateCountDownText() {
        int minutes=(int) (timeLeftInMillis/1000)/60;
        int seconds = (int) (timeLeftInMillis/1000)%60;

        String timeFormatted = String.format(Locale.getDefault(),"%02d:%02d",minutes,seconds);
        txtViewCountDown.setText(timeFormatted);
        if(timeLeftInMillis<1000){
            txtViewCountDown.setTextColor(Color.RED);
        }else {
            txtViewCountDown.setTextColor(textColorDefaultCd);
        }
    }

    private void checkAnswer(){
        answered=true;
        countDownTimer.cancel();
        RadioButton rbSelected = findViewById(rbGroup.getCheckedRadioButtonId());
        int answerNumber = rbGroup.indexOfChild(rbSelected)+1;
        if(answerNumber==currentQuestion.getAnsNumber()){
            score+=5;
            txtViewScore.setText("Score: "+score);
        }
        showSolution();
    }

    private void showSolution() {

        switch (currentQuestion.getAnsNumber()){
            case 1:
                txtQuestion.setText("Answer 1 is correct");
                break;
            case 2:
                txtQuestion.setText("Answer 2 is correct");
                break;
            case 3:
                txtQuestion.setText("Answer 3 is correct");
                break;
        }
        if (questionCounter<questionCountTotal){
            btnConfirmNext.setText("Next");
        }else{
            btnConfirmNext.setText("Finish");
        }
    }

    private void finishQuiz() {
        Intent resultIntent = new Intent();
        resultIntent.putExtra(EXTRA_SCORE,score);
        setResult(RESULT_OK,resultIntent);

        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (bacKPressedTime +2000>System.currentTimeMillis()){
            finish();
        }else {
            Toast.makeText(this, "Press Back Again to Finish", Toast.LENGTH_SHORT).show();
        }
        bacKPressedTime=System.currentTimeMillis();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer!=null){
            countDownTimer.cancel();
        }
    }

    public void init() {
        txtQuestion=findViewById(R.id.txtQuestion);
        txtViewScore=findViewById(R.id.txtScore);
        txtQuestionCount=findViewById(R.id.txtQuestionNumber);
        txtViewCountDown=findViewById(R.id.textTimer);
        rbGroup=findViewById(R.id.radio_group);
        rb1=findViewById(R.id.radio_btn1);
        rb2=findViewById(R.id.radio_btn2);
        rb3=findViewById(R.id.radio_btn3);
        btnConfirmNext=findViewById(R.id.btn_confirm_next);
    }
}