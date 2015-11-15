package ca.uwaterloo.sh6choi.kana.fragments.kana;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ca.uwaterloo.sh6choi.kana.R;
import ca.uwaterloo.sh6choi.kana.activities.MainActivity;
import ca.uwaterloo.sh6choi.kana.fragments.DrawerFragment;
import ca.uwaterloo.sh6choi.kana.model.KanaCharacter;
import ca.uwaterloo.sh6choi.kana.services.TextToSpeechService;

/**
 * Created by Samson on 2015-09-25.
 */
public class KanaCharacterFragment extends Fragment implements DrawerFragment, View.OnClickListener,
       TextToSpeechService.OnInitializedListener {

    private static final String TAG = HiraganaFlashcardFragment.class.getCanonicalName();
    public static final String FRAGMENT_TAG = MainActivity.TAG + ".fragment.kana.flashcards";

    public static final String ARG_KANA_CHARACTER = TAG + ".arg.character";

    private KanaCharacter mKanaCharacter;

    private TextView mCharacterTextView;
    private TextView mNameTextView;
    private FloatingActionButton mPlayFab;

    private TextToSpeechService mTextToSpeechService;
    private boolean mBound;
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            if (service != null) {
                TextToSpeechService.TextToSpeechBinder binder = (TextToSpeechService.TextToSpeechBinder) service;
                mTextToSpeechService = binder.getService();
                mTextToSpeechService.setOnInitializedListener(KanaCharacterFragment.this);

                mBound = true;
                mPlayFab.setEnabled(mTextToSpeechService.isInitialized());
            } else {
                mBound = false;
                mPlayFab.setEnabled(false);
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName className) {
            mBound = false;
            mTextToSpeechService = null;
        }
    };

    public static KanaCharacterFragment getInstance(Bundle args) {
        KanaCharacterFragment fragment = new KanaCharacterFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);

        View contentView = inflater.inflate(R.layout.fragment_kana_character, container, false);

        Bundle args = getArguments();

        if (args.containsKey(ARG_KANA_CHARACTER)) {
            mKanaCharacter = args.getParcelable(ARG_KANA_CHARACTER);
        }

        return contentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mCharacterTextView = (TextView) view.findViewById(R.id.character_text_view);
        mCharacterTextView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        mCharacterTextView.setText(mKanaCharacter.getCharacter());

        mNameTextView = (TextView) view.findViewById(R.id.romanization_text_view);
        mNameTextView.setText(mKanaCharacter.getRomanization());

        mPlayFab = (FloatingActionButton) view.findViewById(R.id.play_fab);
        mPlayFab.setOnClickListener(this);
        mPlayFab.setEnabled(false);

        Intent intent = new Intent(getActivity(), TextToSpeechService.class);
        getActivity().bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onInitialized() {
        mPlayFab.setEnabled(true);
    }

    @Override
    public void onError() {
        mPlayFab.setEnabled(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.play_fab:
                if (mBound) {
                    mTextToSpeechService.playSound(mKanaCharacter.getCharacter());
                }
                break;
        }
    }

    @Override
    public String getFragmentTag() {
        return FRAGMENT_TAG;
    }

    @Override
    public int getTitleStringResId() {
        return R.string.toolbar_title_character_lookup;
    }

    @Override
    public boolean shouldShowUp() {
        return true;
    }

    @Override
    public boolean shouldAddToBackstack() {
        return true;
    }

    @Override
    public boolean onBackPressed() {
        getActivity().getSupportFragmentManager().popBackStack();
        return true;
    }
}
