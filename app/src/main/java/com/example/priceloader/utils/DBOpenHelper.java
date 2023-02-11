package com.example.priceloader.utils;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.priceloader.entity.CartListItem;
import com.example.priceloader.entity.GoodsListItem;
import com.example.priceloader.entity.Product;
import com.example.priceloader.entity.ProductItem;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class DBOpenHelper extends SQLiteOpenHelper {

    private SQLiteDatabase database;


//    private static DBTable tables;

    private Context context;

    public DBOpenHelper(Context context,
                        String name,
                        SQLiteDatabase.CursorFactory factory,
                        int version) {
        super(context, name, factory, version);
        database = getWritableDatabase();
        this.context = context;

    }

    //id integer primary key autoincrement,step integer,time varchar(20)
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE product (" +
                "  id varchar(20) NOT NULL," +
                "  name varchar(20) DEFAULT NULL," +
                "  singleprice double(7,1) DEFAULT NULL," +
                "  PRIMARY KEY (id)" +
                ") ";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addDataByProductItem(ProductItem item){
        return addData(item.getId(),item.getName(),item.getSingleprice());
    }


    public boolean addData(String id,String name,double singleprice){
        long res = 0;
        ContentValues cv = new ContentValues();
        cv.put("id",id);
        cv.put("name",name);
        cv.put("singleprice",singleprice);
        res = database.insert("product",null,cv);
        if(res == -1){
            return false;
        }
        return true;
    }


    public List<GoodsListItem> getList(){
        List<GoodsListItem> res = new ArrayList<>();
//        database.query("yunduostore",null,null,null,null,null,null,null,null,null);
        Cursor cursor = database.query("product",new String[]{"id","name","singleprice"},null,null,null,null,null);
        while(cursor.moveToNext()){
            GoodsListItem goodsListItem = new GoodsListItem();
            goodsListItem.setId(cursor.getString(0));
            goodsListItem.setName(cursor.getString(1));
            goodsListItem.setSinglePrice(cursor.getDouble(2));
            res.add(goodsListItem);
        }
        return res;
    }

    public ProductItem getDataByID(String id){

        Cursor cursor = database.query("product",new String[]{"id","name","singleprice"},"id=?",new String[]{id},null,null,null,null);
        if(cursor.moveToNext()){
            ProductItem p_item = new ProductItem();
            p_item.setId(cursor.getString(0));
            p_item.setName(cursor.getString(1));
            p_item.setSingleprice(cursor.getDouble(2));
            Log.e("DBOpenHelper::",p_item.toString());
            return p_item;
        }else{
            return null;
        }
    }

    /**
     * 根据id进行删除
     * @param id 包含一个或多个id的字符串数组
     * @return 删除的行数
     */
    public int deleteDataByID(String[] id){
        return database.delete("product","id=?",id);
    }

    public int updateData(ProductItem item){
        String target_id = item.getId();
        ContentValues values = new ContentValues();
        values.put("name",item.getName());
        values.put("singleprice",item.getSingleprice());
        return database.update("product",values,"id=?",new String[]{target_id});
    }

}
