package com.example.first;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class RateManager {
    private static final String TAG="RateManager";
    private DBHelper dbHelper;
    private String TBNAME;

    public RateManager(Context context) {
        dbHelper= new DBHelper(context);
        TBNAME=DBHelper.TB_NAME;
    }
    public void add(RateItem item){
        SQLiteDatabase db = dbHelper.getWritableDatabase(); //打开一个可写的数据库
        ContentValues values = new ContentValues();         //类似于bundle的数据包
        values.put("curname", item.getCurName());
        values.put("currate", item.getCurRate());

        long r=db.insert(TBNAME, null, values);//插入数据 类似还有update等等
        Log.i(TAG,"add:ret="+r);
        db.close();
    }
}
