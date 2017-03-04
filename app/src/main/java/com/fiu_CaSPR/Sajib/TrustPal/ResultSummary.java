package com.fiu_CaSPR.Sajib.TrustPal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ResultSummary extends AppCompatActivity {

    private TextView txtProgress;
    private ProgressBar circularProgressBar;

    static int unsafeCount=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_summary);

        txtProgress = (TextView) findViewById(R.id.txtProgress);
        circularProgressBar = (ProgressBar) findViewById(R.id.circularProgressbar);

        sortFriendlist();
        loadViews();

        Button nextbutton = (Button) findViewById(R.id.next);

        nextbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Intent intent = new Intent(getApplicationContext(), FriendResult.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void loadViews() {

        unsafeCount=0;
        int percentValue=0;

        for(int i=0; i<20; i++)
        {
            if(friendsPage.friendsArray[i][2] != "0")
            {
                unsafeCount++;
            }
        }
        percentValue=(20-unsafeCount)*5;
        txtProgress.setText(percentValue+"%");
        circularProgressBar.setProgress(percentValue);
    }

    private void sortFriendlist() {

        //sort the friendsArray based on priority using bubble sort
        for(int i=0;i<20;i++){
            for(int j=i+1;j<20;j++) {
                //only checking the 5th column
                if (Integer.parseInt(friendsPage.friendsArray[i][4]) > Integer.parseInt(friendsPage.friendsArray[j][4])) {
                    //swap(friendsPage.friendsArray[i],friendsPage.friendsArray[j]);
                    String temp[] = friendsPage.friendsArray[j];
                    friendsPage.friendsArray[j] = friendsPage.friendsArray[i];
                    friendsPage.friendsArray[i] = temp;
                }
            }
        }
    }

}
