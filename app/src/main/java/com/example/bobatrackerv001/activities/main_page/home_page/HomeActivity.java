package com.example.bobatrackerv001.activities.main_page.home_page;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bobatrackerv001.R;
import com.example.bobatrackerv001.activities.add_order.AddOrderActivity;
import com.example.bobatrackerv001.activities.main_page.login_page.LoginActivity;
import com.example.bobatrackerv001.activities.order_history.OrderHistoryActivity;
import com.example.bobatrackerv001.data.order_list.OrderList;

public class HomeActivity extends AppCompatActivity {

    private static final String SHARED_PREFS = "sharedPrefs";
    private static final String AUTH = "authPref";
    private static final String USERNAME = "usernamePref";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button buttonAddOrder = findViewById(R.id.button_add_order);
        Button buttonOrderHistory = findViewById(R.id.button_order_history);
        Button buttonFavoriteOrders = findViewById(R.id.button_favorite_orders);
        Button buttonSignOut = findViewById(R.id.button_log_out);

        buttonOrderHistory.setOnClickListener(v -> launchOrderHistoryActivity());
        buttonAddOrder.setOnClickListener(v -> launchAddOrderActivity());

        // sign out
        buttonSignOut.setOnClickListener(v -> {
            OrderList.hasAlreadyLoaded = false;
            removeAuthentication();
            launchLoginActivity();
        });
    }

    /**
     * gets the String value of the username saved in sharedPreferences
     * @return the username
     */
    public String getSavedUsername() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        return sharedPreferences.getString(USERNAME, null);
    }

    /**
     * removes authentication from app
     */
    public void removeAuthentication() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(AUTH, false);
        editor.apply();
    }

    public void launchOrderHistoryActivity() {
        Intent intent = new Intent(HomeActivity.this, OrderHistoryActivity.class);
        startActivity(intent);
    }

    public void launchAddOrderActivity() {
        Intent intent = new Intent(HomeActivity.this, AddOrderActivity.class);
        startActivity(intent);
    }

    /**
     * launch the sign up page activity
     */
    public void launchLoginActivity() {
        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    /**
     * disables the back button
     */
    @Override
    public void onBackPressed() {
        // Do nothing
    }
}