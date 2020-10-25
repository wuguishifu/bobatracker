package com.example.bobatrackerv001;

public class User {

    private String uid;

    public static String password;

    private Visits visits;

    public User() {

    }

    public User(String username, String password, Visits visits) {
        this.uid = username;
        this.visits = visits;
        password = password;
    }
}
