package com.fiu_CaSPR.Sajib.TrustPal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

public class FakeLogout extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fake_logout);
        /*ImageView nextbutton = (ImageView) findViewById(R.id.next);
        nextbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                *//*Intent intent = new Intent(getApplicationContext(), url0.class);
                startActivity(intent);*//*
                finish();
            }
        });*/
    }

}
