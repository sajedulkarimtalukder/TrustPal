package com.mizan.friendstar.dataclass;

import java.util.ArrayList;
import java.util.List;


public class OneFriendInfo {
	private List<CheckBoxValue> checkBoxValues;
	private int totalCheckBox = 20;
	private double time;

	public OneFriendInfo() {
		setCheckBoxValues(new ArrayList<CheckBoxValue>());

		for (int i = 0; i < totalCheckBox; i++) {
			getCheckBoxValues().add(new CheckBoxValue());
		}

		setTime(0);
	}

	public List<CheckBoxValue> getCheckBoxValues() {
		return checkBoxValues;
	}

	public void setCheckBoxValues(List<CheckBoxValue> checkBoxValues) {
		this.checkBoxValues = checkBoxValues;
	}

	public double getTime() {
		return time;
	}

	public void setTime(double time) {
		this.time = time;
	}

	@Override
	public String toString() {
		String string = "{\"checkboxValues\":[";
		for (int i = 0; i < checkBoxValues.size(); i++) {
			string += checkBoxValues.get(i).toString();
			//if (i!=4) {
			string += ",";
			//}
		}

		string += "]," + "\"totalTime\":" + time + "}";
		return string;
	}
}