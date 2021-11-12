package com.example.cloudcards;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cloudcards.database.DBHelper;

public class Register extends AppCompatActivity {

    EditText email, password1, password2;
    Button register_button;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        email = (EditText) findViewById(R.id.register_email);
        password1 = (EditText) findViewById(R.id.register_password_1);
        password2 = (EditText) findViewById(R.id.register_password_2);
        register_button = (Button) findViewById(R.id.register_button);
        db = new DBHelper(this);

        register_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String emailText = email.getText().toString();
                String pass1 = password1.getText().toString();
                String pass2 = password2.getText().toString();

                if (emailText.equals("") || pass1.equals("") || pass2.equals("")) {
                    Toast.makeText(getApplicationContext(), "Please enter all fields", Toast.LENGTH_SHORT).show();
                } else {
                    if (pass1.equals(pass2)) {
                        Boolean checkUser = db.checkEmail(emailText);
                        if (checkUser == false) {
                            Boolean insert = db.insertData(emailText, pass1);
                            if (insert) {
                                Toast.makeText(getApplicationContext(), "Registration Success", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "Registration Fail", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Email already registered.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Passwords do not match", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });


    }

    /**
     * Switch to Login Activity
     * @param view
     */
    public void cancel(View view) {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }




}