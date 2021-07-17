package hcmute.edu.vn.a18110371yumme.models;

public class CartDetail {
    private int CartID;
    private String GoodId;
    private String GoodName;
    private int GoodPrice;
    private int Number;
    private String Image;

    public CartDetail() {
    }

    public CartDetail(int cartID, String goodId, String goodName, int goodPrice, int number, String image){
        this.CartID = cartID;
        this.GoodId = goodId;
        this.GoodName = goodName;
        this.GoodPrice = goodPrice;
        this.Number = number;
        this.Image = image;
    }


    public int getCartID() {
        return CartID;
    }

    public void setCartID(int cartID) {
        CartID = cartID;
    }

    public String getGoodId() {
        return GoodId;
    }

    public void setGoodId(String goodId) {
        GoodId = goodId;
    }

    public String getGoodName() {
        return GoodName;
    }

    public void setGoodName(String goodName) {
        GoodName = goodName;
    }

    public int getGoodPrice() {
        return GoodPrice;
    }

    public void setGoodPrice(int goodPrice) {
        GoodPrice = goodPrice;
    }

    public int getNumber() {
        return Number;
    }

    public void setNumber(int number) {
        Number = number;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
