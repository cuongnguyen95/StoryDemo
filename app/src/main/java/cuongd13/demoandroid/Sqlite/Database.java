package cuongd13.demoandroid.Sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

import cuongd13.demoandroid.model.Detail;

/**
 * Created by cuong pc on 6/20/2017.
 */

public class Database extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "data.db";
    public static final String CONTACTS_TABLE_NAME = "history";
    public static final String CONTACTS_COLUMN_ID = "id";
    public static final String CONTACTS_COLUMN_TITLE = "title";
    public static final String CONTACTS_COLUMN_HREF = "href";
    private HashMap hp;
    public  ArrayList<Detail> detailArrayList ;

    public  Database(Context context){
        super(context , DATABASE_NAME , null , 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table history" +
                "(id integer primary key, title text,href text)");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS history");
    }

    public boolean insert (String title, String href ) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CONTACTS_COLUMN_TITLE, title);
        contentValues.put(CONTACTS_COLUMN_HREF, href);
        db.insert(CONTACTS_TABLE_NAME, null, contentValues);
        return true;
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from history where id="+id+"", null );
        return res;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, CONTACTS_TABLE_NAME);
        return numRows;
    }

    public boolean update (Integer id, String title, String href) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CONTACTS_COLUMN_TITLE, title);
        contentValues.put(CONTACTS_COLUMN_HREF, href);
        db.update(CONTACTS_TABLE_NAME , contentValues , "id = ?" , new String[] {Integer.toString(id)});
        return true;
    }

    public Integer delete (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(CONTACTS_TABLE_NAME,
                "id = ? ",
                new String[] { Integer.toString(id) });
    }

    public ArrayList<Detail> getAllCotacts() {

        detailArrayList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from history", null );
        while (res.moveToNext()){
            String title = res.getString(res.getColumnIndex("title"));
            String href = res.getString(res.getColumnIndex("href"));
            Detail detail = new Detail();
            detail.setTitle(title);
            detail.setHref(href);
            detailArrayList.add(detail);

        }
        return detailArrayList;
    }

}
