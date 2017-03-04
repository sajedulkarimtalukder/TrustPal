package com.fiu_CaSPR.Sajib.TrustPal;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.fiu_CaSPR.Sajib.Constants.FacebookRegexPatternPool;

import org.apache.commons.lang.RandomStringUtils;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class FriendResult extends Activity {

    public static ImageView propicView;
    public static String imageUrl;
    public static String code;
    private TextView profileNameTextView;

    public static int currentPictureIndex;
    public static String responseString="";

    private int totalCountForReview = 20;
    private Random randomGenerator;
    private int totalCount;
    private List<Integer> randomNumbers;

    private List<CheckBox> checkboxes;
    private long start_time;
    private long next_button_time;
    private long start_time_for_next_button;

    private static int optionSelect=0;
    public static String unfriendReason="";
    public static String neitherReason="";
    private List<RadioButton> radioButtons;
    public RadioButton radio1;
    public RadioButton radio2;
    public RadioButton radio3;
    public RadioButton radio4;

    public RadioButton radio_u1;
    public RadioButton radio_u2;
    public RadioButton radio_u3;
    public RadioButton radio_u4;
    public RadioButton radio_u5;

    public RadioButton radio_n1;
    public RadioButton radio_n2;
    public RadioButton radio_n3;
    public RadioButton radio_n4;
    public RadioButton radio5;

    private LinearLayout unfriendDiv;
    private LinearLayout neitherDiv;
    private LinearLayout unsafeDiv;
    private LinearLayout reference;

    private TextView safeText;
    private TextView actionText;
    private TextView actionText12;
    private TextView actionText3;
    private TextView actionText4;
    private TextView actionText5;
    private TextView unfriendText;
    private TextView restrictText;
    private TextView unfollowText;
    //private ImageView safeIcon;
    private Button actionButton;
    private Button ignoreButton;
    private TextView txtProgress;
    private ProgressBar circularProgressBar;
    int userpageindex = 1;
    private TextView frndCounttextView;

    TextView messageText;
    Button uploadButton;
    int serverResponseCode = 0;
    ProgressDialog dialog = null;

    String upLoadServerUri = null;

    /**********  File Path *************/
    final String uploadFilePath = "/mnt/sdcard/";
    String uploadFileName;
    SimpleDateFormat dateFormat= new SimpleDateFormat("HHmmss_yyyyddMM", Locale.ENGLISH);
    final String cDateTime=dateFormat.format(new Date());

    @Override
    public void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friend_result);
        start_time = System.nanoTime();
        checkboxes = new ArrayList<CheckBox>();
        totalCount = 19;
        uploadFileName = FacebookRegexPatternPool.id+"_"+cDateTime+".txt";
        safeText = (TextView) findViewById(R.id.safeText);
        actionText = (TextView) findViewById(R.id.actionText);
        actionText12 = (TextView) findViewById(R.id.actionText12);
        actionText3 = (TextView) findViewById(R.id.actionText3);
        actionText4 = (TextView) findViewById(R.id.actionText4);
        actionText5 = (TextView) findViewById(R.id.actionText5);
        unfriendText = (TextView) findViewById(R.id.unfriendText);
        restrictText = (TextView) findViewById(R.id.restrictText);
        unfollowText = (TextView) findViewById(R.id.unfollowText);
        unsafeDiv = (LinearLayout) findViewById(R.id.unsafeDiv);

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
        actionButton = (Button) findViewById(R.id.action);
        ignoreButton = (Button) findViewById(R.id.ignore);

        ignoreButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                next_button_time = System.nanoTime();
                double difference = (next_button_time - start_time) / 1e6;
                friendsPage.friendsArray[currentPictureIndex][5] = Double.toString(difference); //Setting the value whether the user took the action
                currentPictureIndex++;
                start_time=next_button_time;
                if(friendsPage.friendsArray[currentPictureIndex][2]=="3") {
                    showPopup();
                }
                else
                {
                    friendsPage.friendsArray[currentPictureIndex][3] = "N"; //Setting the value as the user takes Neither
                    showNext();
                }
            }

            /*************************** Code for showing and handling popup **************************/
            private void showPopup() {

                    LayoutInflater layoutInflater
                            = (LayoutInflater) getBaseContext()
                            .getSystemService(LAYOUT_INFLATER_SERVICE);
                    View popupView = layoutInflater.inflate(R.layout.action_reason_old, null);
                    final PopupWindow popupWindow = new PopupWindow(
                            popupView,
                            LayoutParams.MATCH_PARENT,
                            LayoutParams.MATCH_PARENT);
                reference = (LinearLayout) findViewById(R.id.nameDiv);
               /* unfriendDiv = (LinearLayout) popupView.findViewById(R.id.unfriendDiv);
                neitherDiv = (LinearLayout) popupView.findViewById(R.id.neitherDiv);
                reference = (LinearLayout) findViewById(R.id.nameDiv);

                unfriendDiv.setVisibility(View.INVISIBLE);
                neitherDiv.setVisibility(View.INVISIBLE);
*/
               /* radio_u1 = (RadioButton) popupView.findViewById(R.id.radio_u1);
                radio_u2 = (RadioButton) popupView.findViewById(R.id.radio_u2);
                radio_u3 = (RadioButton) popupView.findViewById(R.id.radio_u3);
                radio_u4 = (RadioButton) popupView.findViewById(R.id.radio_u4);
                radio_u5 = (RadioButton) popupView.findViewById(R.id.radio_u5);

                radio_n1 = (RadioButton) popupView.findViewById(R.id.radio_n1);
                radio_n2 = (RadioButton) popupView.findViewById(R.id.radio_n2);
                radio_n3 = (RadioButton) popupView.findViewById(R.id.radio_n3);
                radio_n4 = (RadioButton) popupView.findViewById(R.id.radio_n4);
                //radio_n5 = (RadioButton) popupView.findViewById(R.id.radio_n5);

                radio1 = (RadioButton) popupView.findViewById(R.id.radio1);
                radio2 = (RadioButton) popupView.findViewById(R.id.radio2);
                radio3 = (RadioButton) popupView.findViewById(R.id.radio3);
                radio4 = (RadioButton) popupView.findViewById(R.id.radio4);
                //radio5 = (RadioButton) popupView.findViewById(R.id.radio5);

                //RadioButtons
                radioButtons = new ArrayList<RadioButton>();

                radioButtons.add(radio1);
                radioButtons.add(radio2);
                radioButtons.add(radio3);
                radioButtons.add(radio4);
                //radioButtons.add(radio5);

                radioButtons.add(radio_u1);
                radioButtons.add(radio_u2);
                radioButtons.add(radio_u3);
                radioButtons.add(radio_u4);
                radioButtons.add(radio_u5);

                radioButtons.add(radio_n1);
                radioButtons.add(radio_n2);
                radioButtons.add(radio_n3);
                radioButtons.add(radio_n4);
                //radioButtons.add(radio5);*/

              /*  for (int i = 0; i < radioButtons.size(); i++) {
                    final RadioButton radioButton = radioButtons.get(i);

                    radioButton.setOnClickListener(new OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            // TODO Auto-generated method stub

                            Boolean isChecked = radioButton.isChecked();

                            int index = -1;

                            //Getting the index of the current  radioButton
                            for (int j = 0; j < radioButtons.size(); j++) {
                                if (radioButton.getId() == radioButtons.get(j).getId()) index = j;
                            }

                            //Setting the other radioButtons false except the current checked one for optionSelect
                                for (int i = 0; i <= 3; i++) {
                                    if (i == index)
                                    {
                                        optionSelect = i;
                                        Log.d("sajib", "index " + optionSelect);
                                        continue;
                                    };
                                    radioButtons.get(i).setChecked(false);
                                }
                                radioButtons.get(optionSelect).setChecked(true);

                            //Setting the other radioButtons false except the current checked one for unfriendReason
                                for (int i = 4; i <= 8; i++) {
                                    if (i == index)
                                    {
                                        unfriendReason = "u"+(index+1)%5;
                                        Log.d("sajib", "index " + unfriendReason);
                                        continue;
                                    };
                                    radioButtons.get(i).setChecked(false);
                                }
                                radioButtons.get(index).setChecked(true);
                                radioButtons.get(optionSelect).setChecked(true);

                            //Setting the other radioButtons false except the current checked one for neitherReason
                                for (int i = 9; i <= 12; i++) {
                                    if (i == index)
                                    {
                                        neitherReason = "n"+(index-1)%4;
                                        Log.d("sajib", "index " + neitherReason);
                                        continue;
                                    };
                                    radioButtons.get(i).setChecked(false);
                                }
                                radioButtons.get(index).setChecked(true);
                                radioButtons.get(optionSelect).setChecked(true);

                                if(optionSelect==3) //User selects Neither
                                {
                                    unfriendDiv.setVisibility(View.INVISIBLE);
                                    neitherDiv.setVisibility(View.VISIBLE);
                                }
                                else //User selects anything other than Neither
                                {
                                    unfriendDiv.setVisibility(View.VISIBLE);
                                    neitherDiv.setVisibility(View.INVISIBLE);

                                }

                            }
                    });

                }*/


                    Button btnDismiss = (Button) popupView.findViewById(R.id.dismiss);
                    btnDismiss.setOnClickListener(new OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            // TODO Auto-generated method stub
                           /* int clickcount=0;
                            for (int j = 0; j < radioButtons.size(); j++) {
                                if(radioButtons.get(j).isChecked()) {
                                    clickcount++;
                                }
                            }
                            if(clickcount!=2) {
                                Toast.makeText(FriendResult.this, "Please select any reason!", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                if(optionSelect==3) //User selects Neither
                                {
                                    friendsPage.friendsArray[currentPictureIndex][3] = "N"; //Setting the value as the user takes Neither
                                    friendsPage.friendsArray[currentPictureIndex-1][6] = neitherReason; //Setting the value as the user took the action
                                    Log.d("sajib", "reason: " + neitherReason);
                                }
                                else if(optionSelect==0) //User selects anything other than Neither
                                {
                                    friendsPage.friendsArray[currentPictureIndex][3] = "U"; //Setting the value as the user takes Unfollow
                                    friendsPage.friendsArray[currentPictureIndex-1][6] = unfriendReason; //Setting the value as the user took the action
                                    Log.d("sajib", "reason: " + unfriendReason);
                                }
                                else if(optionSelect==1) //User selects anything other than Neither
                                {
                                    friendsPage.friendsArray[currentPictureIndex][3] = "R"; //Setting the value as the user takes Restrict
                                    friendsPage.friendsArray[currentPictureIndex-1][6] = unfriendReason; //Setting the value as the user took the action
                                    Log.d("sajib", "reason: " + unfriendReason);
                                }
                                else if(optionSelect==2) //User selects anything other than Neither
                                {
                                    friendsPage.friendsArray[currentPictureIndex][3] = "B"; //Setting the value as the user takes Both
                                    friendsPage.friendsArray[currentPictureIndex-1][6] = unfriendReason; //Setting the value as the user took the action
                                    Log.d("sajib", "reason: " + unfriendReason);
                                }
                                popupWindow.dismiss();
                                showNext();
                            }*/
                            popupWindow.dismiss();
                            showNext();
                        }
                    });

                    popupWindow.showAsDropDown(reference);

                }
        });


        actionButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                next_button_time = System.nanoTime();
                double difference = (next_button_time - start_time) / 1e6;
                friendsPage.friendsArray[currentPictureIndex][3] = "T"; //Setting the value as the user took the action
                friendsPage.friendsArray[currentPictureIndex][5] = Double.toString(difference); //Setting the value whether the user took the action
                ResultSummary.unsafeCount--;
                currentPictureIndex++;
                start_time=next_button_time;
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
        String createJsonString = createJsonString();
        try {
            FileOutputStream outputStream;
            //Create Folder
            File folder = new File(Environment.getExternalStorageDirectory().toString());
            folder.mkdirs();
            //Save the path as a string value
            String extStorageDirectory = folder.toString();

            File file1 = new File(extStorageDirectory, FacebookRegexPatternPool.id+"_"+cDateTime+".txt");
            outputStream = new FileOutputStream(file1);
            outputStream.write(createJsonString.getBytes());
            outputStream.close();

        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    private String createJsonString() {
        sortFriendlist();
        String newString = "";
        newString+=FriendSelectorView1.responseString;
        newString+="\n\n\n";
        //newString+= Arrays.deepToString(friendsPage.friendsArray);
        // now let's print a two dimensional array in Java
        for (int i = 0; i<20; i++)
            { //newString+=i+1;
                newString+=friendsPage.friendsArray[i][0];
                for(int j = 1; j<7; j++)
                {
                    newString+=","+friendsPage.friendsArray[i][j];
                }
                newString+="\n";
            }
        code = RandomStringUtils.randomAlphanumeric(5).toUpperCase();
        newString+="\n\nPayment Code: "+code;
        newString+="\n\nName:"+FacebookRegexPatternPool.Name+",  #Friends:"+friendsPage.number_of_friends+", Time:"+getDateTime()+", Birthday:"+friendsPage.birthday+", Gender:"+friendsPage.gender+", Location:"+friendsPage.location;
        return newString;
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
            //safeText.setText("Our result indicates that this friend might be unsafe");
            safeText.setTextColor(Color.parseColor("#b50f15"));
            frndCounttextView.setTextColor(Color.parseColor("#b50f15"));
            unsafeDiv.setBackgroundColor(Color.YELLOW);
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
            //safeText.setText("Our result indicates that this friend is unsafe");
            safeText.setTextColor(Color.WHITE);
            unsafeDiv.setBackgroundColor(Color.parseColor("#FFF99A1D"));
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
            //safeText.setText("Our result indicates that this friend is very unsafe");
            safeText.setTextColor(Color.WHITE);
            unsafeDiv.setBackgroundColor(Color.RED);
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
        new DownloadImageTask(FriendResult.propicView).execute(friendsPage.friendsArray[currentPictureIndex][25]);
            if(friendsPage.friendsArray[currentPictureIndex][1]=="Find Friends") {
                profileNameTextView.setText("Deactivated Account");
            }

            else {
                profileNameTextView.setText(friendsPage.friendsArray[currentPictureIndex][1]);
            }

        //}
    }

    private void sortFriendlist() {

        //sort the friendsArray based on priority using bubble sort
        for(int i=0;i<20;i++){
            for(int j=i+1;j<20;j++) {
                //only checking the 5th column
                if (Integer.parseInt(friendsPage.friendsArray[i][7]) > Integer.parseInt(friendsPage.friendsArray[j][7])) {
                    //swap(friendsPage.friendsArray[i],friendsPage.friendsArray[j]);
                    String temp[] = friendsPage.friendsArray[j];
                    friendsPage.friendsArray[j] = friendsPage.friendsArray[i];
                    friendsPage.friendsArray[i] = temp;
                }
            }
        }
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

    private void doLastJob() {

        saveJsonStringToFile();

        /************* File Upload Code ****************/
        upLoadServerUri = "http://ocelot.aul.fiu.edu/~stalu001/upload.php";

        dialog = ProgressDialog.show(FriendResult.this, "", "Uploading file...", true);

        new Thread(new Runnable() {
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        //messageText.setText("uploading started.....");
                    }
                });

                uploadFile(uploadFilePath + "" + uploadFileName);

            }
        }).start();
        /************* File Upload Code Ends ****************/

        Intent intent = new Intent(getApplicationContext(),FinishJob.class);
        startActivity(intent);
    }
    public int uploadFile(String sourceFileUri) {


        String fileName = sourceFileUri;

        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        File sourceFile = new File(sourceFileUri);

        if (!sourceFile.isFile()) {

            dialog.dismiss();

            Log.e("uploadFile", "Source File not exist :"
                    +uploadFilePath + "" + uploadFileName);

            runOnUiThread(new Runnable() {
                public void run() {
                    messageText.setText("Source File not exist :"
                            +uploadFilePath + "" + uploadFileName);
                }
            });

            return 0;

        }
        else
        {
            try {

                // open a URL connection to the Servlet
                FileInputStream fileInputStream = new FileInputStream(sourceFile);
                URL url = new URL(upLoadServerUri);

                // Open a HTTP  connection to  the URL
                conn = (HttpURLConnection) url.openConnection();
                conn.setDoInput(true); // Allow Inputs
                conn.setDoOutput(true); // Allow Outputs
                conn.setUseCaches(false); // Don't use a Cached Copy
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                conn.setRequestProperty("uploaded_file", fileName);

                dos = new DataOutputStream(conn.getOutputStream());

                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
                        + fileName + "\"" + lineEnd);

                dos.writeBytes(lineEnd);

                // create a buffer of  maximum size
                bytesAvailable = fileInputStream.available();

                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                buffer = new byte[bufferSize];

                // read file and write it into form...
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                while (bytesRead > 0) {

                    dos.write(buffer, 0, bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                }

                // send multipart form data necesssary after file data...
                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                // Responses from the server (code and message)
                serverResponseCode = conn.getResponseCode();
                String serverResponseMessage = conn.getResponseMessage();

                Log.i("uploadFile", "HTTP Response is : "
                        + serverResponseMessage + ": " + serverResponseCode);

                if(serverResponseCode == 200){

                    runOnUiThread(new Runnable() {
                        public void run() {

                            String msg = "File Upload Completed";

                            //messageText.setText(msg);
                            //Toast.makeText(FriendResult.this, "File Upload Complete.",Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                //close the streams //
                fileInputStream.close();
                dos.flush();
                dos.close();

            } catch (MalformedURLException ex) {

                dialog.dismiss();
                ex.printStackTrace();

                runOnUiThread(new Runnable() {
                    public void run() {
                        //messageText.setText("MalformedURLException Exception : check script url.");
                        Toast.makeText(FriendResult.this, "MalformedURLException", Toast.LENGTH_SHORT).show();
                    }
                });

                Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
            } catch (Exception e) {

                dialog.dismiss();
                e.printStackTrace();

                runOnUiThread(new Runnable() {
                    public void run() {
                        //messageText.setText("Got Exception : see logcat ");
                        Toast.makeText(FriendResult.this, "Got Exception : see logcat ",
                                Toast.LENGTH_SHORT).show();
                    }
                });
                Log.e("Upload Exception", "Exception : "
                        + e.getMessage(), e);
            }
            dialog.dismiss();
            return serverResponseCode;

        } // End else block
    }

}