package ca.uwaterloo.sh6choi.kana.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ca.uwaterloo.sh6choi.kana.model.KanaCharacter;

/**
 * Created by Samson on 2015-11-11.
 */
public abstract class KanaCharacterDataSource {

    protected SQLiteDatabase mDatabase;
    protected KanaSQLiteOpenHelper mHelper;

    public void open() throws SQLException {
        mDatabase = mHelper.getWritableDatabase();
    }

    public void close() {
        mHelper.close();
    }

    public abstract void queryKana(final DatabaseRequestCallback<List<KanaCharacter>> callback);

    public abstract void queryKanaFiltered(final DatabaseRequestCallback<List<KanaCharacter>> callback);

    public abstract void update(final KanaCharacter kanaCharacter, final DatabaseRequestCallback<Void> callback );

    protected KanaCharacter cursorToCharacter(Cursor cursor) {
        KanaCharacter character = new KanaCharacter(cursor.getString(0), cursor.getString(1), cursor.getString(2));
        return character;
    }
}
