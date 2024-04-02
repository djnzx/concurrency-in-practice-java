package ch11.testing2.tc;

import ch11.testing2.common.Data;

import edu.umd.cs.mtc.MultithreadedTestCase;

public class TestClassKo extends MultithreadedTestCase {

	
	private Data data;
	private int amount;
	private int initialData;
	
	public TestClassKo (Data data, int amount) {
		this.amount=amount;
		this.data=data;
		this.initialData=data.getData();
	}
	
	@Override
	public void initialize() {
		super.initialize();
	}

	
	public void threadAdd() {
		System.out.println("Add: Getting the data");
		int value=data.getData();
		waitForTick(2);
		System.out.println("Add: Increment the data");
		value+=amount;
		System.out.println("Add: Set the data");
		data.setData(value);
	}
	
	
	public void threadSub() {
		waitForTick(1);
		System.out.println("Sub: Getting the data");
		int value=data.getData();
		waitForTick(3);
		System.out.println("Sub: Decrement the data");
		value-=amount;
		System.out.println("Sub: Set the data");
		data.setData(value);		
	}
	
	@Override
	public void finish() {
		super.finish();
		assertEquals(initialData, data.getData());
	}



	
	
}
