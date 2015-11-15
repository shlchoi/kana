package ca.uwaterloo.sh6choi.kana.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.widget.Toast;

import java.util.Locale;

/**
 * Created by Samson on 2015-10-02.
 */
public class TextToSpeechService extends Service implements TextToSpeech.OnInitListener{

    private static final String TAG = TextToSpeechService.class.getCanonicalName();

    public static final String ACTION_PLAY_TEXT = TAG + ".action.play_text";

    public static final String EXTRA_TEXT = TAG + ".extra.text";

    private TextToSpeech mTextToSpeech;
    private boolean mInitialized;

    private OnInitializedListener mOnIntializedListener;

    private final TextToSpeechBinder mBinder = new TextToSpeechBinder();

    public TextToSpeechService() {
        super();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mTextToSpeech = new TextToSpeech(this, this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        if (TextUtils.equals(intent.getAction(), ACTION_PLAY_TEXT)) {
//            if (intent.hasExtra(EXTRA_TEXT)) {
//                String text = intent.getStringExtra(EXTRA_TEXT);
//                playSound(text);
//            }
//        }

        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onInit(int status) {
        if (mTextToSpeech.isLanguageAvailable(Locale.JAPAN) == TextToSpeech.LANG_AVAILABLE || mTextToSpeech.isLanguageAvailable(Locale.JAPAN) == TextToSpeech.LANG_COUNTRY_AVAILABLE) {
            mTextToSpeech.setLanguage(Locale.JAPAN);
            mTextToSpeech.setSpeechRate(0.5f);
            mInitialized = true;

            if (mOnIntializedListener != null) {
                mOnIntializedListener.onInitialized();
            }
        } else {
            Toast.makeText(this, "No Voice Files found", Toast.LENGTH_SHORT).show();

            if (mOnIntializedListener != null) {
                mOnIntializedListener.onError();
            }
        }
    }

    public void playSound(String text) {
        mTextToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    public boolean isInitialized() {
        return mInitialized;
    }

    public void setOnInitializedListener(OnInitializedListener listener){
        mOnIntializedListener = listener;
    }

    @Override
    public void onDestroy() {
        mTextToSpeech.shutdown();
        super.onDestroy();
    }

    public class TextToSpeechBinder extends Binder {
        public TextToSpeechService getService() {
            return TextToSpeechService.this;
        }
    }

    public interface OnInitializedListener {
        void onInitialized();

        void onError();
    }
}
