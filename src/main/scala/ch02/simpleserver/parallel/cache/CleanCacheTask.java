package ch02.simpleserver.parallel.cache;

import java.util.concurrent.TimeUnit;

/**
 * Task that cleans the cache of items that has not been accessed
 * in the last minutes. Implements the Runnable interface. It will be executed
 * as a thread
 * @author author
 *
 */
public class CleanCacheTask implements Runnable {

	/**
	 * Cache to clean
	 */
	private ParallelCache cache;

	/**
	 * Constructor of the class
	 * @param cache Cache to clean
	 */
	public CleanCacheTask(ParallelCache cache) {
		this.cache = cache;
	}

	@Override
	/**
	 * Main method of the clean task
	 */
	public void run() {
		try {
			while (!Thread.currentThread().interrupted()) {
				TimeUnit.SECONDS.sleep(10);
				cache.cleanCache();
			}
		} catch (InterruptedException e) {

		}
	}

}
