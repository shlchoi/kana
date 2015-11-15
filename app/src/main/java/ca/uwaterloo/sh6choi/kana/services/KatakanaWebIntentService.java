package ca.uwaterloo.sh6choi.kana.services;

import android.content.Intent;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import java.net.MalformedURLException;
import java.net.URL;

import ca.uwaterloo.sh6choi.kana.database.HiraganaCharacterDataSource;
import ca.uwaterloo.sh6choi.kana.database.KatakanaCharacterDataSource;
import ca.uwaterloo.sh6choi.kana.model.KanaCharacter;

/**
 * Created by Samson on 2015-09-24.
 */
public class KatakanaWebIntentService extends WebIntentService {

    private static final String TAG = KatakanaWebIntentService.class.getCanonicalName();
    public static final String ACTION_SUCCESS = TAG + ".action.success";

    public KatakanaWebIntentService() {
        super("HiraganaWebIntentService");
    }

    @Override
    protected URL getUrl() throws MalformedURLException {
        return new URL("https://raw.githubusercontent.com/shlchoi/kana/master/katakana.json");
    }

    @Override
    public void onResponse(String response) {
        Log.d(TAG, "Katakana retrieved");
        JsonArray array = new JsonParser().parse(response).getAsJsonArray();
        KanaCharacter[] kanaCharacters = new Gson().fromJson(array, KanaCharacter[].class);

        final KatakanaCharacterDataSource dataSource = new KatakanaCharacterDataSource(this);
        dataSource.open();
        for (int i = 0; i < kanaCharacters.length; i ++) {
            dataSource.update(kanaCharacters[i], null);
        }

        sendBroadcast(new Intent(ACTION_SUCCESS));
    }

    @Override
    public void onError(Exception e) {
        Log.e(TAG, e.getMessage());
    }
}
