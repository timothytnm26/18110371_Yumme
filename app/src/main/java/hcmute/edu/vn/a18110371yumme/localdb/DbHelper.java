package hcmute.edu.vn.a18110371yumme.localdb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import hcmute.edu.vn.a18110371yumme.models.CartDetail;
import hcmute.edu.vn.a18110371yumme.models.Cart;
import hcmute.edu.vn.a18110371yumme.models.Good;

import java.util.ArrayList;

public class DbHelper extends SQLiteOpenHelper {
    public static ArrayList<Cart> cart = new ArrayList<>();
    SQLiteDatabase db;
    public DbHelper(Context context){
        super(context,"cart",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql= "create table Cart(CartID integer primary key autoincrement, " +
                "OrderID text, GoodID text, Number integer)";
        db.execSQL(sql);
        sql= "create table Good(GoodID text primary key, " +
                "GoodName text, GoodPrice integer, GoodImage text)";
        db.execSQL(sql);
    }

    public void insertCart(Cart cart){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("OrderID", cart.getCartID());
        values.put("GoodID", cart.getGoodID());
        values.put("Number", cart.getNumber());
        db.insert("Cart", null, values);
    }

    public void insertGood(Good good){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("GoodID", good.getGoodID());
        values.put("GoodName", good.getGoodName());
        values.put("GoodPrice", good.getGoodPrice());
        values.put("GoodImage", good.getGoodImage());
        db.insert("Good", null, values);
    }

    public void delete(String id){
        db = this.getWritableDatabase();
        db.delete("Cart", "GoodID=?",new String[]{id});
        db.delete("Good", "GoodID=?",new String[]{id});
    }

    public ArrayList<CartDetail> listCart(){
        ArrayList<CartDetail> list = new ArrayList<>();
        db = this.getReadableDatabase();
        String sql = "select cart.CartID, cart.GoodID, good.GoodName, good.GoodPrice, cart.Number, good.GoodImage " +
                " from Cart cart inner join Good good on cart.GoodID=good.GoodID";
        Cursor cursors = db.rawQuery(sql, null);
        cursors.moveToFirst();
        while (!cursors.isAfterLast()){
            CartDetail cartDetail = new CartDetail(cursors.getInt(0), cursors.getString(1), cursors.getString(2)
                    , cursors.getInt(3), cursors.getInt(4), cursors.getString(5));
            list.add(cartDetail);
            cursors.moveToNext();
        }
        cursors.close();
        return list;
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
