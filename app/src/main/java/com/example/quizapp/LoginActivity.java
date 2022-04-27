package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    Button btnSignUp,btnLogin;
    DBHelper db;
    EditText Lpassword,Lemail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        db=new DBHelper(this);



        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signUp = new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(signUp);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email= Lemail.getText().toString();
                String pass= Lpassword.getText().toString();
                Boolean checkEmailpass=db.emailPassword(email,pass);
                if (checkEmailpass==true) {
                    Toast.makeText(LoginActivity.this, "SuccessFully Login", Toast.LENGTH_SHORT).show();
                    Intent welcomeIntent = new Intent(LoginActivity.this, WelcomeActivity.class);
                    startActivity(welcomeIntent);
                }

                else Toast.makeText(LoginActivity.this, "Wrong Email and Password", Toast.LENGTH_SHORT).show();
            }
        });

    }


    public void init(){
        btnSignUp = findViewById(R.id.btnSignup);
        Lpassword=findViewById(R.id.password);
        Lemail=findViewById(R.id.email);
        btnLogin=findViewById(R.id.btnLogin);
    }
}