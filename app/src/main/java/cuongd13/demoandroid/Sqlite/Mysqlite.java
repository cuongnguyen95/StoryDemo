package cuongd13.demoandroid.Sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by cuong pc on 7/5/2017.
 */

public class Mysqlite extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyDBName.db";
    public static final String STORY_TABLE_NAME = "Story";
    public static final String STORY_COLUMN_ID = "id";
    public static final String STORY_COLUMN_TITLE = "title";
    public static final String STORY_COLUMN_HREF = "href";
    public static final int VERSION = 1 ;

    public Mysqlite(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table "+STORY_TABLE_NAME +
                "("+STORY_COLUMN_ID+" integer primary key, "+ STORY_COLUMN_TITLE+" text,"+STORY_COLUMN_HREF + " text)" ;
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ STORY_TABLE_NAME);
    }
}
