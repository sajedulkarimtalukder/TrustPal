package com.fiu_CaSPR.Frank.safebuk;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.fiu_CaSPR.Frank.Constants.Dictionary;
import com.fiu_CaSPR.Frank.Constants.FacebookRegexPatternPool;
import com.fiu_CaSPR.Frank.DataStructures.FriendRequest;
import com.fiu_CaSPR.Frank.Utility.DatabaseHelper;
import com.fiu_CaSPR.Frank.Utility.MyTimerTask;
import com.mizan.friendstar.MainPageActivity;
import com.fiu_CaSPR.Frank.safebuk.DownloadFilesTask;
import com.temboo.Library.Facebook.Reading.User;
import com.temboo.core.TembooException;
import com.temboo.core.TembooSession;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.Timer;

import static android.os.AsyncTask.execute;


public class AfterLogin extends ActionBarActivity {


    public static boolean finishedLoggingIn = false;
    final Context context = this;
    private FacebookOAuthHelper oAuthHelper;
    public static String htmlSource = "";
    public static String urlLoad = "";
    public static WebView webView;
    public static int filecount=0;

    private ProgressDialog dialog = null;
    // We won't navigate to this URL, we simply use it as an indicator of
    // when in the OAuth flow we should go through the finalize routines
    private final static String FORWARDING_URL = "http://temboo.placeholder.url";
    @Deprecated
    public static String friendRequestDataString = "";
    @Deprecated
    public static boolean alreadyLoadedFriendsURL = false;
    final MyTimerTask myTask = new MyTimerTask(this);
    final Timer myTimer = new Timer();
    public static WebSettings webSettings =null;

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toast.makeText(AfterLogin.this, "After Login Page Loaded", Toast.LENGTH_SHORT).show();

        if (isNetworkAvailable()) {
            Toast.makeText(this, "Loading Please Wait", Toast.LENGTH_LONG);
        } else {
            Toast.makeText(this, "No Network Connection", Toast.LENGTH_LONG);
            this.finish();
            System.exit(0);
        }


        // Initialize the WebView
        //FacebookRegexPatternPool.id = "10200577169468421";
        webView = (WebView) findViewById(R.id.webView);
        webSettings =  webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.getSettings().setUseWideViewPort(false);
        Toast.makeText(AfterLogin.this, "Access Token: " + AccessToken.getCurrentAccessToken().getToken(), Toast.LENGTH_SHORT).show();
        //AfterLogin.webView.loadUrl("https://www.facebook.com/10200577169468421");
        new FacebookGetUserInfoTask().execute(null, null, null);
        /*// Initialize OAuth helper
        oAuthHelper = new FacebookOAuthHelper(webView, FORWARDING_URL, this);

        // Get the Facebook auth URL
        oAuthHelper.initOauth();*/
        webView.setWebViewClient(new WebViewClient() {
            boolean timer = false;

            /*@Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                *//*if (url.startsWith(FORWARDING_URL)) {
                    // spawn worker thread to do api calls to get list of contacts to display
                    oAuthHelper.initOauth();
                    // true = do not navigate to URL in web view
                    return true;
                }*//*
                oAuthHelper.initOauth();

                // Default behavior - redirect to specified URL
                return super.shouldOverrideUrlLoading(view, url);
            }*/

            // When a webview's page has finished loading, we see if we have the user's username or not by determining if we are
            // on the website m.facebook.com yet, and if the user has logged in yet. We know if the user has logged in or not,
            // by if we have their id yet. If we have their id, that means we can get the user's username from the url
            public void onPageFinished(WebView view, String url) {
                Toast.makeText(AfterLogin.this, "OnPageFinished Called", Toast.LENGTH_SHORT).show();

                if (webView != null) {
                    String s = webView.getUrl();
                    //Toast.makeText(AfterLogin.this, "URL LOADED: " + s, Toast.LENGTH_SHORT).show();
                    if (s.contains("facebook.com") && FacebookRegexPatternPool.id != "") {
                        Log.i("PageLoadURL:", webView.getUrl());
                        String[] urlCut = webView.getUrl().split("/");
                        StringBuilder builder = new StringBuilder();
                        for (int i = 0; i < urlCut[urlCut.length - 1].length(); ++i) {
                            if (urlCut[urlCut.length - 1].charAt(i) != '?') {
                                builder.append(urlCut[urlCut.length - 1].charAt(i));
                            } else break;
                        }
                        FacebookRegexPatternPool.userName = builder.toString();
                        start();
                    }

                }

            }
        });


    }

    public void start()
    {
        Intent intent = new Intent(getApplicationContext(),FetchData.class);
        Toast.makeText(AfterLogin.this, "Now calling FetchData to save files", Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        myTimer.cancel();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }




}


