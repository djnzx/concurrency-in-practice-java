package ch03.advancedServer.serial.command;

import ch03.advancedServer.common.Command;
import ch03.advancedServer.wdi.data.WDIDAO;

/**
 * Class that implements the serial version of the Report command. 
 * Report: The format of this query is: r:codIndicator where codIndicator 
 * is the code of the indicator you want to report
 * @author author
 *
 */
public class ReportCommand extends Command {

	/**
	 * Constructor of the class
	 * @param command String that represents the command
	 */
	public ReportCommand (String [] command) {
		super(command);
	}
	
	@Override
	/**
	 * Method that executes the command
	 */
	public String execute() {
	
		WDIDAO dao=WDIDAO.getDAO();
		return dao.report(command[1]);
	}

}
