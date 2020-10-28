package com.example.bobatrackerv001.order_history;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.bobatrackerv001.R;
import com.example.bobatrackerv001.login_page.OnGetDataListener;
import com.example.bobatrackerv001.order_list.Order;
import com.example.bobatrackerv001.order_list.OrderList;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class OrderHistoryActivity extends AppCompatActivity {

    private static final String SHARED_PREFS = "sharedPrefs";
    private static final String USERNAME = "usernamePref";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        loadData();

    }

    /**
     * loads data from the firebase server
     */
    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String userID = sharedPreferences.getString(USERNAME, null);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("users/" + userID + "/order_history");
        readData(ref, new OnGetDataListener() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                loadOrdersFromList(dataSnapshot.getChildren());
            }

            @Override
            public void onStart() {
                Log.d("ONSTART", "Loading Order History Data");
            }

            @Override
            public void onFail() {
                Log.d("ONFAILURE", "Failed to Load Order History Data");
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void loadOrdersFromList(Iterable<DataSnapshot> orders) {

        // define the LinearLayout
        LinearLayout orderHistoryLinearLayout = findViewById(R.id.order_history_linearLayout_fragments);

        // iterate through each order, ignoring orderID
            orders.forEach(data -> {
                //create a bunch of new Order objects based on the data
                Order order = data.getValue(Order.class);
                order.setOrderID(data.getKey());

                //add the order to the static ArrayList of orders
                OrderList.orders.add(order);

                //error testing
                //System.err.println(order);

                // create a new textView for the order and add it to the linear layout
                TextView newOrderTextView = new TextView(OrderHistoryActivity.this);
                newOrderTextView.setTextColor(ContextCompat.getColor(OrderHistoryActivity.this, R.color.white));
                newOrderTextView.setText(Html.fromHtml("<b>" + order.getLocation() + "</b>" +
                        "<br />" + "<small>" + order.getDateAsString() + "</small>" + "<br />" +
                        "<small>" + order.getOrder() + "</small>", Html.FROM_HTML_MODE_LEGACY));
                orderHistoryLinearLayout.addView(newOrderTextView);
            });

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

}