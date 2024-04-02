package ch02.simpleserver.concurrent.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import ch02.simpleserver.common.Command;
import ch02.simpleserver.common.ConcurrentErrorCommand;
import ch02.simpleserver.common.ConcurrentQueryCommand;
import ch02.simpleserver.common.ConcurrentReportCommand;
import ch02.simpleserver.common.ConcurrentStatusCommand;
import ch02.simpleserver.common.ConcurrentStopCommand;
import ch02.simpleserver.parallel.cache.ParallelCache;
import ch02.simpleserver.parallel.log.Logger;

/**
 * Task that executes a request to the concurrent server
 * @author author
 *
 */
public class RequestTask implements Runnable {

	/**
	 * Socket to communicate with the client
	 */
	private Socket clientSocket;

	/**
	 * Constructor of the class
	 * @param clientSocket socket to communicate
	 */
	public RequestTask(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}

	@Override
	/**
	 * Method that executes the request
	 */
	public void run() {

		try (PrintWriter out = new PrintWriter(clientSocket.getOutputStream(),
				true);
				BufferedReader in = new BufferedReader(new InputStreamReader(
						clientSocket.getInputStream()));) {

			String line = in.readLine();
			
			Logger.sendMessage(line);
			ParallelCache cache = ConcurrentServer.getCache();
			String ret = cache.get(line);
			
			if (ret == null) {
				Command command;
				

				String[] commandData = line.split(";");
				System.err.println("Command: " + commandData[0]);
				if (commandData[0].equals("q")) {
					System.err.println("Query");
					command = new ConcurrentQueryCommand(commandData);
				} else if (commandData[0].equals("r")) {
					System.err.println("Report");
					command = new ConcurrentReportCommand(commandData);
				} else if (commandData[0].equals("s")) {
					System.err.println("Status");
					command = new ConcurrentStatusCommand(commandData);
				} else if (commandData[0].equals("z")) {
					System.err.println("Stop");
					command = new ConcurrentStopCommand(commandData);
				
				} else {
					System.err.println("Error");
					command = new ConcurrentErrorCommand(commandData);
				}
				ret = command.execute();
				if (command.isCacheable()) {
					cache.put(line, ret);
				}
			} else {
				Logger.sendMessage("Command "+line+" was found in the cache");
			}

			System.err.println(ret);
			out.println(ret);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				clientSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
