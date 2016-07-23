package com.fiu_CaSPR.Sajib.TrustPal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ResultSummary extends ActionBarActivity {

    private TextView txtProgress;
    private ProgressBar circularProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_summary);

        txtProgress = (TextView) findViewById(R.id.txtProgress);
        circularProgressBar = (ProgressBar) findViewById(R.id.circularProgressbar);

        loadViews();

        Button ignorebutton = (Button) findViewById(R.id.ignore);
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

        int unsafeCount=0;
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

}
