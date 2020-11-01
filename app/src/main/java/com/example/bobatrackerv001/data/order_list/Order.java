package com.example.bobatrackerv001.data.order_list;

public class Order {

    private String order;
    private String location;
    private double price;
    private int date;
    private boolean favorite;
    private String orderID;

    /**
     * default constructor
     * @param order - string of the order
     * @param price - the price in form $0000.00
     * @param date  - the date in form mmddyy -> august 30 2000: 83000 (leading zeros removed)
     */
    public Order(String order, double price, int date, boolean isFavorite, String location) {
        this.order = order;
        this.price = price;
        this.date = date;
        this.favorite = isFavorite;
        this.location  = location;
    }

    public Order() {}

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getOrderID() {
        return this.orderID;
    }

    public String getLocation() {
        return this.location;
    }

    public String getOrder() {
        return this.order;
    }

    public boolean getFavorite() {
        return favorite;
    }

    public double getPrice() {
        return this.price;
    }

    public int getDate() {
        return this.date;
    }

    /**
     * im not even going to write a comment for this one
     * @return date as int in form 83000
     */
    public int getDateAsInt() {
        return this.date;
    }

    /**
     * what do u think lmao
     * @return the price in form $100.99
     */
    public String getPriceAsString() {
        String p = "$";
        if ((int)price * 10 == price * 10) {
            p += price + "0";
        } else {
            p += price;
        }
        return p;
    }

    /**
     * returns the date
     * @return the date in form mm/dd/yy
     */
    public String getDateAsString() {
        int y = date/10000;
        int m = (date - y * 10000)/100;
        int d = date - y * 10000 - m * 100;
        String month = m < 10 ? "0" + m : String.valueOf(m);
        String day   = d < 10 ? "0" + d : String.valueOf(d);
        String year  = y < 10 ? "0" + y : String.valueOf(y);
        return month + "/" + day + "/" + year;
    }
}