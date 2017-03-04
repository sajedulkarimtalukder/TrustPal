package com.fiu_CaSPR.Sajib.TrustPal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class puzzle4 extends Activity {


    public static String responseString="";
    private List<RadioButton> radioButtons;
    private long start_time;

    LinearLayout topLayer1;
    LinearLayout bottomLayer1;
    LinearLayout topLayer2;
    LinearLayout bottomLayer2;

    private static int Q1=0;
    private static int Q2_1=0;
    private static int Q2_2=0;
    private static int Q2_3=0;
    private static int Q2_4=0;

    //puzzle1
    public static ImageView pic11;
    public static ImageView pic21;
    public static ImageView pic31;
    public static ImageView pic41;

    public TextView name11;
    public TextView name21;
    public TextView name31;
    public TextView name41;

    public RadioButton radio11;
    public RadioButton radio21;
    public RadioButton radio31;
    public RadioButton radio41;

    //puzzle2
    private RadioButton radio1_lowest;
    private RadioButton radio1_low;
    private RadioButton radio1_high;
    private RadioButton radio1_highest;

    private RadioButton radio2_lowest;
    private RadioButton radio2_low;
    private RadioButton radio2_high;
    private RadioButton radio2_highest;

    private RadioButton radio3_lowest;
    private RadioButton radio3_low;
    private RadioButton radio3_high;
    private RadioButton radio3_highest;

    private RadioButton radio4_lowest;
    private RadioButton radio4_low;
    private RadioButton radio4_high;
    private RadioButton radio4_highest;

    public static ImageView pic12;
    public static ImageView pic22;
    public static ImageView pic32;
    public static ImageView pic42;

    public TextView name12;
    public TextView name22;
    public TextView name32;
    public TextView name42;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.puzzle_question1);
        radioButtons = new ArrayList<RadioButton>();
        start_time = System.nanoTime();

        //layouts
        topLayer1 = (LinearLayout) findViewById(R.id.topLayer1);
        bottomLayer1 = (LinearLayout) findViewById(R.id.bottomLayer1);

        topLayer2 = (LinearLayout) findViewById(R.id.topLayer2);
        bottomLayer2 = (LinearLayout) findViewById(R.id.bottomLayer2);
        topLayer2.setVisibility(View.INVISIBLE);
        bottomLayer2.setVisibility(View.INVISIBLE);

        //puzzle1
        pic11 = (ImageView) findViewById(R.id.pic11);
        pic21 = (ImageView) findViewById(R.id.pic21);
        pic31 = (ImageView) findViewById(R.id.pic31);
        pic41 = (ImageView) findViewById(R.id.pic41);

        radio11 = (RadioButton) findViewById(R.id.radio11);
        radio21 = (RadioButton) findViewById(R.id.radio21);
        radio31 = (RadioButton) findViewById(R.id.radio31);
        radio41 = (RadioButton) findViewById(R.id.radio41);

        name11 = (TextView) findViewById(R.id.name11);
        name21 = (TextView) findViewById(R.id.name21);
        name31 = (TextView) findViewById(R.id.name31);
        name41 = (TextView) findViewById(R.id.name41);

        name11.setText(friendsPage.friendsArray[12][1]);
        name21.setText(friendsPage.friendsArray[13][1]);
        name31.setText(friendsPage.friendsArray[14][1]);
        name41.setText(friendsPage.friendsArray[15][1]);

        new FacebookGetUserImage(FriendSelectorView4.imageUrl, 41).execute(friendsPage.friendsArray[12][0]);
        new FacebookGetUserImage(FriendSelectorView4.imageUrl, 42).execute(friendsPage.friendsArray[13][0]);
        new FacebookGetUserImage(FriendSelectorView4.imageUrl, 43).execute(friendsPage.friendsArray[14][0]);
        new FacebookGetUserImage(FriendSelectorView4.imageUrl, 44).execute(friendsPage.friendsArray[15][0]);

        //puzzle2
        radio1_lowest = (RadioButton) findViewById(R.id.radio1_lowest);
        radio1_low = (RadioButton) findViewById(R.id.radio1_low);
        radio1_high = (RadioButton) findViewById(R.id.radio1_high);
        radio1_highest = (RadioButton) findViewById(R.id.radio1_highest);

        radio2_lowest = (RadioButton) findViewById(R.id.radio2_lowest);
        radio2_low = (RadioButton) findViewById(R.id.radio2_low);
        radio2_high = (RadioButton) findViewById(R.id.radio2_high);
        radio2_highest = (RadioButton) findViewById(R.id.radio2_highest);

        radio3_lowest = (RadioButton) findViewById(R.id.radio3_lowest);
        radio3_low = (RadioButton) findViewById(R.id.radio3_low);
        radio3_high = (RadioButton) findViewById(R.id.radio3_high);
        radio3_highest = (RadioButton) findViewById(R.id.radio3_highest);

        radio4_lowest = (RadioButton) findViewById(R.id.radio4_lowest);
        radio4_low = (RadioButton) findViewById(R.id.radio4_low);
        radio4_high = (RadioButton) findViewById(R.id.radio4_high);
        radio4_highest = (RadioButton) findViewById(R.id.radio4_highest);

        pic12 = (ImageView) findViewById(R.id.pic12);
        pic22 = (ImageView) findViewById(R.id.pic22);
        pic32 = (ImageView) findViewById(R.id.pic32);
        pic42 = (ImageView) findViewById(R.id.pic42);

        name12 = (TextView) findViewById(R.id.name12);
        name22 = (TextView) findViewById(R.id.name22);
        name32 = (TextView) findViewById(R.id.name32);
        name42 = (TextView) findViewById(R.id.name42);

        name12.setText(friendsPage.friendsArray[12][1]);
        name22.setText(friendsPage.friendsArray[13][1]);
        name32.setText(friendsPage.friendsArray[14][1]);
        name42.setText(friendsPage.friendsArray[15][1]);

        new FacebookGetUserImage(FriendSelectorView4.imageUrl, 45).execute(friendsPage.friendsArray[12][0]);
        new FacebookGetUserImage(FriendSelectorView4.imageUrl, 46).execute(friendsPage.friendsArray[13][0]);
        new FacebookGetUserImage(FriendSelectorView4.imageUrl, 47).execute(friendsPage.friendsArray[14][0]);
        new FacebookGetUserImage(FriendSelectorView4.imageUrl, 48).execute(friendsPage.friendsArray[15][0]);


        loadViews();

    }


    private void loadViews() {

        //RadioButtons for question1
        radioButtons.add((RadioButton) findViewById(R.id.radio11));
        radioButtons.add((RadioButton) findViewById(R.id.radio21));
        radioButtons.add((RadioButton) findViewById(R.id.radio31));
        radioButtons.add((RadioButton) findViewById(R.id.radio41));

        //RadioButtons for question2
        radioButtons.add((RadioButton) findViewById(R.id.radio1_lowest));
        radioButtons.add((RadioButton) findViewById(R.id.radio1_low));
        radioButtons.add((RadioButton) findViewById(R.id.radio1_high));
        radioButtons.add((RadioButton) findViewById(R.id.radio1_highest));

        radioButtons.add((RadioButton) findViewById(R.id.radio2_lowest));
        radioButtons.add((RadioButton) findViewById(R.id.radio2_low));
        radioButtons.add((RadioButton) findViewById(R.id.radio2_high));
        radioButtons.add((RadioButton) findViewById(R.id.radio2_highest));

        radioButtons.add((RadioButton) findViewById(R.id.radio3_lowest));
        radioButtons.add((RadioButton) findViewById(R.id.radio3_low));
        radioButtons.add((RadioButton) findViewById(R.id.radio3_high));
        radioButtons.add((RadioButton) findViewById(R.id.radio3_highest));

        radioButtons.add((RadioButton) findViewById(R.id.radio4_lowest));
        radioButtons.add((RadioButton) findViewById(R.id.radio4_low));
        radioButtons.add((RadioButton) findViewById(R.id.radio4_high));
        radioButtons.add((RadioButton) findViewById(R.id.radio4_highest));

        for (int i = 0; i < radioButtons.size(); i++) {
            final RadioButton radioButton = radioButtons.get(i);

            radioButton.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub

                    Boolean isChecked = radioButton.isChecked();

                    long end_time = System.nanoTime();
                    double difference = (end_time - start_time) / 1e6;

                    int index = -1;

                    //Getting the index of the current  radioButton
                    for (int j = 0; j < radioButtons.size(); j++) {
                        if (radioButton.getId() == radioButtons.get(j).getId()) index = j;
                    }

                    Log.d("sajib", "index " + index);

                    //Setting the other radioButtons false except the current checked one for question 1
                    if (index >= 0 && index <= 3) {
                        Q1 = index % 4;
                        //Log.d("sajib", "Q1:  " + Q1);
                        //Toast.makeText(FriendSelectorView1.this, ""+ Q1, Toast.LENGTH_SHORT).show();
                        for (int i = 0; i <= 3; i++) {
                            if (i == index) continue;
                            radioButtons.get(i).setChecked(false);
                        }
                        radioButtons.get(index).setChecked(true);

                    }

                    //Setting the other checkboxes false except the current checked one for question 2_1
                    if (index >= 4 && index <= 7) {
                        Q2_1 = index % 4;
                        //Toast.makeText(FriendSelectorView1.this, ""+ Q2, Toast.LENGTH_SHORT).show();
                        for (int i = 4; i <= 7; i++) {
                            if (i == index) continue;
                            radioButtons.get(i).setChecked(false);
                        }
                        radioButtons.get(index).setChecked(true);
                    }

                    //Setting the other checkboxes false except the current checked one for question 2_2
                    if (index >= 8 && index <= 11) {
                        Q2_2 = index % 4;
                        //Toast.makeText(FriendSelectorView1.this, ""+ Q2, Toast.LENGTH_SHORT).show();
                        for (int i = 8; i <= 11; i++) {
                            if (i == index) continue;
                            radioButtons.get(i).setChecked(false);
                        }
                        radioButtons.get(index).setChecked(true);
                    }

                    //Setting the other checkboxes false except the current checked one for question 2_3
                    if (index >= 12 && index <= 15) {
                        Q2_3 = index % 4;
                        //Toast.makeText(FriendSelectorView1.this, ""+ Q2, Toast.LENGTH_SHORT).show();
                        for (int i = 12; i <= 15; i++) {
                            if (i == index) continue;
                            radioButtons.get(i).setChecked(false);
                        }
                        radioButtons.get(index).setChecked(true);
                    }

                    //Setting the other checkboxes false except the current checked one for question 2_4
                    if (index >= 16 && index <= 19) {
                        Q2_4 = index % 4;
                        //Toast.makeText(FriendSelectorView1.this, ""+ Q2, Toast.LENGTH_SHORT).show();
                        for (int i = 16; i <= 19; i++) {
                            if (i == index) continue;
                            radioButtons.get(i).setChecked(false);
                        }
                        radioButtons.get(index).setChecked(true);
                    }

                }
            });


            radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {


                @Override
                public void onCheckedChanged(CompoundButton buttonView,
                                             boolean isChecked) {

                }
            });
        }


        Button nextButton = (Button) findViewById(R.id.nextButton);
        Button ignoreButton = (Button) findViewById(R.id.ignoreButton);

        ignoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goNext();
            }
        });


        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                goNext();
            }
        });

    }

    private void goNext() {
        Intent intent = new Intent(getApplicationContext(), FriendSelectorView5.class);
        startActivity(intent);
    }

    protected void showPrevious() {

        Intent intent = new Intent(getApplicationContext(), FriendSelectorView4.class);
        startActivity(intent);
    }
}