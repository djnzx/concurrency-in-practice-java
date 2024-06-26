package ch03.advancedServer.concurrent.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import ch03.advancedServer.parallel.cache.ParallelCache;
import ch03.advancedServer.parallel.log.Logger;
import ch03.advancedServer.concurrent.command.ConcurrentCancelCommand;
import ch03.advancedServer.concurrent.command.ConcurrentCommand;
import ch03.advancedServer.concurrent.command.ConcurrentErrorCommand;
import ch03.advancedServer.concurrent.command.ConcurrentQueryCommand;
import ch03.advancedServer.concurrent.command.ConcurrentReportCommand;
import ch03.advancedServer.concurrent.command.ConcurrentStatusCommand;
import ch03.advancedServer.concurrent.command.ConcurrentStopCommand;
import ch03.advancedServer.concurrent.executor.ServerExecutor;
import ch03.advancedServer.concurrent.executor.ServerTask;

/**
 * Task that executes a request to the concurrent server
 * 
 * @author author
 *
 */
public class RequestTask implements Runnable {

	/**
	 * List with all the connections we have to process
	 */
	private LinkedBlockingQueue<Socket> pendingConnections;

	/**
	 * Executor that will execute the commands
	 */
	private ServerExecutor executor = new ServerExecutor();

	/**
	 * Hashmap that stores the Future instances to control the execution of the
	 * tasks
	 */
	private ConcurrentMap<String, ConcurrentMap<ConcurrentCommand, ServerTask<?>>> taskController;

	/**
	 * Constructor of the class
	 * 
	 * @param pendingConnections
	 *            List to store the connections we have to process
	 * @param taskController
	 *            Hashmap to store the Future instances to control the execution
	 *            of the tasks
	 */
	public RequestTask(LinkedBlockingQueue<Socket> pendingConnections,
			ConcurrentMap<String, ConcurrentMap<ConcurrentCommand, ServerTask<?>>> taskController) {
		this.pendingConnections = pendingConnections;
		this.taskController = taskController;
	}

	@Override
	/**
	 * Method that executes the request
	 */
	public void run() {

		try {
			while (!Thread.currentThread().interrupted()) {
				try {
					Socket clientSocket = pendingConnections.take();
					BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
					String line = in.readLine();

					Logger.sendMessage(line);

					ConcurrentCommand command;

					ParallelCache cache = ConcurrentServer.getCache();
					String ret = cache.get(line);
					if (ret == null) {
						String[] commandData = line.split(";");
						System.out.println("Command: " + commandData[0]);
						switch (commandData[0]) {
						case "q":
							System.out.println("Query");
							command = new ConcurrentQueryCommand(clientSocket, commandData);
							break;
						case "r":
							System.out.println("Report");
							command = new ConcurrentReportCommand(clientSocket, commandData);
							break;
						case "s":
							System.out.println("Status");
							command = new ConcurrentStatusCommand(executor, clientSocket, commandData);
							break;
						case "z":
							System.out.println("Stop");
							command = new ConcurrentStopCommand(clientSocket, commandData);
							break;
						case "c":
							System.out.println("Cancel");
							command = new ConcurrentCancelCommand(clientSocket, commandData);
							break;
						default:
							System.out.println("Error");
							command = new ConcurrentErrorCommand(clientSocket, commandData);
							break;
						}

						ServerTask<?> controller = (ServerTask<?>)executor.submit(command);
						storeContoller(command.getUsername(), controller, command);
					} else {
						PrintWriter out = new PrintWriter(clientSocket.getOutputStream(),true);
						out.println(ret);
						clientSocket.close();
					}

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (InterruptedException e) {
			// No Action Required
		}
	}

	/**
	 * Method that store a Future object in the Hashmap associated to the user
	 * 
	 * @param userName
	 *            Name of the user
	 * @param controller
	 *            Future object that controls the execution of a command
	 * @param command
	 *            Command that is controlled
	 */
	private void storeContoller(String userName, ServerTask<?> controller, ConcurrentCommand command) {
		taskController.computeIfAbsent(userName, k -> new ConcurrentHashMap<>()).put(command, controller);
	}

	/**
	 * Method that shutdown the task
	 */
	public void shutdown() {
		String message="Request Task: "
				+pendingConnections.size()
				+" pending connections.";
		Logger.sendMessage(message);
		executor.shutdown();
	}

	/**
	 * Method that waits for the termination of the executor
	 */
	public void terminate() {
		try {
			executor.awaitTermination(1, TimeUnit.DAYS);
			executor.writeStatistics();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Method that returns the executor
	 * 
	 * @return The executor
	 */
	public ServerExecutor getExecutor() {
		return executor;
	}

}
