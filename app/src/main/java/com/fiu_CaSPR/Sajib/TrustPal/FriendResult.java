package com.fiu_CaSPR.Sajib.TrustPal;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.fiu_CaSPR.Sajib.Constants.FacebookRegexPatternPool;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class FriendResult extends Activity {

    public static ImageView propicView;
    public static String imageUrl;
    private TextView profileNameTextView;

    public static int currentPictureIndex;
    public static String responseString="";
    private int totalCountForReview = 20;
    private Random randomGenerator;
    private int totalCount;
    private List<Integer> randomNumbers;

    private List<CheckBox> checkboxes;
    private long start_time;
    private long start_time_for_next_button;

    private LinearLayout layoutQuestion1;
    private LinearLayout layoutQuestion2;
    private LinearLayout layoutQuestion3;
    private LinearLayout layoutQuestion4;
    private LinearLayout layoutQuestion5;
    private LinearLayout layoutQuestion6;

    private TextView safeText;
    private TextView actionText;
    private TextView actionText12;
    private TextView actionText3;
    private TextView actionText4;
    private TextView actionText5;
    private TextView unfriendText;
    private TextView restrictText;
    private TextView unfollowText;
    private ImageView safeIcon;
    private Button actionButton;
    private Button ignoreButton;
    private TextView txtProgress;
    private ProgressBar circularProgressBar;
    int userpageindex = 1;
    private TextView frndCounttextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friend_result);
        checkboxes = new ArrayList<CheckBox>();
        totalCount = 19;

        safeText = (TextView) findViewById(R.id.safeText);
        actionText = (TextView) findViewById(R.id.actionText);
        actionText12 = (TextView) findViewById(R.id.actionText12);
        actionText3 = (TextView) findViewById(R.id.actionText3);
        actionText4 = (TextView) findViewById(R.id.actionText4);
        actionText5 = (TextView) findViewById(R.id.actionText5);
        unfriendText = (TextView) findViewById(R.id.unfriendText);
        restrictText = (TextView) findViewById(R.id.restrictText);
        unfollowText = (TextView) findViewById(R.id.unfollowText);
        safeIcon = (ImageView) findViewById(R.id.safeIcon);

        txtProgress = (TextView) findViewById(R.id.txtProgress);
        circularProgressBar = (ProgressBar) findViewById(R.id.circularProgressbar);

        int percentValue=0;
        percentValue=(20-ResultSummary.unsafeCount)*5;
        txtProgress.setText(percentValue+"%");
        circularProgressBar.setProgress(percentValue);
        //actionButton = (Button) findViewById(R.id.actionButton);

        //totalCountForReview = getTotalCount();
        currentPictureIndex = 0;
        loadViews();

        if (totalCount > 0) {
            showNext();
        }

    }

    private void loadViews() {


        propicView = (ImageView) findViewById(R.id.propicimageview);
        profileNameTextView = (TextView) findViewById(R.id.usernamenew);
        frndCounttextView = (TextView) findViewById(R.id.frndcount);

        //textView1.setText("Suppose you upload a picture of yourself like below on Facebook. How likely is it that "+friendsPage.friendsArray[currentPictureIndex][1]+" will post an inappropriate comment on this?");


        //Button actionButton = (Button) findViewById(R.id.actionButton);
        actionButton = (Button) findViewById(R.id.action);
        ignoreButton = (Button) findViewById(R.id.ignore);

        ignoreButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showNext();
            }
        });


        actionButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                    ResultSummary.unsafeCount--;
                    showNext();
                }

        });


    }


    public static long generateRandom(int length) {
        Random random = new Random();
        char[] digits = new char[length];
        digits[0] = (char) (random.nextInt(9) + '1');
        for (int i = 1; i < length; i++) {
            digits[i] = (char) (random.nextInt(10) + '0');
        }
        return Long.parseLong(new String(digits));
    }

    private void saveJsonStringToFile() {
        String reportDate = getDateTime();
        String createJsonString = createJsonString();
        try {
            FileOutputStream outputStream;
            //Create Folder
            File folder = new File(Environment.getExternalStorageDirectory().toString() + "/TrustPal/"+ FacebookRegexPatternPool.Name+"/"+FacebookRegexPatternPool.Name);
            folder.mkdirs();
            //Save the path as a string value
            String extStorageDirectory = folder.toString();

            File file1 = new File(extStorageDirectory, FacebookRegexPatternPool.Name+"_response_friend_"+currentPictureIndex+1+"_"+reportDate + ".txt");
            outputStream = new FileOutputStream(file1);
            outputStream.write(createJsonString.getBytes());
            outputStream.close();

        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    private String getDateTime() {
        // Create an instance of SimpleDateFormat used for formatting
        // the string representation of date (month/day/year)
        DateFormat df = new SimpleDateFormat("MMddyyyyHHmmss");

        // Get the date today using Calendar object.
        Date today = Calendar.getInstance().getTime();
        // Using DateFormat format method we can create a string
        // representation of a date with the defined format.
        String reportDate = df.format(today);
        return reportDate;
    }

    private String createJsonString() {
        String newString = "{";

        newString = newString + "\"totalfriends\":" + 20 + ",\"data\":";


        String string = "[";
        /*for (int i = 0; i < allFriendsInfo.size(); i++) {

            string += allFriendsInfo.get(i).toString();
            if (i != allFriendsInfo.size() - 1) {
                string += ",";
            }
        }*/

        string+=responseString;
        string += "]";

        newString = newString + string;

        newString = newString + "}";


        return newString;
    }

    private void resetFields() {
        propicView.setBackgroundResource(R.drawable.icon);
        profileNameTextView.setText("");
        int percentValue=0;
        percentValue=(20-ResultSummary.unsafeCount)*5;
        txtProgress.setText(percentValue + "%");
        circularProgressBar.setProgress(percentValue);
    }
    
    public static boolean isNullOrBlank(String s)
    {
        return (s==null || s.trim().equals(""));
    }

    private void loadNextFriendView() {
        /*if (this.isNullOrBlank(friendsPage.friendsArray[currentPictureIndex][1]))
        {
            currentPictureIndex++;
            showNext();
        }
        else {*/
        resetFields();

        if(friendsPage.friendsArray[currentPictureIndex][2]=="1") { //Unfollow
            safeText.setText("Our result indicates that this friend might be unsafe");
            safeText.setTextColor(Color.YELLOW);
            safeIcon.setBackgroundResource(R.drawable.unsafe);
            actionText12.setVisibility(View.INVISIBLE);
            actionText3.setVisibility(View.INVISIBLE);
            actionText4.setVisibility(View.INVISIBLE);
            actionText5.setVisibility(View.VISIBLE);
            unfriendText.setVisibility(View.INVISIBLE);
            restrictText.setVisibility(View.INVISIBLE);
            unfollowText.setVisibility(View.VISIBLE);
            actionButton.setText("Unfollow");
            actionButton.setEnabled(true);

        }
        else if(friendsPage.friendsArray[currentPictureIndex][2]=="2") { //Restrict
            safeText.setText("Our result indicates that this friend is unsafe");
            safeText.setTextColor(Color.YELLOW);
            safeIcon.setBackgroundResource(R.drawable.unsafe);
            actionText12.setVisibility(View.INVISIBLE);
            actionText3.setVisibility(View.VISIBLE);
            actionText4.setVisibility(View.VISIBLE);
            actionText5.setVisibility(View.INVISIBLE);
            unfriendText.setVisibility(View.INVISIBLE);
            restrictText.setVisibility(View.VISIBLE);
            unfollowText.setVisibility(View.INVISIBLE);
            actionButton.setText("Restrict");
            actionButton.setEnabled(true);
        }
        else if(friendsPage.friendsArray[currentPictureIndex][2]=="3") { //Unfriend
            safeText.setText("Our result indicates that this friend is very unsafe");
            safeText.setTextColor(Color.RED);
            safeIcon.setBackgroundResource(R.drawable.unsafe);
            actionText12.setVisibility(View.VISIBLE);
            actionText3.setVisibility(View.VISIBLE);
            actionText4.setVisibility(View.VISIBLE);
            actionText5.setVisibility(View.VISIBLE);
            unfriendText.setVisibility(View.VISIBLE);
            restrictText.setVisibility(View.INVISIBLE);
            unfollowText.setVisibility(View.INVISIBLE);
            actionButton.setText("Unfriend");
            actionButton.setEnabled(true);
        }


        frndCounttextView.setText(currentPictureIndex + 1 + " of " + totalCountForReview);
            new FacebookGetUserImage(imageUrl,0).execute(friendsPage.friendsArray[currentPictureIndex][0]);
            if(friendsPage.friendsArray[currentPictureIndex][1]=="Find Friends") {
                profileNameTextView.setText("Deactivated Account");
            }

            else {
                profileNameTextView.setText(friendsPage.friendsArray[currentPictureIndex][1]);
            }
            currentPictureIndex++;
        //}
    }
    private void loadPreviousFriendView() {
        /*if (this.isNullOrBlank(friendsPage.friendsArray[currentPictureIndex][1]))
        {
            currentPictureIndex--;
            showPrevious();
        }
        else {*/

            resetFields();
            currentPictureIndex -= 2;

           if(friendsPage.friendsArray[currentPictureIndex][2]=="1") { //Unfollow
                safeText.setText("Our result indicates that this friend might be unsafe");
                safeText.setTextColor(Color.YELLOW);
                safeIcon.setBackgroundResource(R.drawable.unsafe);
                actionText12.setVisibility(View.INVISIBLE);
                actionText3.setVisibility(View.INVISIBLE);
                actionText4.setVisibility(View.INVISIBLE);
                actionText5.setVisibility(View.VISIBLE);
                unfriendText.setVisibility(View.INVISIBLE);
                restrictText.setVisibility(View.INVISIBLE);
                unfollowText.setVisibility(View.VISIBLE);
                actionButton.setText("Unfollow");
                actionButton.setEnabled(true);

            }
            else if(friendsPage.friendsArray[currentPictureIndex][2]=="2") { //Restrict
                safeText.setText("Our result indicates that this friend is unsafe");
                safeText.setTextColor(Color.YELLOW);
                safeIcon.setBackgroundResource(R.drawable.unsafe);
                actionText12.setVisibility(View.INVISIBLE);
                actionText3.setVisibility(View.VISIBLE);
                actionText4.setVisibility(View.VISIBLE);
                actionText5.setVisibility(View.INVISIBLE);
                unfriendText.setVisibility(View.INVISIBLE);
                restrictText.setVisibility(View.VISIBLE);
                unfollowText.setVisibility(View.INVISIBLE);
                actionButton.setText("Restrict");
                actionButton.setEnabled(true);
            }
            else if(friendsPage.friendsArray[currentPictureIndex][2]=="3") { //Unfriend
                safeText.setText("Our result indicates that this friend is very unsafe");
                safeText.setTextColor(Color.RED);
                safeIcon.setBackgroundResource(R.drawable.unsafe);
                actionText12.setVisibility(View.VISIBLE);
                actionText3.setVisibility(View.VISIBLE);
                actionText4.setVisibility(View.VISIBLE);
                actionText5.setVisibility(View.VISIBLE);
                unfriendText.setVisibility(View.VISIBLE);
                restrictText.setVisibility(View.INVISIBLE);
                unfollowText.setVisibility(View.INVISIBLE);
                actionButton.setText("Unfriend");
                actionButton.setEnabled(true);
            }

            frndCounttextView.setText(currentPictureIndex + 1 + " of " + totalCountForReview);
            new FacebookGetUserImage(imageUrl,0).execute(friendsPage.friendsArray[currentPictureIndex][0]);
            if(friendsPage.friendsArray[currentPictureIndex][1]=="Find Friends") {
                profileNameTextView.setText("Deactivated Account");
            }

            else {
                profileNameTextView.setText(unicode2String(friendsPage.friendsArray[currentPictureIndex][1]));
            }
            currentPictureIndex++;

    }

    public String unicode2String(String unicode) {
        byte[] utf8Bytes       = null;
        String convertedString = null;
        try
        {
            utf8Bytes       = unicode.getBytes("UTF8");
            convertedString = new String(utf8Bytes,  "UTF8");
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        return convertedString;
    }

    protected void showNext() {

        if (currentPictureIndex == totalCountForReview) {
            doLastJob();
            return;
        }
        if(friendsPage.friendsArray[currentPictureIndex][2]=="0") { //Safe
            currentPictureIndex++;
            showNext();
        }
        else {
            loadNextFriendView();
        }
    }
    protected void showPrevious() {

        loadPreviousFriendView();
    }

    private void doLastJob() {
        //saveJsonStringToFile();
        Intent intent = new Intent(getApplicationContext(),FinishJob.class);
        startActivity(intent);
    }


}