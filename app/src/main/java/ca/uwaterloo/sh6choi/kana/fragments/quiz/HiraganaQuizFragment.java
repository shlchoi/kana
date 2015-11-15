package ca.uwaterloo.sh6choi.kana.fragments.quiz;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import ca.uwaterloo.sh6choi.kana.R;
import ca.uwaterloo.sh6choi.kana.activities.MainActivity;
import ca.uwaterloo.sh6choi.kana.fragments.DrawerFragment;
import ca.uwaterloo.sh6choi.kana.model.KanaCharacter;
import ca.uwaterloo.sh6choi.kana.presentation.HiraganaCharacterPresenter;
import ca.uwaterloo.sh6choi.kana.presentation.KanaCharacterPresenter;

/**
 * Created by Samson on 2015-11-02.
 */
public class HiraganaQuizFragment extends SelectQuizFragment {
    private static final String TAG = HiraganaQuizFragment.class.getCanonicalName();
    private static final String FRAGMENT_TAG = MainActivity.TAG + ".fragment.hiragana_quiz";

    public static HiraganaQuizFragment getInstance(Bundle args) {
        HiraganaQuizFragment fragment = new HiraganaQuizFragment();
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
