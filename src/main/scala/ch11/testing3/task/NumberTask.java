package ch11.testing3.task;

import ch11.testing3.common.Data;

public class NumberTask implements Runnable {

	private Data data;
	
	public NumberTask (Data data) {
		this.data=data;
	}
	
	@Override
	public void run() {

		for (int i=0; i<10; i++) {
			data.increment(10);
		}
	}

}
