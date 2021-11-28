package com.example.cloudcards.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.cloudcards.Presenter.LoginActivityPresenter;
import com.example.cloudcards.R;

public class LoginActivity extends AppCompatActivity {

    EditText username, password;
    Button sign_in;
    LoginActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        // Uncomment this to test registration and login, etc.
//      getApplicationContext().deleteDatabase("cloudcards.db");
        presenter = new LoginActivityPresenter(getApplicationContext());
        username = (EditText) findViewById(R.id.login_username);
        password = (EditText) findViewById(R.id.login_password);
        sign_in = (Button) findViewById(R.id.login_button);

        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usernameText = username.getText().toString();
                String pass = password.getText().toString();
                if (usernameText.equals("") || pass.equals("")) {
                    Toast.makeText(getApplicationContext(), "Please enter all the fields", Toast.LENGTH_SHORT).show();
                } else {
                    presenter.signIn(usernameText, pass);
                }
            }
        });
    }

    /**
     * Switch to Register Activity.
     * @param view
     */
    public void register(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

}