package shridhar_manages.login;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Rachit on 8/16/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "eventmanager.db";
    public static final String TABLE_NAME = "events";
    public static final int DATABASE_VERSION = 1;
    public static final String COL1 = "ID";
    public static final String COL2 = "Name";
    public static final String COL3 = "Datetime";
    public static final String COL4 = "Type";
    public static final String COL5 = "Desc";
    public static final String COL6 = "Time";
    public static final String[] ALL_KEYS= new String[]{COL1,COL2,COL3,COL4,COL5,COL6};

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, DATETIME NUMERIC, TYPE TEXT, DESC TEXT, TIME DATETIME);";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP IF TABLE EXISTS " + TABLE_NAME);
        onCreate(db);
    }


    public boolean addData(String name, String datetime, String type, String desc,String time){
        ContentValues contentValues=new ContentValues();
        SQLiteDatabase db = this.getWritableDatabase();
        contentValues.put(COL2, name);
        contentValues.put(COL3, datetime);
        contentValues.put(COL4, type);
        contentValues.put(COL5, desc);
        contentValues.put(COL6, time);

        long result = db.insert(TABLE_NAME, null, contentValues);

        if (result == -1) {
            return false;
        }
        else {
            return true;
        }

    }
    public boolean updatedata(String id,String name, String datetime, String type, String desc, String time){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL1,id);
        contentValues.put(COL2,name);
        contentValues.put(COL3,datetime);
        contentValues.put(COL4,type);
        contentValues.put(COL5,desc);
        contentValues.put(COL6,time);
        db.update(TABLE_NAME,contentValues,"ID = ?",new String[] {id});
        return true;
    }
    public Integer deleteData(String id){
        SQLiteDatabase db=this.getWritableDatabase();
        return db.delete(TABLE_NAME,"ID = ?",new String[]{id});
    }


    public Cursor getListContents(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return data;
    }
    /*public Cursor getListContents(){
        String where = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(true,TABLE_NAME,ALL_KEYS,where,null,null,null,null,null);
        if(c != null){
            c.moveToFirst();
        }
        return c;
    }*/

}
