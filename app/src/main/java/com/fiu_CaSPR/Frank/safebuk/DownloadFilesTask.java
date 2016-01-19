package com.fiu_CaSPR.Frank.safebuk;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.webkit.WebView;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by sajib on 3/24/15.
 */
public class DownloadFilesTask extends AsyncTask<URL, Integer, Long> {
    protected Long doInBackground(URL... urls) {

        /**** New Code for calling webview *****/
        // Initialize the WebView

        int count = urls.length;

        long totalSize = 0;
        for (int i = 0; i < count; i++) {
            FetchData.wv.loadUrl(urls[i].toString());
        }
        //AfterLogin.webView.loadUrl(urls[0].toString());
        return totalSize;
    }

    protected void onProgressUpdate(Integer... progress) {
        //setProgressPercent(progress[0]);
    }

    protected void onPostExecute(Long result) {
        //showDialog("Downloaded " + result + " bytes");
    }
}