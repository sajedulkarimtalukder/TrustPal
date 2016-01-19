package com.fiu_CaSPR.Frank.safebuk;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
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

import com.fiu_CaSPR.Frank.Constants.FacebookRegexPatternPool;
import com.fiu_CaSPR.Frank.Utility.DatabaseHelper;
import com.fiu_CaSPR.Frank.safebuk.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;


public class FriendPicker extends Activity {

    public static WebView wv;
    ProgressBar progressBar;
    private int progressStatus = 0;
    private Handler handler = new Handler();
    public static WebSettings webSettings = null;
    public static int position = 0;
    public static int filecount = 0;
    public static int counter = 0;
    public static String username="";
    public static String url0 = "";
    public static String url1 = "";
    public static String url2 = "";
    public static String url3 = "";
    public static String url4 = "";
    public static String url5 = "";
    public static String url6 = "";
    public static String url7 = "";
    public static String url8 = "";
    public static String url9 = "";

    /**
     * ****************
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fetch_data);
        progressBar = (ProgressBar) findViewById(R.id.myProgressBar);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            username = extras.getString("username");
            Toast.makeText(FriendPicker.this, "Passed Username: "+ username, Toast.LENGTH_SHORT).show();
            url0 = "https://www.facebook.com/" + username + "/about";
            url1 = "https://www.facebook.com/" + username + "/about?section=education&pnref=about";
            url2 = "https://www.facebook.com/" + username + "/about?section=living&pnref=about";
            url3 = "https://www.facebook.com/" + username + "/about?section=contact-info&pnref=about";
            url4 = "https://www.facebook.com/" + username + "/about?section=relationship&pnref=about";
            url5 = "https://www.facebook.com/" + username + "/about?section=bio&pnref=about";
            url6 = "https://www.facebook.com/" + username + "/about?section=year-overviews&pnref=about";
            url7 = "https://www.facebook.com/" + username + "/places_cities_desktop";
            url8 = "https://www.facebook.com/" + username + "/map";
            url9 = "https://www.facebook.com/" + username + "/places_visited";
        }
        else
            Toast.makeText(FriendPicker.this, "Passed Username NULL ", Toast.LENGTH_SHORT).show();

        // Initialize the WebView
        wv = new WebView(this);
        wv.setWebViewClient(new WebViewClient());
        wv.addJavascriptInterface(new LoadListener(), "HTMLOUT");
        wv.getSettings().setJavaScriptEnabled(true);
        wv.getSettings().setUserAgentString("Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.9.0.4) Gecko/20100101 Firefox/4.0");


     /*   new Thread(new Runnable() {
            public void run() {
                while (progressStatus < 100) {
                    progressStatus += 1;
                    // Update the progress bar and display the
                    //current value in the text view
                    handler.post(new Runnable() {
                        public void run() {
                            progressBar.setProgress(progressStatus);
                        }
                    });
                    try {
                        // Sleep for 200 milliseconds.
                        //Just to display the progress slowly
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();*/



        String[] urlArr = {
                url0,url1,url2,url3,url4,url5,url6,url7,url8,url9
        };


        if(username!="") {

            wv.loadUrl(url0);
            wv.setWebChromeClient(new WebChromeClient()
            {
                public void onProgressChanged(WebView view, int progress)
                {

                    if(progress == 100)
                    {
                        progressBar.setProgress(position*10);

                        //Toast.makeText(FetchData.this, "First page finished", Toast.LENGTH_SHORT).show();
                        if(position==1)
                        {
                            wv.loadUrl(url1);
                            //position=1;
                            //Toast.makeText(FetchData.this, "1 page started", Toast.LENGTH_SHORT).show();
                        }
                        if(position==2)
                        {
                            wv.loadUrl(url2);
                            //position=2;
                            //Toast.makeText(FetchData.this, "2 page Started", Toast.LENGTH_SHORT).show();
                        }
                        if(position==3)
                        {
                            wv.loadUrl(url3);
                            //position=1;
                            //Toast.makeText(FetchData.this, "3 page started", Toast.LENGTH_SHORT).show();
                        }
                        if(position==4)
                        {
                            wv.loadUrl(url4);
                            //position=2;
                            //Toast.makeText(FetchData.this, "4 page Started", Toast.LENGTH_SHORT).show();
                        }
                        if(position==5)
                        {
                            wv.loadUrl(url5);
                            //position=1;
                            //Toast.makeText(FetchData.this, "5 page started", Toast.LENGTH_SHORT).show();
                        }
                        if(position==6)
                        {
                            wv.loadUrl(url6);
                            //position=2;
                            //Toast.makeText(FetchData.this, "6 page Started", Toast.LENGTH_SHORT).show();
                        }
                        if(position==7)
                        {
                            wv.loadUrl(url7);
                            //position=1;
                            //Toast.makeText(FetchData.this, "7 page started", Toast.LENGTH_SHORT).show();
                        }
                        if(position==8)
                        {
                            wv.loadUrl(url8);
                            //position=2;
                            //Toast.makeText(FetchData.this, "8 page Started", Toast.LENGTH_SHORT).show();
                        }
                        if(position==9)
                        {
                            wv.loadUrl(url9);
                            //position=1;
                            //Toast.makeText(FetchData.this, "9 page started", Toast.LENGTH_SHORT).show();
                        }
                        if(position==10)
                        {
                            FriendSelectorView.friendListCounter++;

                            Intent intentMessage=new Intent();
                            // Set The Result in Intent
                            setResult(1,intentMessage);
                            // finish The activity
                            finish();
                        }

                    }
                }
            });

            //}
        }


        // Set up WebView for OAuth2 login - intercept redirect when the redirect
        // URL matches our FORWARDING_URL, in which case we will complete the OAuth
        // flow using Temboo
        wv.setWebViewClient(new WebViewClient() {
            boolean timer = false;

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // Default behavior - redirect to specified URL
                return super.shouldOverrideUrlLoading(view, url);
            }

            // When a webview's page has finished loading, we see if we have the user's username or not by determining if we are
            // on the website m.facebook.com yet, and if the user has logged in yet. We know if the user has logged in or not,
            // by if we have their id yet. If we have their id, that means we can get the user's username from the url
            public void onPageFinished(WebView view, String url) {
                //If counter value is 10 then we have saved all files and move to FriendPickerStarter activity
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    if (wv != null) {
                    String s = wv.getUrl();
                    //Toast.makeText(FetchData.this, "onPageFinished Called", Toast.LENGTH_SHORT).show();
                    //Toast.makeText(FetchData.this, "URL ON FINISHED: " + s, Toast.LENGTH_SHORT).show();
                    FriendPicker.wv.loadUrl("javascript:window.HTMLOUT.saveHTML('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>','user');");


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
        public void saveHTML(String html, String filename) {

            try {
                FileOutputStream outputStream;
                String currenturl="";
                String prevurl="";
                currenturl=wv.getUrl().toString();

                //if(webView.getUrl().contains(FacebookRegexPatternPool.userName) && (!FacebookRegexPatternPool.userName.contentEquals("")) ){
                //Create New file
                if(prevurl.compareTo(currenturl)!=0 && currenturl.contains(username)){

                    //Create Folder
                    File folder = new File(Environment.getExternalStorageDirectory().toString() + "/SafeBuk/Friends/"+username);
                    folder.mkdirs();

                    //Save the path as a string value
                    String extStorageDirectory = folder.toString();

                    if(currenturl.compareTo(url0)==0) {
                        File file = new File(extStorageDirectory, username + "_" + "about" + ".txt");
                        outputStream = new FileOutputStream(file);
                        outputStream.write(html.getBytes());
                        outputStream.close();
                        filecount++;
                        Toast.makeText(FriendPicker.this, "About file Saved Successfully. File: " + filecount +" for url: "+ currenturl, Toast.LENGTH_SHORT).show();
                    }
                    else if(currenturl.compareTo(url1)==0) {
                        File file = new File(extStorageDirectory, username + "_" + "education" + ".txt");
                        outputStream = new FileOutputStream(file);
                        outputStream.write(html.getBytes());
                        outputStream.close();
                        filecount++;
                        Toast.makeText(FriendPicker.this, "Education file Saved Successfully. File: " + filecount +" for url: "+ currenturl, Toast.LENGTH_SHORT).show();
                    }
                    else if(currenturl.compareTo(url2)==0) {
                        File file = new File(extStorageDirectory, username + "_" + "living" + ".txt");
                        outputStream = new FileOutputStream(file);
                        outputStream.write(html.getBytes());
                        outputStream.close();
                        filecount++;
                        Toast.makeText(FriendPicker.this, "Living file Saved Successfully. File: " + filecount +" for url: "+ currenturl, Toast.LENGTH_SHORT).show();
                    }
                    else if(currenturl.compareTo(url3)==0) {
                        File file = new File(extStorageDirectory, username + "_" + "contact" + ".txt");
                        outputStream = new FileOutputStream(file);
                        outputStream.write(html.getBytes());
                        outputStream.close();
                        filecount++;
                        Toast.makeText(FriendPicker.this, "Contact file Saved Successfully. File: " + filecount +" for url: "+ currenturl, Toast.LENGTH_SHORT).show();
                    }
                    else if(currenturl.compareTo(url4)==0) {
                        File file = new File(extStorageDirectory, username + "_" + "relationship" + ".txt");
                        outputStream = new FileOutputStream(file);
                        outputStream.write(html.getBytes());
                        outputStream.close();
                        filecount++;
                        Toast.makeText(FriendPicker.this, "Relationship file Saved Successfully. File: " + filecount +" for url: "+ currenturl, Toast.LENGTH_SHORT).show();
                    }
                    else if(currenturl.compareTo(url5)==0) {
                        File file = new File(extStorageDirectory, username + "_" + "bio" + ".txt");
                        outputStream = new FileOutputStream(file);
                        outputStream.write(html.getBytes());
                        outputStream.close();
                        filecount++;
                        Toast.makeText(FriendPicker.this, "Bio file Saved Successfully. File: " + filecount +" for url: "+ currenturl, Toast.LENGTH_SHORT).show();
                    }
                    else if(currenturl.compareTo(url6)==0) {
                        File file = new File(extStorageDirectory, username + "_" + "year-overview" + ".txt");
                        outputStream = new FileOutputStream(file);
                        outputStream.write(html.getBytes());
                        outputStream.close();
                        filecount++;
                        Toast.makeText(FriendPicker.this, "Year-Overview file Saved Successfully. File: " + filecount +" for url: "+ currenturl, Toast.LENGTH_SHORT).show();
                    }
                    else if(currenturl.compareTo(url7)==0) {
                        File file = new File(extStorageDirectory, username + "_" + "cities" + ".txt");
                        outputStream = new FileOutputStream(file);
                        outputStream.write(html.getBytes());
                        outputStream.close();
                        filecount++;
                        Toast.makeText(FriendPicker.this, "Cities file Saved Successfully. File: " + filecount +" for url: "+ currenturl, Toast.LENGTH_SHORT).show();
                    }
                    else if(currenturl.compareTo(url8)==0) {
                        File file = new File(extStorageDirectory, username + "_" + "map" + ".txt");
                        outputStream = new FileOutputStream(file);
                        outputStream.write(html.getBytes());
                        outputStream.close();
                        filecount++;
                        Toast.makeText(FriendPicker.this, "Map file Saved Successfully. File: " + filecount +" for url: "+ currenturl, Toast.LENGTH_SHORT).show();
                    }
                    else if(currenturl.compareTo(url9)==0) {
                        File file = new File(extStorageDirectory, username + "_" + "places-visited" + ".txt");
                        outputStream = new FileOutputStream(file);
                        outputStream.write(html.getBytes());
                        outputStream.close();
                        filecount++;
                        Toast.makeText(FriendPicker.this, "Places-Visited file Saved Successfully. File: " + filecount +" for url: "+ currenturl, Toast.LENGTH_SHORT).show();
                    }
                    position++;
                }
                else
                    Toast.makeText(FriendPicker.this, "Url Same: " + filecount, Toast.LENGTH_SHORT).show();
                prevurl=currenturl;
                // }
            } catch (IOException e) {
                Toast.makeText(FriendPicker.this, "File Creation Failed. Reason:" + e.toString(), Toast.LENGTH_SHORT).show();
                Log.e("Exception", "File write failed: " + e.toString());
            }
        }

    }
}
