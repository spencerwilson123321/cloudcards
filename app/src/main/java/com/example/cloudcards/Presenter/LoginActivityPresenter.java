package com.example.cloudcards.Presenter;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.cloudcards.Model.DBHelper;
import com.example.cloudcards.View.HomepageActivity;

public class LoginActivityPresenter {

    Context loginContext;
    DBHelper DB;

    public LoginActivityPresenter(Context context) {
        loginContext = context;
        DB = new DBHelper(loginContext);
    }

    public void signIn(String username, String password) {
        Boolean checkEmailPassword = DB.checkUsernamePassword(username, password);
        if (checkEmailPassword == true) {
            Toast.makeText(loginContext, "Sign in success", Toast.LENGTH_SHORT).show();
            int userID = DB.getUserID(username, password);
            Intent intent = new Intent(loginContext, HomepageActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("userID", userID);
            intent.putExtra("username", username);
            loginContext.startActivity(intent);
        } else {
            Toast.makeText(loginContext, "Invalid Email and Password", Toast.LENGTH_SHORT).show();
        }
    }

}
