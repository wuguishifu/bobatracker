package com.example.bobatrackerv001.activities.order_history;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.bobatrackerv001.R;
import com.example.bobatrackerv001.activities.login_page.OnGetDataListener;
import com.example.bobatrackerv001.data.order_list.Order;
import com.example.bobatrackerv001.data.order_list.OrderList;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Comparator;

public class OrderHistoryActivity extends AppCompatActivity {

    private static final String SHARED_PREFS = "sharedPrefs";
    private static final String USERNAME = "usernamePref";

    private static final int orderTextViewSpaceLength = 80;
    private static final String star = "\u2605";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        // load the data
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

    private void loadOrdersFromList(Iterable<DataSnapshot> orders) {

        // define the LinearLayout
        LinearLayout orderHistoryLinearLayout = findViewById(R.id.order_history_linearLayout_fragments);

        // if the data has not already been loaded
        if (!OrderList.hasAlreadyLoaded) {
            OrderList.hasAlreadyLoaded = true;

            // clear array list
            OrderList.orders.clear();

            // iterate through each order, ignoring orderID
            orders.forEach(data -> {
                //create a bunch of new Order objects based on the data
                Order order = data.getValue(Order.class);
                assert order != null;

                //add the order to the static ArrayList of orders
                OrderList.orders.add(order);

            });
        }

        // sort items by date
        OrderList.orders.sort(Comparator.comparing(Order::getDate).reversed());

        // iterate through every order in the list and make a TextView for it
        for (Order order : OrderList.orders) {
            // create the newOrderFragment
            RelativeLayout newOrderFrag = generateOrderFragment(order);

            // calculate padding
            int paddingDp = 10;
            float density = OrderHistoryActivity.this.getResources().getDisplayMetrics().density;
            int paddingPixel = (int)(paddingDp * density);

            // set padding
            newOrderFrag.setPadding(0,paddingPixel,0,paddingPixel);
            orderHistoryLinearLayout.addView(newOrderFrag);

            // create the separator
            View separator = new View(OrderHistoryActivity.this);
            separator.setId(View.generateViewId());
            separator.setBackgroundColor(ContextCompat.getColor(OrderHistoryActivity.this, R.color.gray1));
            separator.setMinimumHeight(5);

            orderHistoryLinearLayout.addView(separator);
        }

    }

    private RelativeLayout generateOrderFragment(Order order) {

        int textSize = 20;

        // create new RelativeLayout
        RelativeLayout orderFrag = new RelativeLayout(OrderHistoryActivity.this);

        // setting the new RelativeLayout's params
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.MATCH_PARENT,
            RelativeLayout.LayoutParams.MATCH_PARENT
        );

        // set the RelativeLayout's view ID
        orderFrag.setId(View.generateViewId());
        orderFrag.setLayoutParams(layoutParams);

        // setting the orderText TextView's params
        RelativeLayout.LayoutParams orderTextLP = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );

        // create the orderText TextView
        TextView orderText = new TextView(OrderHistoryActivity.this);

        int dateAsInt = order.getDate();
        int y = dateAsInt/10000;
        int m = (dateAsInt - y * 10000)/100;
        int d = dateAsInt - y * 10000 - m * 100;
        String month = m < 10 ? "0" + m : String.valueOf(m);
        String day   = d < 10 ? "0" + d : String.valueOf(d);
        String year  = y < 10 ? "0" + y : String.valueOf(y);
        String dateAsString =  month + "/" + day + "/" + year;

        double priceAsDouble = order.getPrice();
        String priceAsString = "$";
        if ((int)priceAsDouble * 10 == priceAsDouble * 10) {
            priceAsString += priceAsDouble + "0";
        } else {
            priceAsString += priceAsDouble;
        }

        // add the favorite drink star symbol
        if (order.getFavorite()) {
            priceAsString = star + "\t\t" + priceAsString;
        }

        orderText.setText(Html.fromHtml("<b>" + order.getLocation() + "</b>" +
                "<br />" + "<small>" + dateAsString + "</small>" + "<br />" +
                "<small>" + order.getOrder() + "</small>", Html.FROM_HTML_MODE_LEGACY));
        orderText.setTextAlignment(TextView.TEXT_ALIGNMENT_VIEW_START);
        orderText.setTextColor(ContextCompat.getColor(OrderHistoryActivity.this, R.color.gray1));
        orderText.setTextSize(textSize);

        // setting the priceText TextView's params
        RelativeLayout.LayoutParams priceTextLP = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );

        // create the priceText TextView
        TextView priceText = new TextView(OrderHistoryActivity.this);
        priceText.setText(priceAsString);
        priceText.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);
        priceText.setTextColor(ContextCompat.getColor(OrderHistoryActivity.this, R.color.gray1));
        priceText.setTextSize(textSize);

        // adding constraints
        orderTextLP.addRule(RelativeLayout.START_OF, orderFrag.getId());
        priceTextLP.addRule(RelativeLayout.ALIGN_PARENT_END, orderFrag.getId());

        // adding TextViews to the RelativeLayout
        orderFrag.addView(orderText, orderTextLP);
        orderFrag.addView(priceText, priceTextLP);

        // return
        return orderFrag;
    }

    /**
     * method for handling reading the password from the Firebase Database
     * @param reference - the DatabaseReference to read
     * @param listener - a com.example.bobatrackerv001.activities.login_page.OnGetDataListener
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