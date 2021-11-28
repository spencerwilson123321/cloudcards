package com.example.cloudcards.Presenter;

import android.content.Context;
import android.widget.Toast;

import com.example.cloudcards.Model.DBHelper;

public class RegisterActivityPresenter {

    Context registerContext;
    DBHelper DB;

    public RegisterActivityPresenter(Context c) {
        registerContext = c;
        DB = new DBHelper(registerContext);
    }

    public void register(String email, String pass) {
        Boolean checkUser = DB.checkEmail(email);
        if (checkUser == false) {
            Boolean insert = DB.insertUser(email, pass);
            if (insert) {
                Toast.makeText(registerContext, "Registration Success", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(registerContext, "Registration Fail", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(registerContext, "Email already registered.", Toast.LENGTH_SHORT).show();
        }
    }
}
