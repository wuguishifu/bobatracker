package com.example.bobatrackerv001.login_page;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;

public interface OnGetDataListener {

    void onSuccess(DataSnapshot dataSnapshot);
    void onStart();
    void onFail();
}
