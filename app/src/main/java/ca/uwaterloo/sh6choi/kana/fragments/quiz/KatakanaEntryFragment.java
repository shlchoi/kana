package ca.uwaterloo.sh6choi.kana.fragments.quiz;

import android.content.Context;
import android.os.Bundle;

import ca.uwaterloo.sh6choi.kana.R;
import ca.uwaterloo.sh6choi.kana.activities.MainActivity;
import ca.uwaterloo.sh6choi.kana.presentation.HiraganaCharacterPresenter;
import ca.uwaterloo.sh6choi.kana.presentation.KanaCharacterPresenter;
import ca.uwaterloo.sh6choi.kana.presentation.KatakanaCharacterPresenter;

/**
 * Created by Samson on 2015-11-11.
 */
public class KatakanaEntryFragment extends EntryQuizFragment {
    private static final String TAG = KatakanaEntryFragment.class.getCanonicalName();
    private static final String FRAGMENT_TAG = MainActivity.TAG + ".quiz.katakana_entry";

    public static KatakanaEntryFragment getInstance(Bundle args) {
        KatakanaEntryFragment fragment = new KatakanaEntryFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    protected KanaCharacterPresenter getCharacterPresenter(Context context, KanaCharacterPresenter.CharacterView view) {
        return new KatakanaCharacterPresenter(context, view);
    }

    @Override
    public String getFragmentTag() {
        return FRAGMENT_TAG;
    }

    @Override
    public int getTitleStringResId() {
        return R.string.quiz_katakana;
    }
}
