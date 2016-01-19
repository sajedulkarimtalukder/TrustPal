package com.mizan.friendstar.dataclass;

import java.util.List;

public class AppData {
	private static AppData objLogger;
	private List<FacebookFriend> allFriendList;
	private String username;
	private String currentFilePath;

	private AppData() {

	}

	public static AppData getInstance() {
		if (objLogger == null) {
			objLogger = new AppData();
		}
		return objLogger;
	}

	public List<FacebookFriend> getAllFriendList() {
		return allFriendList;
	}

	public void setAllFriendList(List<FacebookFriend> allFriendList) {
		this.allFriendList = allFriendList;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setCurrentFilePath(String fileName) {
		this.currentFilePath = fileName;
	}

	public String getCurrentFilePath() {
		return this.currentFilePath;
	}


}