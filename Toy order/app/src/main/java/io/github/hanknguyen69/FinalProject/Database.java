package io.github.hanknguyen69.FinalProject;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteAssetHelper {

    private static final String DB_NAME="toyDB";
    private static final int DB_VER=1;




    public Database(Context context) {
        super(context, DB_NAME,null,DB_VER);
    }

    public List<Order> getCart()
    {
        SQLiteDatabase db =getReadableDatabase();
        SQLiteQueryBuilder qb= new SQLiteQueryBuilder();
        String[] sqlSelect = {"productName","productId","quantity","price"};
        String sqltable = "OrderDetail";

        qb.setTables(sqltable);
        Cursor c = qb.query(db,sqlSelect,null,null,null,null,null);
        final List<Order> result = new ArrayList<>();
            if (c.moveToFirst())
            {
                do {
                   result.add(new Order(c.getString(c.getColumnIndex("productId")),
                           c.getString(c.getColumnIndex("productName")),
                           c.getString(c.getColumnIndex("quantity")),
                           c.getString(c.getColumnIndex("price"))
                           ));
                }
                while (c.moveToNext());
            }
            return result;

    }

    public void addToCart(Order order)
    {
        SQLiteDatabase db = getReadableDatabase();
        String querry = String.format("INSERT INTO OrderDetail(productId,productName,quantity,price) VALUES('%s','%s','%s','%s');",
                                order.getProductId(),
                                order.getProductName(),
                                order.getQuantity(),
                                order.getPrice());

        db.execSQL(querry);
    }

    public void clearCart()
    {
        SQLiteDatabase db = getReadableDatabase();
        String querry = String.format("DELETE FROM OrderDetail");


        db.execSQL(querry);
    }


}
