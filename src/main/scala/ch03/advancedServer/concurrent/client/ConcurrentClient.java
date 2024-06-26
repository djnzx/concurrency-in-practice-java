package ch03.advancedServer.concurrent.client;

import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadPoolExecutor;

import ch03.advancedServer.wdi.data.WDI;
import ch03.advancedServer.wdi.data.WDIDAO;

/**
 * Class that implements a client of the concurrent server. It implements the
 * Runnable interface. It will executed as an independent thread
 * @author author
 *
 */
public class ConcurrentClient implements Runnable{

	private String username;
	
	private ThreadPoolExecutor executor;
	
	public ConcurrentClient(String username, ThreadPoolExecutor executor) {
		this.username=username;
		this.executor=executor;
	}
	
	public void run() {
		WDIDAO dao = WDIDAO.getDAO();
		List<WDI> data = dao.getData();
		Date globalStart, globalEnd;
		Random randomGenerator = new Random();

		globalStart = new Date();

		for (int i = 0; i < 10; i++) {
				for (int j = 0; j < 9; j++) {
					QueryTask task=new QueryTask(data,username);
					executor.submit(task);
			}
				ReportTask task=new ReportTask(data, username);
				executor.submit(task);
		}

		globalEnd = new Date();
		System.out.println("Total Time: "
				+ ((globalEnd.getTime() - globalStart.getTime()) / 1000)
				+ " seconds.");

	}


}
