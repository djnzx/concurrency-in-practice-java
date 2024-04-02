package ch03.news.rss.reader.advanced;

import java.util.concurrent.RunnableScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * This is the overriden ScheduledThreadPoolExecutor used in the advanced example
 * @author author
 *
 */
public class NewsExecutor extends ScheduledThreadPoolExecutor {

	/**
	 * Constructor of the class
	 * @param corePoolSize
	 */
	public NewsExecutor(int corePoolSize) {
		super(corePoolSize);
	}

	/**
	 * Override the decorateTask to change the task executed by the executor
	 */
	@Override
	protected <V> RunnableScheduledFuture<V> decorateTask(Runnable runnable,
			RunnableScheduledFuture<V> task) {
		ExecutorTask<V> myTask = new ExecutorTask<V>(runnable, null, task, this);
		return myTask;
	}
}
