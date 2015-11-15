package ca.uwaterloo.sh6choi.kana.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Date;
import java.util.Random;

import ca.uwaterloo.sh6choi.kana.R;
import ca.uwaterloo.sh6choi.kana.activities.MainActivity;
import ca.uwaterloo.sh6choi.kana.utils.KanaUtils;

/**
 * Created by Samson on 2015-11-04.
 */
public class DictationFragment extends Fragment implements DrawerFragment, View.OnClickListener, View.OnTouchListener {
    private static final String TAG = DictationFragment.class.getCanonicalName();
    public static final String FRAGMENT_TAG = MainActivity.TAG + ".fragment.dictation";

    private String[] mDictationSet;

    private int mCurIndex = -1;

    private TextView mWordTextView;
    private EditText mInputEditText;
    private TextView mHintTextView;
    private Button mCheckButton;

    public static DictationFragment getInstance(Bundle args) {
        DictationFragment fragment = new DictationFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);

        View contentView = inflater.inflate(R.layout.fragment_dictation, container, false);
        return contentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mDictationSet = getResources().getStringArray(R.array.dictation_set_1);

        mWordTextView = (TextView) view.findViewById(R.id.word_text_view);
        mWordTextView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        mWordTextView.setOnTouchListener(this);

        mHintTextView = (TextView) view.findViewById(R.id.hint_text_view);

        mInputEditText = (EditText) view.findViewById(R.id.input_edit_text);
        mInputEditText.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        mCheckButton = (Button) view.findViewById(R.id.check_button);
        mCheckButton.setOnClickListener(this);

        switchWord();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.check_button:
                if (TextUtils.equals(mInputEditText.getText(), mDictationSet[mCurIndex])) {
                    switchWord();
                } else {
                    mInputEditText.setError("Incorrect");
                }
                break;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                switch (v.getId()) {
                    case R.id.word_text_view:
                        mHintTextView.setVisibility(View.VISIBLE);
                        return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                switch (v.getId()) {
                    case R.id.word_text_view:
                        mHintTextView.setVisibility(View.INVISIBLE);
                        return true;
                }
                break;
        }
        return false;
    }

    private void switchWord() {
        Random random = new Random(new Date().getTime());

        int nextInt;
        do {
            nextInt = random.nextInt(mDictationSet.length);
        } while (nextInt == mCurIndex);

        mCurIndex = nextInt;

        mWordTextView.setText(KanaUtils.romanize(mDictationSet[mCurIndex]));
        mHintTextView.setText(mDictationSet[mCurIndex]);

        mInputEditText.setText("");
        mInputEditText.setError(null);
    }

    @Override
    public String getFragmentTag() {
        return FRAGMENT_TAG;
    }

    @Override
    public int getTitleStringResId() {
        return R.string.nav_menu_dictation;
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
