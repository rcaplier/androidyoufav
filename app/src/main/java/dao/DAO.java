package dao;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DAO {
    protected SQLiteDatabase db = null;
    protected SQLiteOpenHelper baseHelper = null;

    public DAO(SQLiteOpenHelper baseHelper) {
        this.baseHelper = baseHelper;
    }

    public SQLiteDatabase open(){
        return this.db = this.baseHelper.getWritableDatabase();
    }

    public void close(){
        this.db.close();
    }

    public SQLiteDatabase getDb(){
        return this.db;
    }

}
