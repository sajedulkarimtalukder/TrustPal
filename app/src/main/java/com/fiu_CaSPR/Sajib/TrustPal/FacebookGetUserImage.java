package com.fiu_CaSPR.Sajib.TrustPal;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.facebook.AccessToken;
import com.temboo.Library.Facebook.Reading.Picture;
import com.temboo.core.TembooException;
import com.temboo.core.TembooSession;

import org.json.JSONObject;

/**
 * Created by sajib on 2/5/16.
 */
public class FacebookGetUserImage extends AsyncTask<String, Void, String> {
    String userId;
    ImageView bmImage;
    private AccessToken accesstoken;
    // Replace with your Temboo credentials.
    private static final String TEMBOO_ACCOUNT_NAME = "fiuseclab";
    private static final String TEMBOO_APP_KEY_NAME = "myFirstApp";
    private static final String TEMBOO_APP_KEY_VALUE = "fea7fa83813941fc8b445a5b4ed22bb0";
    String AccessTokenString = AccessToken.getCurrentAccessToken().getToken();
    private TembooSession session;
    String imageUrl;
    int id;

    public FacebookGetUserImage(String imageUrl, int id) {
        this.imageUrl = imageUrl;
        this.id=id;
    }
    protected String doInBackground(String... ids) {
        userId = ids[0];

        try {
            try {

                session = new TembooSession(TEMBOO_ACCOUNT_NAME, TEMBOO_APP_KEY_NAME, TEMBOO_APP_KEY_VALUE);
            } catch (TembooException te) {
                Log.d("", "Error1");
                Log.e("FacebookOAuthHelpder", te.getMessage());
            }
            Picture pictureChoreo = new Picture(session);

// Get an InputSet object for the choreo
            Picture.PictureInputSet pictureInputs = pictureChoreo.newInputSet();

// Set inputs
            pictureInputs.set_ProfileID(userId);
            pictureInputs.set_AccessToken(AccessTokenString);
            pictureInputs.set_Redirect("false");

// Execute Choreo
            Picture.PictureResultSet pictureResults = pictureChoreo.execute(pictureInputs);

            return pictureResults.get_Response();
        } catch (Exception e) {
            // if an exception occurred, log it
            Log.e(this.getClass().toString(), e.getMessage());
        }
        return null;
    }

    protected void onPostExecute(String userInfo) {
        try {

            JSONObject nytJSON = new JSONObject(userInfo);
            JSONObject jsonObject = nytJSON.getJSONObject("data");
            String URL = jsonObject.get("url").toString();
            URL=URL.replaceAll("\\\\", "");
            FriendSelectorView1.imageUrl=URL;

            //For the general question pictures
            if(id==0) {
                new DownloadImageTask(FriendResult.propicView).execute(URL);
            }
            else if(id==1) {
                new DownloadImageTask(FriendSelectorView1.propicView).execute(URL);
            }
            else if(id==2) {
                new DownloadImageTask(FriendSelectorView2.propicView).execute(URL);
            }
            else if(id==3) {
                new DownloadImageTask(FriendSelectorView3.propicView).execute(URL);
            }
            else if(id==4) {
                new DownloadImageTask(FriendSelectorView4.propicView).execute(URL);
            }
            else if(id==5) {
                new DownloadImageTask(FriendSelectorView5.propicView).execute(URL);
            }

            //For the puzzle1 question pictures
            else if(id==11) {
                new DownloadImageTask(puzzle1.pic11).execute(URL);
            }
            else if(id==12) {
                new DownloadImageTask(puzzle1.pic21).execute(URL);
            }
            else if(id==13) {
                new DownloadImageTask(puzzle1.pic31).execute(URL);
            }
            else if(id==14) {
                new DownloadImageTask(puzzle1.pic41).execute(URL);
            }
            else if(id==15) {
                new DownloadImageTask(puzzle1.pic12).execute(URL);
            }
            else if(id==16) {
                new DownloadImageTask(puzzle1.pic22).execute(URL);
            }
            else if(id==17) {
                new DownloadImageTask(puzzle1.pic32).execute(URL);
            }
            else if(id==18) {
                new DownloadImageTask(puzzle1.pic42).execute(URL);
            }

            //For the puzzle2 question pictures
            else if(id==21) {
                new DownloadImageTask(puzzle2.pic11).execute(URL);
            }
            else if(id==22) {
                new DownloadImageTask(puzzle2.pic21).execute(URL);
            }
            else if(id==23) {
                new DownloadImageTask(puzzle2.pic31).execute(URL);
            }
            else if(id==24) {
                new DownloadImageTask(puzzle2.pic41).execute(URL);
            }
            else if(id==25) {
                new DownloadImageTask(puzzle2.pic12).execute(URL);
            }
            else if(id==26) {
                new DownloadImageTask(puzzle2.pic22).execute(URL);
            }
            else if(id==27) {
                new DownloadImageTask(puzzle2.pic32).execute(URL);
            }
            else if(id==28) {
                new DownloadImageTask(puzzle2.pic42).execute(URL);
            }

            //For the puzzle3 question pictures
            else if(id==31) {
                new DownloadImageTask(puzzle3.pic11).execute(URL);
            }
            else if(id==32) {
                new DownloadImageTask(puzzle3.pic21).execute(URL);
            }
            else if(id==33) {
                new DownloadImageTask(puzzle3.pic31).execute(URL);
            }
            else if(id==34) {
                new DownloadImageTask(puzzle3.pic41).execute(URL);
            }
            else if(id==35) {
                new DownloadImageTask(puzzle3.pic12).execute(URL);
            }
            else if(id==36) {
                new DownloadImageTask(puzzle3.pic22).execute(URL);
            }
            else if(id==37) {
                new DownloadImageTask(puzzle3.pic32).execute(URL);
            }
            else if(id==38) {
                new DownloadImageTask(puzzle3.pic42).execute(URL);
            }

            //For the puzzle4 question pictures
            else if(id==41) {
                new DownloadImageTask(puzzle4.pic11).execute(URL);
            }
            else if(id==42) {
                new DownloadImageTask(puzzle4.pic21).execute(URL);
            }
            else if(id==43) {
                new DownloadImageTask(puzzle4.pic31).execute(URL);
            }
            else if(id==44) {
                new DownloadImageTask(puzzle4.pic41).execute(URL);
            }
            else if(id==45) {
                new DownloadImageTask(puzzle4.pic12).execute(URL);
            }
            else if(id==46) {
                new DownloadImageTask(puzzle4.pic22).execute(URL);
            }
            else if(id==47) {
                new DownloadImageTask(puzzle4.pic32).execute(URL);
            }
            else if(id==48) {
                new DownloadImageTask(puzzle4.pic42).execute(URL);
            }

            //For the puzzle5 question pictures
            else if(id==51) {
                new DownloadImageTask(puzzle5.pic11).execute(URL);
            }
            else if(id==52) {
                new DownloadImageTask(puzzle5.pic21).execute(URL);
            }
            else if(id==53) {
                new DownloadImageTask(puzzle5.pic31).execute(URL);
            }
            else if(id==54) {
                new DownloadImageTask(puzzle5.pic41).execute(URL);
            }
            else if(id==55) {
                new DownloadImageTask(puzzle5.pic12).execute(URL);
            }
            else if(id==56) {
                new DownloadImageTask(puzzle5.pic22).execute(URL);
            }
            else if(id==57) {
                new DownloadImageTask(puzzle5.pic32).execute(URL);
            }
            else if(id==58) {
                new DownloadImageTask(puzzle5.pic42).execute(URL);
            }

        } catch (Exception e) {
            // if an exception occurred, show an error message
            Log.e("error:", e.toString());
        }
    }
}
