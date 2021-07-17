package hcmute.edu.vn.a18110371yumme.models;

import java.io.Serializable;

public class Cart implements Serializable {
    private int CartID;
    private String OrderID;
    private String GoodID;
    private int Number;



    public Cart() {
    }

    public Cart(String orderID, String goodID, int number) {
        this.OrderID = orderID;
        this.GoodID = goodID;
        this.Number = number;
    }

    public Cart(int cartID, String orderID, String goodID, int number) {
        this.CartID = cartID;
        this.OrderID = orderID;
        this.GoodID = goodID;
        this.Number = number;
    }

    public int getNumber() {
        return Number;
    }

    public void setNumber(int number) {
        Number = number;
    }

    public String getGoodID() {
        return GoodID;
    }

    public void setGoodID(String goodID) {
        GoodID = goodID;
    }

    public String getOrderID() {
        return OrderID;
    }

    public void setOrderID(String orderID) {
        OrderID = orderID;
    }

    public int getCartID() {
        return CartID;
    }

    public void setCartID(int cartID) {
        CartID = cartID;
    }
}
