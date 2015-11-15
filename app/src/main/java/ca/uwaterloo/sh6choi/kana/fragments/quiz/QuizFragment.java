package ca.uwaterloo.sh6choi.kana.fragments.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import ca.uwaterloo.sh6choi.kana.R;
import ca.uwaterloo.sh6choi.kana.activities.MainActivity;
import ca.uwaterloo.sh6choi.kana.fragments.DrawerFragment;


/**
 * Created by Samson on 2015-09-25.
 */
public class QuizFragment extends Fragment implements DrawerFragment, View.OnClickListener {

    private static final String TAG = QuizFragment.class.getCanonicalName();
    public static final String FRAGMENT_TAG = MainActivity.TAG + ".fragment.quiz";

    private Button mHiraganaQuizButton;
    private Button mKatakanaQuizButton;

    private Button mHiraganaEntryButton;
    private Button mKatakanaEntryButton;

    public static QuizFragment getInstance(Bundle args) {
        QuizFragment fragment = new QuizFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);
        setHasOptionsMenu(false);
        View contentView = inflater.inflate(R.layout.fragment_quiz, container, false);
        return contentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mHiraganaQuizButton = (Button) view.findViewById(R.id.hiragana_quiz_button);
        mKatakanaQuizButton = (Button) view.findViewById(R.id.katakana_quiz_button);
        mHiraganaEntryButton = (Button) view.findViewById(R.id.hiragana_entry_button);
        mKatakanaEntryButton = (Button) view.findViewById(R.id.katakana_entry_button);

        mHiraganaQuizButton.setOnClickListener(this);
        mKatakanaQuizButton.setOnClickListener(this);
        mHiraganaEntryButton.setOnClickListener(this);
        mKatakanaEntryButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.hiragana_quiz_button:
                onHiraganaQuizButtonClicked();
                break;
            case R.id.katakana_quiz_button:
                onKatakanaQuizButtonClicked();
                break;
            case R.id.hiragana_entry_button:
                onHiraganaEntryButtonClicked();
                break;
            case R.id.katakana_entry_button:
                onKatakanaEntryButtonClicked();
                break;
        }
    }

    private void onHiraganaQuizButtonClicked() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.setAction(MainActivity.ACTION_HIRAGANA_SELECT);
        startActivity(intent);
    }

    private void onKatakanaQuizButtonClicked() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.setAction(MainActivity.ACTION_KATAKANA_SELECT);
        startActivity(intent);
    }

    private void onHiraganaEntryButtonClicked() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.setAction(MainActivity.ACTION_HIRAGANA_ENTRY);
        startActivity(intent);
    }

    private void onKatakanaEntryButtonClicked() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.setAction(MainActivity.ACTION_KATAKANA_ENTRY);
        startActivity(intent);
    }

    @Override
    public String getFragmentTag() {
        return FRAGMENT_TAG;
    }

    @Override
    public int getTitleStringResId() {
        return R.string.nav_menu_quiz;
    }

    @Override
    public boolean shouldShowUp() {
        return false;
    }

    @Override
    public boolean shouldAddToBackstack() {
        return false;
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }
}
