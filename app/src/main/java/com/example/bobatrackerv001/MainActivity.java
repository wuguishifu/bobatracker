package com.example.bobatrackerv001;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference database;
    private static String password;

    private static boolean passwordIsShown;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Button showPassword = findViewById(R.id.button_show_password);
        passwordIsShown = false;

        showPassword.setOnClickListener(v -> {
            passwordIsShown = !passwordIsShown;
            EditText passwordTextEntry = findViewById(R.id.password_entry);
            if (passwordIsShown) {
                passwordTextEntry.setTransformationMethod(null);
                showPassword.setText(R.string.hide_password_button);
            } else {
                passwordTextEntry.setTransformationMethod(new PasswordTransformationMethod());
                showPassword.setText(R.string.show_password_button);
            }
            passwordTextEntry.setSelection(passwordTextEntry.getText().length());
        });



    }
}