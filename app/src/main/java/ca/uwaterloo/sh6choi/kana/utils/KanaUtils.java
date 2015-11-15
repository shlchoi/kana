package ca.uwaterloo.sh6choi.kana.utils;

import android.content.Context;
import android.text.TextUtils;

import java.util.HashMap;
import java.util.List;

import ca.uwaterloo.sh6choi.kana.database.DatabaseRequestCallback;
import ca.uwaterloo.sh6choi.kana.database.HiraganaCharacterDataSource;
import ca.uwaterloo.sh6choi.kana.database.KatakanaCharacterDataSource;
import ca.uwaterloo.sh6choi.kana.model.KanaCharacter;

/**
 * Created by Samson on 2015-11-05.
 */
public class KanaUtils {
    private static HashMap<String, KanaCharacter> sRomanizedHiraganaMap;
    private static HashMap<String, KanaCharacter> sRomanizedKatakanaMap;

    private static HashMap<String, KanaCharacter> sKanaMap;
    private static HashMap<String, String> sDiagraphMap;

    public static void refreshMap(Context context) {
        sRomanizedHiraganaMap = new HashMap<>();
        sRomanizedKatakanaMap = new HashMap<>();
        sKanaMap = new HashMap<>();
        sDiagraphMap = new HashMap<>();

        final HiraganaCharacterDataSource hiraganaDataSource = new HiraganaCharacterDataSource(context);
        final KatakanaCharacterDataSource katakanaDataSource = new KatakanaCharacterDataSource(context);

        hiraganaDataSource.open();
        hiraganaDataSource.queryKana(new DatabaseRequestCallback<List<KanaCharacter>>() {
            @Override
            public void processResults(List<KanaCharacter> results) {
                for (int i = 0; i < results.size(); i++) {
                    sRomanizedHiraganaMap.put(results.get(i).getCharId(), results.get(i));
                    sKanaMap.put(results.get(i).getCharacter(), results.get(i));
                }
                hiraganaDataSource.close();
            }
        });

        katakanaDataSource.open();
        katakanaDataSource.queryKana(new DatabaseRequestCallback<List<KanaCharacter>>() {
            @Override
            public void processResults(List<KanaCharacter> results) {
                for (int i = 0; i < results.size(); i++) {
                    sRomanizedKatakanaMap.put(results.get(i).getCharId(), results.get(i));
                    sKanaMap.put(results.get(i).getCharacter(), results.get(i));
                }
                katakanaDataSource.close();
            }
        });

        sDiagraphMap.put("ゃ", "ya");
        sDiagraphMap.put("ゅ", "yu");
        sDiagraphMap.put("ょ", "yo");
        sDiagraphMap.put("ャ", "ya");
        sDiagraphMap.put("ュ", "ya");
        sDiagraphMap.put("ョ", "ya");
    }

    public static String romanize(String input) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < input.length(); i ++) {
            String key = input.substring(i, i + 1);

            if (input.length() > i + 1) {
                String nextChar = input.substring(i + 1, i + 2);
                if (sDiagraphMap.containsKey(nextChar)) {
                    key = key.concat(nextChar);
                    i ++;
                }
            }

            if (TextUtils.equals(key, "っ") || TextUtils.equals(key, "ッ")) {
                if (input.length() > i + 1) {
                    String nextChar = input.substring(i + 1, i + 2);

                    if (sKanaMap.containsKey(nextChar)) {
                        builder.append(sKanaMap.get(nextChar).getCharId().substring(0, 1));
                    }
                }
            } else if (sKanaMap.containsKey(key)) {
                builder.append(sKanaMap.get(key).getRomanization());
            } else {
                builder.append(key);
            }
        }
        return builder.toString();
    }



}
