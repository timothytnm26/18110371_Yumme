package hcmute.edu.vn.a18110371yumme.models;

import java.io.Serializable;

public class User implements Serializable {
    private String UserID;
    private String UserName;
    private String UserAddress;
    private String UserPassword;
    private String UserPhone;
    private String UserEmail;
    private String UserBirthday;
    private int UserRoll;

    public User() {

    }


    public String getUserID() {
        return UserID;
    }
    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserAddress() {
        return UserAddress;
    }

    public void setUserAddress(String userAddress) {
        UserAddress = userAddress;
    }

    public String getUserPassword() {
        return UserPassword;
    }

    public void setUserPassword(String userPassword) {
        UserPassword = userPassword;
    }

    public String getUserPhone() {
        return UserPhone;
    }

    public void setUserPhone(String userPhone) {
        UserPhone = userPhone;
    }

    public String getUserEmail() {
        return UserEmail;
    }

    public void setUserEmail(String userEmail) {
        UserEmail = userEmail;
    }

    public String getUserBirthday() {
        return UserBirthday;
    }

    public void setUserBirthday(String userBirthday) {
        UserBirthday = userBirthday;
    }

    public int getUserRoll() {
        return UserRoll;
    }

    public void setUserRoll(int userRoll) {
        UserRoll = userRoll;
    }

    public User(String userID, String userName, String userAddress, String userPassword, String userPhone, String userEmail, String userBirthday, int userRoll) {
        UserID = userID;
        UserName = userName;
        UserAddress = userAddress;
        UserPassword = userPassword;
        UserPhone = userPhone;
        UserEmail = userEmail;
        UserBirthday = userBirthday;
        UserRoll = userRoll;
    }
}
