package com.mizan.friendstar.dataclass;


public class CheckBoxValue {
	private Boolean value;
	private double time;

	public CheckBoxValue(Boolean value, double time) {
		this.time = time;
		this.value = value;
	}

	public CheckBoxValue() {
		this.time = 0.0;
		this.value = false;
	}


	public Boolean getValue() {
		return value;
	}
	public void setValue(Boolean value) {
		this.value = value;
	}
	public double getTime() {
		return time;
	}
	public void setTime(double time) {
		this.time = time;
	}

	@Override
	public String toString() {
		//{"type": "home",  "number": "212 555-1234"}

		String string = "{\"check\": \""+ String.valueOf(value) +"\",  \"time\": \""+ time +"\"}";
		return string;
	}

}