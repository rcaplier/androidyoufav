package dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class YoutubeVideoDBHelper extends SQLiteOpenHelper {

    public static final int VERSION = 1;
    public static final String DATABASE_NAME = "youfav.db";
    public static final String YTVIDEO_KEY = "id";
    public static final String YTVIDEO_TITLE = "title";
    public static final String YTVIDEO_DESCRIPTION = "description";
    public static final String YTVIDEO_URL = "url";
    public static final String YTVIDEO_CATEGORY = "category";

    public static final String YTVIDEO_TABLE_NAME = "Video";

    public static final int YTVIDEO_KEY_COLUMN_INDEX = 0;
    public static final int YTVIDEO_TITLE_COLUMN_INDEX = 1;
    public static final int YTVIDEO_DESCRITION_COLUMN_INDEX = 2;
    public static final int YTVIDEO_URL_COLUMN_INDEX = 3;
    public static final int YTVIDEO_CATEGORY_COLUMN_INDEX = 4;

    private static final String YTVIDEO_TABLE_CREATE =
            "CREATE TABLE " + YTVIDEO_TABLE_NAME + " (" +
                    YTVIDEO_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    YTVIDEO_TITLE + " TEXT, " +
                    YTVIDEO_DESCRIPTION + " TEXT, " +
                    YTVIDEO_URL + " TEXT, " +
                    YTVIDEO_CATEGORY + " TEXT);";

    private static final String YTVIDEO_TABLE_DROP = "DROP TABLE IF EXISTS " + YTVIDEO_TABLE_NAME + ";";


    public YoutubeVideoDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(YTVIDEO_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(YTVIDEO_TABLE_DROP);
        onCreate(db);
    }
}
