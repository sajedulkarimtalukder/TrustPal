package com.mizan.friendstar;

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

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fiu_CaSPR.Frank.Constants.FacebookRegexPatternPool;
import com.fiu_CaSPR.Frank.safebuk.DownloadImageTask;
import com.fiu_CaSPR.Frank.safebuk.FacebookOAuthHelper;
import com.fiu_CaSPR.Frank.safebuk.FetchData;
import com.fiu_CaSPR.Frank.safebuk.R;
import com.mizan.friendstar.dataclass.AppData;
import com.mizan.friendstar.dataclass.CheckBoxValue;
import com.mizan.friendstar.dataclass.FacebookFriend;
import com.mizan.friendstar.dataclass.OneFriendInfo;


public class FriendListActivity extends Activity {

	ImageView propicView;
	private TextView profileNameTextView;
	private CheckBox checkBox1;
	private CheckBox checkBox2;
	private CheckBox checkBox3;
	private CheckBox checkBox4;
	private CheckBox checkBox5;

	private int currentPictureIndex;
	private int totalCountForReview = 20;
	private Random randomGenerator;
	private int totalCount;
	private List<Integer> randomNumbers;
	private List<OneFriendInfo> allFriendsInfo;
	private OneFriendInfo currentFriendInfo;

	private List<CheckBox> checkboxes;
	private long start_time;
	private long start_time_for_next_button;
	private FacebookOAuthHelper oAuthHelper;

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

		if (AppData.getInstance().getAllFriendList().size() > 500) {
			totalCountForReview = 40;
		}

		totalCount = AppData.getInstance().getAllFriendList().size();

		if (totalCount > 0) {
			currentPictureIndex = 0;
			loadFriendInfoView(AppData.getInstance().getAllFriendList().get(getNextIndex()));
		}

		totalCountForReview = getTotalCount();
	}

	private void loadViews() {
		propicView = (ImageView) findViewById(R.id.propicimageview);
		profileNameTextView = (TextView) findViewById(R.id.frndcount);

		profileNameTextView.setText(FacebookRegexPatternPool.Name);
		//Toast.makeText(FriendListActivity.this, oAuthHelper., Toast.LENGTH_SHORT).show();
		//new DownloadImageTask(propicView).execute("https:\\/\\/scontent.xx.fbcdn.net\\/hprofile-xpt1\\/v\\/t1.0-1\\/c16.0.50.50\\/p50x50\\/11045468_10200779822774627_1071057245615326487_n.jpg?oh=7234fde6a832b5320e7443bad236dbd1&oe=56C8C020");


		checkBox1 = (CheckBox) findViewById(R.id.checkBox417);
		checkBox2 = (CheckBox) findViewById(R.id.CheckBox418);
		checkBox3 = (CheckBox) findViewById(R.id.CheckBox3);
		checkBox4 = (CheckBox) findViewById(R.id.CheckBox4);
		checkBox5 = (CheckBox) findViewById(R.id.CheckBox5);

		checkboxes.add(checkBox1);
		checkboxes.add(checkBox2);
		checkboxes.add(checkBox3);
		checkboxes.add(checkBox4);
		checkboxes.add(checkBox5);

		for (int i = 0; i < checkboxes.size(); i++) {
			CheckBox checkBox = checkboxes.get(i);
			/*checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

				@Override
				public void onCheckedChanged(CompoundButton buttonView,
											 boolean isChecked) {
					long end_time = System.nanoTime();
					double difference = (end_time - start_time) / 1e6;

					int index=-1;

					if(buttonView.getId() == R.id.checkBox417) index = 0;
					if(buttonView.getId() == R.id.CheckBox418) index = 1;
					if(buttonView.getId() == R.id.CheckBox3) index = 2;
					if(buttonView.getId() == R.id.CheckBox4) index = 3;
					if(buttonView.getId() == R.id.CheckBox5) index = 4;

					CheckBoxValue boxValue = new CheckBoxValue(isChecked, difference);
					currentFriendInfo.getCheckBoxValues().set(index, boxValue);

					start_time = System.nanoTime();
				}
			});*/
		}


		Button nextButton = (Button) findViewById(R.id.nextButton);
		Button doneButton = (Button) findViewById(R.id.doneButton);


		nextButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				long end_time = System.nanoTime();
				double difference = (end_time - start_time_for_next_button) / 1e6;
//				currentFriendInfo.setTime(difference);
				allFriendsInfo.add(currentFriendInfo);

				showNext();
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
			return 20;
		}else{
			if (totalCount <= 100) {
				return 20;
			}
			else{
				return 30;
			}
		}
	}

	protected void finishPage() {
		finish();
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
		finishPage();
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
		String string = "[";
		for (int i = 0; i < allFriendsInfo.size(); i++) {

			string += allFriendsInfo.get(i).toString();
			if (i != allFriendsInfo.size() - 1) {
				string += ",";
			}
		}

		string += "]";

		return string;
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

		checkBox1.setChecked(false);
		checkBox2.setChecked(false);
		checkBox3.setChecked(false);
		checkBox4.setChecked(false);
		checkBox5.setChecked(false);
	}

	private void loadFriendInfoView(FacebookFriend facebookFriend) {
		currentFriendInfo = new OneFriendInfo();
		resetFields();
		currentPictureIndex++;
		//new DownloadImageTask(propicView).execute(oAuthHelper.getProfilePicture());
		//profileNameTextView.setText(facebookFriend.name);
		//profileNameTextView.setText(FacebookRegexPatternPool.Name);
		start_time = System.nanoTime();
		start_time_for_next_button = System.nanoTime();
	}
}