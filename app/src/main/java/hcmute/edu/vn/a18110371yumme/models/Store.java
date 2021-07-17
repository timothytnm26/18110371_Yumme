package hcmute.edu.vn.a18110371yumme.models;

import java.io.Serializable;
import java.util.List;

public class Store implements Serializable {

    private String StoreID;
    private String StoreMail;
    private String StorePassword;
    private List StoreGood;
    private String StoreName;
    private String StoreAddress;
    private Double StoreRate;
    private String StoreImage;
    private Double StoreLatitude;
    private Double StoreLongitude;
    private int Roll;

    public String getStoreID() {
        return StoreID;
    }

    public void setStoreID(String storeID) {
        StoreID = storeID;
    }

    public String getStoreMail() {
        return StoreMail;
    }

    public void setStoreMail(String storeMail) {
        StoreMail = storeMail;
    }

    public String getStorePassword() {
        return StorePassword;
    }

    public void setStorePassword(String storePassword) {
        StorePassword = storePassword;
    }

    public List getStoreGood() {
        return StoreGood;
    }

    public void setStoreGood(List storeGood) {
        StoreGood = storeGood;
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

    public int getRoll() {
        return Roll;
    }

    public void setRoll(int roll) {
        Roll = roll;
    }

    public Store() {
    }

    public Store(String storeID, String storeMail, String storePassword, List storeGood, String storeName, String storeAddress, Double storeRate, String storeImage, Double storeLatitude, Double storeLongitude, int roll) {
        StoreID = storeID;
        StoreMail = storeMail;
        StorePassword = storePassword;
        StoreGood = storeGood;
        StoreName = storeName;
        StoreAddress = storeAddress;
        StoreRate = storeRate;
        StoreImage = storeImage;
        StoreLatitude = storeLatitude;
        StoreLongitude = storeLongitude;
        Roll = roll;
    }

}
