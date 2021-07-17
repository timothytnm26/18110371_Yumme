package hcmute.edu.vn.a18110371yumme;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import hcmute.edu.vn.a18110371yumme.models.User;

public class DBHelper extends SQLiteOpenHelper {

    // DB Version
    private static final int DATABASE_VERSION = 1;

    // DB Name
    private static final String DATABASE_NAME = "UserManager.db";

    // Table Name
    private static final String TABLE_USER = "User";

    // Columns names
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USER_NAME = "user_name";
    private static final String COLUMN_USER_EMAIL = "user_email";
    private static final String COLUMN_USER_PASSWORD = "user_password";
    private static final String COLUMN_USER_PHONE = "user_phone";

    // create table sql query
    private String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_USER_PHONE + " TEXT," + COLUMN_USER_NAME + " TEXT,"
            + COLUMN_USER_EMAIL + " TEXT," + COLUMN_USER_PASSWORD + " TEXT" +  ")";

    // drop table sql query
    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;


    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //Drop User Table if exist
        db.execSQL(DROP_USER_TABLE);

        // Create tables again
        onCreate(db);
    }


    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, user.getUserName());
        values.put(COLUMN_USER_EMAIL, user.getUserEmail());
        values.put(COLUMN_USER_PASSWORD, user.getUserPassword());
        values.put(COLUMN_USER_PHONE, user.getUserPhone());
        // Inserting Row
        db.insert(TABLE_USER, null, values);
        db.close();
    }

    public boolean registerUser(String username, String email, String phone, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(COLUMN_USER_NAME, username);
        value.put(COLUMN_USER_EMAIL, email);
        value.put(COLUMN_USER_PHONE, phone);
        value.put(COLUMN_USER_PASSWORD, password);

        long result = db.insert(TABLE_USER, null, value);
        if(result==-1)
            return false;
        else return true;

    }

    // Fetch all users and add to List
    public List<User> getAllUser() {
        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID,
                COLUMN_USER_EMAIL,
                COLUMN_USER_NAME,
                COLUMN_USER_PASSWORD,
                COLUMN_USER_PHONE
        };

        // sorting orders
        String sortOrder =
                COLUMN_USER_NAME + " ASC";
        List<User> userList = new ArrayList<User>();
        SQLiteDatabase db = this.getReadableDatabase();

        // query the user table
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order

        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setUserID(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID)));
                user.setUserName(cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME)));
                user.setUserEmail(cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL)));
                user.setUserPassword(cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD)));
                user.setUserPhone(cursor.getString(cursor.getColumnIndex(COLUMN_USER_PHONE)));
                // Adding user record to list
                userList.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return userList;
    }

   // Update User Record
    public void updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, user.getUserName());
        values.put(COLUMN_USER_EMAIL, user.getUserName());
        values.put(COLUMN_USER_PASSWORD, user.getUserPassword());
        values.put(COLUMN_USER_PHONE, user.getUserPhone());

        // updating row
        db.update(TABLE_USER, values, COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(user.getUserID())});
        db.close();
    }

   // delete user record
    public void deleteUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        // delete user record by id
        db.delete(TABLE_USER, COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(user.getUserID())});
        db.close();
    }

    // Check user exited
    public boolean checkUser(String phone) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection = COLUMN_USER_PHONE + " = ?";

        // selection argument
        String[] selectionArgs = {phone};

        // query user table with condition

        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order

        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        }

        return false;
    }


    // User Exited or not
    public boolean checkUser(String phone, String password) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = COLUMN_USER_PHONE + " = ?" + " AND " + COLUMN_USER_PASSWORD + " = ?";

        // selection arguments
        String[] selectionArgs = {phone, password};

        // query user table with conditions
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order

        int cursorCount = cursor.getCount();

        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }

        return false;
    }
}