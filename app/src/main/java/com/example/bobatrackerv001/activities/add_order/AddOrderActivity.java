package com.example.bobatrackerv001.activities.add_order;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bobatrackerv001.R;
import com.example.bobatrackerv001.activities.home_page.HomeActivity;
import com.example.bobatrackerv001.activities.login_page.LoginActivity;
import com.example.bobatrackerv001.activities.signup_page.SignupActivity;
import com.example.bobatrackerv001.data.order_list.Order;
import com.example.bobatrackerv001.data.order_list.OrderHistory;
import com.example.bobatrackerv001.data.order_list.OrderList;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class AddOrderActivity extends AppCompatActivity {

    private int dateAsInt;

    private static final String SHARED_PREFS = "sharedPrefs";
    private static final String USERNAME = "usernamePref";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_order);

        // changing the gravity of the store name EditText if someone enters text
        EditText storeNameEditText = findViewById(R.id.store_name_edit_text);
        storeNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    storeNameEditText.setGravity(Gravity.START);
                } else {
                    storeNameEditText.setGravity(Gravity.END | Gravity.BOTTOM);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // changing the gravity of the order description
        EditText orderDetailsEditText = findViewById(R.id.order_details_edit_text);
        orderDetailsEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    orderDetailsEditText.setGravity(Gravity.START);
                } else {
                    orderDetailsEditText.setGravity(Gravity.END | Gravity.BOTTOM);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // changing the gravity of the price description
        EditText priceEditText = findViewById(R.id.price_entry);
        priceEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    priceEditText.setGravity(Gravity.START);
                } else {
                    priceEditText.setGravity(Gravity.END | Gravity.BOTTOM);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // displaying the date picker
        final Calendar calendarPicker = Calendar.getInstance();
        EditText enterDateEditText = findViewById(R.id.date_entry);
        DatePickerDialog.OnDateSetListener date = (view, year, month, dayOfMonth) -> {
            calendarPicker.set(Calendar.YEAR, year);
            calendarPicker.set(Calendar.MONTH, month);
            calendarPicker.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            String myFormat = "MM/dd/yy";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            enterDateEditText.setText(sdf.format(calendarPicker.getTime()));

            int yearAsValue = 10000 * (calendarPicker.get(Calendar.YEAR) % 100);
            int monthAsValue = 100 * (calendarPicker.get(Calendar.MONTH));
            dateAsInt = yearAsValue + monthAsValue + calendarPicker.get(Calendar.DAY_OF_MONTH);

            // add date to int conversion here using Calendar.get(Calendar.YEAR()) etc.

        };
        enterDateEditText.setOnClickListener(v -> new DatePickerDialog(AddOrderActivity.this, date,
                calendarPicker.get(Calendar.YEAR), calendarPicker.get(Calendar.MONTH),
                calendarPicker.get(Calendar.DAY_OF_MONTH)).show());
        enterDateEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    enterDateEditText.setGravity(Gravity.START);
                } else {
                    enterDateEditText.setGravity(Gravity.END | Gravity.BOTTOM);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        Button confirm = findViewById(R.id.button_add_order);
        confirm.setOnClickListener(v -> {
            Order order = new Order();
            order.setDate(dateAsInt);
            order.setLocation(storeNameEditText.getText().toString());
            order.setOrder(orderDetailsEditText.getText().toString());
            order.setPrice(Double.parseDouble(priceEditText.getText().toString()));


            SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
            String user = sharedPreferences.getString(USERNAME, null);

            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users/" + user + "/order_history/");
            ref.push().setValue(order);

            OrderList.hasAlreadyLoaded = false;

            Intent intent = new Intent(AddOrderActivity.this, HomeActivity.class);
            startActivity(intent);
        });
    }
}