package com.fiu_CaSPR.Sajib.TrustPal;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.fiu_CaSPR.Sajib.Constants.FacebookRegexPatternPool;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class url4 extends Activity {

    public static WebView wv;
    ProgressBar progressBar;
    private int progressStatus = 0;
    private Handler handler = new Handler();
    public static WebSettings webSettings = null;
    public String username;
    public String url4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            username = extras.getString("username");
        }
        url4= "https://www.facebook.com/" + username + "/about?section=education&pnref=about";
        setContentView(R.layout.url_fetch_4);
        progressBar = (ProgressBar) findViewById(R.id.myProgressBar);

        // Initialize the WebView
        wv = new WebView(this);
        wv.setWebViewClient(new WebViewClient());
        wv.addJavascriptInterface(new LoadListener(), "HTMLOUT");
        wv.getSettings().setJavaScriptEnabled(true);
        wv.getSettings().setUserAgentString("Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.9.0.4) Gecko/20100101 Firefox/4.0");

        wv.loadUrl(url4);
        //Toast.makeText(url4.this, "page 4 started", Toast.LENGTH_SHORT).show();


        wv.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                progressBar.setProgress(progress);
               /* if(progress==100)
                {
                    Intent intent = new Intent(getApplicationContext(), FriendSelectorView.class);
                    startActivity(intent);
                }*/
            }
        });



        // Set up WebView for OAuth2 login - intercept redirect when the redirect
        // URL matches our FORWARDING_URL, in which case we will complete the OAuth
        // flow using Temboo
        /*wv.setWebViewClient(new WebViewClient() {
            private boolean isRedirected;
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                view.loadUrl(url);
                isRedirected = true;
                return true;
            }
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {

                if (!isRedirected) {
                    //Do something you want when starts loading
                }

                isRedirected = false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {

                if (!isRedirected) {
                    //Do something you want when finished loading
                    //System.out.println("PageFinished: " + url);
                    wv.loadUrl("javascript:window.HTMLOUT.saveHTML('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>');");
                }
            }

        });*/

        wv.setWebViewClient(new WebViewClient() {
            boolean loadingFinished = true;
            boolean redirect = false;

            long last_page_start;
            long now;

            // Load the url
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (!loadingFinished) {
                    redirect = true;
                }

                loadingFinished = false;
                view.loadUrl(url);
                return false;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                Log.i("p","pagestart 4");
                loadingFinished = false;
            }

            // When finish loading page
            public void onPageFinished(WebView view, String url) {
                Log.i("p","pagefinish 4");
                if(!redirect){
                    loadingFinished = true;
                }
                //call remove_splash in 500 miSec
                if(loadingFinished && !redirect){
                    wv.loadUrl("javascript:window.HTMLOUT.saveHTML('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>');");
                } else{
                    redirect = false;
                }
            }

        });

        /********************/


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_fetch_data, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class LoadListener {
        @JavascriptInterface
        public void saveHTML(String html) {
            //Toast.makeText(com.fiu_CaSPR.Frank.safebuk.url0.this, "JS function called", Toast.LENGTH_SHORT).show();
            try {
                FileOutputStream outputStream;

                //Create Folder
                File folder = new File(Environment.getExternalStorageDirectory().toString() + "/TrustPal/"+FacebookRegexPatternPool.Name+"/"+FacebookRegexPatternPool.Name);
                folder.mkdirs();

                //Save the path as a string value
                String extStorageDirectory = folder.toString();

                File file = new File(extStorageDirectory, FacebookRegexPatternPool.userName + "_" + "education.txt");
                if(file.exists())
                {
                    //Do something

                }
                else {
                    // Do something else.
                    outputStream = new FileOutputStream(file);
                    outputStream.write(html.getBytes());
                    outputStream.close();
                    //Toast.makeText(url4.this, "100% done", Toast.LENGTH_SHORT).show();
                }
                //Toast.makeText(friendurl0.this, "About file Saved Successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), SaveFriends.class);
                startActivity(intent);


            } catch (IOException e) {
                Toast.makeText(com.fiu_CaSPR.Sajib.TrustPal.url4.this, "File Creation Failed. Reason:" + e.toString(), Toast.LENGTH_SHORT).show();
                Log.e("Exception", "File write failed: " + e.toString());
            }
        }

    }
}
