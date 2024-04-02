package ch09.synchronization.barrier;

public class FinishBarrierTask implements Runnable {

	@Override
	public void run() {
		System.out.println("FinishBarrierTask: All the tasks have finished");
	}
}
