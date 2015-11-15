package ca.uwaterloo.sh6choi.kana.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ca.uwaterloo.sh6choi.kana.R;
import ca.uwaterloo.sh6choi.kana.fragments.DictationFragment;
import ca.uwaterloo.sh6choi.kana.fragments.DrawerFragment;
import ca.uwaterloo.sh6choi.kana.fragments.kana.KanaCharacterFragment;
import ca.uwaterloo.sh6choi.kana.fragments.kana.HiraganaFlashcardFragment;
import ca.uwaterloo.sh6choi.kana.fragments.kana.KanaFragment;
import ca.uwaterloo.sh6choi.kana.fragments.kana.KanaLookupFragment;
import ca.uwaterloo.sh6choi.kana.fragments.quiz.HiraganaEntryFragment;
import ca.uwaterloo.sh6choi.kana.fragments.quiz.HiraganaQuizFragment;
import ca.uwaterloo.sh6choi.kana.fragments.kana.KatakanaFlashcardFragment;
import ca.uwaterloo.sh6choi.kana.fragments.quiz.KatakanaEntryFragment;
import ca.uwaterloo.sh6choi.kana.fragments.quiz.KatakanaQuizFragment;
import ca.uwaterloo.sh6choi.kana.fragments.quiz.QuizFragment;
import ca.uwaterloo.sh6choi.kana.model.KanaCharacter;
import ca.uwaterloo.sh6choi.kana.utils.KanaUtils;
import ca.uwaterloo.sh6choi.kana.utils.KeyboardUtils;
import ca.uwaterloo.sh6choi.kana.views.DrawerMenuAdapter;
import ca.uwaterloo.sh6choi.kana.views.IDrawerMenuItem;
import ca.uwaterloo.sh6choi.kana.views.ISlidingPane;
import ca.uwaterloo.sh6choi.kana.views.KanaMenuItem;
import ca.uwaterloo.sh6choi.kana.views.NavigationDrawerLayout;

public class MainActivity extends AppCompatActivity implements ViewTreeObserver.OnGlobalLayoutListener {

    public static final String TAG = MainActivity.class.getCanonicalName();

    public static final String ACTION_KANA = TAG + ".action.kana";
    public static final String ACTION_KANA_LOOKUP = ACTION_KANA + ".lookup";
    public static final String ACTION_KANA_CHARACTER = ACTION_KANA + "..character";
    public static final String ACTION_HIRAGANA_FLASHCARDS = ACTION_KANA + ".hiragana_flashcards";
    public static final String ACTION_KATAKANA_FLASHCARADS = ACTION_KANA + ".katakana_flashcards";

    public static final String ACTION_QUIZ = TAG + ".action.quiz";
    public static final String ACTION_HIRAGANA_SELECT = ACTION_QUIZ + ".hiragana_select";
    public static final String ACTION_KATAKANA_SELECT = ACTION_QUIZ + ".katakana_select";
    public static final String ACTION_HIRAGANA_ENTRY = ACTION_QUIZ + ".hiragana_entry";
    public static final String ACTION_KATAKANA_ENTRY = ACTION_QUIZ + ".katakana_entry";

    public static final String ACTION_DICTATION = TAG + ".action.dictation";

    public static final String ACTION_SPEECH = TAG + ".action.speech";

    public static final String EXTRA_CHARACTER = TAG +".extra.character";


    private NavigationDrawerLayout mDrawerLayout;
    private DrawerLayout mWrappedDrawerLayout;

    private ActionBarDrawerToggle mDrawerToggle;

    private Toolbar mToolbar;
    private TextView mToolbarActionTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        Intent hiragana = new Intent(this, HiraganaWebIntentService.class);
//        startService(hiragana);
//
//        Intent katakana = new Intent(this, KatakanaWebIntentService.class);
//        startService(katakana);

        setContentView(R.layout.activity_main);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        KanaUtils.refreshMap(this);

        mDrawerLayout = (NavigationDrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setMenuAdapter(new DrawerMenuAdapter(this, setupMenu()));


        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbarActionTextView = (TextView) mToolbar.findViewById(R.id.toolbar_action_tv);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM
                | ActionBar.DISPLAY_SHOW_HOME);

        mWrappedDrawerLayout = new ISlidingPane.DrawerLayoutWrapper(this, mDrawerLayout) {
            @Override
            public void openDrawer(int gravity) {
                KeyboardUtils.hideKeyboard(MainActivity.this);
                super.openDrawer(gravity);
            }
        };

        mDrawerToggle = new ActionBarDrawerToggle(this, mWrappedDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        mDrawerLayout.setDrawerEnabled(true);

        if (getIntent() == null || TextUtils.isEmpty(getIntent().getAction())) {
            Intent start = new Intent(ACTION_KANA);
            startActivity(start);
        } else {
            handleAction(getIntent());
        }
    }

    private List<IDrawerMenuItem> setupMenu() {
        List<IDrawerMenuItem> menuList = new ArrayList<>();
        menuList.add(KanaMenuItem.KANA);
        menuList.add(KanaMenuItem.QUIZ);
        menuList.add(KanaMenuItem.DICTATION);
        return menuList;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (!TextUtils.isEmpty(intent.getAction())) {
            handleAction(intent);
            setIntent(intent);
        }
    }

    private void handleAction(Intent intent) {
        String action = intent.getAction();

        if (TextUtils.equals(action, ACTION_KANA)) {
            onKana();
        } else if (TextUtils.equals(action, ACTION_KANA_LOOKUP)) {
            onKanaLookup();
        } else if (TextUtils.equals(action, ACTION_KANA_CHARACTER)) {
            onKanaCharacter(intent);
        } else if (TextUtils.equals(action, ACTION_HIRAGANA_FLASHCARDS)) {
            onHiraganaFlashcards();
        } else if (TextUtils.equals(action, ACTION_KATAKANA_FLASHCARADS)) {
            onKatakanaFlashcards();
        } else if (TextUtils.equals(action, ACTION_QUIZ)) {
            onQuiz();
        } else if (TextUtils.equals(action, ACTION_HIRAGANA_SELECT)) {
            onHiraganaQuiz();
        } else if (TextUtils.equals(action, ACTION_KATAKANA_SELECT)) {
            onKatakanaQuiz();
        } else if (TextUtils.equals(action, ACTION_HIRAGANA_ENTRY)) {
            onHiraganaEntry();
        } else if (TextUtils.equals(action, ACTION_KATAKANA_ENTRY)) {
            onKatakanaEntry();
        } else if (TextUtils.equals(action, ACTION_DICTATION)) {
            onDictation();
        } else {
            onKana();
        }
    }

    private void onKana() {
        if (getSupportFragmentManager().findFragmentById(R.id.fragment_container) instanceof KanaFragment) {
            mDrawerLayout.closePane();
            return;
        }
        KanaFragment fragment = KanaFragment.getInstance(new Bundle());
        swapFragment(fragment);
    }

    private void onKanaLookup() {
        if (getSupportFragmentManager().findFragmentById(R.id.fragment_container) instanceof KanaLookupFragment) {
            mDrawerLayout.closePane();
            return;
        }
        KanaLookupFragment fragment = KanaLookupFragment.getInstance(new Bundle());
        swapFragment(fragment);
    }

    private void onKanaCharacter(Intent intent) {
        if (getSupportFragmentManager().findFragmentById(R.id.fragment_container) instanceof KanaCharacterFragment) {
            mDrawerLayout.closePane();
            return;
        }

        KanaCharacter character = intent.getParcelableExtra(EXTRA_CHARACTER);

        Bundle args = new Bundle();
        args.putParcelable(KanaCharacterFragment.ARG_KANA_CHARACTER, character);

        KanaCharacterFragment fragment = KanaCharacterFragment.getInstance(args);
        swapFragment(fragment);
    }

    private void onHiraganaFlashcards() {
        if (getSupportFragmentManager().findFragmentById(R.id.fragment_container) instanceof HiraganaFlashcardFragment) {
            mDrawerLayout.closePane();
            return;
        }
        HiraganaFlashcardFragment fragment = HiraganaFlashcardFragment.getInstance(new Bundle());
        swapFragment(fragment);
    }

    private void onKatakanaFlashcards() {
        if (getSupportFragmentManager().findFragmentById(R.id.fragment_container) instanceof KatakanaFlashcardFragment) {
            mDrawerLayout.closePane();
            return;
        }
        KatakanaFlashcardFragment fragment = KatakanaFlashcardFragment.getInstance(new Bundle());
        swapFragment(fragment);
    }

    private void onQuiz() {
        if (getSupportFragmentManager().findFragmentById(R.id.fragment_container) instanceof QuizFragment) {
            mDrawerLayout.closePane();
            return;
        }
        QuizFragment fragment = QuizFragment.getInstance(new Bundle());
        swapFragment(fragment);
    }

    private void onHiraganaQuiz() {
        if (getSupportFragmentManager().findFragmentById(R.id.fragment_container) instanceof HiraganaQuizFragment) {
            mDrawerLayout.closePane();
            return;
        }
        HiraganaQuizFragment fragment = HiraganaQuizFragment.getInstance(new Bundle());
        swapFragment(fragment);
    }

    private void onKatakanaQuiz() {
        if (getSupportFragmentManager().findFragmentById(R.id.fragment_container) instanceof KatakanaQuizFragment) {
            mDrawerLayout.closePane();
            return;
        }
        KatakanaQuizFragment fragment = KatakanaQuizFragment.getInstance(new Bundle());
        swapFragment(fragment);
    }

    private void onHiraganaEntry() {
        if (getSupportFragmentManager().findFragmentById(R.id.fragment_container) instanceof HiraganaEntryFragment) {
            mDrawerLayout.closePane();
            return;
        }
        HiraganaEntryFragment fragment = HiraganaEntryFragment.getInstance(new Bundle());
        swapFragment(fragment);
    }

    private void onKatakanaEntry() {
        if (getSupportFragmentManager().findFragmentById(R.id.fragment_container) instanceof KatakanaEntryFragment) {
            mDrawerLayout.closePane();
            return;
        }
        KatakanaEntryFragment fragment = KatakanaEntryFragment.getInstance(new Bundle());
        swapFragment(fragment);
    }

    private void onDictation() {
        if (getSupportFragmentManager().findFragmentById(R.id.fragment_container) instanceof DictationFragment) {
            mDrawerLayout.closePane();
            return;
        }
        DictationFragment fragment = DictationFragment.getInstance(new Bundle());
        swapFragment(fragment);
    }

    private <T extends Fragment & DrawerFragment> void swapFragment(T fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out);

        if (fragment.shouldAddToBackstack()) {
            transaction.addToBackStack(fragment.getFragmentTag());
        }

        transaction.replace(R.id.fragment_container, fragment, fragment.getFragmentTag());
        transaction.commit();

        updateToolbar(fragment);

        final View fragmentContainer = findViewById(R.id.fragment_container);

        fragmentContainer.getViewTreeObserver().addOnGlobalLayoutListener(this);

        refreshToolbar(fragment.shouldShowUp(), fragment.getTitleStringResId());
    }

    private <T extends Fragment & DrawerFragment> void updateToolbar(T fragment) {
        mToolbarActionTextView.setText(fragment.getTitleStringResId());
    }

    @Override
    public void onBackPressed() {
        KeyboardUtils.hideKeyboard(this);
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);

        if (currentFragment instanceof DrawerFragment) {
            if (!((DrawerFragment) currentFragment).onBackPressed()) {
                super.onBackPressed();
            }
        } else {
            super.onBackPressed();
        }
    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (resultCode == RESULT_OK && data != null) {
//            if (getSupportFragmentManager().findFragmentById(R.id.fragment_container) instanceof PronunciationFragment) {
//                Intent intent = new Intent();
//                intent.setAction(ACTION_SPEECH);
//                intent.putExtra(PronunciationFragment.EXTRA_RESULTS, data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS));
//
//                sendBroadcast(intent);
//            }
//        }
//
//        super.onActivityResult(requestCode, resultCode, data);
//    }

    @Override
    public void onGlobalLayout() {
        final View fragmentContainer = findViewById(R.id.fragment_container);
        mDrawerLayout.closePane();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
            fragmentContainer.getViewTreeObserver().removeOnGlobalLayoutListener(this);
        else
            fragmentContainer.getViewTreeObserver().removeGlobalOnLayoutListener(this);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (mDrawerToggle != null) {
            mDrawerToggle.syncState();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    private void refreshToolbar(boolean shouldShowUp, int titleResId) {
        if (getSupportActionBar() == null)
            return;

        if (shouldShowUp) {
            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM
                    | ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_SHOW_HOME);
            mDrawerLayout.setDrawerEnabled(false);
            setTitle(titleResId);

            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        } else {
            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM
                    | ActionBar.DISPLAY_SHOW_HOME);
            mDrawerLayout.setDrawerEnabled(true);

            mDrawerToggle = new ActionBarDrawerToggle(this, mWrappedDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

            mDrawerLayout.addDrawerListener(mDrawerToggle);
            mDrawerToggle.syncState();

            mDrawerToggle.setToolbarNavigationClickListener(null);
        }
    }
}
