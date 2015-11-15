package ca.uwaterloo.sh6choi.kana.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ca.uwaterloo.sh6choi.kana.model.KanaCharacter;

/**
 * Created by Samson on 2015-10-23.
 */
public class KatakanaCharacterDataSource extends KanaCharacterDataSource {

    private String[] mColumns = { KanaSQLiteOpenHelper.COLUMN_CHAR_ID_KATAKANA,
            KanaSQLiteOpenHelper.COLUMN_CHARACTER_KATAKANA,
            KanaSQLiteOpenHelper.COLUMN_ROMANIZATION_KATAKANA};

    public KatakanaCharacterDataSource(Context context) {
        mHelper = new KanaSQLiteOpenHelper(context);
    }

    @Override
    public void queryKana(final DatabaseRequestCallback<List<KanaCharacter>> callback) {
        new AsyncTask<Void, Void, List<KanaCharacter>>() {
            @Override
            protected List<KanaCharacter> doInBackground(Void... params) {
                List<KanaCharacter> kanaCharacters = new ArrayList<>();

                Cursor cursor = mDatabase.query(KanaSQLiteOpenHelper.TABLE_KATAKANA, mColumns, null, null, null, null, null);

                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    kanaCharacters.add(cursorToCharacter(cursor));
                    cursor.moveToNext();
                }

                cursor.close();

                Collections.sort(kanaCharacters, new KanaCharacter.CharacterComparator());
                return kanaCharacters;
            }

            @Override
            protected void onPostExecute(List<KanaCharacter> kanaCharacterList) {
                callback.processResults(kanaCharacterList);
            }

        }.execute();
    }

    @Override
    public void queryKanaFiltered(final DatabaseRequestCallback<List<KanaCharacter>> callback) {
        new AsyncTask<Void, Void, List<KanaCharacter>>() {
            @Override
            protected List<KanaCharacter> doInBackground(Void... params) {
                List<KanaCharacter> kanaCharacters = new ArrayList<>();

                Cursor cursor = mDatabase.query(KanaSQLiteOpenHelper.TABLE_KATAKANA, mColumns,
                        KanaSQLiteOpenHelper.COLUMN_CHAR_ID_KATAKANA + " NOT IN (\"wi\", \"we\") " +
                                "AND " + KanaSQLiteOpenHelper.COLUMN_CHAR_ID_KATAKANA + " NOT LIKE \"g_\" " +
                                "AND " + KanaSQLiteOpenHelper.COLUMN_CHAR_ID_KATAKANA + " NOT LIKE \"z_\" " +
                                "AND " + KanaSQLiteOpenHelper.COLUMN_CHAR_ID_KATAKANA + " NOT LIKE \"d_\" " +
                                "AND " + KanaSQLiteOpenHelper.COLUMN_CHAR_ID_KATAKANA + " NOT LIKE \"b_\" " +
                                "AND " + KanaSQLiteOpenHelper.COLUMN_CHAR_ID_KATAKANA + " NOT LIKE \"p_\" " +
                                "AND " + KanaSQLiteOpenHelper.COLUMN_CHAR_ID_KATAKANA + " NOT LIKE \"v_\" " +
                                "AND " + KanaSQLiteOpenHelper.COLUMN_CHAR_ID_KATAKANA + " NOT LIKE \"_y_\"",
                        null, null, null, null);

                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    kanaCharacters.add(cursorToCharacter(cursor));
                    cursor.moveToNext();
                }

                cursor.close();

                Collections.sort(kanaCharacters, new KanaCharacter.CharacterComparator());
                return kanaCharacters;
            }

            @Override
            protected void onPostExecute(List<KanaCharacter> kanaCharacterList) {
                callback.processResults(kanaCharacterList);
            }

        }.execute();
    }

    @Override
    public void update(final KanaCharacter kanaCharacter, final DatabaseRequestCallback<Void> callback ) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(KanaSQLiteOpenHelper.COLUMN_CHAR_ID_KATAKANA, kanaCharacter.getCharId());
                contentValues.put(KanaSQLiteOpenHelper.COLUMN_CHARACTER_KATAKANA, kanaCharacter.getCharacter());
                contentValues.put(KanaSQLiteOpenHelper.COLUMN_ROMANIZATION_KATAKANA, kanaCharacter.getRomanization());

                mDatabase.insertWithOnConflict(KanaSQLiteOpenHelper.TABLE_KATAKANA, null, contentValues,
                        SQLiteDatabase.CONFLICT_REPLACE);

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                if (callback != null) {
                    callback.processResults(aVoid);
                }
            }
        }.execute();
    }
}
