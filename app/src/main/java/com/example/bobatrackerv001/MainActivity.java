package com.example.bobatrackerv001;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
    private static String user_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //buttons
        Button login = (Button) findViewById(R.id.login);
        Button signUp = (Button) findViewById(R.id.signUp);

        //text boxes
        EditText usernameTextField = (EditText) findViewById(R.id.editTextTextPersonName);
        EditText passwordTextField = (EditText) findViewById(R.id.editTextTextPassword2);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = usernameTextField.getText().toString();
                String password = passwordTextField.getText().toString();

                System.out.println(username);
                System.out.println(password);

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users/" + username + "/pwd");
                String pass = getPasswordOfUser(username, ref);


                if (pass != null && pass.equals(password)) {
                    startActivity(new Intent(MainActivity.this, HomeActivity.class));
                }
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, HomeActivity.class));

                database = FirebaseDatabase.getInstance().getReference();

            }
        });
    }

    private void writeNewUser(String id, String password) {
        Visits visits = new Visits();
        User user = new User(id, password, visits);
        database.child("users").child(id).setValue(user);
    }

    private String getPasswordOfUser(String uid, DatabaseReference ref) {
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() != null) {
                    user_password = snapshot.getValue().toString();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        System.out.println(user_password);
        return user_password;
    }
}