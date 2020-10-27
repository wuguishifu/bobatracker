package com.example.bobatrackerv001.login_page;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.bobatrackerv001.R;
import com.example.bobatrackerv001.home_page.HomeActivity;
import com.example.bobatrackerv001.signup_page.SignupActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private static boolean passwordIsShown;

    private static final String SHARED_PREFS = "sharedPrefs";
    private static final String AUTH = "authPref";
    private static final String USERNAME = "usernamePref";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // if the app is authenticated just go ahead and launch the home page instead of going
        // through the whole sign in process. There's probably a better way of doing this but
        // as of now i'm not entirely sure.
        if (checkAuth()) {
            launchHomePage();
        }

        EditText passwordEntry = findViewById(R.id.password_entry);

        // show/hide password button onClickListener creation
        Button showPassword = findViewById(R.id.button_show_password);
        passwordIsShown = false;
        showPassword.setOnClickListener(v -> {
            passwordIsShown = !passwordIsShown;
            if (passwordIsShown) {
                passwordEntry.setTransformationMethod(null);
                showPassword.setText(R.string.hide_password_button);
            } else {
                passwordEntry.setTransformationMethod(new PasswordTransformationMethod());
                showPassword.setText(R.string.show_password_button);
            }
            passwordEntry.setSelection(passwordEntry.getText().length());
        });

        //sign-up button onClickListener creation
        Button signUpButton = findViewById(R.id.button_sign_up);
        signUpButton.setOnClickListener(v -> launchSignUpPage());


        // login button onClickListener creation
        Button loginButton = findViewById(R.id.button_login);
        EditText usernameEntry = findViewById(R.id.username_entry);
        loginButton.setOnClickListener(v -> {

            ((TextView)findViewById(R.id.login_error)).setText("");

            String username = usernameEntry.getText().toString();
            String password = passwordEntry.getText().toString();

            readData(FirebaseDatabase.getInstance().getReference("users/" + username + "/pwd"), new OnGetDataListener() {
                @Override
                public void onSuccess(DataSnapshot passwordSnapshot) {
                    System.out.println("Found password");
                    if (passwordSnapshot.getValue() != null && passwordSnapshot.getValue().toString().equals(password)) {
                        // if the password is correct, launch the new activity
                        Log.d("SUCCESS", "Launching Home Page");

                        // save the authentication in the sharedPreferences of this app
                        saveAuthentication(username);

                        // retrieve order history to populate the OrderList
                        getOrderHistory();

                        // launch the home page
                        launchHomePage();

                    } else {
                        // if the password is incorrect
                        toggleLoginError();

                        // send a message to the log
                        Log.d("ERROR", "Password Incorrect");
                    }
                }

                @Override
                public void onStart() {

                }

                @Override
                public void onFail() {
                    Log.d("ONFAILURE", "Could not resolve symbol: " + password);
                }
            });
        });
    }

    /**
     * retrieves data from the firebase about order history -- to be implemented
     */
    public void getOrderHistory() {

    }

    /**
     * save authentication into app database
     */
    public void saveAuthentication(String username) {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(AUTH, true);
        editor.putString(USERNAME, username);
        editor.apply();
    }

    /**
     * check if app is currently authenticated (if someone has logged in)
     * @return if th app is currently authenticated
     */
    public boolean checkAuth() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        return sharedPreferences.getBoolean(AUTH, false);
    }

    /**
     * launch the home page activity
     */
    public void launchHomePage() {
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(intent);
    }

    /**
     * launch the sign up page activity
     */
    public void launchSignUpPage() {
        Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
        startActivity(intent);
    }

    /**
     * method for displaying the login error textbox
     */
    public void toggleLoginError() {
        TextView loginErrorTextView = findViewById(R.id.login_error);
        if (loginErrorTextView.getText().toString().equals("")) {
            loginErrorTextView.setText(R.string.incorrectUsernamePassword);
        }
    }

    /**
     * method for handling reading the password from the Firebase Database
     * @param reference - the DatabaseReference to read
     * @param listener - a com.example.bobatrackerv001.login_page.OnGetDataListener
     */
    public void readData(DatabaseReference reference, final OnGetDataListener listener) {
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listener.onSuccess(snapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    /**
     * disables the back button
     */
    @Override
    public void onBackPressed() {
        // Do nothing
    }
}