package com.example.cloudcards;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
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