package org.openqa.selevance.exception;

import org.apache.log4j.Logger;
import org.testng.SkipException;


/**
 * @author Tanmay Sarkar
 *
 */
public class PageLoadException extends Exception{	
	
	private static final long serialVersionUID = 4126562044156388824L;

	public PageLoadException(int maxTimeOut, String msg){	
		super("Status : "+msg+" Please check your connection. If you are using VPN connection make sure it is connected properly. Connection closed after "+maxTimeOut + " seconds.");
		Logger log = Logger.getLogger("PageLoadException");
		log.error("Status : "+msg+" Please check your connection. If you are using VPN connection make sure it is connected properly. Connection closed after "+maxTimeOut + " seconds.");
		throw new SkipException("Skipping this exception as Browser not loaded properly");
	}

}
