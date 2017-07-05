package cuongd13.demoandroid.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import cuongd13.demoandroid.Sqlite.Mysqlite;

/**
 * Created by cuong pc on 7/5/2017.
 */

public class Story  {
    Mysqlite mysqlite ;

    public Story(Context context) {
        mysqlite = new Mysqlite(context) ;
    }

    public boolean insert (String name, String phone ) {
        SQLiteDatabase db = mysqlite.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(mysqlite.STORY_COLUMN_TITLE, name);
        contentValues.put(mysqlite.STORY_COLUMN_HREF, phone);
        db.insert(mysqlite.STORY_TABLE_NAME, null, contentValues);
        return true;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = mysqlite.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+mysqlite.STORY_TABLE_NAME, null );
        return res;
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = mysqlite.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+mysqlite.STORY_TABLE_NAME+" where "+Mysqlite.STORY_COLUMN_ID+" = "+id+"", null );
        return res;
    }

    public int numberOfRows(){
        SQLiteDatabase db = mysqlite.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, mysqlite.STORY_TABLE_NAME);
        return numRows;
    }

    public boolean update (Integer id, String name, String phone) {
        SQLiteDatabase db = mysqlite.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(mysqlite.STORY_COLUMN_TITLE, name);
        contentValues.put(mysqlite.STORY_COLUMN_HREF, phone);
        db.update(mysqlite.STORY_TABLE_NAME, contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public Integer delete (Integer id) {
        SQLiteDatabase db = mysqlite.getWritableDatabase();
        return db.delete(mysqlite.STORY_TABLE_NAME,
                "id = ? ",
                new String[] { Integer.toString(id) });
    }

//    public ArrayList<String> getAllCotacts() {
//        ArrayList<String> array_list = new ArrayList<String>();
//
//        //hp = new HashMap();
//        SQLiteDatabase db = mysqlite.getReadableDatabase();
//        Cursor res =  db.rawQuery( "select * from contacts", null );
//        res.moveToFirst();
//
//        while(res.isAfterLast() == false){
//            array_list.add(res.getString(res.getColumnIndex(CONTACTS_COLUMN_NAME)));
//            res.moveToNext();
//        }
//        return array_list;
//    }


}
