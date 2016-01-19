package com.fiu_CaSPR.Frank.safebuk;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import android.R.string;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fiu_CaSPR.Frank.Constants.FacebookRegexPatternPool;
import com.mizan.friendstar.dataclass.AppData;
import com.mizan.friendstar.dataclass.CheckBoxValue;
import com.mizan.friendstar.dataclass.FacebookFriend;
import com.mizan.friendstar.dataclass.OneFriendInfo;

public class FriendSelectorView extends Activity {

    ImageView propicView;
    public static int friendListCounter=0;
    private TextView profileNameTextView;

    private int currentPictureIndex;
    private int totalCountForReview = 20;
    private Random randomGenerator;
    private int totalCount;
    private List<Integer> randomNumbers;
    private List<OneFriendInfo> allFriendsInfo;
    private OneFriendInfo currentFirndInfo;

    private List<CheckBox> checkboxes;
    private long start_time;
    private long start_time_for_next_button;

    private LinearLayout layoutQuestion1;
    private LinearLayout layoutQuestion2;
    private LinearLayout layoutQuestion3;
    private LinearLayout layoutQuestion4;

    int userpageindex = 1;
    private TextView frndCounttextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        start_time = System.nanoTime();
        start_time_for_next_button = System.nanoTime();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.friendlist);

        randomGenerator = new Random();

        randomNumbers = new ArrayList<Integer>();
        allFriendsInfo = new ArrayList<OneFriendInfo>();
        checkboxes = new ArrayList<CheckBox>();



        loadViews();


        totalCount = AppData.getInstance().getAllFriendList().size();

        totalCountForReview = getTotalCount();


        if (totalCount > 0) {
            currentPictureIndex = 0;
            loadFriendInfoView(AppData.getInstance().getAllFriendList().get(getNextIndex()));
        }


    }

    private void loadViews() {
        propicView = (ImageView) findViewById(R.id.propicimageview);
        profileNameTextView = (TextView) findViewById(R.id.usernamenew);
        frndCounttextView = (TextView) findViewById(R.id.frndcount);


        layoutQuestion1 = (LinearLayout) findViewById(R.id.layoutquestion1);
        layoutQuestion2 = (LinearLayout) findViewById(R.id.layoutquestion2);
        layoutQuestion3 = (LinearLayout) findViewById(R.id.layoutquestion3);
        layoutQuestion4 = (LinearLayout) findViewById(R.id.LinearLayout4);



        checkboxes.add((CheckBox) findViewById(R.id.CheckBox401));
        checkboxes.add((CheckBox) findViewById(R.id.CheckBox402));
        checkboxes.add((CheckBox) findViewById(R.id.CheckBox403));
        checkboxes.add((CheckBox) findViewById(R.id.CheckBox404));
        checkboxes.add((CheckBox) findViewById(R.id.CheckBox405));
        checkboxes.add((CheckBox) findViewById(R.id.CheckBox406));

        checkboxes.add((CheckBox) findViewById(R.id.CheckBox407));
        checkboxes.add((CheckBox) findViewById(R.id.CheckBox408));
        checkboxes.add((CheckBox) findViewById(R.id.CheckBox409));
        checkboxes.add((CheckBox) findViewById(R.id.CheckBox410));
        checkboxes.add((CheckBox) findViewById(R.id.CheckBox411));
        checkboxes.add((CheckBox) findViewById(R.id.CheckBox412));
        checkboxes.add((CheckBox) findViewById(R.id.CheckBox413));
        checkboxes.add((CheckBox) findViewById(R.id.CheckBox414));
        checkboxes.add((CheckBox) findViewById(R.id.CheckBox415));
        checkboxes.add((CheckBox) findViewById(R.id.CheckBox416));
        checkboxes.add((CheckBox) findViewById(R.id.checkBox417));
        checkboxes.add((CheckBox) findViewById(R.id.CheckBox418));
        checkboxes.add((CheckBox) findViewById(R.id.CheckBox419));
        checkboxes.add((CheckBox) findViewById(R.id.CheckBox420));


        for (int i = 0; i < checkboxes.size(); i++) {
            final CheckBox checkBox = checkboxes.get(i);

            checkBox.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub

                    Boolean isChecked = checkBox.isChecked();

                    long end_time = System.nanoTime();
                    double difference = (end_time - start_time) / 1e6;

                    int index=-1;

                    for (int j = 0; j < checkboxes.size(); j++) {
                        if(checkBox.getId() == checkboxes.get(j).getId()) index = j;
                    }

                    Log.d("mizan", "index " + index);

                    //for question 1
                    if (index >= 0 && index <= 5) {
                        for (int i = 0; i <= 5; i++) {
                            if (i == index) continue;
                            checkboxes.get(i).setChecked(false);
                            CheckBoxValue boxValue = new CheckBoxValue(false, 0);
                            currentFirndInfo.getCheckBoxValues().set(i, boxValue);
                        }

                        //checkboxes.get(index).setChecked(true);
                    }


                    //for question 3
                    if (index >= 12 && index <= 12+5) {
                        for (int i = 12; i <= 12+5; i++) {
                            if (i == index) continue;
                            checkboxes.get(i).setChecked(false);
                            CheckBoxValue boxValue = new CheckBoxValue(false, 0);
                            currentFirndInfo.getCheckBoxValues().set(i, boxValue);
                        }
                    }


                    //for question 1
                    if (index >= 18 && index <= 19) {
                        for (int i = 18; i <= 19; i++) {
                            if (i == index) continue;
                            checkboxes.get(i).setChecked(false);
                            CheckBoxValue boxValue = new CheckBoxValue(false, 0);
                            currentFirndInfo.getCheckBoxValues().set(i, boxValue);
                        }
                    }

//					if(buttonView.getId() == R.id.checkBox1) index = 0;
//					if(buttonView.getId() == R.id.CheckBox2) index = 1;
//					if(buttonView.getId() == R.id.CheckBox3) index = 2;
//					if(buttonView.getId() == R.id.CheckBox4) index = 3;
//					if(buttonView.getId() == R.id.CheckBox5) index = 4;
//
                    CheckBoxValue boxValue = new CheckBoxValue(isChecked, difference);
                    currentFirndInfo.getCheckBoxValues().set(index, boxValue);

                    start_time = System.nanoTime();

                }
            });



            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {



                @Override
                public void onCheckedChanged(CompoundButton buttonView,
                                             boolean isChecked) {

                }
            });
        }


        Button nextButton = (Button) findViewById(R.id.nextButton);
        Button doneButton = (Button) findViewById(R.id.doneButton);
        Button backButton = (Button) findViewById(R.id.backButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userpageindex == 2) {

                    userpageindex = 1;
                    layoutQuestion1.setVisibility(LinearLayout.VISIBLE);
                    layoutQuestion2.setVisibility(LinearLayout.VISIBLE);
                    layoutQuestion3.setVisibility(LinearLayout.GONE);
                    layoutQuestion4.setVisibility(LinearLayout.GONE);
                }
            }
        });


        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userpageindex == 1) {
                    userpageindex = 2;
                    layoutQuestion1.setVisibility(LinearLayout.GONE);
                    layoutQuestion2.setVisibility(LinearLayout.GONE);
                    layoutQuestion3.setVisibility(LinearLayout.VISIBLE);
                    layoutQuestion4.setVisibility(LinearLayout.VISIBLE);

                }else{
                    userpageindex = 1;
                    long end_time = System.nanoTime();
                    double difference = (end_time - start_time_for_next_button) / 1e6;
                    currentFirndInfo.setTime(difference);
                    allFriendsInfo.add(currentFirndInfo);

                    showNext();
                }
            }
        });

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doLastJob();
            }
        });
    }

    private int getTotalCount() {
        totalCount = AppData.getInstance().getAllFriendList().size();

        if(totalCount <= 20){
            return totalCount;
        }else{
                return 20;
            }
    }

    //Do all the source page saving for the 20 friends here
    public void getMessage()
    {
        // Create The  Intent and Start The Activity to get The message
        Intent intent=new Intent(this,FriendPickerStarter.class);

        if (friendListCounter == 0) {

            String webUrl = "https://www.facebook.com/" + "100002147482856";
            intent.putExtra("website", webUrl);
            startActivityForResult(intent, 1);// Activity is started with requestCode=1
        }
        else if (friendListCounter == 1) {

            String webUrl = "https://www.facebook.com/" + "561225236";
            intent.putExtra("website", webUrl);
            startActivityForResult(intent, 1);// Activity is started with requestCode=1

        }
    }


    // Call Back method  to get the Message form other Activity    override the method
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        // check if the request code is same as what is passed  here it is 1
        if(resultCode==1 && friendListCounter<20)
        {
            // fetch the message String
            getMessage();

        }
        else
        {
            Toast.makeText(FriendSelectorView.this, "All the pages for 20 friends have been saved", Toast.LENGTH_SHORT).show();
        }


    }



    protected void showNext() {

        if (currentPictureIndex == totalCountForReview) {
            doLastJob();
            return;
        }

        loadFriendInfoView(AppData.getInstance().getAllFriendList().get(getNextIndex()));
    }

    private void doLastJob() {
        saveJsonStringToFile();
        getMessage();
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
            File folder = new File(Environment.getExternalStorageDirectory().toString() + "/SafeBuk/"+ FacebookRegexPatternPool.Name);
            folder.mkdirs();
            //Save the path as a string value
            String extStorageDirectory = folder.toString();

            File file1 = new File(extStorageDirectory, FacebookRegexPatternPool.Name+"_response_"+reportDate + ".json");
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

        newString = newString + "\"totalfriends\":" + totalCount + ",\"data\":";


        String string = "[";
        for (int i = 0; i < allFriendsInfo.size(); i++) {

            string += allFriendsInfo.get(i).toString();
            if (i != allFriendsInfo.size() - 1) {
                string += ",";
            }
        }

        string += "]";

        newString = newString + string;

        newString = newString + "}";


        return newString;
    }

    private int getNextIndex() {
        Integer randomInt;

        while (true) {
            randomInt = randomGenerator.nextInt(totalCount);
            if (randomNumbers.contains(randomInt)) {
                randomInt = randomGenerator.nextInt(totalCount);
            } else {
                break;
            }
        }

        randomNumbers.add(randomInt);
        return randomInt;
    }

    private void resetFields() {
        propicView.setBackgroundResource(R.drawable.icon);
        profileNameTextView.setText("");

        for (int i = 0; i < checkboxes.size(); i++) {
            CheckBox checkBox = checkboxes.get(i);
            checkBox.setChecked(false);
        }
    }

    private void loadFriendInfoView(FacebookFriend facebookFriend) {
        layoutQuestion1.setVisibility(LinearLayout.VISIBLE);
        layoutQuestion2.setVisibility(LinearLayout.VISIBLE);
        layoutQuestion3.setVisibility(LinearLayout.GONE);
        layoutQuestion4.setVisibility(LinearLayout.GONE);


        currentFirndInfo = new OneFriendInfo();
        resetFields();
        currentPictureIndex++;

        frndCounttextView.setText(currentPictureIndex + " of " + totalCountForReview);

        new DownloadImageTask(propicView).execute(facebookFriend.pictureUrl);
        profileNameTextView.setText(facebookFriend.name);
        start_time = System.nanoTime();
        start_time_for_next_button = System.nanoTime();
    }
}