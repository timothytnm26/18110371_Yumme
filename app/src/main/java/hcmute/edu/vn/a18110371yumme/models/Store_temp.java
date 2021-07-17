package hcmute.edu.vn.a18110371yumme.models;

import java.io.Serializable;

public class Store_temp implements Serializable {
    private String StoreID;
    private String StoreName;
    private String StoreAddress;
    private Double StoreRate;
    private String StoreImage;
    private Double StoreLatitude;
    private Double StoreLongitude;
    private Double StoreDistance;


    public Store_temp(String storeID, String storeName, String storeAddress, Double storeRate, String storeImage, Double storeLatitude, Double storeLongitude, Double storeDistance) {
        StoreID = storeID;
        StoreName = storeName;
        StoreAddress = storeAddress;
        StoreRate = storeRate;
        StoreImage = storeImage;
        StoreLatitude = storeLatitude;
        StoreLongitude = storeLongitude;
        StoreDistance = storeDistance;
    }
    public Store_temp() {

    }

    public String getStoreID() {
        return StoreID;
    }

    public void setStoreID(String storeID) {
        StoreID = storeID;
    }

    public String getStoreName() {
        return StoreName;
    }

    public void setStoreName(String storeName) {
        StoreName = storeName;
    }

    public String getStoreAddress() {
        return StoreAddress;
    }

    public void setStoreAddress(String storeAddress) {
        StoreAddress = storeAddress;
    }

    public Double getStoreRate() {
        return StoreRate;
    }

    public void setStoreRate(Double storeRate) {
        StoreRate = storeRate;
    }

    public String getStoreImage() {
        return StoreImage;
    }

    public void setStoreImage(String storeImage) {
        StoreImage = storeImage;
    }

    public Double getStoreLatitude() {
        return StoreLatitude;
    }

    public void setStoreLatitude(Double storeLatitude) {
        StoreLatitude = storeLatitude;
    }

    public Double getStoreLongitude() {
        return StoreLongitude;
    }

    public void setStoreLongitude(Double storeLongitude) {
        StoreLongitude = storeLongitude;
    }

    public Double getStoreDistance() {
        return StoreDistance;
    }

    public void setStoreDistance(Double storeDistance) {
        StoreDistance = storeDistance;
    }
}
