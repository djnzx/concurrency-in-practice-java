package ch11.testing3.common;

public class Data {
	
	private int value;
	
	public Data() {
		value=0;
	}
	
	public void increment(int amount) {
		value+=amount;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

}
