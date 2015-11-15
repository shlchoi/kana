package ca.uwaterloo.sh6choi.kana.fragments.quiz;

import android.content.Context;
import android.content.Intent;
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
import ca.uwaterloo.sh6choi.kana.presentation.KatakanaCharacterPresenter;

/**
 * Created by Samson on 2015-11-10.
 */
public abstract class EntryQuizFragment extends Fragment implements DrawerFragment, View.OnClickListener,
        View.OnTouchListener, KanaCharacterPresenter.CharacterView {
    private static final String TAG = EntryQuizFragment.class.getCanonicalName();

    private List<KanaCharacter> mKanaCharacterList;
    protected KanaCharacterPresenter mPresenter;

    protected KanaCharacter mCurCharacter;

    private TextView mRomanizationTextView;
    private EditText mAnswerEditText;
    private TextView mHintTextView;
    private Button mCheckButton;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setHasOptionsMenu(false);
        return inflater.inflate(R.layout.fragment_text_entry, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRomanizationTextView = (TextView) view.findViewById(R.id.prompt_text_view);
        mAnswerEditText = (EditText) view.findViewById(R.id.input_edit_text);
        mHintTextView = (TextView) view.findViewById(R.id.hint_text_view);
        mCheckButton = (Button) view.findViewById(R.id.check_button);

        view.findViewById(R.id.text_entry_linear_layout).setOnTouchListener(this);
        mRomanizationTextView.setOnTouchListener(this);
        mCheckButton.setOnClickListener(this);

        mKanaCharacterList = new ArrayList<>();

        mPresenter = getCharacterPresenter(getContext(), this);
        mPresenter.obtainRegularCharacters();
    }

    @Override
    public void refreshCharacterList(List<KanaCharacter> kanaCharacterList) {
        mKanaCharacterList = kanaCharacterList;
        switchCharacter();
    }

    protected abstract KanaCharacterPresenter getCharacterPresenter(Context context, KanaCharacterPresenter.CharacterView view);

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.check_button:
                if (TextUtils.equals(mAnswerEditText.getText().toString().replace(" ", ""), mCurCharacter.getCharacter())) {
                    switchCharacter();
                } else {
                    mAnswerEditText.setError("Incorrect");
                }
                break;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                switch (v.getId()) {
                    case R.id.prompt_text_view:
                        mHintTextView.setVisibility(View.VISIBLE);
                        return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                switch (v.getId()) {
                    case R.id.prompt_text_view:
                    case R.id.text_entry_linear_layout:
                        mHintTextView.setVisibility(View.INVISIBLE);
                        return true;
                }
                break;
        }
        return false;
    }

    private void switchCharacter() {
        Random random = new Random(new Date().getTime());
        int nextInt;

        do {
            nextInt = random.nextInt(mKanaCharacterList.size());
        } while (mKanaCharacterList.get(nextInt) == mCurCharacter);

        mCurCharacter = mKanaCharacterList.get(nextInt);

        mRomanizationTextView.setText(mCurCharacter.getRomanization());
        mHintTextView.setText(mCurCharacter.getCharacter());

        mAnswerEditText.setText("");
        mAnswerEditText.setError(null);
    }

    @Override
    public boolean shouldShowUp() {
        return true;
    }

    @Override
    public boolean shouldAddToBackstack() {
        return false;
    }

    @Override
    public boolean onBackPressed() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.setAction(MainActivity.ACTION_QUIZ);

        startActivity(intent);
        return true;
    }

}
