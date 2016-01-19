/**
 * Copyright 2010-present Facebook.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mizan.friendstar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.fiu_CaSPR.Frank.safebuk.R;
import com.mizan.friendstar.dataclass.AppData;
import com.mizan.friendstar.dataclass.FacebookFriend;

import org.json.JSONArray;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainPageActivity extends FragmentActivity {


	// private static final String PERMISSION = "publish_actions";

	// private static final List<String> PERMISSIONS = new ArrayList<String>() {
	// /**
	// *
	// */
	// private static final long serialVersionUID = -128958230042605100L;
	//
	// {
	// add("user_friends");
	// add("public_profile");
	// add("publish_actions");
	// }
	// };

	private final String PENDING_ACTION_BUNDLE_KEY = "com.facebook.samples.hellofacebook:PendingAction";

	private LoginButton loginButton;
	private ImageView profilePictureView;
	private TextView greeting;
	private PendingAction pendingAction = PendingAction.NONE;
	private ViewGroup controlsContainer;
	boolean loggedIn=false;
	CallbackManager callbackManager;
	private Button startGameButton;
	private List<FacebookFriend> myList;

	private enum PendingAction {
		NONE, POST_PHOTO, POST_STATUS_UPDATE
	}

	private ProgressDialog mDialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
        FacebookSdk.sdkInitialize(this.getApplicationContext());
		callbackManager = CallbackManager.Factory.create();
		super.onCreate(savedInstanceState);

		// Add code to print out the key hash
		try {
			PackageInfo info = getPackageManager().getPackageInfo(
					"com.facebook.samples.hellofacebook",
					PackageManager.GET_SIGNATURES);
			for (Signature signature : info.signatures) {
				MessageDigest md = MessageDigest.getInstance("SHA");
				md.update(signature.toByteArray());
				Log.d("KeyHash:",
						Base64.encodeToString(md.digest(), Base64.DEFAULT));
			}
		} catch (NameNotFoundException e) {
			Log.d("KeyHash", e.getMessage());

		} catch (NoSuchAlgorithmException e) {
			Log.d("KeyHash", e.getMessage());

		}

		if (savedInstanceState != null) {
			String name = savedInstanceState
					.getString(PENDING_ACTION_BUNDLE_KEY);
			pendingAction = PendingAction.valueOf(name);
		}

		setContentView(R.layout.main2);

		loginButton = (LoginButton) findViewById(R.id.login_button);
		loginButton.setReadPermissions(Arrays.asList("user_friends", "public_profile"));
		//loginButton.setReadPermissions(permissions)
		loginButton
				.registerCallback(callbackManager, new FacebookCallback<LoginResult>(){
					@Override
					public void onSuccess(LoginResult loginResult) {
						startGameButton.setEnabled(true);
						Log.i("ran here:", "asdf");
					}

					@Override
					public void onCancel() {
						// App code
						Log.i("ran here:", "asdf");
					}

					@Override
					public void onError(FacebookException exception) {
						// App code
						Log.i("ran here:", "asdf");
					}
				});

		profilePictureView = (ImageView) findViewById(R.id.profilePicture);
		greeting = (TextView) findViewById(R.id.greeting);

		startGameButton = (Button) findViewById(R.id.getInvitableFriends);
		startGameButton.setEnabled(false);
		if(AccessToken.getCurrentAccessToken()!=null) {
			loggedIn = true;
			startGameButton.setEnabled(true);
		}

		startGameButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				onClickPickInvitableFriend();
			}
		});

		controlsContainer = (ViewGroup) findViewById(R.id.main_ui_container);


	}

	@Override
	protected void onResume() {
		super.onResume();

		// Call the 'activateApp' method to log an app event for use in
		// analytics and advertising reporting. Do so in
		// the onResume methods of the primary Activities that an app may be
		// launched into.
		AppEventsLogger.activateApp(this);

	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		outState.putString(PENDING_ACTION_BUNDLE_KEY, pendingAction.name());
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		callbackManager.onActivityResult(requestCode,resultCode,data);
	}

	@Override
	public void onPause() {
		super.onPause();

		// Call the 'deactivateApp' method to log an app event for use in
		// analytics and advertising
		// reporting. Do so in the onPause methods of the primary Activities
		// that an app may be launched into.
		AppEventsLogger.deactivateApp(this);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	private GraphRequest createRequest(String userID, Set<String> extraFields) {

		Log.d("Mizan", "creatinf request");


		GraphRequest request = new GraphRequest(AccessToken.getCurrentAccessToken(),
				"me/invitable_friends", null,
				HttpMethod.GET,
				new GraphRequest.Callback() {
					public void onCompleted(GraphResponse response) {
            /* handle the result */
						// Log.d("Mizan", response.toString());
						myList = new ArrayList<FacebookFriend>();
						// System.out.println(response.toString());

						JSONObject graphObject = response.getJSONObject();
						int numberOfRecords = 0;
						try {
							JSONArray dataArray = (JSONArray) graphObject.getJSONArray("data");

							if (dataArray.length() > 0) {
								// Ensure the user has at least one friend ...
								for (int i = 0; i < dataArray.length(); i++) {
									JSONObject jsonObject = dataArray.optJSONObject(i);
									FacebookFriend facebookFriend = new FacebookFriend(jsonObject,
											FacebookFriend.Type.AVAILABLE);
									if (facebookFriend.isValid) {
										numberOfRecords++;
										// Log.d("Mizan", facebookFriend.name);
										// Log.d("Mizan", facebookFriend.facebookId);
										// Log.d("Mizan", "url: " + facebookFriend.pictureUrl);
										myList.add(facebookFriend);
									}
								}
							}
						}catch(Exception e){

						}
						startFriendListActivity();
					}
				}
		);
		Set<String> fields = new HashSet<String>(extraFields);
		String[] requiredFields = new String[] { "id", "name" };
		fields.addAll(Arrays.asList(requiredFields));
		fields.add("picture.height(150).width(150)");
		Bundle parameters = request.getParameters();
		parameters.putString("fields", TextUtils.join(",", fields));
		request.setParameters(parameters);
		request.executeAsync();

		return request;
	}

	protected void requestCompleted(GraphResponse response) {

	}

	private void startFriendListActivity() {

		AppData.getInstance().setAllFriendList(myList);
		mDialog.dismiss();

		Intent nextScreen = new Intent(getApplicationContext(),
				FriendListActivity.class);

		startActivity(nextScreen);
	}

	private void onClickPickInvitableFriend() {
		HashSet<String> extraFields = new HashSet<String>();
		if (mDialog == null) {
			mDialog = new ProgressDialog(MainPageActivity.this);
			mDialog.setMessage("Please wait...");
			mDialog.show();
			mDialog.setCanceledOnTouchOutside(false);
			mDialog.setCancelable(false);
		}
		createRequest("me", extraFields);
	}
}
