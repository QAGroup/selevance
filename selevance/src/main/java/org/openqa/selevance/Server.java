package org.openqa.selevance;

import java.io.IOException;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

/**
 * @author Tanmay Sarkar
 *
 */
public class Server extends Plus{
	@BeforeClass
	public void startWindowsServer(){
		System.out.println("Start ------------");
		  CommandLine command = new CommandLine("cmd");
		  command.addArgument("/c");
		  command.addArgument(prop.getProperty("NodeJS"));
		  command.addArgument(prop.getProperty("Appium"));
		  command.addArgument("--address");
		  command.addArgument(prop.getProperty("AppiumIP"));
		  command.addArgument("--port");
		  command.addArgument(prop.getProperty("AppiumPort"));
		  command.addArgument("--full-reset");
		  command.addArgument("--log");
		  command.addArgument(prop.getProperty("AppiumLog"));
		  DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();
		  DefaultExecutor executor = new DefaultExecutor();
		  executor.setExitValue(1);		  
		  try {
			  executor.execute(command, resultHandler);
			  Thread.sleep(8000);
		  } catch (Exception e) {
			  e.printStackTrace();
		  }
	}
	@AfterClass
	public void stopWindowsServer(){		  
		  CommandLine command = new CommandLine("cmd");
		  command.addArgument("/c");
		  command.addArgument("taskkill");
		  command.addArgument("/F");
		  command.addArgument("/IM");
		  command.addArgument("node.exe");		  
		  DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();
		  DefaultExecutor executor = new DefaultExecutor();
		  executor.setExitValue(1);		  
		  try {
			  executor.execute(command, resultHandler);
		  } catch (IOException e) {
			  e.printStackTrace();
		  }
		  System.out.println("Stop ------");
	}
}
