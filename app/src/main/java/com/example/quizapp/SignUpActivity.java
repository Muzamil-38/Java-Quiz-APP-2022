package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {

    EditText fName,lName,password,email;
    Button register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        init();
        DBHelper helper = new DBHelper(this);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName =fName.getText().toString().trim();
                String lastName= lName.getText().toString().trim();
                String signPassword=password.getText().toString().trim();
                String emailAdd=email.getText().toString().trim();
                if (firstName.equals("")||lastName.equals("")||signPassword.equals("")||emailAdd.equals("")){
                    Toast.makeText(SignUpActivity.this, "Fields are Empty", Toast.LENGTH_SHORT).show();
                }else {
                    ContentValues values=new ContentValues();
                    values.put(helper.r_firstName,firstName);
                    values.put(helper.r_lastName,lastName);
                    values.put(helper.r_password,signPassword);
                    values.put(helper.r_email,emailAdd);
                    helper.insertData(values);
                    Toast.makeText(SignUpActivity.this, "Successfully Added", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void init(){
        fName=findViewById(R.id.email);
        lName=findViewById(R.id.lnameSignup);
        password=findViewById(R.id.passwordSignup);
        email=findViewById(R.id.addressSignup);
        register=findViewById(R.id.btnRegister);
    }
}