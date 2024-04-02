package ch11.testing3.main;

import ch11.testing3.common.Data;
import ch11.testing3.task.NumberTask;

public class MainNumber {

	public static void main(String[] args) {
		int numTasks=2;
		Data data=new Data();
		
		Thread threads[]=new Thread[numTasks];
		for (int i=0; i<numTasks; i++) {
			threads[i]=new Thread(new NumberTask(data));
			threads[i].start();
		}
		
		for (int i=0; i<numTasks; i++) {
			try {
				threads[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		System.out.println(data.getValue());
	}

}
