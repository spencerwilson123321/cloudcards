package com.example.cloudcards;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cloudcards.database.DBHelper;

public class Login extends AppCompatActivity {

    EditText email, password;
    Button sign_in;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
//        getApplicationContext().deleteDatabase("cloudcards.db");
        email = (EditText) findViewById(R.id.login_email);
        password = (EditText) findViewById(R.id.login_password);
        sign_in = (Button) findViewById(R.id.login_button);
        db = new DBHelper(this);

        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailText = email.getText().toString();
                String pass = password.getText().toString();
                if (emailText.equals("") || pass.equals("")) {
                    Toast.makeText(getApplicationContext(), "Please enter all the fields", Toast.LENGTH_SHORT).show();
                } else {
                    Boolean checkEmailPassword = db.checkEmailPassword(emailText, pass);
                    if (checkEmailPassword == true) {
                        Toast.makeText(getApplicationContext(), "Sign in success", Toast.LENGTH_SHORT).show();
                        int userID = db.getUserID(emailText, pass);
                        Intent intent = new Intent(getApplicationContext(), Homepage.class);
                        intent.putExtra("userID", userID);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "Invalid Email and Password", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


    }

    /**
     * Switch to Register Activity.
     * @param view
     */
    public void register(View view) {
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }

}