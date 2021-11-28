package com.example.cloudcards.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cloudcards.Presenter.RegisterActivityPresenter;
import com.example.cloudcards.R;

public class RegisterActivity extends AppCompatActivity {

    EditText username, password1, password2;
    Button register_button;
    RegisterActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        presenter = new RegisterActivityPresenter(getApplicationContext());
        username = (EditText) findViewById(R.id.register_username);
        password1 = (EditText) findViewById(R.id.register_password_1);
        password2 = (EditText) findViewById(R.id.register_password_2);
        register_button = (Button) findViewById(R.id.register_button);
        register_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String usernameText = username.getText().toString();
                String pass1 = password1.getText().toString();
                String pass2 = password2.getText().toString();

                if (usernameText.equals("") || pass1.equals("") || pass2.equals("")) {
                    Toast.makeText(getApplicationContext(), "Please enter all fields", Toast.LENGTH_SHORT).show();
                } else {
                    if (pass1.equals(pass2)) {
                        presenter.register(usernameText, pass1);
                    } else {
                        Toast.makeText(getApplicationContext(), "Passwords do not match", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

    }

    /**
     * Switch to LoginActivity
     * @param view
     */
    public void cancel(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }




}