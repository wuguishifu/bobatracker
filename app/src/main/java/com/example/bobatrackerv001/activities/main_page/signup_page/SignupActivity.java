package com.example.bobatrackerv001.activities.main_page.signup_page;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bobatrackerv001.R;
import com.example.bobatrackerv001.activities.main_page.home_page.HomeActivity;
import com.example.bobatrackerv001.activities.main_page.login_page.OnGetDataListener;
import com.example.bobatrackerv001.data.order_list.OrderHistory;
import com.example.bobatrackerv001.data.user.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignupActivity extends AppCompatActivity {

    private static final String SHARED_PREFS = "sharedPrefs";
    private static final String USERNAME = "usernamePref";
    private static final String AUTH = "authPref";

    static boolean passwordIsShown = false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        TextView errorText = findViewById(R.id.login_error2);
        errorText.setText("");

        Button confirm = findViewById(R.id.button_confirm);
        Button showPassword = findViewById(R.id.button_show_password2);

        EditText usernameEntry = findViewById(R.id.username_entry);
        EditText passwordEntry = findViewById(R.id.password_entry);
        EditText repeatPasswordEntry = findViewById(R.id.repeat_password_entry);

        String[] username = new String[1];
        String[] password = new String[1];
        String[] repeatPassword = new String[1];

        confirm.setOnClickListener(v -> {
            username[0] = usernameEntry.getText().toString();
            password[0] = passwordEntry.getText().toString();
            repeatPassword[0] = repeatPasswordEntry.getText().toString();

            if (username[0].equals("")) {
                displayEmptyMessage("Username");
            } else if (password[0].equals("")) {
                displayEmptyMessage("Password");
            } else if (repeatPassword[0].equals("") || !password[0].equals(repeatPassword[0])) {
                displayEmptyMessage("Password");
            } else {
                readData(FirebaseDatabase.getInstance().getReference("users/" + username[0]), new OnGetDataListener() {
                    @Override
                    public void onSuccess(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getValue() != null) {
                            displayUsernameTakenError();
                        } else {
                            // generate new user and push to the Firebase Database
                            createNewUser(username[0], password[0]);

                            // add user and authentication to the sharedPreferences
                            SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putBoolean(AUTH, true);
                            editor.putString(USERNAME, username[0]);
                            editor.apply();

                            // launch the home page activity
                            Intent intent = new Intent(SignupActivity.this, HomeActivity.class);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onStart() {
                        Log.d("ONSTART", "Started");
                    }

                    @Override
                    public void onFail() {
                        Log.d("ONFAILURE", "Failed");
                    }
                });
            }
        });

        // show/hide password button onClickListener creation
        passwordIsShown = false;
        showPassword.setOnClickListener(v -> {
            passwordIsShown = !passwordIsShown;
            if (passwordIsShown) {
                passwordEntry.setTransformationMethod(null);
                repeatPasswordEntry.setTransformationMethod(null);
                showPassword.setText(R.string.hide_password_button);
            } else {
                passwordEntry.setTransformationMethod(new PasswordTransformationMethod());
                repeatPasswordEntry.setTransformationMethod(new PasswordTransformationMethod());
                showPassword.setText(R.string.show_password_button);
            }
            passwordEntry.setSelection(passwordEntry.getText().length());
            repeatPasswordEntry.setSelection(repeatPasswordEntry.getText().length());
        });
    }

    /**
     * displays an error message that one of the text boxes isn't filled out
     * @param s - which text box isn't filled out
     */
    @SuppressLint("SetTextI18n")
    public void displayEmptyMessage(String s) {
        TextView errorText = findViewById(R.id.login_error2);
        errorText.setText(s + " is required.");
    }

    /**
     * displays an error message that the username is already taken
     */
    @SuppressLint("SetTextI18n")
    public void displayUsernameTakenError() {
        TextView errorText = findViewById(R.id.login_error2);
        errorText.setText("Username already taken.");
    }

    /**
     * creates a new user on the Firebase Database
     * @param username - the new user's username
     * @param password - the new user's password
     */
    public void createNewUser(String username, String password) {
        DatabaseReference newUserRef = FirebaseDatabase.getInstance().getReference("users/" + username);
        newUserRef.setValue(new User(password, new OrderHistory()));
    }

    /**
     * method for handling reading the password from the Firebase Database
     * @param reference - the DatabaseReference to read
     * @param listener - a com.example.bobatrackerv001.activities.main_page.login_page.OnGetDataListener
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
}