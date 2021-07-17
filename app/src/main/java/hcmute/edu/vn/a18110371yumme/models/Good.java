package hcmute.edu.vn.a18110371yumme.models;

import java.io.Serializable;

public class Good implements Serializable {
    private String GoodID;
    private String GoodName;
    private int GoodPrice;
    private String GoodImage;
    private String StoreID;
    private String TypeID;
    private String Description;

    public Good(String goodID, String goodName, int goodPrice, String goodImage, String storeID, String typeID, String description) {
        GoodID = goodID;
        GoodName = goodName;
        GoodPrice = goodPrice;
        GoodImage = goodImage;
        StoreID = storeID;
        TypeID = typeID;
        Description = description;
    }

    public Good() {

    }

    @Override
    public String toString() {
        return "Good{" +
                "GoodID='" + GoodID + '\'' +
                ", GoodName='" + GoodName + '\'' +
                ", GoodPrice=" + GoodPrice +
                ", GoodImage='" + GoodImage + '\'' +
                ", StoreID='" + StoreID + '\'' +
                ", TypeID='" + TypeID + '\'' +
                ", Description='" + Description + '\'' +
                '}';
    }


    public String getGoodID() {
        return GoodID;
    }

    public void setGoodID(String goodID) {
        GoodID = goodID;
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

    public String getGoodImage() {
        return GoodImage;
    }

    public void setGoodImage(String goodImage) {
        GoodImage = goodImage;
    }

    public String getStoreID() {
        return StoreID;
    }

    public void setStoreID(String storeID) {
        StoreID = storeID;
    }

    public String getTypeID() {
        return TypeID;
    }

    public void setTypeID(String typeID) {
        TypeID = typeID;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}




