package com.fiu_CaSPR.Sajib.TrustPal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class UnqualifiedUser extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.unqualified_user);


        Button nextbutton = (Button) findViewById(R.id.logout);

        nextbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Intent intent = new Intent(getApplicationContext(), FakeLogout.class);
                startActivity(intent);
                finish();
            }
        });
    }


}
