package ca.uwaterloo.sh6choi.kana.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Samson on 2015-10-23.
 */
public class KanaSQLiteOpenHelper extends SQLiteOpenHelper{
    private static final String TAG = KanaSQLiteOpenHelper.class.getCanonicalName();

    public static final String TABLE_HIRAGANA = "hiragana";
    public static final String COLUMN_CHAR_ID_HIRAGANA = "char_id";
    public static final String COLUMN_CHARACTER_HIRAGANA = "character";
    public static final String COLUMN_ROMANIZATION_HIRAGANA = "romanization";

    public static final String TABLE_KATAKANA = "katakana";
    public static final String COLUMN_CHAR_ID_KATAKANA = "char_id";
    public static final String COLUMN_CHARACTER_KATAKANA = "character";
    public static final String COLUMN_ROMANIZATION_KATAKANA = "romanization";

    private static final String DATABASE_NAME = "Kana.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_HIRAGANA_CREATE = "CREATE TABLE " + TABLE_HIRAGANA + "(" +
            COLUMN_CHAR_ID_HIRAGANA + " TEXT NOT NULL PRIMARY KEY, " +
            COLUMN_CHARACTER_HIRAGANA + " TEXT NOT NULL, " +
            COLUMN_ROMANIZATION_HIRAGANA + " TEXT NOT NULL);";

    private static final String TABLE_KATAKANA_CREATE = "CREATE TABLE " + TABLE_KATAKANA + "(" +
            COLUMN_CHAR_ID_KATAKANA + " TEXT NOT NULL PRIMARY KEY, " +
            COLUMN_CHARACTER_KATAKANA + " TEXT NOT NULL, " +
            COLUMN_ROMANIZATION_KATAKANA + " TEXT NOT NULL);";


    public KanaSQLiteOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_HIRAGANA_CREATE);
        db.execSQL(TABLE_KATAKANA_CREATE);
        Log.d(TAG, "Database Created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HIRAGANA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_KATAKANA);
        onCreate(db);
    }
}
