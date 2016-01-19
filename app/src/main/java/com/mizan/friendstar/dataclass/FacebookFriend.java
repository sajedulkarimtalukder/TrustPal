package com.mizan.friendstar.dataclass;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class FacebookFriend implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 9050096313919256917L;
	public String facebookId;
	public String name;
	public String pictureUrl;
	public boolean invitable;
	public boolean available;
	public boolean isValid;

	public enum Type {
		AVAILABLE, INVITABLE
	};

	public FacebookFriend(JSONObject jsonObject, Type type) {
		//
		// Parse the Facebook Data from the JSON object.
		//
		try {
			// parse /me/invitable_friend
			this.facebookId = jsonObject.getString("id");
			this.name = jsonObject.getString("name");

			// Handle the picture data.
			JSONObject pictureJsonObject = jsonObject.getJSONObject("picture")
					.getJSONObject("data");
			boolean isSilhouette = pictureJsonObject
					.getBoolean("is_silhouette");
			// this.pictureUrl = pictureJsonObject.getString("url");

			if (!isSilhouette) {
				this.pictureUrl = pictureJsonObject.getString("url");

			} else {
				this.pictureUrl = "";
			}

			this.invitable = true;

			isValid = true;
		} catch (JSONException e) {
			Log.w("#",
					"Warnings - unable to process FB JSON: "
							+ e.getLocalizedMessage());
		}
	}
}