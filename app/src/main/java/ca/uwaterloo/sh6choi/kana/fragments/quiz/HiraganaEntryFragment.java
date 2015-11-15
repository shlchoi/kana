package ca.uwaterloo.sh6choi.kana.fragments.quiz;

import android.content.Context;
import android.os.Bundle;

import ca.uwaterloo.sh6choi.kana.R;
import ca.uwaterloo.sh6choi.kana.activities.MainActivity;
import ca.uwaterloo.sh6choi.kana.presentation.HiraganaCharacterPresenter;
import ca.uwaterloo.sh6choi.kana.presentation.KanaCharacterPresenter;

/**
 * Created by Samson on 2015-11-11.
 */
public class HiraganaEntryFragment extends EntryQuizFragment {
    private static final String TAG = HiraganaEntryFragment.class.getCanonicalName();
    private static final String FRAGMENT_TAG = MainActivity.TAG + ".quiz.hiragana_entry";

    public static HiraganaEntryFragment getInstance(Bundle args) {
        HiraganaEntryFragment fragment = new HiraganaEntryFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    protected KanaCharacterPresenter getCharacterPresenter(Context context, KanaCharacterPresenter.CharacterView view) {
        return new HiraganaCharacterPresenter(context, view);
    }

    @Override
    public String getFragmentTag() {
        return FRAGMENT_TAG;
    }

    @Override
    public int getTitleStringResId() {
        return R.string.quiz_hiragana;
    }
}
