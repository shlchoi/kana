package ca.uwaterloo.sh6choi.kana.fragments.kana;

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
public class KanaFragment extends Fragment implements DrawerFragment, View.OnClickListener {

    private static final String TAG = KanaFragment.class.getCanonicalName();
    public static final String FRAGMENT_TAG = MainActivity.TAG + ".fragment.kana";

    private Button mLookupButton;
    private Button mHiraganaFlashcardButton;
    private Button mKatakanaFlashcardButton;

    public static KanaFragment getInstance(Bundle args) {
        KanaFragment fragment = new KanaFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);
        setHasOptionsMenu(false);
        View contentView = inflater.inflate(R.layout.fragment_kana, container, false);
        return contentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mLookupButton = (Button) view.findViewById(R.id.lookup_button);
        mHiraganaFlashcardButton = (Button) view.findViewById(R.id.hiragana_flashcards_button);
        mKatakanaFlashcardButton = (Button) view.findViewById(R.id.katakana_flashcards_button);

        mLookupButton.setOnClickListener(this);
        mHiraganaFlashcardButton.setOnClickListener(this);
        mKatakanaFlashcardButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lookup_button:
                onLookupButtonClicked();
                break;
            case R.id.hiragana_flashcards_button:
                onHiraganaFlashcardButtonClicked();
                break;
            case R.id.katakana_flashcards_button:
                onKatakanaFlashcardButtonClicked();
                break;
        }
    }

    private void onLookupButtonClicked() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.setAction(MainActivity.ACTION_KANA_LOOKUP);
        startActivity(intent);
    }

    private void onHiraganaFlashcardButtonClicked() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.setAction(MainActivity.ACTION_HIRAGANA_FLASHCARDS);
        startActivity(intent);
    }

    private void onKatakanaFlashcardButtonClicked() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.setAction(MainActivity.ACTION_KATAKANA_FLASHCARADS);
        startActivity(intent);
    }

    @Override
    public String getFragmentTag() {
        return FRAGMENT_TAG;
    }

    @Override
    public int getTitleStringResId() {
        return R.string.nav_menu_kana;
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
