package ca.uwaterloo.sh6choi.kana.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Samson on 2015-09-23.
 */
public class KanaCharacter implements Parcelable, FlashcardItem {

    private static final String[] VOWEL_ORDERING = new String[]{"a", "i", "u", "e", "o", "ya", "yu", "yo"};
    private static final String[] CONSONANT_ORDERING = new String[]{" ", "k", "s", "t", "n", "h", "m", "y", "r", "w", "-", "g", "z", "d", "b", "p", "v"};

    @SerializedName("char_id")
    private String mCharId;

    @SerializedName("character")
    private String mCharacter;

    @SerializedName("romanization")
    private String mRomanization;

    public KanaCharacter(String charId, String character, String romanization) {
        mCharId = charId;
        mCharacter = character;
        mRomanization = romanization;
    }

    protected KanaCharacter(Parcel in) {
        mCharId = in.readString();
        mCharacter = in.readString();
        mRomanization = in.readString();
    }

    public static final Creator<KanaCharacter> CREATOR = new Creator<KanaCharacter>() {
        @Override
        public KanaCharacter createFromParcel(Parcel in) {
            return new KanaCharacter(in);
        }

        @Override
        public KanaCharacter[] newArray(int size) {
            return new KanaCharacter[size];
        }
    };

    public String getCharId() {
        return mCharId;
    }

    public String getCharacter() {
        return mCharacter;
    }

    public String getRomanization() {
        return mRomanization;
    }

    @Override
    public String getFlashcardText() {
        return getCharacter();
    }

    @Override
    public String getFlashcardHint() {
        return getRomanization();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mCharId);
        dest.writeString(mCharacter);
        dest.writeString(mRomanization);
    }


    public static class CharacterComparator implements Comparator<KanaCharacter>
    {
        public int compare(KanaCharacter c1, KanaCharacter c2)
        {
            List<String> vowelOrder = Arrays.asList(VOWEL_ORDERING);
            List<String> consonantOrder = Arrays.asList(CONSONANT_ORDERING);

            String consonant1;
            String vowel1;

            if (c1.getCharId().length() == 1) {
                consonant1 = " ";
                vowel1 = c1.getCharId();
            } else {
                consonant1 = c1.getCharId().substring(0, 1);
                vowel1 = c1.getCharId().substring(1);
            }

            String consonant2;
            String vowel2;

            if (c2.getCharId().length() == 1) {
                consonant2 = " ";
                vowel2 = c2.getCharId();
            } else {
                consonant2 = c2.getCharId().substring(0, 1);
                vowel2 = c2.getCharId().substring(1);
            }

            if (TextUtils.equals(consonant1, consonant2)) {
                return vowelOrder.indexOf(vowel1) - vowelOrder.indexOf(vowel2);
            } else {
                return consonantOrder.indexOf(consonant1) - consonantOrder.indexOf(consonant2);
            }
        }
    }
}
