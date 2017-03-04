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

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;


public class url0 extends Activity {

    public static WebView wv;
    ProgressBar progressBar;
    private int progressStatus = 0;
    private Handler handler = new Handler();
    public static WebSettings webSettings = null;
    public String username;
    public String url0;
    public String html;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();
        username = FacebookRegexPatternPool.userName;
        url0= "https://www.facebook.com/" + username + "/about";
        setContentView(R.layout.url_fetch_0);
        progressBar = (ProgressBar) findViewById(R.id.myProgressBar);

        // Initialize the WebView
        wv = new WebView(this);
        wv.setWebViewClient(new WebViewClient());
        wv.addJavascriptInterface(new LoadListener(), "HTMLOUT");
        wv.getSettings().setJavaScriptEnabled(true);
        wv.getSettings().setUserAgentString("Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.9.0.4) Gecko/20100101 Firefox/4.0");

        wv.loadUrl(url0);
        //Toast.makeText(url1.this, "page 1 started", Toast.LENGTH_SHORT).show();

        /*Thread thread = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    //method1
                    getHTML HTML = new getHTML();
                    html = HTML.run(url0);

                    //method22
                    html=getHtml(url0);
                    saveHTML(html);

                    //method3
                    final Connection.Response response = Jsoup.connect(url0).execute();
                    final Document doc = response.parse();
                    html=doc.outerHtml();
                    saveHTML(html);

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });

        thread.start();*/


        wv.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                progressBar.setProgress(progress);
                    /*if(progress==100)
                    {
                        Intent intent = new Intent(getApplicationContext(), url2.class);
                        intent.putExtra("username",username);
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
                Log.i("p","pagestart 1");
                loadingFinished = false;
            }

            // When finish loading page
            public void onPageFinished(WebView view, String url) {
                Log.i("p","pagefinish 1");
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

                File file = new File(extStorageDirectory, FacebookRegexPatternPool.userName + "_" + "about.txt");
                if(file.exists())
                {
                    //Do something

                }
                else {
                    // Do something else.
                    outputStream = new FileOutputStream(file);
                    outputStream.write(html.getBytes());
                    outputStream.close();
                    //Toast.makeText(url1.this, "40% done", Toast.LENGTH_SHORT).show();
                }
                //Toast.makeText(friendurl0.this, "About file Saved Successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), url1.class);
                intent.putExtra("username", username);
                startActivity(intent);


            } catch (IOException e) {
                Toast.makeText(url0.this, "File Creation Failed. Reason:" + e.toString(), Toast.LENGTH_SHORT).show();
                Log.e("Exception", "File write failed: " + e.toString());
            }
        }

    }

/*    public void saveHTML(String html) {
        //Toast.makeText(com.fiu_CaSPR.Frank.safebuk.url0.this, "JS function called", Toast.LENGTH_SHORT).show();
        try {
            FileOutputStream outputStream;

            //Create Folder
            File folder = new File(Environment.getExternalStorageDirectory().toString() + "/TrustPal/"+FacebookRegexPatternPool.Name+"/"+FacebookRegexPatternPool.Name);
            folder.mkdirs();

            //Save the path as a string value
            String extStorageDirectory = folder.toString();

            File file = new File(extStorageDirectory, FacebookRegexPatternPool.userName + "_" + "about.txt");
            if(file.exists())
            {
                //Do something

            }
            else {
                // Do something else.
                outputStream = new FileOutputStream(file);
                outputStream.write(html.getBytes());
                outputStream.close();
                //Toast.makeText(url1.this, "40% done", Toast.LENGTH_SHORT).show();
            }
            //Toast.makeText(friendurl0.this, "About file Saved Successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), url1.class);
            intent.putExtra("username", username);
            startActivity(intent);


        } catch (IOException e) {
            Toast.makeText(url0.this, "File Creation Failed. Reason:" + e.toString(), Toast.LENGTH_SHORT).show();
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    public static String getHtml(String url) throws IOException {
        // Build and set timeout values for the request.
        URLConnection connection = (new URL(url)).openConnection();
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);
        connection.connect();

        // Read and store the result line by line then return the entire string.
        InputStream in = connection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder html = new StringBuilder();
        for (String line; (line = reader.readLine()) != null; ) {
            html.append(line);
        }
        in.close();

        return html.toString();
    }*/



}
