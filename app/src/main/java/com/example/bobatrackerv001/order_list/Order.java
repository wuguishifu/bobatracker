package com.example.bobatrackerv001.order_list;

public class Order {

    private final String order;
    private final double price;
    private final int    date;

    /**
     * default constructor
     * @param order - string of the order
     * @param price - the price in form $0000.00
     * @param date  - the date in form mmddyy -> august 30 2000: 83000 (leading zeros removed)
     */
    public Order(String order, double price, int date) {
        this.order = order;
        this.price = price;
        this.date = date;
    }

    public String getOrderAsString() {
        return this.order;
    }

    public double getPriceAsDouble() {
        return this.price;
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
        return "$" + price;
    }

    /**
     * returns the date
     * @return the date in form mm/dd/yy
     */
    public String getDateAsString() {
        int m = date/10000;
        int d = (date - m * 10000)/100;
        int y = date - m * 10000 - d * 100;
        String month = m < 10 ? "0" + m : String.valueOf(m);
        String day   = d < 10 ? "0" + d : String.valueOf(d);
        String year  = y < 10 ? "0" + y : String.valueOf(y);
        return month + "/" + day + "/" + year;
    }
}