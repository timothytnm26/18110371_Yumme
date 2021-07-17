package hcmute.edu.vn.a18110371yumme.models;

import java.io.Serializable;

public class Order implements Serializable {
    private String OrderID;
    private String UserID;
    private String StoreID;
    private String State;
    private String Time;

    public String getOrderID() {
        return OrderID;
    }

    public void setOrderID(String orderID) {
        this.OrderID = orderID;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getStoreID() {
        return StoreID;
    }

    public void setStoreID(String storeID) {
        StoreID = storeID;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        this.State = state;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        this.Time = time;
    }

    public Order() {
    }

    public Order(String orderID, String userID, String storeID, String state, String time) {
        this.OrderID = orderID;
        UserID = userID;
        StoreID = storeID;
        this.State = state;
        this.Time = time;
    }
}

