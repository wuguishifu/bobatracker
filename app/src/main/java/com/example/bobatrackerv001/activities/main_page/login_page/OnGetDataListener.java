package com.example.bobatrackerv001.activities.main_page.login_page;

import com.google.firebase.database.DataSnapshot;

public interface OnGetDataListener {

    void onSuccess(DataSnapshot dataSnapshot);
    void onStart();
    void onFail();
}
