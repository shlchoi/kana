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
public abstract class SelectQuizFragment extends Fragment implements DrawerFragment, View.OnClickListener, KanaCharacterPresenter.CharacterView {
    private static final String TAG = SelectQuizFragment.class.getCanonicalName();
    private static final String FRAGMENT_TAG = MainActivity.TAG + ".fragment.kana_quiz";

    private List<KanaCharacter> mKanaCharacterList;
    protected KanaCharacterPresenter mPresenter;

    protected KanaCharacter mCurCharacter;

    private TextView mCharacterTextView;
    private Button[] mAnswerButtons;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setHasOptionsMenu(false);
        return inflater.inflate(R.layout.fragment_kana_quiz, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mCharacterTextView = (TextView) view.findViewById(R.id.character_text_view);
        mKanaCharacterList = new ArrayList<>();

        mAnswerButtons = new Button[4];

        mAnswerButtons[0] = (Button) view.findViewById(R.id.answer_1_button);
        mAnswerButtons[1] = (Button) view.findViewById(R.id.answer_2_button);
        mAnswerButtons[2] = (Button) view.findViewById(R.id.answer_3_button);
        mAnswerButtons[3] = (Button) view.findViewById(R.id.answer_4_button);

        for (int i = 0; i < mAnswerButtons.length; i ++) {
            mAnswerButtons[i].setOnClickListener(this);
        }

        mPresenter = getCharacterPresenter(getContext(), this);
        mPresenter.obtainRegularCharacters();
    }

    @Override
    public void refreshCharacterList(List<KanaCharacter> kanaCharacterList) {
        mKanaCharacterList = kanaCharacterList;
        switchCharacter();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.answer_1_button:
                if (checkAnswer(mAnswerButtons[0].getText().toString())) {
                    switchCharacter();
                } else {
                    mAnswerButtons[0].setEnabled(false);
                }
                break;
            case R.id.answer_2_button:
                if (checkAnswer(mAnswerButtons[1].getText().toString())) {
                    switchCharacter();
                } else {
                    mAnswerButtons[1].setEnabled(false);
                }
                break;
            case R.id.answer_3_button:
                if (checkAnswer(mAnswerButtons[2].getText().toString())) {
                    switchCharacter();
                } else {
                    mAnswerButtons[2].setEnabled(false);
                }
                break;
            case R.id.answer_4_button:
                if (checkAnswer(mAnswerButtons[3].getText().toString())) {
                    switchCharacter();
                } else {
                    mAnswerButtons[3].setEnabled(false);
                }
                break;
        }
    }

    protected abstract KanaCharacterPresenter getCharacterPresenter(Context context, KanaCharacterPresenter.CharacterView view);

    private boolean checkAnswer(String answer) {
        return TextUtils.equals(mCurCharacter.getRomanization(), answer);
    }

    private void switchCharacter() {
        Random random = new Random(new Date().getTime());
        int nextInt;

        do {
            nextInt = random.nextInt(mKanaCharacterList.size());
        } while (mKanaCharacterList.get(nextInt) == mCurCharacter);

        mCurCharacter = mKanaCharacterList.get(nextInt);
        mCharacterTextView.setText(mCurCharacter.getCharacter());

        int correctAnswer = random.nextInt(4);
        List<Integer> choices = new ArrayList<>();
        choices.add(nextInt);
        for (int i = 0; i < mAnswerButtons.length; i ++) {
            int answerInt;
            do {
                answerInt = random.nextInt(mKanaCharacterList.size());
            } while (choices.contains(answerInt));

            if (i == correctAnswer) {
                mAnswerButtons[i].setText(mCurCharacter.getRomanization());
            } else {
                mAnswerButtons[i].setText(mKanaCharacterList.get(answerInt).getRomanization());
                choices.add(answerInt);
            }
            mAnswerButtons[i].setEnabled(true);
        }
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
