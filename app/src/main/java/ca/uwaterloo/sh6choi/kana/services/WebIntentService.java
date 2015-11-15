package ca.uwaterloo.sh6choi.kana.services;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Samson on 2015-09-24.
 */
public abstract class WebIntentService extends IntentService {

    private final static String TAG = WebIntentService.class.getCanonicalName();

    public WebIntentService(String name) {
        super(name);
    }

    /** Returns the URL to be used. */
    protected abstract URL getUrl() throws MalformedURLException;

    /** Reads the content of the HTML page */
    private String readStream(InputStream inputStream) throws IOException {
        BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder total = new StringBuilder();
        String line;
        while ((line = r.readLine()) != null) {
            total.append(line);
        }
        return total.toString();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        try {
            HttpURLConnection urlConnection = (HttpURLConnection) getUrl().openConnection();

            InputStream in = new BufferedInputStream(urlConnection.getInputStream());

            onResponse(readStream(in));
        } catch (Exception e) {
            Log.e(TAG, "Could not perform the operation! " + e.getMessage());
            e.printStackTrace();
            onError(e);
        }
    }

    public abstract void onResponse(String response);

    public abstract void onError(Exception e);
}